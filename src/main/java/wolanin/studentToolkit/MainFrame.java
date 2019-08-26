package wolanin.studentToolkit;

import wolanin.studentToolkit.about.*;
import wolanin.studentToolkit.database.dbConnection;
import wolanin.studentToolkit.frame.FormatFrame;
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


public class MainFrame extends JFrame {
	private final JTabbedPane tabs = new JTabbedPane();
	private final JLabel grades = new JLabel();
	private final JLabel teachers = new JLabel();
	private final JLabel labelSchedule = new JLabel();
	private final JLabel exams = new JLabel();
	private final JLabel ownNotes = new JLabel();
	private static final String currentDate = "";
	public static final JLabel scheduleLabel = new JLabel(currentDate, SwingConstants.CENTER);
	private final JMenuBar menuBar = new JMenuBar();
	private final JMenu fileMenu = new JMenu("Plik");
	private final JMenu helpMenu = new JMenu("Pomoc");
	private final JMenuItem exit = new JMenuItem("Wyjście");
	private final JMenuItem about = new JMenuItem("O programie");
	private final JToolBar gradesToolbar = new JToolBar();
	private final JToolBar teachersToolbar = new JToolBar();
	private final JToolBar examsToolbar = new JToolBar();
	private final JToolBar scheduleToolbar = new JToolBar();
	private final JToolBar notesToolbar = new JToolBar();
	public static final JTextArea noteArea = new JTextArea();
	private final JPanel tabPanel = new JPanel();
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


