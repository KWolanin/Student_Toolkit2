package wolanin.studentToolkit;


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
