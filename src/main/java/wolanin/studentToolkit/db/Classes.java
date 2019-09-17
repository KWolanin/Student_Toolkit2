package wolanin.studentToolkit.db;

import javax.persistence.*;

@Entity
class Classes {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String name;
	private String dayofweek;
	private String startHour;
	private String endHour;
	private int room;

	public Classes() {
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

	String getDayofweek() {
		return dayofweek;
	}

	void setDayofweek(String dayofweek) {
		this.dayofweek = dayofweek;
	}

	String getStartHour() {
		return startHour;
	}

	void setStartHour(String startHour) {
		this.startHour = startHour;
	}

	String getEndHour() {
		return endHour;
	}

	void setEndHour(String endHour) {
		this.endHour = endHour;
	}

	int getRoom() {
		return room;
	}

	void setRoom(int room) {
		this.room = room;
	}
}