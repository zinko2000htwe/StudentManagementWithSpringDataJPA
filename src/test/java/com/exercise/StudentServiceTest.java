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

import com.exercise.dao.StudentRepository;
import com.exercise.dao.StudentService;
import com.exercise.model.Course;
import com.exercise.model.Student;
import com.exercise.model.User;



@SpringBootTest
public class StudentServiceTest {
	@Mock
	StudentRepository studentRepository;
	@InjectMocks
	StudentService studentService;
	@Test
	public void getAllAtudentTest() {
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
	    when(studentRepository.findAll()).thenReturn(list);
		List<Student> courseList=studentService.getAllStudents();
		assertEquals(1,courseList.size());
		verify(studentRepository, times(1)).findAll();
	}
	
	@Test
	public void updateTest() {
		
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
        studentService.updateStudent(s1);
		verify(studentRepository,times(1)).save(s1);
	}
	@Test
	public void saveTest() {
		
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
		studentService.addStudent(s1);
		verify(studentRepository,times(1)).save(s1);
	}

	@Test
	public void deleteTest() {
		studentService.deleteStudent("STU001");
		verify(studentRepository,times(1)).deleteStudentById("STU001");
		
	}
	
	@Test
	public void getByIdOrNameorCourseTest() {
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
		when(studentRepository.findDistinctByStudentIdContainingOrStudentNameContainingOrCourse_CourseNameContaining("STU001","Mg Mg","Java")).thenReturn(list);
		List<Student> courseList=studentService.getStudentByIdOrNameOrCourse("STU001","Mg Mg","Java");
		assertEquals(1,courseList.size());
	}
	@Test
	public void getByIdTest() {
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
		when(studentRepository.findByStudentId("STU001")).thenReturn(s1);
		Student courseList=studentService.getStudentbyStudentId("STU001");
		assertEquals("Mg Mg",courseList.getStudentName());
	}
}
