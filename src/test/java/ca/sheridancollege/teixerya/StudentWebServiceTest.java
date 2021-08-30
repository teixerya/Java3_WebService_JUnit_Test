package ca.sheridancollege.teixerya;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;

import ca.sheridancollege.teixerya.bean.Student;

@SpringBootTest
@AutoConfigureMockMvc
public class StudentWebServiceTest {

	@Autowired
	private MockMvc mockMvc;
	
	
	@Test  //Get a collection of students
	@Order(1)
	public void testGetCollection() throws Exception {
		String uri = "/students";
		//Test the particular uri with a get request
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		
		//Test that it was done successfully
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		//Retrieve the JSON value and convert it into the appropriate object type 
		String content = mvcResult.getResponse().getContentAsString();
		Student[] students = new ObjectMapper().readValue(content, Student[].class);
		
		//Evaluate your result
		assertTrue(students.length > 0);
	}
	

	@Test //Get a single record that exists
	@Order(2)
	public void testGetStudentExists() throws Exception {
		//Specify a specific record to retrieve with a get request
		String uri = "/students/3";
		
		//Test the uri with a get request
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();

		//Test that it was done successfully
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
//		Retrieve the JSON value and convert it
		String content = mvcResult.getResponse().getContentAsString();
		Student student = new ObjectMapper().readValue(content, Student.class);

		assertTrue(student != null);
	}
	
	
	@Test //Check for a record that does not exist
	@Order(3)
	public void testGetStudentDoesntExists() throws Exception {
		//Test a value that doesn't exist
		String uri = "/students/66";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		//Test that it was done successfully
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		//Retrieve the JSON value and convert it
		String content = mvcResult.getResponse().getContentAsString();
		
		//In this case since my API returns null if the record doesn't exist
		//JSON will return an empty string.
		assertTrue(content.contentEquals(""));
	}
	
	
	
	
	@Test //Test for adding a student using post
	@Order(4)
	public void createStudent() throws Exception {

		String uri = "/students";
		
		//Create a Student and convert it to a JSON string.
		Student student = Student.builder().name("Jess").build();
		String studentJson = new ObjectMapper().writeValueAsString(student);

		//Test the uri with a get request
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(studentJson))
				.andReturn();

		//Test that it was done sucessfully
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		//Retrieve the JSON value and convert it
		String content = mvcResult.getResponse().getContentAsString();
		//Check if content is a String "student was added"
		
		//The API returns the index value of the object after it has
		//been added.
		assertTrue(content.equals("student was added"));
	}
	
//	
	@Test
	@Order(5)//Order Not working
	public void testDeleteCollection() throws Exception {
		String uri = "/students";
		//Test the particular uri with a get request
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.delete(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE))
				.andReturn();
		
		//Test that it was done successfully
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		//Retrieve the JSON value and convert it into the appropriate object type 
		String content = mvcResult.getResponse().getContentAsString();
//		Student[] students = new ObjectMapper().readValue(content, Student[].class); //Not expected students
		
		//Evaluate your result
		assertTrue(content.equals("Records deleted"));
	}
	
	@Test //Check for a record that does not exist
//	@Order(6)
	public void testPutStudentExist() throws Exception {
		//Test a value that doesn't exist
		String uri = "/students/3";
		MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get(uri)
				.accept(MediaType.APPLICATION_JSON_VALUE)).andReturn();

		//Create a Student and convert it to a JSON string.
		Student student = Student.builder().name("Jess").grade(76).letterGrade("b").build();
		String studentJson = new ObjectMapper().writeValueAsString(student);
		
		mvcResult = mockMvc.perform(MockMvcRequestBuilders.put(uri)
				.contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(studentJson))
				.andReturn();

		//Test that it was done successfully
		int status = mvcResult.getResponse().getStatus();
		assertEquals(200, status);
		
		//Retrieve the JSON value and convert it
		String content = mvcResult.getResponse().getContentAsString();
		
		//In this case since my API returns null if the record doesn't exist
		//JSON will return an empty string.
		assertTrue(content.contentEquals("Record Edited"));
		
		
	}
		

	
	
}