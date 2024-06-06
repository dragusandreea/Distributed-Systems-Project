export class User {
  id: string | undefined;
  name: string = "";
  userType: string = "";
  username: string = "";
  password: string = "";
}

export interface UserDetails {
  isSelected: boolean;
  id:string | undefined;
  name:string;
  userType: string;
  username:string;
  password:string;
  isEdit:boolean;
}

export const UserColumns = [
  {
    key: 'isSelected',
    type: 'isSelected',
    label: '',
  },
  {
    key: 'id',
    type: 'text',
    label: 'id',
    required: true,
  },
  {
    key: 'userType',
    type: 'text',
    label: 'userType',
    required: true,
  },
  {
    key: 'name',
    type: 'text',
    label: 'name',
    required: true,
  },
  {
    key: 'username',
    type: 'text',
    label: 'username',
    required: true,
  },
  {
    key: 'password',
    type: 'text',
    label: 'password',
    required: true,
  },
  {
    key: 'isEdit',
    type: 'isEdit',
    label: '',
  },
];
