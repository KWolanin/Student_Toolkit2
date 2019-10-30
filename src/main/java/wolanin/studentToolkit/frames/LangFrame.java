package wolanin.studentToolkit.frames;


import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static wolanin.studentToolkit.frames.ComponentCreator.setFrameCenter;


public class LangFrame extends JFrame {

	private Container contentPane;


	private void createLangButtons() {
		JButton polish = new JButton();
		JButton english = new JButton();
		polish.setIcon(new ImageIcon("src\\main\\resources\\pl.png"));
		english.setIcon(new ImageIcon("src\\main\\resources\\en.png"));
		polish.setHorizontalTextPosition(SwingConstants.CENTER);
		english.setHorizontalTextPosition(SwingConstants.CENTER);
		contentPane.add(polish);
		contentPane.add(english);
		setLanguage(polish, english);
		dispose();
	}

	private void setLanguage(JButton polish, JButton english) {
		polish.addActionListener(e -> {
			MainFrame.isPolishSet = true;
			try {
				new MainFrame();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			dispose();
		});
		english.addActionListener(e -> {
			MainFrame.isPolishSet = false;
			try {
				new MainFrame();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
			dispose();
		});
	}

	public LangFrame() {
		initComponents();
		setVisible(true);

	}

	private void initComponents() {
		setFrameCenter(this);
		setIconImage(new ImageIcon("src\\main\\resources\\student.png").getImage());
		setResizable(false);
		setSize(200, 200);
		contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(1, 2));
		createLangButtons();
		pack();
		setDefaultCloseOperation(EXIT_ON_CLOSE);
	}
}
