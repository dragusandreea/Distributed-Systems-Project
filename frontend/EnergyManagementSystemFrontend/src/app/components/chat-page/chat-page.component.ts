import {AfterContentChecked, ChangeDetectorRef, Component, OnInit} from '@angular/core';
import jwt_decode from "jwt-decode";
import {UserService} from "../../services/user.service";
import {User} from "../../models/User";
import * as SockJS from "sockjs-client";
import * as Stomp from "stompjs";
import {ChatMessage} from "../../models/ChatMessage";
import {ChatService} from "../../services/chat.service";

@Component({
  selector: 'app-chat-page',
  templateUrl: './chat-page.component.html',
  styleUrls: ['./chat-page.component.css']
})
export class ChatPageComponent implements OnInit, AfterContentChecked {
  currentUsername: string = ""
  currentName: string = ""
  currentUserType: string = ""
  currentUid: string = ""
  otherUserUid: any
  users: User[] = []
  typingUsers: boolean[] = []
  usernames: string[] = []
  typedMessage: string = ""
  orderedAllMessages: ChatMessage[] = []
  socketChat = new SockJS('http://localhost:8085/chat');
  stompClientChat = Stomp.over(this.socketChat);
  socketSeen = new SockJS('http://localhost:8085/notification-seen');
  stompClientSeen = Stomp.over(this.socketSeen);
  socketTyping = new SockJS('http://localhost:8085/notification-typing');
  stompClientTyping = Stomp.over(this.socketTyping);

  constructor(private userService: UserService, private chatService: ChatService, private ref: ChangeDetectorRef) {
  }

  ngAfterContentChecked(): void {
    this.ref.detectChanges();
  }

  ngOnInit(): void {
    this.socketChat = new SockJS('http://localhost:8085/chat');
    this.stompClientChat = Stomp.over(this.socketChat);
    this.stompClientChat.connect({}, (frame) => {
      console.log('Connected: ' + frame);
    })
    this.socketSeen = new SockJS('http://localhost:8085/notification-seen');
    this.stompClientSeen = Stomp.over(this.socketSeen);
    this.stompClientSeen.connect({}, (frame) => {
      console.log('Connected: ' + frame);
    })

    this.socketTyping = new SockJS('http://localhost:8085/notification-typing');
    this.stompClientTyping = Stomp.over(this.socketTyping);
    this.stompClientTyping.connect({}, (frame) => {
      console.log('Connected: ' + frame);
    })

    let token: any = localStorage.getItem('tokenLogin')
    let tokenPayload: any;
    tokenPayload = jwt_decode(token);
    this.currentUserType = tokenPayload.userType
    this.currentUsername = tokenPayload.sub
    this.currentUid = tokenPayload.id

    if (this.currentUserType === 'ADMIN') {
      this.userService.getUsersByUserType('CLIENT').subscribe((res: User[]) => {
        this.users = res
        for (let i = 0; i < res.length; i++) {
          this.usernames[i] = res[i].username
          this.users[i] = res[i]
          this.typingUsers[i] = false
        }
      })
    } else {
      this.userService.getUsersByUserType('ADMIN').subscribe((res: any) => {
        this.users = res
        for (let i = 0; i < res.length; i++) {
          this.usernames[i] = res[i].username
          this.users[i] = res[i]
          this.typingUsers[i] = false
        }
      })
    }
  }

  openChat(username: String) {
    for (let i = 0; i < this.users.length; i++) {
      if (this.users[i].username == username) {
        this.otherUserUid = this.users[i].id
        this.currentName = this.users[i].name
      }
    }

    this.refreshMessages()

    if (this.currentUserType === 'ADMIN') {
      this.stompClientChat.subscribe('/private-chat/' + this.currentUid + '/' + this.otherUserUid, (message: {
        body: string
      }): any => {
        this.refreshMessages()
        this.unshowTyping(this.otherUserUid)
      });

      this.stompClientSeen.subscribe('/private-chat/' + this.currentUid + '/' + this.otherUserUid, (message: {
        body: string
      }): any => {
        this.refreshMessages()
      }, (error: any) => {window.alert(error.message)});

      this.stompClientTyping.subscribe('/private-chat/typing/' + this.currentUid + '/' + this.otherUserUid, (message: {
        body: string
      }): any => {
        this.showTyping(JSON.parse(message.body))
      });


    } else {
      this.stompClientChat.subscribe('/private-chat/' + this.otherUserUid + '/' + this.currentUid, (message: {
        body: string
      }): any => {
        this.refreshMessages()
        this.unshowTyping(this.otherUserUid)
      });

      this.stompClientSeen.subscribe('/private-chat/' + this.otherUserUid + '/' + this.currentUid, (message: {
        body: string
      }): any => {
        this.refreshMessages()
      });

      this.stompClientTyping.subscribe('/private-chat/typing/' + this.otherUserUid + '/' + this.currentUid, (message: {
        body: string
      }): any => {
        this.showTyping(JSON.parse(message.body))
      });
    }
  }

  sendMessage() {
    let chatMessage: ChatMessage = new ChatMessage()
    chatMessage.message = this.typedMessage
    chatMessage.senderUid = this.currentUid
    chatMessage.receiverUid = this.otherUserUid
    if (this.currentUserType == 'ADMIN') {
      this.stompClientChat.send('/private-chat/' + this.currentUid + '/' + this.otherUserUid, {}, JSON.stringify(chatMessage))
      this.refreshMessages()
      this.unshowTyping(this.currentUid)
    } else {
      this.stompClientChat.send('/private-chat/' + this.otherUserUid + '/' + this.currentUid, {}, JSON.stringify(chatMessage))
      this.refreshMessages()
      this.unshowTyping(this.currentUid)
    }

  }

  refreshMessages() {
    this.typedMessage = ""
    this.orderedAllMessages = []
    this.chatService.getMessagesBetweenSenderUidAndReceiverUid(this.currentUid, this.otherUserUid).subscribe((res: ChatMessage[]) => {
      for (let i = 0; i < res.length; i++) {
        this.orderedAllMessages[i] = res[i]
      }
    })

  }

  seen(message: ChatMessage) {
    if(message.receiverUid == this.currentUid && !message.seen) {
      this.chatService.updateSeen(message).subscribe((chatMessage: ChatMessage) => {
        if (this.currentUserType == 'ADMIN') {
          this.stompClientSeen.send('/private-chat/' + this.currentUid + '/' + this.otherUserUid, {}, JSON.stringify(chatMessage))
          this.refreshMessages()
        } else {
          this.stompClientSeen.send('/private-chat/' + this.otherUserUid + '/' + this.currentUid, {}, JSON.stringify(chatMessage))
          this.refreshMessages()
        }

      })
    }

  }

  showTyping(typingUid: string) {
    for(let i = 0; i < this.users.length; i++) {
      if(typingUid == this.users[i].id) {
        this.typingUsers[i] = true
      }
    }
  }

  unshowTyping(typingUid: string) {
    for(let i = 0; i < this.users.length; i++) {
      if(typingUid == this.users[i].id) {
        this.typingUsers[i] = false
      }
    }
  }

  typing(currentUid: string) {
    if (this.currentUserType == 'ADMIN') {
      this.stompClientTyping.send('/private-chat/typing/' + this.currentUid + '/' + this.otherUserUid, {}, JSON.stringify(currentUid))
      //this.refreshMessages()
    } else {
      this.stompClientSeen.send('/private-chat/typing/' + this.otherUserUid + '/' + this.currentUid, {}, JSON.stringify(currentUid))
      //this.refreshMessages()
    }
  }


}
