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


import com.exercise.dao.CourseRepository;
import com.exercise.dao.CourseService;
import com.exercise.model.Course;


@SpringBootTest
public class CourseServiceTest {
	@Mock
	CourseRepository courseRepository;
	@InjectMocks
	CourseService courseService;
	@Test
	public void getAllCourseTest() {
		List<Course> list=new ArrayList<Course>();
		Course c1=new Course();
	    c1.setCourseId("CUR001");
	    c1.setCourseName("Java");
	    Course c2=new Course();
	    c2.setCourseId("CUR002");
	    c2.setCourseName("C#");
	    list.add(c1);
		list.add(c2);
	
		when(courseRepository.findAll()).thenReturn(list);
		List<Course> courseList=courseService.getAllCourse();
		assertEquals(2,courseList.size());
		verify(courseRepository, times(1)).findAll();
	}
	@Test
	public void getByCodeTest() {
		List<Course> list=new ArrayList<Course>();
		Course c1=new Course();
	    c1.setCourseId("CUR001");
	    c1.setCourseName("Java");
	    list.add(c1);
		when(courseRepository.findAllByCourseId("CUR001")).thenReturn(list);
		List<Course> courseList=courseService.getCourseById("CUR001");
		assertEquals(1,courseList.size());
	}
	

	@Test
	public void saveTest() {
		Course setCourse=new Course();
		setCourse.setCourseId("CUR001");
		setCourse.setCourseName("Java");
		courseService.addCourse(setCourse);
		verify(courseRepository,times(1)).save(setCourse);
	}
}
