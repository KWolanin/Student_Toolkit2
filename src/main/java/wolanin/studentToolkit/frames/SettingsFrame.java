package wolanin.studentToolkit.frames;

import wolanin.studentToolkit.db.BooksDAO;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static java.awt.BorderLayout.LINE_START;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

class SettingsFrame extends JDialog {

	private final JTextField penaltyValueField = new JTextField();


	SettingsFrame() throws IOException {
		setTitle(setProperties().getProperty("settings.title"));
		setLocationRelativeTo(getOwner());
		Container contentPane = getContentPane();
		setSize(300, 100);
		setResizable(false);
		JLabel penaltyValue = new JLabel(setProperties().getProperty("penalty.info"));
		contentPane.add(penaltyValue, LINE_START);
		contentPane.add(penaltyValueField, BorderLayout.CENTER);
		penaltyValueField.setText("0.2");
		JButton save = new JButton(setProperties().getProperty("save.button"));
		contentPane.add(save, BorderLayout.SOUTH);
		save.addActionListener(e -> {
					BooksDAO.penaltyValuePerDay = Float.parseFloat(penaltyValueField.getText());
					dispose();
				}
		);
	}


}
