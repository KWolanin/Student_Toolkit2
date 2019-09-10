package wolanin.studentToolkit.notes;

import wolanin.studentToolkit.frames.MainFrame;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.io.*;

import static wolanin.studentToolkit.language.LangProperties.setProperties;

public class Notes {

	private JFileChooser fileChooser;
	private final FileNameExtensionFilter filter = new FileNameExtensionFilter(setProperties().getProperty("saving.desc"), "*.txt");
	private final FileNameExtensionFilter openFilter = new FileNameExtensionFilter(setProperties().getProperty("saving.desc"), "txt");

	public Notes() throws IOException {
	}

	public void clearNoteArea() {
		MainFrame.noteArea.setText("");
		if (!MainFrame.noteArea.isEditable()) {
			MainFrame.noteArea.setEnabled(true);
			MainFrame.noteArea.setEditable(true);
			MainFrame.noteArea.setText("");
			MainFrame.noteArea.setBackground(Color.white);
		}
	}

	public void saveFile() throws IOException {
		File fileToSave;
		fileChooser = new JFileChooser();
		if (MainFrame.isPolishSet) {
			setPolishFileChooser(fileChooser);
		}
		fileChooser.addChoosableFileFilter(filter);
		fileChooser.setFileFilter(filter);
		fileChooser.setDialogTitle("Zapisz plik");
		fileChooser.setCurrentDirectory(new File("UserNotes"));
		fileChooser.setMultiSelectionEnabled(false);
		int userSelection = fileChooser.showSaveDialog(null);
		if (userSelection == JFileChooser.APPROVE_OPTION) {
			fileToSave = fileChooser.getSelectedFile();
			if (!fileToSave.getName().contains(".")) fileToSave = new File(fileToSave.toString() + ".txt");
			PrintWriter writer = new PrintWriter(fileToSave);
			writer.println(MainFrame.noteArea.getText());
			writer.close();
		}
	}

	public void openFile() throws IOException {
		clearNoteArea();
		String line;
		BufferedReader in;
		fileChooser = new JFileChooser();
		if (MainFrame.isPolishSet) {
			setPolishFileChooser(fileChooser);
		}
		fileChooser.setCurrentDirectory(new File("UserNotes"));
		fileChooser.setFileFilter(openFilter);
		fileChooser.setMultiSelectionEnabled(false);
		fileChooser.setDialogTitle("Otwórz plik");
		int returnVal = fileChooser.showOpenDialog(null);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			File file = fileChooser.getSelectedFile();
			in = new BufferedReader(new FileReader(file));
			line = in.readLine();
			while (line != null) {
				MainFrame.noteArea.append(line);
				line = in.readLine();
			}
		}
	}

	public void openDir() throws IOException {
		Desktop.getDesktop().open(new File("UserNotes"));
	}

	private static void setPolishFileChooser(JFileChooser fileChooser) {
		UIManager.put("FileChooser.saveInLabelText", "Zapisz w");
		UIManager.put("FileChooser.saveButtonText", "Zapisz");
		UIManager.put("FileChooser.openButtonText", "Otwórz");
		UIManager.put("FileChooser.openSaveText", "Zapisz");
		UIManager.put("FileChooser.cancelButtonText", "Anuluj");
		UIManager.put("FileChooser.fileNameLabelText", "Nazwa pliku");
		UIManager.put("FileChooser.filesOfTypeLabelText", "Typ");
		UIManager.put("FileChooser.lookInLabelText", "Przeglądaj");
		UIManager.put("FileChooser.newFolderToolTipText", "Utwórz nowy folder");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegóły plików");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Lista");
		UIManager.put("FileChooser.upFolderToolTipText", "Do góry");
		UIManager.put("FileChooser.openDialogTitleText", "Otwórz");
		UIManager.put("FileChooser.lookInLabelText", "Przeglądaj");
		UIManager.put("FileChooser.openButtonToolTipText", "Otwórz zaznaczony plik");
		UIManager.put("FileChooser.saveButtonToolTipText", "Zapisz plik w tym miejscu");
		UIManager.put("FileChooser.cancelButtonToolTipText", "Anuluj");
		UIManager.put("FileChooser.fileNameHeaderText", "Nazwa pliku");
		UIManager.put("FileChooser.homeFolderToolTipText", "Pulpit");
		UIManager.put("FileChooser.listViewButtonToolTipText", "Pokaż widok listy");
		UIManager.put("FileChooser.newFolderButtonText", "Nowy folder");
		UIManager.put("FileChooser.renameFileButtonText", "Zmień nazwę");
		UIManager.put("FileChooser.deleteFileButtonText", "Usuń");
		UIManager.put("FileChooser.filterLabelText", "Rozszerzenie, w którym zapisany zostanie plik");
		UIManager.put("FileChooser.detailsViewButtonToolTipText", "Szczegóły");
		UIManager.put("FileChooser.fileSizeHeaderText", "Rozmiar");
		UIManager.put("FileChooser.fileDateHeaderText", "Data modyfikacji");
		SwingUtilities.updateComponentTreeUI(fileChooser);
	}

}
