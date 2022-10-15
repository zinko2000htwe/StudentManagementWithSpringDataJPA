package com.exercise;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.dao.UserRepository;
import com.exercise.dao.UserService;
import com.exercise.model.Course;
import com.exercise.model.User;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;

	@MockBean
	UserRepository userRepo;

	@Test
	public void LoginTest() throws Exception {
		this.mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("LGN001"));
	}

	@Test
	public void displayUserViewTest() throws Exception {
		List<User> userlist = new ArrayList<>();
		User user1 = new User();
		user1.setUserID("USR001");
		user1.setUserName("John");
		user1.setUserPassword("123");
		user1.setUserConfirmPassword("123");
		user1.setUserRole("Admin");
		User user2 = new User();
		user2.setUserID("USR002");
		user2.setUserName("Mike");
		user2.setUserPassword("123");
		user2.setUserConfirmPassword("123");
		user2.setUserRole("User");
		userlist.add(user1);
		userlist.add(user2);
		when(userService.getAllUsers()).thenReturn(userlist);
		this.mockMvc.perform(get("/user")).andExpect(status().isOk()).andExpect(view().name("USR003"))
				.andExpect(model().attributeExists("userlist"));
	}

	@Test
	public void usersearchTest() throws Exception {
		List<User> userlist = new ArrayList<>();
		User user1 = new User();
		user1.setUserID("USR001");
		user1.setUserName("John");
		user1.setUserPassword("123");
		user1.setUserConfirmPassword("123");
		user1.setUserRole("Admin");
		userlist.add(user1);
		when(userService.getAllUsers()).thenReturn(userlist);
		this.mockMvc.perform(post("/searchuser").param("userID", "").param("userName", ""))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"))
		.andExpect(model().attributeExists("userlist"));
		when(userService.getUserByIdOrName("USR001", "John")).thenReturn(userlist);
		this.mockMvc.perform(post("/searchuser").param("userID", "USR001").param("userName", "John"))
				.andExpect(status().isOk())
				.andExpect(view().name("USR003"))
				.andExpect(model().attributeExists("userlist"));
	}

	@Test
	public void setupaddUserTest() throws Exception {
		
		List<User> list=new ArrayList<User>();
		User u1=new User();
		u1.setUserID("USR001");
		u1.setUserName("Zaw Zaw");
		u1.setUserEmail("zaw@gmail.com");
		u1.setUserPassword("1234");
		u1.setUserConfirmPassword("1234");
		u1.setUserRole("Admin");
		
        list.add(u1);
        
        when(userService.getAllUsers()).thenReturn(list);
		this.mockMvc.perform(get("/user/add-user").flashAttr("user", u1))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("user"));
	}


	@Test
	public void addUserValidateTest() throws Exception {
		this.mockMvc.perform(post("/adduser"))
		.andExpect(status().isOk())
		.andExpect(view().name("USR001"));
	
	}


	@Test

	public void addUserPasswordFalseTest() throws Exception {
		
		User user1 = new User();
		user1.setUserID("USR001");
		user1.setUserName("John");
		user1.setUserEmail("zin@gmail.com");
		user1.setUserPassword("123");
		user1.setUserConfirmPassword("12345");
		user1.setUserRole("Admin");
		this.mockMvc.perform(post("/adduser").flashAttr("user", user1))
		.andExpect(status().is(200))
		.andExpect(view().name("USR001"))
		.andExpect(model().attributeExists("error"));
	}

	@Test
	public void addUserPasswordTrueTest() throws Exception {
		User user1 = new User();
		user1.setUserID("USR001");
		user1.setUserName("John");
		user1.setUserEmail("zin@gmail.com");
		user1.setUserPassword("123");
		user1.setUserConfirmPassword("123");
		user1.setUserRole("Admin");
		this.mockMvc.perform(post("/adduser").flashAttr("user", user1))
		         .andExpect(status().is(200))
				.andExpect(view().name("USR003"))
				.andExpect(model().attributeExists("success"));
	}
	@Test
	public void setupUpdateUserTest() throws Exception {
		when(userService.getUserbyUserId("USR001")).thenReturn(new User());
		this.mockMvc.perform(get("/setupUpdateUser").param("userId", "USR001"))
		.andExpect(status().isOk())
	    .andExpect(view().name("USR002"))
	    .andExpect(model().attributeExists("User"));
	}

	@Test
	public void updateUserValidateTest() throws Exception {
		this.mockMvc.perform(post("/updateuser")).andExpect(status().isOk())
		.andExpect(view().name("USR002"));
		
	}

	@Test
	public void updateUserPasswordFalseTest() throws Exception {
		User user1 = new User();
		user1.setUserID("USR001");
		user1.setUserName("John");
		user1.setUserEmail("zin@gmail.com");
		user1.setUserPassword("123");
		user1.setUserConfirmPassword("111");
		user1.setUserRole("Admin");
		this.mockMvc.perform(post("/updateuser").flashAttr("User", user1))
		.andExpect(status().isOk())
		.andExpect(view().name("USR002"));
	}


	@Test
	public void updateUserPasswordTrueTest() throws Exception {
		User user1 = new User();
		user1.setUserID("USR001");
		user1.setUserName("John");
		user1.setUserEmail("zin@gmail.com");
		user1.setUserPassword("123");
		user1.setUserConfirmPassword("123");
		user1.setUserRole("Admin");
		this.mockMvc.perform(post("/updateuser").flashAttr("User", user1))
		.andExpect(status().isOk())
		.andExpect(view().name("USR003"));
		
	}

	@Test
	public void deleteuserTest() throws Exception {
		this.mockMvc.perform(get("/deleteUser").param("userId", "userID")).andExpect(status().isOk())
				.andExpect(view().name("USR003"));
	}
}
