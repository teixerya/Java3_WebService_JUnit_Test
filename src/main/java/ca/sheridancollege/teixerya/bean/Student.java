package ca.sheridancollege.teixerya.bean;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class Student {

	private int id;
	private String name;
	private int grade;
	private String letterGrade;
	
	
	
	
	public void setGrade(int grade) {
		this.grade = grade;
		this.letterGrade = assignLetter(grade);
		
	}
	
	private String assignLetter(int grade) {
		if(grade >= 80) return "A";
		else if (grade >= 70) return "B";
		else if (grade >= 60) return "C";
		else if (grade >= 50) return "D";
		else return "F";

	}

	public Student(int id, String name, int grade) {
		
		this.id = id;
		this.name = name;
		this.grade = grade;
		this.letterGrade = assignLetter(grade);
	}

}