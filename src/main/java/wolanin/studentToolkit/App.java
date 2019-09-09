package wolanin.studentToolkit;


import wolanin.studentToolkit.frames.LangFrame;

import javax.swing.*;

class App {
	public static void main(String[] args) {
		SwingUtilities.invokeLater(LangFrame::new);
	}
}
