package wolanin.studentToolkit.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.sql.Date;

@SuppressWarnings("ALL")
@Entity
class Exams {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String type;
	private Date date;
	private String hour;
	private int room;

	public Exams() {

	}

	@SuppressWarnings("unused")
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

	String getType() {
		return type;
	}

	void setType(String type) {
		this.type = type;
	}

	Date getDate() {
		return date;
	}

	void setDate(Date date) {
		this.date = date;
	}

	String getHour() {
		return hour;
	}

	void setHour(String hour) {
		this.hour = hour;
	}

	int getRoom() {
		return room;
	}

	void setRoom(int room) {
		this.room = room;
	}
}
