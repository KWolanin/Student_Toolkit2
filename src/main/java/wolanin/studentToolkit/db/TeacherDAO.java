package wolanin.studentToolkit.db;

import org.hibernate.Query;
import org.hibernate.Session;
import wolanin.studentToolkit.frames.MainFrame;
import wolanin.studentToolkit.table.TableFormatter;
import wolanin.studentToolkit.frames.TeacherFrame;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Objects;

import static java.awt.Desktop.getDesktop;
import static wolanin.studentToolkit.frames.MainFrame.*;
import static wolanin.studentToolkit.language.LangProperties.setProperties;
import static wolanin.studentToolkit.table.TableFormatter.setTableModelProp;
import static wolanin.studentToolkit.table.TableFormatter.tableModel;
import static wolanin.studentToolkit.frames.TeacherFrame.*;

public class TeacherDAO implements HibernateDBFlow {

	private final String[] columnNames = new String[]{
			setProperties().getProperty("table.firstName"),
			setProperties().getProperty("table.lastName"),
			setProperties().getProperty("table.email"),
			setProperties().getProperty("table.academicDegree")};

	public TeacherDAO() throws IOException {
	}

	@Override
	public void showAll(Session session) {
		setTableModelProp(columnNames);
		@SuppressWarnings("unchecked")
		List<Teachers> teachers = (List<Teachers>) session.createQuery("from Teachers").list();
		for (Teachers value : teachers) {
			String firstName = value.getFirstName();
			String lastName = value.getLastName();
			String email = value.getEmail();
			String title = value.getTitle();
			String[] data = {firstName, lastName, email, title};
			tableModel.addRow(data);
		}
		TableFormatter.setTableProp(teachersPanel, teacherTable, tableModel);
	}

	@Override
	public void delete(Session session) {
		int row = teacherTable.getSelectedRow();
		String selected = "" + MainFrame.teacherTable.getValueAt(row, 2);
		Query query = session.createQuery("delete from Teachers where email=:email");
		query.setParameter("email", selected);
		query.executeUpdate();
		showAll(session);
	}

	@Override
	public void add(Session session) throws IOException {
		new TeacherFrame().setVisible(true);
		showAll(session);
	}

	public static void saveToBase(Session session) throws IOException {
		String firstName = firstNameField.getText();
		String lastName = lastNameField.getText();
		String title = Objects.requireNonNull(titlesCombo.getSelectedItem()).toString();
		String email = emailField.getText();
		if (firstNameField.getText().equals("") | lastNameField.getText().equals("") | emailField.getText().equals("")) {
			JOptionPane.showMessageDialog(null, setProperties().getProperty("badInputMsg"),
					setProperties().getProperty("add.title.teachers"), JOptionPane.INFORMATION_MESSAGE);
		} else {
			session.beginTransaction();
			Teachers newTeacher = new Teachers();
			newTeacher.setFirstName(firstName);
			newTeacher.setLastName(lastName);
			newTeacher.setTitle(title);
			newTeacher.setEmail(email);
			session.save(newTeacher);
			session.getTransaction().commit();
		}

	}

	public void emailTo() throws URISyntaxException, IOException {
		int row = MainFrame.teacherTable.getSelectedRow();
		String emailFromBase = "" + MainFrame.teacherTable.getValueAt(row, 2);
		String mailto = "mailto:";
		String email = mailto + emailFromBase;
		Desktop desktop = getDesktop();
		desktop.mail(new URI(email));
	}
}


