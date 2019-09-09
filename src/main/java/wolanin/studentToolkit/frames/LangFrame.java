package wolanin.studentToolkit.frames;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.Objects;


public class LangFrame extends JFrame {

	private String[] langs = {"Polski", "English"};
	private JComboBox<String> langCombo = new JComboBox<>(langs);
	private JButton save = new JButton("Zapisz | Save");

	public LangFrame() {
		initComponents();
		setVisible(true);

	}

	private void initComponents() {
		FormatFrame.setFrameCenter(this);
		setResizable(false);
		setTitle("Język | Language");
		setLocationRelativeTo(getOwner());
		setSize(300, 100);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(2, 1));
		contentPane.add(langCombo);
		contentPane.add(save);
		save.addActionListener(e -> {
			MainFrame.PLlang = Objects.equals(langCombo.getSelectedItem(), "Polski");
					try {
						new MainFrame();
						dispose();
					} catch (IOException ex) {
						ex.printStackTrace();
					}
				}
		);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}


}
