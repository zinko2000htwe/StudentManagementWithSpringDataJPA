package com.exercise;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import com.exercise.dao.UserRepository;
import com.exercise.dao.UserService;
import com.exercise.model.User;

@SpringBootTest
public class UserServiceTest {
	@Mock
	UserRepository userRepository;
	@InjectMocks
	UserService userService;
	@Test
	public void getAllUserTest() {
	   List<User> list=new ArrayList<User>();
	   User u1=new User();
	   u1.setUserID("USR001");
	   u1.setUserName("Zaw Zaw");
	   u1.setUserEmail("Zaw@gamil.com");
	   u1.setUserPassword("1234");
	   u1.setUserConfirmPassword("1234");
	   u1.setUserRole("Admin");
	   User u2=new User();
	   u2.setUserID("USR002");
	   u2.setUserName("Aye Aye");
	   u1.setUserEmail("aye@gamil.com");
	   u1.setUserPassword("1234");
	   u1.setUserConfirmPassword("1234");
	   u1.setUserRole("Admin");
	    list.add(u1);
		list.add(u2);
	
		when(userRepository.findAll()).thenReturn(list);
		List<User> userList=userService.getAllUsers();
		assertEquals(2,userList.size());
		verify(userRepository, times(1)).findAll();
	}
	@Test
	public void updateTest() {
		
		User u1=new User();
		   u1.setUserID("USR001");
		   u1.setUserName("Zaw Zaw");
		   u1.setUserEmail("Zaw@gamil.com");
		   u1.setUserPassword("1234");
		   u1.setUserConfirmPassword("1234");
		   u1.setUserRole("Admin");
		userService.updateUser(u1);
		verify(userRepository,times(1)).save(u1);
	}
	@Test
	public void saveTest() {
		
		User u1=new User();
		   u1.setUserID("USR001");
		   u1.setUserName("Zaw Zaw");
		   u1.setUserEmail("Zaw@gamil.com");
		   u1.setUserPassword("1234");
		   u1.setUserConfirmPassword("1234");
		   u1.setUserRole("Admin");
		userService.addUser(u1);
		verify(userRepository,times(1)).save(u1);
	}

	@Test
	public void deleteTest() {
		userService.deleteUser("USR001");
		verify(userRepository,times(1)).deleteById("USR001");
	}
	
	
	@Test
	public void getByIdOrNameTest() {
		List<User> list=new ArrayList<User>();
		   User u1=new User();
		   u1.setUserID("USR001");
		   u1.setUserName("Zaw Zaw");
		   u1.setUserEmail("Zaw@gamil.com");
		   u1.setUserPassword("1234");
		   u1.setUserConfirmPassword("1234");
		   u1.setUserRole("Admin");
	    list.add(u1);
		when(userRepository.findAllByUserIDOrUserName("USR001","Zaw Zaw")).thenReturn(list);
		List<User> courseList=userService.getUserByIdOrName("USR001","Zaw Zaw");
		assertEquals(1,courseList.size());
	}
	@Test
	public void getByNameAndPasswordTest() {
		
		when(userRepository.existsByUserNameAndUserPassword("Zaw Zaw","1234")).thenReturn(true);
		boolean courseList=userService.getUserNameandPassword("Zaw Zaw","1234");
		assertEquals(true,courseList);
	}
	@Test
	public void getByIdTest() {
		List<User> list=new ArrayList<User>();
		   User u1=new User();
		   u1.setUserID("USR001");
		   u1.setUserName("Zaw Zaw");
		   u1.setUserEmail("Zaw@gamil.com");
		   u1.setUserPassword("1234");
		   u1.setUserConfirmPassword("1234");
		   u1.setUserRole("Admin");
	         list.add(u1);
		when(userRepository.findAllByUserID("USR001")).thenReturn(u1);
		User courseList=userService.getUserbyUserId("USR001");
		assertEquals("Zaw Zaw",courseList.getUserName());
	}
}
