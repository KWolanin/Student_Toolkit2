package wolanin.studentToolkit.language;

import wolanin.studentToolkit.frames.MainFrame;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class LangProperties {

	public static Properties setProperties() throws IOException {
		FileInputStream fileInputStream;
		if (MainFrame.PLlang) {
			fileInputStream = new FileInputStream("polishLang.properties");
		} else {
			fileInputStream = new FileInputStream("englishLang.properties");
		}
		Properties properties = new Properties();
		properties.load(fileInputStream);
		return properties;
	}
}
