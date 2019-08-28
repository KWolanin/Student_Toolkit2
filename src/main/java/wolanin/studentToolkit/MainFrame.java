package wolanin.studentToolkit;

import wolanin.studentToolkit.about.*;
import wolanin.studentToolkit.database.dbConnection;
import wolanin.studentToolkit.frame.FormatFrame;
import wolanin.studentToolkit.frame.FrameLayout;
import wolanin.studentToolkit.schedule.*;
import wolanin.studentToolkit.exam.*;
import wolanin.studentToolkit.grade.*;
import wolanin.studentToolkit.notes.*;
import wolanin.studentToolkit.teacher.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.net.URISyntaxException;
import java.sql.*;
import java.text.ParseException;

import static wolanin.studentToolkit.language.LangProperties.setProperties;


public class MainFrame extends JFrame {
	public static final JTabbedPane tabs = new JTabbedPane();
	private final JLabel grades = new JLabel();
	private final JLabel teachers = new JLabel();
	private final JLabel labelSchedule = new JLabel();
	private final JLabel exams = new JLabel();
	private final JLabel ownNotes = new JLabel();
	private static final String currentDate = "";
	public static final JLabel scheduleLabel = new JLabel(currentDate, SwingConstants.CENTER);
	private static final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu(setProperties().getProperty("menu.file"));
	private final JMenu helpMenu = new JMenu(setProperties().getProperty("menu.help"));
	private final JMenuItem exit = new JMenuItem(setProperties().getProperty("menu.exit"));
	private final JMenuItem about = new JMenuItem(setProperties().getProperty("menu.about"));
	private final JToolBar gradesToolbar = new JToolBar();
	private final JToolBar teachersToolbar = new JToolBar();
	private final JToolBar examsToolbar = new JToolBar();
	private final JToolBar scheduleToolbar = new JToolBar();
	private final JToolBar notesToolbar = new JToolBar();
	public static final JTextArea noteArea = new JTextArea();
	public static final JPanel tabPanel = new JPanel();
	public static final JPanel gradesPanel = new JPanel();
	public static final JPanel examPanel = new JPanel();
	public static final JPanel teachersPanel = new JPanel();
	public static final JPanel schedulePanel = new JPanel();
	private final JScrollPane gradeScroll = new JScrollPane(gradesPanel);
	private final JScrollPane examScroll = new JScrollPane(examPanel);
	private final JScrollPane teacherScroll = new JScrollPane(teachersPanel);
	public static final JTable gradesTable = new JTable();
	public static final JTable teacherTable = new JTable();
	public static final JTable scheduleTable = new JTable();
	public static final JTable examTable = new JTable();
	public static Connection con;

	private static final Exam exam = new Exam();
	private static final Grade grade = new Grade();
	private static final Teacher teacher = new Teacher();
	private static final Schedule schedule = new Schedule();
	private static final Notes notes = new Notes();
	public static final boolean PLlang = true;

