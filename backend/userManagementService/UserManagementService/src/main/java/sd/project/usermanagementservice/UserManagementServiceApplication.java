package sd.project.usermanagementservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class UserManagementServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserManagementServiceApplication.class, args);
	}

//	@Bean
//	CommandLineRunner init(UserService userService) {
//		return args -> {
//			User user = new User();
//			user.setUserType(UserType.ADMIN);
//			user.setUsername("user_main@yahoo.com");
//			user.setPassword("abcd");
//			user.setName("user_main");
//
//			User user2 = new User();
//			user2.setUserType(UserType.CLIENT);
//			user2.setUsername("client_main@yahoo.com");
//			user2.setPassword("abcdefggrf");
//			user2.setName("client_main");
//
//			User savedUser = userService.add(user);
//			System.out.println("Saved user" + savedUser);
//
//			User savedClient = userService.add(user2);
//			System.out.println("Saved client" + savedClient);
//
//			User foundUserById = userService.getById(savedUser.getId());
//			System.out.println("Found user by id" + foundUserById);
//
//			List<User> foundUserByName = userService.getByName(savedUser.getName());
//			System.out.println("Found user by name" + foundUserByName);
//
//			User foundUserByUsername = userService.getByUsername(savedUser.getUsername());
//			System.out.println("Found user by username" + foundUserByUsername);
//
//			List<User> foundUsers = userService.getAll();
//			System.out.println("All users" + foundUsers);
//
//			user.setName("ANAAAA");
//			User updatedUser = userService.update(user);
//			System.out.println("Updated user" + updatedUser);
//
//		};
//		}

}