	MainFrame() {
		initComponents();
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

	private void initComponents() {
		setIconImage(new ImageIcon("src\\main\\resources\\student.png").getImage());
		setTitle("Przybornik Studenta");
		Container contentPane = getContentPane();
		setJMenuBar(menuBar);
		menuBar.add(fileMenu);
		menuBar.add(helpMenu);
		fileMenu.add(exit);
		helpMenu.add(about);
		about.addActionListener(e -> new AboutFrame().setVisible(true));
		exit.addActionListener(e -> System.exit(0));
		setGroupLayout(contentPane);
		tabs.addTab("Oceny", null, grades, "Informacje o otrzymanych ocenach");
		tabs.addTab("Wykładowcy", null, teachers, "Informacje o wykładowcach");
		tabs.addTab("Zaliczenia", null, exams, "Informacje o nadchodzących zaliczeniach");
		tabs.addTab("Plan zajęć", null, labelSchedule, "Informacje o planie zajęć");
		tabs.addTab("Notatki", null, ownNotes, "Własne notatki");

		grades.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(gradesToolbar);
		grades.add(gradesToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton("Pełna lista ocen", gradesToolbar);
		FormatFrame.createToolbarButton("Wylicz średnią", gradesToolbar);
		FormatFrame.createToolbarButton("Dodaj ocenę", gradesToolbar);
		FormatFrame.createToolbarButton("Usuń ocenę", gradesToolbar);
		FormatFrame.createToolbarButton("Pokaż niezaliczone", gradesToolbar);

		grades.add(gradeScroll, BorderLayout.CENTER);
		gradesPanel.setLayout(new BorderLayout());

		teachers.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(teachersToolbar);
		teachers.add(teachersToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton("Dodaj wykładowcę", teachersToolbar);
		FormatFrame.createToolbarButton("Usuń wykładowcę", teachersToolbar);
		FormatFrame.createToolbarButton("Wyślij E-mail", teachersToolbar);
		teachers.add(teacherScroll, BorderLayout.CENTER);
		teachersPanel.setLayout(new BorderLayout());

		examPanel.setLayout(new BorderLayout());
		exams.setLayout(new BorderLayout());
		exams.add(examScroll, BorderLayout.CENTER);
		FormatFrame.setToolbarSettings(examsToolbar);
		exams.add(examsToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton("Dodaj zaliczenie", examsToolbar);
		FormatFrame.createToolbarButton("Usuń zaliczenie", examsToolbar);

		labelSchedule.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(scheduleToolbar);
		labelSchedule.add(scheduleToolbar, BorderLayout.LINE_START);
		labelSchedule.add(scheduleLabel, BorderLayout.NORTH);
		scheduleLabel.setOpaque(true);
		labelSchedule.add(schedulePanel, BorderLayout.CENTER);
		schedulePanel.setLayout(new BorderLayout());
		schedulePanel.add(scheduleTable, BorderLayout.CENTER);
		FormatFrame.createToolbarButton("Dziś", scheduleToolbar);
		FormatFrame.createToolbarButton("Jutro", scheduleToolbar);
		FormatFrame.createToolbarButton("Tydzień", scheduleToolbar);
		FormatFrame.createToolbarButton("Dodaj zajęcia", scheduleToolbar);
		FormatFrame.createToolbarButton("Usuń zajęcia", scheduleToolbar);

		ownNotes.setLayout(new BorderLayout());
		FormatFrame.setToolbarSettings(notesToolbar);
		ownNotes.add(notesToolbar, BorderLayout.LINE_START);
		FormatFrame.createToolbarButton("Utwórz", notesToolbar);
		FormatFrame.createToolbarButton("Otwórz", notesToolbar);
		FormatFrame.createToolbarButton("Zapisz", notesToolbar);
		FormatFrame.createToolbarButton("Otwórz folder", notesToolbar);
		ownNotes.add(noteArea, BorderLayout.CENTER);
		JScrollPane scrollPane = new JScrollPane(noteArea);
		scrollPane.setHorizontalScrollBar(null);
		ownNotes.add(scrollPane);
		scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		noteArea.setLineWrap(true);
	}

	public static void listenerChooser(ActionEvent e) {
		String tmp = e.getActionCommand();
		switch (tmp) {
			case "Wylicz średnią":
				try {
					grade.calcAverage();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Dodaj ocenę":
				grade.addGrade(con);
				break;
			case "Usuń ocenę":
				try {
					grade.deleteGrade();
				} catch (NullPointerException | IndexOutOfBoundsException x) {
					JOptionPane.showMessageDialog(null, "Najpierw wybierz ocenę!", "Usuwanie oceny", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Pełna lista ocen":
				try {
					grade.showGrades(con);
				} catch (SQLException | NullPointerException ex) {
					ex.printStackTrace();
				}
				break;
			case "Pokaż niezaliczone":
				try {
					grade.showFailed();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Dodaj wykładowcę":
				teacher.addTeacher(con);
				break;
			case "Usuń wykładowcę":
				try {
					teacher.deleteTeacher(con);
				} catch (NullPointerException | IndexOutOfBoundsException a) {
					JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Usuwanie wykładowcy", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Wyślij E-mail":
				try {
					teacher.emailTo();
				} catch (NullPointerException | ArrayIndexOutOfBoundsException n) {
					JOptionPane.showMessageDialog(null, "Najpierw wybierz wykładowcę!", "Wysyłanie e-maila", JOptionPane.INFORMATION_MESSAGE);
				} catch (IOException | URISyntaxException ex) {
					ex.printStackTrace();
				}
				break;
			case "Dodaj zaliczenie":
				exam.addExam();
				break;
			case "Usuń zaliczenie":
				try {
					exam.deleteExam(con);
				} catch (NullPointerException | IndexOutOfBoundsException ee) {
					JOptionPane.showMessageDialog(null, "Najpierw wybierz zaliczenie!", "Usuwanie zaliczenia", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Odśwież":

				try {
					exam.showExams(con);
				} catch (NullPointerException | SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Dziś":
				try {
					schedule.showTodaySchedule(con);
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Jutro":
				try {
					schedule.showTommorowSchedule(con);
				} catch (ParseException | SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Tydzień":
				try {
					schedule.showWeekSchedule();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				break;
			case "Dodaj zajęcia":
				schedule.addClasses();
				break;
			case "Usuń zajęcia":
				try {
					schedule.deleteClasses();
				} catch (IndexOutOfBoundsException | NullPointerException ex) {
					JOptionPane.showMessageDialog(null, "Najpierw wybierz zajęcia w widoku tygodniowym!", "Usuwanie zajęć", JOptionPane.INFORMATION_MESSAGE);
				} catch (SQLException es) {
					es.printStackTrace();
				}
				break;
			case "Utwórz":
				notes.clearNoteArea();
				break;
			case "Otwórz":
				try {
					notes.openFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			case "Zapisz":
				try {
					notes.saveFile();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			case "Otwórz folder":
				try {
					notes.openDir();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			default:
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

	/*public static void setTableProp(JPanel panel, JTable table, DefaultTableModel tablemodel) {
		table.setModel(tablemodel);
		table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		panel.add(table, BorderLayout.CENTER);
		panel.add(table.getTableHeader(), BorderLayout.NORTH);
		table.setAutoCreateRowSorter(true);
		panel.updateUI();
	}

	public static void setTableModelProp(String[] columnNames) {
		tableModel = new DefaultTableModel(columnNames, 0) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};*/



	private void setGroupLayout(Container contentPane) {
		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(tabPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(5, 5, 5))
		);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
								.addComponent(tabPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
		);
		setLocationRelativeTo(getOwner());
		GroupLayout tabPanelLayout = new GroupLayout(tabPanel);
		tabPanel.setLayout(tabPanelLayout);
		tabPanelLayout.setHorizontalGroup(
				tabPanelLayout.createParallelGroup()
						.addComponent(tabs, GroupLayout.Alignment.TRAILING)
		);
		tabPanelLayout.setVerticalGroup(
				tabPanelLayout.createParallelGroup()
						.addGroup(tabPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(tabs, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
		);
	}


}
