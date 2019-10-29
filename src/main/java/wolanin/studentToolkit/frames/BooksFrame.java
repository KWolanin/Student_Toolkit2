package wolanin.studentToolkit.frames;

import org.jdatepicker.JDatePicker;
import wolanin.studentToolkit.db.BooksDAO;

import javax.swing.*;
import java.io.IOException;
import java.util.Objects;

import static wolanin.studentToolkit.frames.ComponentCreator.createDialog;
import static wolanin.studentToolkit.frames.MainFrame.session;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class BooksFrame extends JDialog {

	private final BooksDAO b = new BooksDAO();
	public static final JTextField titleField = new JTextField();
	public static final JTextField authorField = new JTextField();
	public static final JDatePicker borrowedPicker = new JDatePicker();
	public static final JDatePicker returnToPicker = new JDatePicker();

	private static String[] isPenalty;

	static {
		try {
			isPenalty = new String[]
					{setProperties().getProperty("no"),
							setProperties().getProperty("yes")};
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static final JComboBox<String> penaltyCombo = new JComboBox<>(isPenalty);
	public static final JTextField penaltyField = new JTextField();


	public BooksFrame() throws IOException {
		JPanel panel = new JPanel();
		JButton save = createDialog(this, panel, setProperties().getProperty("book.window.add"), 6);
		JLabel title = new JLabel(setProperties().getProperty("book.title") + ":");
		panel.add(title);
		panel.add(titleField);
		JLabel author = new JLabel(setProperties().getProperty("book.author") + ":");
		panel.add(author);
		panel.add(authorField);
		JLabel borrowed = new JLabel(setProperties().getProperty("book.borrowed") + ":");
		panel.add(borrowed);
		panel.add(borrowedPicker);
		JLabel returnTo = new JLabel(setProperties().getProperty("book.returnTo") + ":");
		panel.add(returnTo);
		panel.add(returnToPicker);
		JLabel penaltyLabel = new JLabel(setProperties().getProperty("book.isPenalty"));
		panel.add(penaltyLabel);
		panel.add(penaltyCombo);
		JLabel penaltyValue = new JLabel(setProperties().getProperty("book.penalty"));
		panel.add(penaltyValue);
		panel.add(penaltyField);
		penaltyCombo.addActionListener(e -> {
			String tmp = Objects.requireNonNull(penaltyCombo.getSelectedItem()).toString();
			try {
				if (tmp.equals(setProperties().getProperty("no"))) {
					penaltyField.setEnabled(false);
				} else {
					penaltyField.setEnabled(true);
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
		save.addActionListener(e -> {
			try {
				b.addToBase(session);

			} catch (IOException ex) {
				ex.printStackTrace();
			}
			dispose();
		});

	}
}





