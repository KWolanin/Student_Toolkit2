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
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.sql.*;

import static wolanin.studentToolkit.AppLogic.createFileDir;
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

	static final Exam exam = new Exam();
	static final Grade grade = new Grade();
	static final Teacher teacher = new Teacher();
	static final Schedule schedule = new Schedule();
	static final Notes notes = new Notes();
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
					schedule.showWeekSchedule(con);
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


}
