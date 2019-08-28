package wolanin.studentToolkit.language;

import wolanin.studentToolkit.MainFrame;

import javax.swing.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LangSet {

	//	public static Properties setProperties() throws FileNotFoundException {
//		return properties;
//	}


	public static Properties setProperties(FileInputStream fileInputStream) throws IOException {
		Properties properties = new Properties();
		properties.load(setLanguage("Polski"));
		return properties;
	}

	public static FileInputStream setLanguage(String lang) throws IOException {
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		if (lang.equals("Polski") | lang.equals("Polish")) {
			fileInputStream = new FileInputStream("polishLang.properties");
		} else if (lang.equals("English") | lang.equals("Angielski")) {
			fileInputStream = new FileInputStream("englishLang.properties");
		}
		properties.load(fileInputStream);
		return fileInputStream;
	}

}