import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

public class Client 
{
	public static void main(String args[])
	{
		JFrame login = new JFrame("Login");
		login.setSize(400,400);
		login.setLocationRelativeTo(null);
		login.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JLabel jLabel = new JLabel("please login or register");
		jLabel.setBounds(130, 20, 150,30);
		login.add(jLabel);
		JTextField Account = new JTextField(20);
		Account.setHorizontalAlignment(JTextField.CENTER);
		Dimension dim1 = new Dimension(300,30);
		Account.setPreferredSize(dim1);//�]�w�����Ůe������H�~��L���󪺤j�p
		login.add(Account);
		login.setLayout(null);
		login.setVisible(true);
	}
}
