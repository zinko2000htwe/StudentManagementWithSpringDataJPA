package com.exercise;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.dao.CourseService;
import com.exercise.dao.StudentService;
import com.exercise.model.Course;
import com.exercise.model.Student;


@SpringBootTest
@AutoConfigureMockMvc
public class StudentControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	StudentService studentService;

	@MockBean
	CourseService courseService;

	@Test
	public void studentdisplayViewTest() throws Exception {
		studentService.getAllStudents();
		this.mockMvc.perform(get("/students")).andExpect(status().isOk()).andExpect(view().name("STU003"))
				.andExpect(model().attributeExists("student"));
	}

	@Test
	public void studentsearchBlankTest() throws Exception {
		List<Student> studentlist = new ArrayList<>();
		List<Course> courselist = new ArrayList<>();
		Student s1 = new Student();
		s1.setStudentId("STU001");
		s1.setStudentName("Jone");
		s1.setDob("22.11.2008");
		s1.setGender("Male");
		s1.setPhone("09111112232");
		Course c1 = new Course();
		c1.setCourseId("COU001");
		c1.setCourseName("Java");
		courselist.add(c1);
		s1.setCourse(courselist);
		studentlist.add(s1);
		when(studentService.getAllStudents()).thenReturn(studentlist);
		this.mockMvc.perform(post("/searchstudent").param("studentId", "").param("studentName", "").param("course", ""))
				.andExpect(status().isOk()).andExpect(view().name("STU003"))
				.andExpect(model().attributeExists("student"));
	}

	@Test
	public void studentsearchTest() throws Exception {
		List<Student> studentlist = new ArrayList<>();
		List<Course> courselist = new ArrayList<>();
		Student s1 = new Student();
		s1.setStudentId("STU001");
		s1.setStudentName("Jone");
		s1.setDob("22.11.2008");
		s1.setGender("Male");
		s1.setPhone("09111112232");
		Course c1 = new Course();
		c1.setCourseId("COU001");
		c1.setCourseName("Java");
		courselist.add(c1);
		s1.setCourse(courselist);
		studentlist.add(s1);
		when(studentService.getStudentByIdOrNameOrCourse("STU001", "Jone", "Java")).thenReturn(studentlist);
		this.mockMvc
				.perform(post("/searchstudent").param("studentId", "STU001").param("studentName", "Jone")
						.param("course", "Java"))
				.andExpect(status().isOk()).andExpect(view().name("STU003"))
				.andExpect(model().attributeExists("student"));
	}

	@Test
	public void setupaddstudentTest() throws Exception {
		List<Student> list=new ArrayList<Student>();
		List<Course> course=new ArrayList<Course>();
		Course cou=new Course();
		cou.setCourseName("Java");
		
		course.add(cou);
		
		Student s1=new Student();
	    s1.setStudentId("STU001");
	    s1.setStudentName("Mg Mg");
	    s1.setDob("12/2/2000");
	    s1.setEducation("IT");
	    s1.setGender("Male");
	    s1.setPhone("12345");
	    s1.setCourse(course);
	    list.add(s1);
	    when(studentService.getAllStudents()).thenReturn(list);
		this.mockMvc.perform(get("/setupaddstudent").flashAttr("student", s1))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"))
	    .andExpect(model().attributeExists("student"));
	}

	@Test
	public void addstudentValidateTest() throws Exception {
		this.mockMvc.perform(post("/addstudent"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"));
       
	}


	@Test
	public void addstudentOkTest() throws Exception {
		List<Course> courselist = new ArrayList<>();
		Student s1 = new Student();
		s1.setStudentId("STU001");
		s1.setStudentName("Jone");
		s1.setDob("22.11.2008");
		s1.setGender("Male");
		s1.setPhone("09111112232");
		s1.setEducation("IT Diploma");
		Course c1 = new Course();
		c1.setCourseId("COU001");
		c1.setCourseName("Java");
		courselist.add(c1);
		s1.setCourse(courselist);
		this.mockMvc.perform(post("/addstudent")
				.flashAttr("student", s1))
				.andExpect(status().is(200))
				.andExpect(view().name("STU001"))
				.andExpect(model().attributeExists("success"));
	}

	@Test
	public void setupupdateStudentTest() throws Exception {
		List<Course> courselist = new ArrayList<>();
		when(courseService.getAllCourse()).thenReturn(courselist);
		when(studentService.getStudentbyStudentId("STU001")).thenReturn(new Student());
		this.mockMvc.perform(get("/seemoreStudent").param("studentId", "STU001"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU002"))
		.andExpect(model().attributeExists("student"));
	}

	@Test
	public void updateStudentValidateTest() throws Exception {
		this.mockMvc.perform(post("/updatestudent"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU002"));
	}

	@Test
	public void updateStudentOkTest() throws Exception {
		List<Course> courselist = new ArrayList<>();
		Student s1 = new Student();
		s1.setStudentId("STU001");
		s1.setStudentName("Jone");
		s1.setDob("22.11.2008");
		s1.setGender("Male");
		s1.setPhone("09111112232");
		s1.setEducation("IT Diploma");
		Course c1 = new Course();
		c1.setCourseId("COU001");
		c1.setCourseName("Java");
		courselist.add(c1);
		s1.setCourse(courselist);
		this.mockMvc.perform(post("/updatestudent").flashAttr("student", s1))
		.andExpect(status().is(302))	
		.andExpect(redirectedUrl("/students"));
	}
	
	
	@Test
	public void deletestudentTest() throws Exception {
		this.mockMvc.perform(get("/deleteStudent").param("studentId", "STU001"))
		.andExpect(status().is(302))
		.andExpect(redirectedUrl("/students"));
	}
	
	@Test
	public void resetViewTest() throws Exception {
		courseService.getAllCourse();
		this.mockMvc.perform(get("/resetStudent"))
		.andExpect(status().isOk())
		.andExpect(view().name("STU001"))
		.andExpect(model().attributeExists("courselist"));
	}


}
