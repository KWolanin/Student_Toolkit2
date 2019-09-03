package wolanin.studentToolkit.language;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

class LangSet {

	//todo

	public static Properties setProperties() throws IOException {
		Properties properties = new Properties();
		properties.load(setLanguage("Polski"));
		return properties;
	}

	private static FileInputStream setLanguage(String lang) throws IOException {
		Properties properties = new Properties();
		FileInputStream fileInputStream = null;
		if (lang.equals("Polski") | lang.equals("Polish")) {
			fileInputStream = new FileInputStream("polishLang.properties");
		} else if (lang.equals("English") | lang.equals("Angielski")) {
			fileInputStream = new FileInputStream("englishLang.properties");
		}
		assert fileInputStream != null;
		properties.load(fileInputStream);
		return fileInputStream;
	}

}