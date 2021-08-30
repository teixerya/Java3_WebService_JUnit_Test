package ca.sheridancollege.teixerya.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import ca.sheridancollege.teixerya.bean.Student;
import ca.sheridancollege.teixerya.repository.StudentRepo;

@RestController
public class StudentRestController {

	@Autowired
	private StudentRepo stuRepo;
	
	@GetMapping("/")
	public Student root() {
		Student s = new Student (1,"Jon", 77);
		return s;
	}
	
	@GetMapping("/students")
	public List<Student> getStudents(){
		return stuRepo.getStudents();
	}
	
	@GetMapping("/students/{id}")
	public Student getStudentsById(@PathVariable int id){

		System.out.println(stuRepo.getStudentsById(id));
		return stuRepo.getStudentsById(id);
	}
	

	
	@PostMapping(value="/students", headers = {"Content-type=application/json"})
	public String poststudent(@RequestBody Student student) {
		stuRepo.addStudent(student);
		return "student was added";
	}
	
	@PutMapping(value="/students", headers = {"Content-type=application/json"})
	public String putStudentList(@RequestBody List<Student> students) {
		stuRepo.deleteAllStudents();
		stuRepo.resetCounter();
		for(Student s: students) {
			stuRepo.addStudent(s);
		}
		return "Records Added: " + stuRepo.getStudents().size();
		
	}
	
	
	//PUT-ELEMENT edit element
	@PutMapping(value="/students/{id}", headers = {"Content-type=application/json"})
	public String putStudentsById(@PathVariable int id, @RequestBody Student student) {
//		stuRepo.updateStudents(id,student);
//		stuRepo.deleteStudentByID(id);
//		stuRepo.addStudent(student);
		System.out.println("Path variable ID# " + id);
		System.out.println("student from JSON " + student);

		stuRepo.editStudent(id, student);
		
		return "Record Edited";
	}
	
	
	//DELETE-COLLECTION
	@DeleteMapping(value="/students", headers= {"Content-type=application/json"})
	public String deleteStudentList() {
		stuRepo.deleteAllStudents();
		return "Records deleted";
	}
	
	//DELETE-ELEMENT
	@DeleteMapping(value="/students/{id}",headers = {"Content-type=application/json"})
	public String deleteStudentsById(@PathVariable int id) {
		stuRepo.deleteStudentByID(id);
		return "Student " + id + " deleted";
	}
	
	
	// HTTP methods - Collection
	//GET - return entire collection 				localhost:8080/students
	//POST - add new element into the collection	localhost:8080/students
	//PUT - edit the entire collection				localhost:8080/students
	
	//**DELETE - delete the entire collection			localhost:8080/students
	
	// HTTP methods - Element
	//GET - return a single element by id			localhost:8080/students/id
	//POST - Do nothing 
	
	//**PUT - edit a single element by id				localhost:8080/students/id
	//**DELETE - delete a single element by id 		localhost:8080/students/id
	
	// All URL's should not be a plural noun based on the data
	// the URL should not have descriptive text
	
	

	
}
