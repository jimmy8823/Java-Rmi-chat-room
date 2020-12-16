import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.rmi.*;

public class loginGUI implements ActionListener
{
	private static JLabel usernameL;
	private static JTextField usernameT;
	private static JButton loginB;
	private static JButton registerB;
	private static JLabel successL;
	public static void launch_login()
	{
		JFrame login = new JFrame();
		JPanel loginPanel = new JPanel();
		login.setSize(400,200);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		login.add(loginPanel);
		
		
		loginPanel.setLayout(null);
		
		JLabel loginL = new JLabel("login");
		loginL.setBounds(180, 20, 150,30);
		usernameL = new JLabel("username : ");
		usernameL.setBounds(110, 60, 80, 20);		
		loginPanel.add(loginL);
		loginPanel.add(usernameL);
		usernameT = new JTextField(20);
		usernameT.setBounds(180, 60, 110, 20);
		loginPanel.add(usernameT);
		
		loginB = new JButton("login");
		loginB.setBounds(210, 100, 80, 25);
		loginB.addActionListener(new loginGUI());
		loginB.setActionCommand("1");
		loginPanel.add(loginB);
		
		registerB = new JButton("register");
		registerB.setBounds(100, 100, 80, 25);
		registerB.addActionListener(new loginGUI());
		registerB.setActionCommand("0");
		loginPanel.add(registerB);
		
		successL = new JLabel("");
		successL.setBounds(110, 130, 300, 25);
		loginPanel.add(successL);
		
		login.setVisible(true);
	
	}

	@Override
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		String cmd = e.getActionCommand();
		String username = usernameT.getText();
		if(cmd == "1") {
			System.out.print(username+"login\n");
			successL.setText("Login successful !");
        }
		if(cmd == "0") {
			System.out.print(username+"register\n");
			successL.setText("register successful ! Loign pls");
        }
	}
}
