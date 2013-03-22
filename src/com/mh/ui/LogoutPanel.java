package com.mh.ui;

import javax.swing.JPanel;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import java.awt.GridBagConstraints;
@Deprecated
public class LogoutPanel extends JPanel{
	public LogoutPanel() {
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWidths = new int[]{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0};
		gridBagLayout.rowHeights = new int[]{0, 0, 0, 0, 0};
		gridBagLayout.columnWeights = new double[]{0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		gridBagLayout.rowWeights = new double[]{0.0, 0.0, 0.0, 0.0, Double.MIN_VALUE};
		setLayout(gridBagLayout);
		
		JButton btnLogout = new JButton("Logout");
		GridBagConstraints gbc_btnLogout = new GridBagConstraints();
		gbc_btnLogout.gridx = 9;
		gbc_btnLogout.gridy = 3;
		add(btnLogout, gbc_btnLogout);
	}

}
