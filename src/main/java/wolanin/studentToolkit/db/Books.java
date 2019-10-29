package wolanin.studentToolkit.db;

import javax.persistence.*;
import java.sql.Date;

@SuppressWarnings("unused")
@Entity
class Books {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private int id;
	private String title;
	private String author;
	private Date borrowed;
	private Date returnTo;
	private boolean isPenalty;
	@Column(name = "penalty")
	private double penaltyValue;

	public Books() {
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	Date getBorrowed() {
		return borrowed;
	}

	public void setBorrowed(Date borrowed) {
		this.borrowed = borrowed;
	}

	Date getReturnTo() {
		return returnTo;
	}

	public void setReturnTo(Date returnTo) {
		this.returnTo = returnTo;
	}

	boolean isPenalty() {
		return isPenalty;
	}

	public void setPenalty(boolean penalty) {
		isPenalty = penalty;
	}

	double getPenaltyValue() {
		return penaltyValue;
	}

	public void setPenaltyValue(double penaltyValue) {
		this.penaltyValue = penaltyValue;
	}
}
