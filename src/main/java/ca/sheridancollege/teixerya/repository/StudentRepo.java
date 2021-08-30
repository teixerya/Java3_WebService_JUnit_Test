package ca.sheridancollege.teixerya.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import ca.sheridancollege.teixerya.bean.Student;



@Repository
public class StudentRepo {


	@Autowired
	private NamedParameterJdbcTemplate jdbc;

	public void addStudent(Student student) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();

		String query = " INSERT INTO students (name, grade, letter_grade)"
				+ " VALUES "+
				"(:name, :grade, :lg)";
		parameters.addValue("name", student.getName());
		parameters.addValue("grade", student.getGrade());
		parameters.addValue("lg", student.getLetterGrade());
		
//		String query = " INSERT INTO students (name) VALUES (:name)";
//		parameters.addValue("name", student.getName());


		System.out.println("\naddStudent method was called");
		jdbc.update(query, parameters);

	}


	
	public ArrayList<Student> getStudents(){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM students ";

		ArrayList<Student> students = (ArrayList<Student>)
				jdbc.query(query, new BeanPropertyRowMapper<Student>(Student.class));
//		jdbc.update(query, parameters);q
		System.out.println("\ngetStudents method was called");
		
		return students;
		
		
	}
	
	
	public Student getStudentsById(int id){
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "SELECT * FROM students WHERE id=:id";
		parameters.addValue("id", id);
		ArrayList<Student> students =  (ArrayList<Student>)
				jdbc.query(query, parameters, new BeanPropertyRowMapper<Student>(Student.class));
		
		System.out.println("\ngetStudentByID method was called");
		
		
		
//		if(students != null) {
//			System.out.println(students.get(0));
//			
//			return students.get(0);
//		}else {
//		
//		return null;
//		}
		
		if(students.isEmpty()) {
			return null;
		}else {
		
		return students.get(0);
		}
		

	}
	
	
	public void deleteAllStudents() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "delete FROM students";
		
		System.out.println("\ndeleteAllStudents method was called");
		jdbc.update(query, parameters);
	}
	
	public void deleteStudentByID(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "delete from students where id=:id";
		parameters.addValue("id", id);
		jdbc.update(query, parameters);
		System.out.println("\ndeleteStudentByID method was called");
		
	}
	
	
	
	public void editStudent(int id2, Student student) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "UPDATE students SET id=:id, name=:name, grade=:grade,"
				+ " letter_grade=:letter_grade WHERE id=:id";
		

		
		parameters.addValue("id", id2);
		parameters.addValue("name", student.getName());
		parameters.addValue("grade", student.getGrade());
		parameters.addValue("letter_grade", student.getLetterGrade());
		
		jdbc.update(query, parameters);
		System.out.println("\nRepo method editStudent was called with ID# "+ id2);
		
		
	}
	
	

	
	public void resetCounter() {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "ALTER TABLE students ALTER COLUMN id RESTART WITH 1";
		jdbc.update(query, parameters);
	}
	
	public void resetCounter(int id) {
		MapSqlParameterSource parameters = new MapSqlParameterSource();
		String query = "ALTER TABLE students ALTER COLUMN id RESTART WITH 1";
		jdbc.update(query, parameters);
	}
	
	
	//Delete all
	//Delete by id
	// Edit all
	//Edit by id
	
	
}















