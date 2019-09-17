package wolanin.studentToolkit;

import java.io.File;
import java.io.IOException;

import static wolanin.studentToolkit.frames.MainFrame.notes;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class NoteLogic implements ChooseAction {

	@Override
	public void doAction(String actionCommand) throws IOException {
		if (setProperties().getProperty("note.create").equals(actionCommand)) {
			notes.clearNoteArea();
		} else if (setProperties().getProperty("note.open").equals(actionCommand)) {
			try {
				notes.openFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("note.save").equals(actionCommand)) {
			try {
				notes.saveFile();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} else if (setProperties().getProperty("note.openFolder").equals(actionCommand)) {
			try {
				notes.openDir();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

	public static void createFileDir() {
		File dir = new File("UserNotes");
		if (!dir.exists()) {
			dir.mkdir();
		}
	}
}
