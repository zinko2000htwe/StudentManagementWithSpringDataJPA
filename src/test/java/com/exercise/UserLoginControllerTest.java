package com.exercise;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.dao.UserRepository;
import com.exercise.dao.UserService;
import com.exercise.model.User;



@SpringBootTest
@AutoConfigureMockMvc
public class UserLoginControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	UserService userService;
	@MockBean
	UserRepository userRepo;
	MockHttpSession session=new MockHttpSession();
	@Test
	public void loginViewTest() throws Exception {
		this.mockMvc.perform(get("/login")).andExpect(status().isOk()).andExpect(view().name("LGN001"))
				.andExpect(model().attributeExists("ubean"));

	}

	@Test
	public void setloginTest() throws Exception {
		
		User user = new User();
		user.setUserID("USR001");
		user.setUserName("John");
		user.setUserPassword("123");
		user.setUserConfirmPassword("123");
		user.setUserRole("Admin");
		when(userService.getUserNameandPassword(user.getUserName(), user.getUserPassword())).thenReturn(true);
	
		session.setAttribute("userdata", user.getUserName());
		this.mockMvc.perform(post("/login").param("userName", "John")
				.param("userPassword", "123")
				.session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("MNU001"));
	}
	@Test
	public void setloginTestValidate() throws Exception {
		
		User user = new User();
		user.setUserID("USR001");
		user.setUserName("John");
		user.setUserPassword("123");
		user.setUserConfirmPassword("123");
		user.setUserRole("Admin");
		when(userService.getUserNameandPassword(user.getUserName(), user.getUserPassword())).thenReturn(true);
		this.mockMvc.perform(post("/login").param("userName", "John")
				.param("userPassword", "12345")
				.session(session))
				.andExpect(status().isOk())
				.andExpect(view().name("LGN001"))
				.andExpect(model().attributeExists("error"));
		
	}
	@Test
	public void testdisplayView() throws Exception {
		this.mockMvc.perform(get("/menu"))
		.andExpect(status().isOk())
		.andExpect(view().name("MNU001"));
		
	}
	@Test
	public void logOut() throws Exception {
		this.mockMvc.perform(get("/logout"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/login"));	;
		
	}
}
