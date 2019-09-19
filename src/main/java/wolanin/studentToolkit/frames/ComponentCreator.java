package wolanin.studentToolkit.frames;

import wolanin.studentToolkit.*;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.IOException;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

class ComponentCreator {

	private static final Insets buttonInsets = new Insets(10, 10, 10, 10);
	private static final Dimension buttonDims = new Dimension(140, 30);
	private static final Insets insetsToolbar = new Insets(15, 5, 5, 5);

	private static GradeLogic gradeLogic;

	static {
		try {
			gradeLogic = new GradeLogic();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final ExamLogic examLogic = new ExamLogic();
	private static final TeacherLogic teacherLogic = new TeacherLogic();
	private static ClassLogic classLogic;

	static {
		try {
			classLogic = new ClassLogic();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static final NoteLogic noteLogic = new NoteLogic();
	private static BookLogic bookLogic;

	static {
		try {
			bookLogic = new BookLogic();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	static JButton createDialog(JDialog dialog, JPanel panel, String title, int row) throws IOException {
		dialog.setModal(true);
		dialog.setSize(400, 200);
		dialog.setTitle(title);
		dialog.setLocationRelativeTo(dialog.getOwner());
		Container contentPane = dialog.getContentPane();
		panel.setLayout(new GridLayout(row, 2));
		JButton save = new JButton(setProperties().getProperty("save.button"));
		contentPane.add(save, BorderLayout.SOUTH);
		contentPane.add(panel, BorderLayout.CENTER);
		dialog.setResizable(true);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return save;

	}

	static void setFrameCenter(JFrame frame) {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		frame.setSize(width / 2, (int) (height / (1.5)));
		int widthFrame = frame.getSize().width;
		int heightFrame = frame.getSize().height;
		frame.setLocation((width - widthFrame) / 2, (height - heightFrame) / 2);
	}


	static void createToolbarButton(String title, JToolBar toolbarName) {
		JButton button = new JButton();
		try {
			button.setText(setProperties().getProperty(title));
		} catch (IOException e) {
			e.printStackTrace();
		}
		button.setMargin(buttonInsets);
		int iconTextGapButton = 15;
		button.setIconTextGap(iconTextGapButton);
		button.setMinimumSize(buttonDims);
		button.setMaximumSize(buttonDims);
		toolbarName.add(button);
		button.addActionListener(e -> addActionListener(toolbarName, e));
	}

	static void setToolbarSettings(JToolBar toolBar) {
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolBar.setBorderPainted(true);
		toolBar.setBorder(null);
		toolBar.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar.setMargin(insetsToolbar);
	}

	private static void addActionListener(JToolBar toolbarName, ActionEvent e){
		String actionCommand = e.getActionCommand();
		switch (toolbarName.getName()) {
			case "gradesToolbar":
				try {
					gradeLogic.doAction(actionCommand);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			case "examsToolbar":
				try {
					examLogic.doAction(actionCommand);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			case "teachersToolbar":
				try {
					teacherLogic.doAction(actionCommand);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			case "classesToolbar":
				try {
					classLogic.doAction(actionCommand);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			case "notesToolbar":
				try {
					noteLogic.doAction(actionCommand);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
			case "booksToolbar":
				try {
					bookLogic.doAction(actionCommand);
				} catch (IOException ex) {
					ex.printStackTrace();
				}
				break;
		}
	}
}

