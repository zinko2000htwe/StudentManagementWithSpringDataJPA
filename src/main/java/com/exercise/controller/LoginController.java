package com.exercise.controller;

import java.sql.Date;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.UserService;
import com.exercise.model.User;

@Controller
public class LoginController {
	@Autowired
	private UserService userService;
	@GetMapping (value = "/login")
	public ModelAndView login() {
		return new ModelAndView("LGN001", "ubean", new User());
	}

	@PostMapping(value = "/login")
	public String setlogin( @RequestParam ("userName") String name, @RequestParam ("userPassword") String password,
			HttpSession session, ModelMap model) {
		Boolean check = userService.getUserNameandPassword(name, password);
		if(check==true) {
		
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date(System.currentTimeMillis());
		String currentdate = formatter.format(date);
		
		session.setAttribute("userdata", name);
		session.setAttribute("date", currentdate);
		return "MNU001";
		}else {
			model.addAttribute("error","Username or Password is Wrong! Please Check!" );
			return "LGN001";
		}
	}

	@GetMapping(value = "/menu")
	public String Menu() {
		return "MNU001";
	}

	@GetMapping(value = "/logout")
	public String Logout( HttpSession session) {
		session.removeAttribute("userdata");
		session.invalidate();
		return "redirect:/login";
	}
}
