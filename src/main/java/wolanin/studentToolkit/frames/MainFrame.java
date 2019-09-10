package wolanin.studentToolkit.frames;

import org.hibernate.Session;
import wolanin.studentToolkit.dbHibernate.*;
import wolanin.studentToolkit.notes.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.*;
import java.util.Objects;

import static wolanin.studentToolkit.AppLogic.createFileDir;
import static wolanin.studentToolkit.language.LangProperties.setProperties;


public class MainFrame extends JFrame {
	static final JTabbedPane tabs = new JTabbedPane();
	private final JLabel grades = new JLabel();
	private final JLabel teachers = new JLabel();
	private final JLabel labelClasses = new JLabel();
	private final JLabel exams = new JLabel();
	private final JLabel ownNotes = new JLabel();
	private static final String currentDate = "";
	private static final JLabel classesLabel = new JLabel(currentDate, SwingConstants.CENTER);
	private static final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu(setProperties().getProperty("menu.file"));
	private final JMenu helpMenu = new JMenu(setProperties().getProperty("menu.help"));
	private final JMenuItem exit = new JMenuItem(setProperties().getProperty("menu.exit"));
	private final JMenuItem about = new JMenuItem(setProperties().getProperty("menu.about"));
	private final JToolBar gradesToolbar = new JToolBar();
	private final JToolBar teachersToolbar = new JToolBar();
	private final JToolBar examsToolbar = new JToolBar();
	private final JToolBar classesToolbar = new JToolBar();
	private final JToolBar notesToolbar = new JToolBar();
	public static final JTextArea noteArea = new JTextArea();
	static final JPanel tabPanel = new JPanel();
	public static final JPanel gradesPanel = new JPanel();
	public static final JPanel examPanel = new JPanel();
	public static final JPanel teachersPanel = new JPanel();
	public static final JPanel classesPanel = new JPanel();
	private final JScrollPane gradeScroll = new JScrollPane(gradesPanel);
	private final JScrollPane examScroll = new JScrollPane(examPanel);
	private final JScrollPane teacherScroll = new JScrollPane(teachersPanel);
	public static final JTable gradesTable = new JTable();
	public static final JTable teacherTable = new JTable();
	public static final JTable classesTable = new JTable();
	public static final JTable examTable = new JTable();

	public static final Notes notes = new Notes();
	public static boolean PLlang = true;

	public static Session session;

	public MainFrame() throws IOException {
		try {
			initComponents();
		} catch (IOException e) {
			e.printStackTrace();
		}
		session = HibernateUtil.currentSession();
		addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				GradesDAO g = null;
				try {
					g = new GradesDAO();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				TeacherDAO t = null;
				try {
					t = new TeacherDAO();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				ExamsDAO ex = new ExamsDAO();
				ClassesDAO c = null;
				try {
					c = new ClassesDAO();
				} catch (IOException exc) {
					exc.printStackTrace();
				}
				Objects.requireNonNull(g).showAll(session);
				Objects.requireNonNull(t).showAll(session);
				ex.showAll(session);
				Objects.requireNonNull(c).showAll(session);
			}
		});

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				HibernateUtil.closeSession();
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
		tabs.addTab(setProperties().getProperty("tab.schedule"), null, labelClasses, setProperties().getProperty("tab.tip.schedule"));
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

		labelClasses.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(classesToolbar);
		labelClasses.add(classesToolbar, BorderLayout.LINE_START);
		labelClasses.add(classesLabel, BorderLayout.NORTH);
		classesLabel.setOpaque(true);
		labelClasses.add(classesPanel, BorderLayout.CENTER);
		classesPanel.setLayout(new BorderLayout());
		classesPanel.add(classesTable, BorderLayout.CENTER);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.today"), classesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.tommorow"), classesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.week"), classesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.add"), classesToolbar);
		FormatFrame.createToolbarButton(setProperties().getProperty("schedule.delete"), classesToolbar);

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
