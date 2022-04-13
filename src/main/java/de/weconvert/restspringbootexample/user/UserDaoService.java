package de.weconvert.restspringbootexample.user;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.stereotype.Component;

@Component
public class UserDaoService {

	private static List<User> users = new ArrayList<>();
	
	private static Integer usersCount = 3;
	
	static {
		users.add(new User(1, "Adam", new Date()));
		users.add(new User(2, "Adam", new Date()));
		users.add(new User(3, "Adam", new Date()));
	}
	
	 public List<User> findAll() {
		 return users;
	 }

	 public User save(User user) {
		 if(user.getId() == null) {
			 user.setId(++usersCount);
		 }
		 users.add(user);
		 return user;
	 }
	
	 public User findOne(Integer id) {
		 for(User user : users) {
			 if(user.getId()== id) {
				 return user;
			 }
		 }
		 return null;
	 }
	
}
