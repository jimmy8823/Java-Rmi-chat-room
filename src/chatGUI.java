import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.rmi.*;

public class chatGUI implements ActionListener
{
	private static JLabel commentL;
	private static JTextField commentT;
	private static JTextArea commentA;
	private static JButton sentB;
	public static void main(String args[])
	{
		JFrame chat = new JFrame();
		JPanel chatPanel = new JPanel();
		
		chat.setSize(400,400);
		chat.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		chat.add(chatPanel);
		
		chatPanel.setLayout(null);
		
		commentL = new JLabel("comment : ");
		commentL.setBounds(10, 380, 80, 20);		
		chatPanel.add(commentL);
		
		commentT = new JTextField(20);
		commentT.setBounds(80, 380, 200, 20);
		chatPanel.add(commentT);
		
		sentB = new JButton("sent");
		sentB.setBounds(300, 380, 80, 25);
		sentB.addActionListener(new chatGUI());
		sentB.setActionCommand("1");
		chatPanel.add(sentB);
		
		commentA = new JTextArea();
		commentA.setBounds(10, 10, 370, 350);
		chatPanel.add(commentA);
		
		chat.setVisible(true);
	}
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String comment = commentT.getText();
		System.out.print(comment+"\n");
		commentT.setText("");
		commentA.append(comment+"\n");
	}
	
}
