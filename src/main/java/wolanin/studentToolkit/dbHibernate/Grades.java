package wolanin.studentToolkit.dbHibernate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Grades {

	@Id
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

	public int getEcts() {
		return ects;
	}

	public void setEcts(int ects) {
		this.ects = ects;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public float getGrade() {
		return grade;
	}

	public void setGrade(float grade) {
		this.grade = grade;
	}

	public String getExamKind() {
		return examKind;
	}

	public void setExamKind(String examKind) {
		this.examKind = examKind;
	}
}
