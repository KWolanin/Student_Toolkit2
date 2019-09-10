package wolanin.studentToolkit.frames;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import static java.awt.Desktop.getDesktop;
import static wolanin.studentToolkit.language.LangProperties.setProperties;

class AboutFrame extends JDialog {

	private final String email = "mailto:wolanin4u@gmail.com";
	private final String sendEmail = "<html><b>Wyślij e-mail</b></html>";
	private final String about = "<html><center>Katarzyna Wolanin  <br/> Informatyka II WSPA <br/> © 2019</center></html>";
	private final JLabel aboutLabel = new JLabel(about, SwingUtilities.CENTER);
	private final String usedTechAndTools = "<html><center>Java Swing, Hibernate <br/> JDatePicker, LGoodDatePicker</center></html>";
	private final JLabel toolsLabel = new JLabel(usedTechAndTools, SwingUtilities.CENTER);
	private final JLabel emailto = new JLabel(sendEmail, SwingUtilities.CENTER);

	AboutFrame() throws IOException {
		initComponents();
	}

	private void initComponents() throws IOException {
		setTitle(setProperties().getProperty("about.title"));
		setLocationRelativeTo(getOwner());
		setSize(300, 200);
		Container contentPane = getContentPane();
		contentPane.setLayout(new GridLayout(3,1));
		contentPane.add(aboutLabel);
		contentPane.add(toolsLabel);
		contentPane.add(emailto);
		emailto.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				Desktop desktop = getDesktop();
				try {
					desktop.mail(new URI(email));
				} catch (IOException | URISyntaxException ex) {
					ex.printStackTrace();
				}
			}
		});
		setDefaultCloseOperation(DISPOSE_ON_CLOSE);
	}


}
