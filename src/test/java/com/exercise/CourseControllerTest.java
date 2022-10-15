package com.exercise;

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
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.exercise.dao.CourseRepository;
import com.exercise.dao.CourseService;
import com.exercise.model.Course;

@SpringBootTest
@AutoConfigureMockMvc
public class CourseControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	CourseService courseService;
	@MockBean
	CourseRepository repo;
	@Test
	public void testsetupaddcoursevalidate() throws Exception {
		this.mockMvc.perform(get("/setupaddcourse"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("Course"));
	}
	@Test
	public void testsetupaddcourse() throws Exception {
		List<Course> list=new ArrayList<Course>();
		Course c1 = new Course();
		c1.setCourseId("COU001");
		c1.setCourseName("Java");
        Course c2=new Course();
        c2.setCourseId("COU002");
        c2.setCourseName("C#");
        list.add(c1);
        list.add(c2);
    
        when(courseService.getAllCourse()).thenReturn(list);
		this.mockMvc.perform(get("/setupaddcourse").flashAttr("Course", c1))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("Course"));
	}
	@Test
	public void addCourseValidateTest() throws Exception {
		this.mockMvc.perform(post("/addcourse"))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("error"));
	}

	
	@Test
	public void addCourseOkTest() throws Exception {
		Course c1 = new Course();
		c1.setCourseId("COU001");
		c1.setCourseName("Java");
		courseService.addCourse(c1);
		this.mockMvc.perform(post("/addcourse")
		.flashAttr("Course", c1))
		.andExpect(status().isOk())
		.andExpect(view().name("BUD003"))
		.andExpect(model().attributeExists("success"));
	}
}
