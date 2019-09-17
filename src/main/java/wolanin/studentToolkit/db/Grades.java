package wolanin.studentToolkit.db;

import javax.persistence.*;

@Entity
@Table(name = "grades")
class Grades {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private int ects;
	private String type;
	private float grade;
	private String examKind;

	public Grades() {
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	int getEcts() {
		return ects;
	}

	void setEcts(int ects) {
		this.ects = ects;
	}

	String getType() {
		return type;
	}

	void setType(String type) {
		this.type = type;
	}

	float getGrade() {
		return grade;
	}

	void setGrade(float grade) {
		this.grade = grade;
	}

	String getExamKind() {
		return examKind;
	}

	void setExamKind(String examKind) {
		this.examKind = examKind;
	}
}
