import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.rmi.*;

public class Client 
{
	public static void main(String args[])
	{/*
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
		Account.setPreferredSize(dim1);//設定除頂級容器元件以外其他元件的大小
		login.add(Account);
		login.setLayout(null);
		login.setVisible(true);
	*/
		String username;
		ArithmeticInterface		o = null;
		Scanner scn = new Scanner(System.in);
		try
		{
			o = (ArithmeticInterface) Naming.lookup("rmi://127.0.0.1/arithmetic");
			System.out.println("RMI server connected");
		}
		catch(Exception e)
		{
			System.out.println("Server lookup exception: " + e.getMessage());
		}
		
		while(true) {  // login and register
			try {
				System.out.println("0 = register 1 = login");
				int reg_or_login = scn.nextInt();
				System.out.println("input your username");
				username = scn.next();
				if(reg_or_login==0) {
					int register_result = o.register(username);
					if(register_result == 0) {
						System.out.println("register scuccess!");
					}else {
						System.out.println("username already existed");
					}
				}else if(reg_or_login==1) {
					int login_result = o.login(username);
					if(login_result == 0) {
						ThreadByRunnable st = new ThreadByRunnable(o);
						System.out.println("Welcome " + username);
						break;
					}else if(login_result == -1){
						System.out.println("login failed please register first !");
					}else {
						System.out.println(o.login(username));
					}
				}
			}catch(RemoteException e) {
				e.printStackTrace();
			}
		}
		while(true) {
			String comment= scn.next();
			try {
				o.add_comment(username, comment);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	public static class ThreadByRunnable implements Runnable
	{
		InputStream		in = null;
		OutputStream	out = null;
		ArithmeticInterface	rmi =null;
		public ThreadByRunnable(ArithmeticInterface	o)
		{
			this.rmi = o;
			Thread t = new Thread(this);
			t.start();
		}
		
		public void run()
		{
			int index = 0;
			String[] str = new String [1000];
			while(true) {
				try {
					if(rmi.check_have_new_comment(index)==0) {
						continue;
					}else if(rmi.check_have_new_comment(index)==1) { // have new comment 
						str = rmi.read_chat();
						for(int i=index;true;i++) {
							if(str[i]==null) {
								index = i;
								break;
							}else {
								System.out.println(str[i]);
							}
						}			
					}
				} catch (RemoteException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
		}
	}
}
