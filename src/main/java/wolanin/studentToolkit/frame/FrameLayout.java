package wolanin.studentToolkit.frame;

import javax.swing.*;
import java.awt.*;

import static wolanin.studentToolkit.MainFrame.tabPanel;
import static wolanin.studentToolkit.MainFrame.tabs;

public class FrameLayout {

	public static void setGroupLayout(Container contentPane) {
		GroupLayout contentPaneLayout = new GroupLayout(contentPane);
		contentPane.setLayout(contentPaneLayout);
		contentPaneLayout.setHorizontalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(contentPaneLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(tabPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addGap(5, 5, 5))
		);
		contentPaneLayout.setVerticalGroup(
				contentPaneLayout.createParallelGroup()
						.addGroup(GroupLayout.Alignment.TRAILING, contentPaneLayout.createSequentialGroup()
								.addComponent(tabPanel, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
								.addContainerGap())
		);
		GroupLayout tabPanelLayout = new GroupLayout(tabPanel);
		tabPanel.setLayout(tabPanelLayout);
		tabPanelLayout.setHorizontalGroup(
				tabPanelLayout.createParallelGroup()
						.addComponent(tabs, GroupLayout.Alignment.TRAILING)
		);
		tabPanelLayout.setVerticalGroup(
				tabPanelLayout.createParallelGroup()
						.addGroup(tabPanelLayout.createSequentialGroup()
								.addContainerGap()
								.addComponent(tabs, GroupLayout.DEFAULT_SIZE, 0, Short.MAX_VALUE))
		);
	}

}
