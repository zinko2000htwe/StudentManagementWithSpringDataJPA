package com.exercise.controller;



import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.CourseService;
import com.exercise.model.Course;

@Controller
public class CourseController {
	@Autowired
	private CourseService courseService;
	
	@GetMapping (value="/setupaddcourse")
	public ModelAndView addcourse(Model model,Course course) {
		setId(model,course);
		return new ModelAndView ("BUD003", "Course",course); 
	}
	

	private void setId(Model model, Course course) {
		List<Course> courseList = courseService.getAllCourse();
		if (courseList.size() == 0) {
			course.setCourseId("COU001");
		}else {
		int tempId = Integer.parseInt(courseList.get(courseList.size() - 1).getCourseId().substring(3)) + 1;
		String classid = String.format("COU%03d", tempId);
		course.setCourseId(classid);
		}
		
	}

	@PostMapping(value="/addcourse")
	public ModelAndView addCourse(@ModelAttribute("Course") @Validated Course courseBean, BindingResult bs,
			Model model) {
		if(bs.hasErrors()) {
			model.addAttribute("error", "Fill Blanks!");
			return new ModelAndView("BUD003");
			
		}

		else {
				Course course=new Course();
				course.setCourseId(courseBean.getCourseId());
				course.setCourseName(courseBean.getCourseName());
				
				courseService.addCourse(course);
				Course  c=new Course();
				setId(model,c);
				model.addAttribute("success", "Successful Register");
				return new ModelAndView("BUD003","Course",c);		
				}
				
		
			}	
		
}

