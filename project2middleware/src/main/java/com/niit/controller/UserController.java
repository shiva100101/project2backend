package com.niit.controller;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.niit.dao.UserDao;
import com.niit.model.ErrorClazz;
import com.niit.model.User;

@Controller
public class UserController {
	@Autowired
	private UserDao userDao;

	public UserController() {
		System.out.println("UserController is Instantiated");
	}

	// client -Angular JS client
	// User in JSON
	// convert JSON to JAVA object
	// ? any type,for success type is User, for error type is ErrorClazz
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST)
	public ResponseEntity<?> registerUser(@RequestBody User user) { // 5
		try {
			/*if (!userDao.isUsernameValid(user.getUsername())) { // Duplicate username
				ErrorClazz error = new ErrorClazz(2, "Username already exist...Please choose different username");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.CONFLICT);
			}
			if (!userDao.isEmailValid(user.getEmail())) {
				ErrorClazz error = new ErrorClazz(3, "EmailId already exist...Please choose different EmailId");
				return new ResponseEntity<ErrorClazz>(error, HttpStatus.CONFLICT);
			}*/
			userDao.registerUser(user);
		} catch (Exception e) {
			ErrorClazz error = new ErrorClazz(1, "unable to register user");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.INTERNAL_SERVER_ERROR);
			// failure - response.data=error, response.status=500
		}
		return new ResponseEntity<User>(user, HttpStatus.OK);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ResponseEntity<?> login(@RequestBody User user, HttpSession session) {
		User validUser = userDao.login(user);
		if (validUser == null) {
			ErrorClazz erorrClazz = new ErrorClazz(4, "Invalid username/password");
			return new ResponseEntity<ErrorClazz>(erorrClazz, HttpStatus.UNAUTHORIZED);// ErrorClazz 401
		} else {
			validUser.setOnline(true);
			session.setAttribute("username", validUser.getUsername());
			userDao.updateUser(validUser);
			return new ResponseEntity<User>(validUser, HttpStatus.OK);
		}
	}

	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ResponseEntity<?> logout(HttpSession session) {
		String username = (String) session.getAttribute("username");
		if (username == null) {
			ErrorClazz error = new ErrorClazz(5, "Unauthorised accesss");
			return new ResponseEntity<ErrorClazz>(error, HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUserByUsername(username);//select * from user where username=?
		user.setOnline(false);
		userDao.updateUser(user);//Update user set online=false where username=?
		session.removeAttribute("username");
		session.invalidate();
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	@RequestMapping(value = "/getuser", method = RequestMethod.GET)
	public ResponseEntity<?> getUser(HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null) { // not logged in
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		User user=userDao.getUserByUsername(username);//select from user where username=?
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
	
	@RequestMapping(value="/edituserprofile", method=RequestMethod.PUT)
	public ResponseEntity<?> editUserProfile(@RequestBody User user,HttpSession session){
		String username=(String)session.getAttribute("username");
		if(username==null) {//not logged in
			ErrorClazz error=new ErrorClazz(5,"Unauthorized access");
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.UNAUTHORIZED);
		}
		try {
			userDao.updateUser(user);
		}catch(Exception e) {
			ErrorClazz error=new ErrorClazz(6,e.getMessage());
			return new ResponseEntity<ErrorClazz>(error,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<User>(user,HttpStatus.OK);
	}
}




