	MainFrame() throws IOException {
		try {
			initComponents();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			dbConnection.connectToBase();
		} catch (ClassNotFoundException | SQLException | NullPointerException | IOException e) {
			e.printStackTrace();
		}
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				try {
					grade.showGrades(con);
					teacher.showTeachers(con);
					exam.showExams(con);
					schedule.showWeekSchedule();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		});
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				dbConnection.disconnectToBase(con);
			}
		});
		createFileDir();
		FormatFrame.setFrameCenter(this);
		setVisible(true);
		setResizable(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}

	private void initComponents() throws IOException {
		setIconImage(new ImageIcon("src\\main\\resources\\student.png").getImage());
		setTitle(setProperties().getProperty("app.title"));
		Container contentPane = getContentPane();
		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(exit);
		helpMenu.add(about);
		about.addActionListener(e -> new AboutFrame().setVisible(true));
		exit.addActionListener(e -> System.exit(0));
		FrameLayout.setGroupLayout(contentPane);
		tabs.addTab(setProperties().getProperty("tab.grade"), null, grades, setProperties().getProperty("tab.tip.grade"));
		tabs.addTab(setProperties().getProperty("tab.lecturer"), null, teachers, setProperties().getProperty("tab.tip.lecturer"));
		tabs.addTab(setProperties().getProperty("tab.exam"), null, exams, setProperties().getProperty("tab.tip.exam"));
		tabs.addTab(setProperties().getProperty("tab.schedule"), null, labelSchedule, setProperties().getProperty("tab.tip.schedule"));
		tabs.addTab(setProperties().getProperty("tab.note"), null, ownNotes, setProperties().getProperty("tab.tip.note"));

		grades.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(gradesToolbar);
		grades.add(gradesToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton(setProperties().getProperty("grade.fulllist"), gradesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("grade.average"), gradesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("grade.add"), gradesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("grade.delete"), gradesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("grade.showUnpassed"), gradesToolbar);

		grades.add(gradeScroll, BorderLayout.CENTER);
		gradesPanel.setLayout(new BorderLayout());

		teachers.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(teachersToolbar);
		teachers.add(teachersToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton(setProperties().getProperty("teacher.add"), teachersToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("teacher.delete"), teachersToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("teacher.emailTo"), teachersToolbar);
		teachers.add(teacherScroll, BorderLayout.CENTER);
		teachersPanel.setLayout(new BorderLayout());

		examPanel.setLayout(new BorderLayout());
		exams.setLayout(new BorderLayout());
		exams.add(examScroll, BorderLayout.CENTER);
		FormatFrame.setToolbarSettings(examsToolbar);
		exams.add(examsToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton(setProperties().getProperty("exam.add"), examsToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("exam.delete"), examsToolbar);

		labelSchedule.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(scheduleToolbar);
		labelSchedule.add(scheduleToolbar, BorderLayout.LINE_START);
		labelSchedule.add(scheduleLabel, BorderLayout.NORTH);
		scheduleLabel.setOpaque(true);
		labelSchedule.add(schedulePanel, BorderLayout.CENTER);
		schedulePanel.setLayout(new BorderLayout());
		schedulePanel.add(scheduleTable, BorderLayout.CENTER);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.today"), scheduleToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.tommorow"), scheduleToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.week"), scheduleToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.add"), scheduleToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.delete"), scheduleToolbar);

		ownNotes.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(notesToolbar);
		ownNotes.add(notesToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton(setProperties().getProperty("note.create"), notesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("note.open"), notesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("note.save"), notesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("note.openFolder"), notesToolbar);
		ownNotes.add(noteArea, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(noteArea);
		scrollPane.setHorizontalScrollBar(null);
		ownNotes.add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		noteArea.setLineWrap(true);
	}

	public static void listenerChooser(ActionEvent e) throws IOException {
		String tmp = e.getActionCommand();
		if (setProperties().getProperty("grade.average").equals(tmp)) {
			try {
				grade.calcAverage();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.add").equals(tmp)) {
			grade.addGrade(con);
		} else if (setProperties().getProperty("grade.delete").equals(tmp)) {
			try {
				grade.deleteGrade();
			} catch (NullPointerException | IndexOutOfBoundsException x) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz ocenę!", "Usuwanie oceny", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.fulllist").equals(tmp)) {
			try {
				grade.showGrades(con);
			} catch (SQLException | NullPointerException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("grade.showUnpassed").equals(tmp)) {
			try {
				grade.showFailed();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("teacher.add").equals(tmp)) {
			teacher.addTeacher(con);
		} else if (setProperties().getProperty("teacher.delete").equals(tmp)) {
			try {
				teacher.deleteTeacher(con);
			} catch (NullPointerException | IndexOutOfBoundsException a) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Usuwanie wykładowcy", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("teacher.emailTo").equals(tmp)) {
			try {
				teacher.emailTo();
			} catch (NullPointerException | ArrayIndexOutOfBoundsException n) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Wysyłanie e-maila", JOptionPane.INFORMATION_MESSAGE);
			} catch (IOException | URISyntaxException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("exam.add").equals(tmp)) {
			exam.addExam();
		} else if (setProperties().getProperty("exam.delete").equals(tmp)) {
			try {
				exam.deleteExam(con);
			} catch (NullPointerException | IndexOutOfBoundsException ee) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz zaliczenie!", "Usuwanie zaliczenia", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.today").equals(tmp)) {
			try {
				schedule.showTodaySchedule(con);
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.tommorow").equals(tmp)) {
			try {
				schedule.showTommorowSchedule(con);
			} catch (ParseException | SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.week").equals(tmp)) {
			try {
				schedule.showWeekSchedule();
			} catch (SQLException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("schedule.add").equals(tmp)) {
			schedule.addClasses();
		} else if (setProperties().getProperty("schedule.delete").equals(tmp)) {
			try {
				schedule.deleteClasses();
			} catch (IndexOutOfBoundsException | NullPointerException ex) {
				JOptionPane.showMessageDialog(null, "Najpierw wybierz zajęcia w widoku tygodniowym!", "Usuwanie zajęć", JOptionPane.INFORMATION_MESSAGE);
			} catch (SQLException es) {
				es.printStackTrace();
			}
		} else if (setProperties().getProperty("note.create").equals(tmp)) {
			notes.clearNoteArea();
		} else if (setProperties().getProperty("note.open").equals(tmp)) {
			try {
				notes.openFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("note.save").equals(tmp)) {
			try {
				notes.saveFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("note.openFolder").equals(tmp)) {
			try {
				notes.openDir();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else {
			throw new IllegalStateException("Unexpected value: " + tmp);
		}
	}

	@SuppressWarnings("ignored")
	private void createFileDir() {
		File dir = new File("Notatki");
		if (!dir.exists()) {
			dir.mkdir();
		}
	}
}
