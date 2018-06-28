package sample.springboot.doc.user.service;

import org.springframework.stereotype.Service;

import sample.springboot.doc.user.entity.User;

@Service
public class UserService {

	public boolean login(String username, String password) {
		return "admin".equals(username) && "admin123".equals(password);
	}

	public User findUser(String username) {
		User user = new User();
		user.setId(1);
		user.setUsername("spring boot");
		user.setNickname("Spring Boot Doc 使用指南");
		user.setAge(18);
		user.setGender("F");
		return user;
	}

}
