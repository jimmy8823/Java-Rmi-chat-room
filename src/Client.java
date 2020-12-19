import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import java.rmi.*;

public class Client implements ActionListener
{
	private static JLabel usernameL;
	private static JTextField usernameT;
	private static JButton loginB;
	private static JButton registerB;
	private static JLabel successL;
	static ArithmeticInterface		o = null;
	static JFrame login = new JFrame("Login");
	static JPanel loginPanel = new JPanel();
	private static JLabel commentL;
	private static JTextField commentT;
	private static JTextArea commentA;
	private static JButton sentB;
	private static JButton exitB;
	private static JFrame chat;
	private static JTextArea userA;
	public static void main(String args[])
	{
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
		loginB.addActionListener(new Client());
		loginB.setActionCommand("login");
		loginPanel.add(loginB);
		
		registerB = new JButton("register");
		registerB.setBounds(100, 100, 80, 25);
		registerB.addActionListener(new Client());
		registerB.setActionCommand("register");
		loginPanel.add(registerB);
		
		successL = new JLabel("");
		successL.setBounds(110, 130, 300, 25);
		loginPanel.add(successL);
		
		login.setVisible(true);
		
		/*while(true) {  // login and register
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
		}*/
		/*while(true) {
			String comment= scn.next();
			try {
				o.add_comment(username, comment);
			} catch (RemoteException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}*/
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
								commentA.append(str[i]+"\n");
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
	
	public void chat_launch()
	{
		chat = new JFrame("chat room");
		JPanel chatPanel = new JPanel();
		
		chat.setSize(600,600);
		chat.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		chat.add(chatPanel);
		//chatPanel.setBackground(Color.black);
		chatPanel.setLayout(null);
		
		commentL = new JLabel("comment : ");
		//commentL.setForeground(Color.CYAN);
		commentL.setBounds(10, 371, 80, 20);		
		chatPanel.add(commentL);
		
		commentT = new JTextField(20);
		commentT.setBounds(75, 373, 210, 20);
		chatPanel.add(commentT);
		
		sentB = new JButton("send");
		sentB.setBounds(300, 370, 80, 25);
		sentB.addActionListener(new Client());
		sentB.setActionCommand("send");
		chatPanel.add(sentB);
		
		commentA = new JTextArea();
		commentA.setBounds(10, 10, 370, 350);
		commentA.setEditable(false);
		//commentA.setBackground(Color.black); 
		//commentA.setForeground(Color.white);
		JScrollPane commentAScroll = new JScrollPane(commentA);
		commentAScroll.setBounds(10,10, 370, 350);
		chatPanel.add(commentAScroll);
		
		String s = "Online users :";
		userA = new JTextArea(s);
		userA.setEditable(false);
		JScrollPane userAScroll = new JScrollPane(userA);
		userAScroll.setBounds(390,10, 180, 350);
		chatPanel.add(userAScroll);
		
		
		exitB = new JButton("exit");
		exitB.setBounds(490, 520, 80, 25);
		exitB.addActionListener(new Client());
		exitB.setActionCommand("exit");
		chatPanel.add(exitB);
		
		chat.setVisible(true);
	}
	
	public void actionPerformed(ActionEvent e) 
	{
		// TODO Auto-generated method stub
		String username = usernameT.getText();
		String cmd = e.getActionCommand();
		try {
			if(cmd == "login") {
				System.out.print(username+"login\n");
				int login_result = o.login(username);
				if(login_result == 0) {
					JOptionPane.showMessageDialog(login, "登入成功!!",
				    "Welcome!",JOptionPane.INFORMATION_MESSAGE);
					successL.setText("Login successful !");
					login.dispose();
					chat_launch();
					o.join(username);
					ThreadByRunnable st = new ThreadByRunnable(o);
				}else if(login_result == -1) {
					JOptionPane.showMessageDialog(login, "登入失敗...",
					"Sorry...",JOptionPane.WARNING_MESSAGE);
					successL.setText("check your name or register pls!");
				}
			}
			if(cmd == "register") {
				System.out.print(username+"register\n");
				o.register(username);
				usernameT.setText("");
				successL.setText("register successful ! Loign pls");
	        }
			if(cmd == "send") 
			{
				String comment = commentT.getText();
				commentT.setText("");
				//System.out.print(comment+"\n");
				o.add_comment(username, comment);
	        }
			if(cmd == "exit") 
			{
				System.out.print("exit");
				o.logout(username);
				chat.dispose();
	        }
		} catch (RemoteException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}
	
}
