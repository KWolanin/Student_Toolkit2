package wolanin.studentToolkit.frame;

import wolanin.studentToolkit.AppLogic;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class FormatFrame {

	private static final Insets buttonInsets = new Insets(10, 10, 10, 10);
	private static final Dimension buttonDims = new Dimension(140, 30);
	private static final Insets insetsToolbar = new Insets(15, 5, 5, 5);


	public static JButton createDialog(JDialog dialog, JPanel panel, String title, int row, int col) {
		dialog.setModal(true);
		dialog.setSize(400, 200);
		dialog.setTitle(title);
		dialog.setLocationRelativeTo(dialog.getOwner());
		Container contentPane = dialog.getContentPane();
		panel.setLayout(new GridLayout(row, col));
		JButton save = new JButton("Zapisz");
		contentPane.add(save, BorderLayout.SOUTH);
		contentPane.add(panel, BorderLayout.CENTER);
		dialog.setResizable(true);
		dialog.setDefaultCloseOperation(DISPOSE_ON_CLOSE);
		return save;

	}

	public static void setFrameCenter(JFrame frame) {
		int width = Toolkit.getDefaultToolkit().getScreenSize().width;
		int height = Toolkit.getDefaultToolkit().getScreenSize().height;
		frame.setSize(width / 2, (int) (height / (1.5)));
		int widthFrame = frame.getSize().width;
		int heightFrame = frame.getSize().height;
		frame.setLocation((width - widthFrame) / 2, (height - heightFrame) / 2);
	}


	public static void createToolbarButton(String title, JToolBar toolbarName) {
		JButton button = new JButton();
		button.setText(title);
		button.setMargin(buttonInsets);
		int iconTextGapButton = 15;
		button.setIconTextGap(iconTextGapButton);
		button.setMinimumSize(buttonDims);
		button.setMaximumSize(buttonDims);
		toolbarName.add(button);
		button.addActionListener(e -> {
			try {
				AppLogic.listenerChooser(e);
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		});
	}

	public static void setToolbarSettings(JToolBar toolBar) {
		toolBar.setFloatable(false);
		toolBar.setOrientation(SwingConstants.VERTICAL);
		toolBar.setBorderPainted(true);
		toolBar.setBorder(null);
		toolBar.applyComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
		toolBar.setMargin(insetsToolbar);
	}
}

