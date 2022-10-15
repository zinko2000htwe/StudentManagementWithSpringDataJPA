package com.exercise.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.UserService;
import com.exercise.model.User;

@Controller
public class UserController {
	@Autowired
	private UserService userService;
	
	@ModelAttribute("User")
	public User getUserBean() {
	return new User();
	}
	
	@GetMapping (value="/")
	public String Login(ModelMap model) {
		return "LGN001";
	}
	

	@GetMapping (value="/user")
	public String displayView(ModelMap model) {
		List<User> userlist=userService.getAllUsers();
		model.addAttribute("userlist",userlist);
		return "USR003";
	}
	
	
	
	@PostMapping(value="/searchuser")
	public String usersearch(@RequestParam("userID") String userID,@RequestParam("userName") String userName,
			ModelMap model) {
		List<User> searchlist = new ArrayList<>();
		 if(userID.isEmpty()&& userName.isEmpty()) {
			searchlist= userService.getAllUsers();
		}
		 else  {
			 searchlist = userService.getUserByIdOrName(userID, userName);
		}
			
		
		model.addAttribute("userlist", searchlist);
		return "USR003";
	}
	
	@GetMapping("/user/add-user")
	public ModelAndView addUser(Model model) {
		
		User uid=new User();
		setId(model,uid);
		
		return new ModelAndView("USR001","user",uid);
	}
	
	private void setId(Model model,User user) {
		List<User> userlist = userService.getAllUsers();
		if(userlist.size() == 0 ) {
	
			user.setUserID("USR001");
		}
		else
		{
			int tempId = Integer.parseInt(userlist.get(userlist.size() - 1).getUserID().substring(3)) + 1;
			String userId = String.format("USR%03d", tempId);
			user.setUserID(userId);
		}
		
	}


	@PostMapping(value="/adduser")
	public ModelAndView addUser(@ModelAttribute("user") @Validated User user, BindingResult bs,
			ModelMap model,HttpSession session) {
		
		
		if(bs.hasErrors()) {
		
			return new ModelAndView("USR001");
		}
		
		if(!user.getUserPassword().equals(user.getUserConfirmPassword()))
		{
			model.addAttribute("error","Password do not match!");
			return new ModelAndView("USR001");
		}
				
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
					Date date = new Date(System.currentTimeMillis());
					String currentdate = formatter.format(date);
					
					session.setAttribute("userdata",user.getUserName() );
					session.setAttribute("date", currentdate);
				    userService.addUser(user);
				    List<User> userlist=userService.getAllUsers();
					model.addAttribute("userlist",userlist);
					model.addAttribute("success", "Successful Register");
					return new ModelAndView("USR003");
			
	}
	
	@GetMapping(value="/setupUpdateUser")
	public  ModelAndView updateUser(@RequestParam ("userId") String userID) {
		return new ModelAndView ("USR002", "User", userService.getUserbyUserId(userID));
	}
	
	@PostMapping(value="/updateuser")
	public String updateUser(@ModelAttribute("User") @Validated User user, BindingResult bs,
		ModelMap model) {
		
			if(bs.hasErrors() ) {
				
				return "USR002";
			}
			if(!user.getUserPassword().equals(user.getUserConfirmPassword()))
			{
				model.addAttribute("error","Password do not match!");
			   return "USR002";
			}
					
						    userService.updateUser(user);
						    List<User> userlist=userService.getAllUsers();
							model.addAttribute("userlist",userlist);
							model.addAttribute("success", "Successful Updated");
							return "USR003";
				
						
				
		}
	
	@GetMapping(value="/deleteUser")
	public String deleteuser(@RequestParam ("userId") String userID,ModelMap model,HttpSession session) {
		userService.deleteUser(userID);
		List<User> userlist=userService.getAllUsers();
		session.removeAttribute("userdata");
		session.invalidate();
		model.addAttribute("userlist",userlist);
		return "USR003";
	}
	
}