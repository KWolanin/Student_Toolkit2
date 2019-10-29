package wolanin.studentToolkit.frames;

import org.hibernate.Session;
import wolanin.studentToolkit.db.*;
import wolanin.studentToolkit.notes.Notes;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.util.Objects;

import static wolanin.studentToolkit.NoteLogic.createFileDir;
import static wolanin.studentToolkit.frames.ComponentCreator.createToolbarButton;
import static wolanin.studentToolkit.frames.ComponentCreator.setToolbarSettings;
import static wolanin.studentToolkit.language.LangProperties.setProperties;


public class MainFrame extends JFrame {
	static final JTabbedPane tabs = new JTabbedPane();
	private final JLabel grades = new JLabel();
	private final JLabel teachers = new JLabel();
	private final JLabel labelClasses = new JLabel();
	private final JLabel exams = new JLabel();
	private final JLabel ownNotes = new JLabel();
	private final JLabel books = new JLabel();
	private static final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu(setProperties().getProperty("menu.file"));
	private final JMenu helpMenu = new JMenu(setProperties().getProperty("menu.help"));
	private final JMenuItem settings = new JMenuItem(setProperties().getProperty("settings.title"));
	private final JMenuItem exit = new JMenuItem(setProperties().getProperty("menu.exit"));
	private final JMenuItem about = new JMenuItem(setProperties().getProperty("menu.about"));
	private final JToolBar gradesToolbar = new JToolBar("gradesToolbar");
	private final JToolBar teachersToolbar = new JToolBar("teachersToolbar");
	private final JToolBar examsToolbar = new JToolBar("examsToolbar");
	private final JToolBar classesToolbar = new JToolBar("classesToolbar");
	private final JToolBar notesToolbar = new JToolBar("notesToolbar");
	private final JToolBar booksToolbar = new JToolBar("booksToolbar");
	public static final JTextArea noteArea = new JTextArea();
	static final JPanel tabPanel = new JPanel();
	public static final JPanel gradesPanel = new JPanel();
	public static final JPanel examPanel = new JPanel();
	public static final JPanel teachersPanel = new JPanel();
	public static final JPanel classesPanel = new JPanel();
	public static final JPanel booksPanel = new JPanel();
	private final JScrollPane gradeScroll = new JScrollPane(gradesPanel);
	private final JScrollPane examScroll = new JScrollPane(examPanel);
	private final JScrollPane teacherScroll = new JScrollPane(teachersPanel);
	private final JScrollPane booksScroll = new JScrollPane(booksPanel);
	public static final JTable gradesTable = new JTable();
	public static final JTable teacherTable = new JTable();
	public static final JTable classesTable = new JTable();
	public static final JTable examTable = new JTable();
	public static final JTable bookTable = new JTable();

	public static Notes notes;

	static {
		try {
			notes = new Notes();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static boolean isPolishSet = true;
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
				BooksDAO b = null;
				try {
					b = new BooksDAO();

				} catch (IOException exc) {
					exc.printStackTrace();
				}
				try {
					Objects.requireNonNull(b).showAll(session);

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
		ComponentCreator.setFrameCenter(this);
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
		menuBar.add(settings);
		settings.addActionListener(e -> {
			try {
				new SettingsFrame().setVisible(true);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		fileMenu.add(exit);
		helpMenu.add(about);
		about.addActionListener(e -> {
			try {
				new AboutFrame().setVisible(true);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		exit.addActionListener(e -> {
			HibernateUtil.closeSession();
			System.exit(0);
		});
		LayoutSetter.setGroupLayout(contentPane);
		tabs.addTab(setProperties().getProperty("tab.grade"), null, grades, setProperties().getProperty("tab.tip.grade"));
		tabs.addTab(setProperties().getProperty("tab.lecturer"), null, teachers, setProperties().getProperty("tab.tip.lecturer"));
		tabs.addTab(setProperties().getProperty("tab.exam"), null, exams, setProperties().getProperty("tab.tip.exam"));
		tabs.addTab(setProperties().getProperty("tab.schedule"), null, labelClasses, setProperties().getProperty("tab.tip.schedule"));
		tabs.addTab(setProperties().getProperty("tab.note"), null, ownNotes, setProperties().getProperty("tab.tip.note"));
		tabs.addTab(setProperties().getProperty("tab.book"), null, books, setProperties().getProperty("tab.tip.book"));

		grades.setLayout(new BorderLayout());
		setToolbarSettings(gradesToolbar);
		grades.add(gradesToolbar, BorderLayout.LINE_START);
		createToolbarButton("grade.fulllist", gradesToolbar);
		createToolbarButton("grade.average", gradesToolbar);
		createToolbarButton("grade.add", gradesToolbar);
		createToolbarButton("grade.delete", gradesToolbar);
		createToolbarButton("grade.showUnpassed", gradesToolbar);
		grades.add(gradeScroll, BorderLayout.CENTER);
		gradesPanel.setLayout(new BorderLayout());

		teachers.setLayout(new BorderLayout());
		setToolbarSettings(teachersToolbar);
		teachers.add(teachersToolbar, BorderLayout.LINE_START);

		createToolbarButton("teacher.add", teachersToolbar);
		createToolbarButton("teacher.delete", teachersToolbar);
		createToolbarButton("teacher.emailTo", teachersToolbar);
		teachers.add(teacherScroll, BorderLayout.CENTER);
		teachersPanel.setLayout(new BorderLayout());

		books.setLayout(new BorderLayout());
		setToolbarSettings(booksToolbar);
		books.add(booksToolbar, BorderLayout.LINE_START);
		createToolbarButton("book.add", booksToolbar);
		createToolbarButton("book.delete", booksToolbar);
		createToolbarButton("book.checkPenalty", booksToolbar);
		books.add(booksScroll, BorderLayout.CENTER);
		booksPanel.setLayout(new BorderLayout());

		examPanel.setLayout(new BorderLayout());
		exams.setLayout(new BorderLayout());
		exams.add(examScroll, BorderLayout.CENTER);
		setToolbarSettings(examsToolbar);
		exams.add(examsToolbar, BorderLayout.LINE_START);
		createToolbarButton("exam.add", examsToolbar);
		createToolbarButton("exam.delete", examsToolbar);


		labelClasses.setLayout(new BorderLayout());
		setToolbarSettings(classesToolbar);
		labelClasses.add(classesToolbar, BorderLayout.LINE_START);
		labelClasses.add(classesPanel, BorderLayout.CENTER);
		classesPanel.setLayout(new BorderLayout());
		classesPanel.add(classesTable, BorderLayout.CENTER);
		createToolbarButton("schedule.today", classesToolbar);
		createToolbarButton("schedule.tommorow", classesToolbar);
		createToolbarButton("schedule.week", classesToolbar);
		createToolbarButton("schedule.add", classesToolbar);
		createToolbarButton("schedule.delete", classesToolbar);

		ownNotes.setLayout(new BorderLayout());
		setToolbarSettings(notesToolbar);
		ownNotes.add(notesToolbar, BorderLayout.LINE_START);
		createToolbarButton("note.create", notesToolbar);
		createToolbarButton("note.open", notesToolbar);
		createToolbarButton("note.save", notesToolbar);
		createToolbarButton("note.openFolder", notesToolbar);
		ownNotes.add(noteArea, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(noteArea);
		scrollPane.setHorizontalScrollBar(null);
		ownNotes.add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		noteArea.setLineWrap(true);
	}


}
