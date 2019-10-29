package wolanin.studentToolkit.db;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

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