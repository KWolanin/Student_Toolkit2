package wolanin.studentToolkit.language;

import wolanin.studentToolkit.frames.MainFrame;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

public class LangProperties {

	public static Properties setProperties() throws IOException {
		File polLang = new File("src\\main\\resources\\polishLang.properties");
		File engLang = new File("src\\main\\resources\\englishLang.properties");
		BufferedReader in;
		if (MainFrame.PLlang) {
			in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(polLang), StandardCharsets.UTF_8));

		} else {
			in = new BufferedReader(
					new InputStreamReader(
							new FileInputStream(engLang), StandardCharsets.UTF_8));
		}
		Properties properties = new Properties();
		properties.load(in);
		return properties;
	}
}
