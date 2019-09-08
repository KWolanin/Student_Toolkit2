package wolanin.studentToolkit;


import wolanin.studentToolkit.frames.MainFrame;

import javax.swing.*;
import java.io.IOException;

class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> {
			try {
				new MainFrame();
			} catch (IOException e) {
				e.printStackTrace();
			}
		});
	}
}
