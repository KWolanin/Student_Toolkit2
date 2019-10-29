package wolanin.studentToolkit.frames;

import wolanin.studentToolkit.db.BooksDAO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static wolanin.studentToolkit.language.LangProperties.setProperties;

class SettingsFrame extends JDialog {

	private final JTextField penaltyValueField = new JTextField();

	public SettingsFrame() throws IOException {
		setTitle(setProperties().getProperty("settings.title"));
		setLocationRelativeTo(getOwner());
		Container contentPane = getContentPane();
		setSize(300, 100);
		setResizable(false);
		contentPane.setLayout(new GridLayout(1, 2));
		JLabel penaltyValue = new JLabel(setProperties().getProperty("penalty.info"));
		contentPane.add(penaltyValue);
		contentPane.add(penaltyValueField);
		JButton save = new JButton(setProperties().getProperty("save.button"));
		contentPane.add(save);
		save.addActionListener(e -> {
					BooksDAO.penaltyValuePerDay = Float.parseFloat(penaltyValueField.getText());
					dispose();
				}
		);
	}


}
