package com.exercise.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.exercise.dao.CourseService;
import com.exercise.dao.StudentService;
import com.exercise.model.Student;

@Controller
public class StudentController {
	@Autowired
	private StudentService studentService;
	@Autowired
	private CourseService courseService;
	
	@GetMapping (value="/students")
	public ModelAndView displayView(ModelMap model) {	
		
	 	List<Student> studentlist = studentService.getAllStudents();
	 	model.addAttribute("courselist",courseService.getAllCourse());
		return new ModelAndView ("STU003", "student", studentlist);
	} 
	
	
	@PostMapping(value="/searchstudent")
	public String studentsearch(@RequestParam("studentId") String studentId,@RequestParam("studentName") String studentName, 
			@RequestParam("course") String course, ModelMap model) {
			String stuId= studentId.isBlank()?"@#$%": studentId;
			String stuName=studentName.isBlank()?"@#$%" :studentName;
			String stuCourse=course.isBlank()?"@#$%" : course;
			List<Student> studentlist = new ArrayList<Student>();
			
			
			if(studentId.isBlank() && studentName.isBlank() && course.isBlank()) {
				studentlist=studentService.getAllStudents();
				model.addAttribute("student", studentlist);
				return "STU003";
			}
			else{
				studentlist=studentService.getStudentByIdOrNameOrCourse(stuId, stuName, stuCourse);
				
				
					model.addAttribute("student", studentlist);				
					return"STU003";
				}
				
			
				
		}
	
	

	@GetMapping(value="/setupaddstudent")
	public ModelAndView addstudent(ModelMap model,Student student) {
		setId(model,student);
		model.addAttribute("courselist",courseService.getAllCourse());
		
		return new ModelAndView ("STU001", "student", student);
		
	}
	
	
	private void setId(ModelMap model, Student student) {
		List<Student> studentList = studentService.getAllStudents();
		if (studentList.size() == 0) {
			student.setStudentId("STU001");
		} else {
			int tempId = Integer.parseInt(studentList.get(studentList.size() - 1).getStudentId().substring(3)) + 1;
			String userId = String.format("STU%03d", tempId);
			student.setStudentId(userId);
		}
		
	}


	@PostMapping(value="/addstudent")
	public ModelAndView addstudent(@ModelAttribute("student") @Validated Student student, BindingResult bs, 
			ModelMap model) {
		
		
		if(bs.hasErrors())
		{
			//model.addAttribute("error", "Fill Blanks!");
			return new ModelAndView("STU001");
		}
		
		 Student s=new Student();
				studentService.addStudent(student);
				setId(model,s);
				model.addAttribute("courselist",courseService.getAllCourse());
				model.addAttribute("success", "Successful Register");
				return new ModelAndView("STU001","student",s);
			
		
	}
	
	
	@GetMapping(value="/seemoreStudent")
	public  ModelAndView updateUser(@RequestParam ("studentId") String studentId, ModelMap model, HttpSession session) {		
		Student dto = new Student();
		dto.setStudentId(studentId);
		Student student = studentService.getStudentbyStudentId(studentId);
		
		model.addAttribute("list", courseService.getAllCourse());
		return new ModelAndView ("STU002", "student", student);
	}
	
	
	
	@PostMapping(value="/updatestudent")
	public String updateUser(@ModelAttribute("student") @Validated Student student, BindingResult bs,
		ModelMap model) {
		
			if(bs.hasErrors())
			{
				//model.addAttribute("error", "Fill Blanks!");
				return "STU002";
			}
			
			
					studentService.updateStudent(student);				
					
					model.addAttribute("success", "Successful Update");
					return "redirect:/students";
				
		}
	
	@RequestMapping(value="/deleteStudent", method=RequestMethod.GET)
	public String deletestudent(@RequestParam ("studentId") String studentId,ModelMap model) {
		
		studentService.deleteStudent(studentId);
		return "redirect:/students";
	}
	@GetMapping (value="/resetStudent")
	public ModelAndView resetView(ModelMap model) {	
		Student student=new Student();
		setId(model,student);
		student.setStudentName("");
		student.setDob("");
	 	model.addAttribute("courselist",courseService.getAllCourse());
		return new ModelAndView ("STU001", "student",student );
	} 
}
