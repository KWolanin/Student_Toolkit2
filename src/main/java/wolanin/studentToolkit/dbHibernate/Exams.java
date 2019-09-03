package wolanin.studentToolkit.dbHibernate;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
class Exams {

	@Id
	private int id;
	private String name;
	private String type;
	private String date;
	private String hour;
	private int room;

	public Exams() {

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

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getHour() {
		return hour;
	}

	public void setHour(String hour) {
		this.hour = hour;
	}

	public int getRoom() {
		return room;
	}

	public void setRoom(int room) {
		this.room = room;
	}
}
