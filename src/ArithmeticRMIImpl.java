import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.rmi.server.UnicastRemoteObject;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class ArithmeticRMIImpl extends UnicastRemoteObject implements ArithmeticInterface
{
	// This implementation must have a public constructor.
	// The constructor throws a RemoteException.
	boolean user_change = false;
	String userlist[] = new String[100];
	
	//用來儲存線上的使用者
	List<String> onlineUserList = new LinkedList<String>(); 
	
	public ArithmeticRMIImpl() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
	}
		
	public int register(String username) throws java.rmi.RemoteException // return 0 = register success ; 1 = account already exist
	{
		int exist = 0;
		BufferedWriter fw = null;
		BufferedReader reader = null;
		File file = new File(".\\User.txt");
		try {
			fw = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		try {
			reader = new BufferedReader(new FileReader(file));
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		String str = "";
		try {
			while(true) 
			{
				str = reader.readLine();
				if(str == null)
					break;
				if(str.compareTo(username)== 0)
				{
					exist = 1;
				}
			}
			if(exist==0) 
			{
				fw.append(username);
				fw.newLine();
				fw.close();
				reader.close();
				return 0;
			}else {
				reader.close();
				fw.close();
				return -1;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}

	public int login(String username)throws java.rmi.RemoteException // return 0 = login success ; -1 = account isn`t exist ; -2 = already login ;
	{
		int exist = 0;
		//BufferedWriter fw = null;
		BufferedReader reader = null;
		//BufferedWriter fwo = null;
		File file = new File(".\\User.txt");
		//File fileo = new File(".\\Online.txt");
		try {
			//fw = new BufferedWriter(new FileWriter(file, true));
			reader = new BufferedReader(new FileReader(file));
			//fwo = new BufferedWriter(new FileWriter(fileo, true));
		} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
		}
		String str = "";
		try {
			while(true) 
			{
				str = reader.readLine();
				if(str == null)
					break;
				if(str.compareTo(username)== 0)
				{
					exist = 1;
				}
			}
			if(exist == 1) 
			{
				//fwo.append(username);
				//fwo.newLine();
				//fwo.close();
				if(!onlineUserList.contains(username)) {
					reader.close();
					user_change = true;
					onlineUserList.add(username);
					return 0; // login success
				}else {
					reader.close();
					return -2; // this user already login
				}
				//fw.close();
				
				
			}else if(exist == 0){
				reader.close();
				//fw.close();
				//fwo.close();
				return -1; 
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -2;
	}
	
	public String[] read_chat()throws java.rmi.RemoteException
	{
		BufferedReader reader = null;
		File files = new File(".\\chat.txt");
		try {
			reader = new BufferedReader(new FileReader(files));
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			String strs = null;
			String strarr[] = new String[1000];
			int i=0;;
			while(true) 
			{
				strs = reader.readLine();
				if(strs == null)
					break;
				else {
					strarr[i] = strs;
					i++;
				}
			}
			return 	strarr;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public int check_have_new_comment(int index)throws java.rmi.RemoteException // return 0 = no new comment no need to update chat ; 1 = have new comment read the chat
	{
		BufferedReader reader = null;
		File files = new File(".\\chat.txt");
		try {
			reader = new BufferedReader(new FileReader(files));
		} 
		catch (FileNotFoundException e1) 
		{
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try 
		{
			String strs = null;
			int i = 0;
			while(true)
			{
				strs = reader.readLine();
				if(strs==null)
					break;
				else 
				{
					i++;
				}
			}
			if(index!=i) {
				return 1; // have new comment
			}else {
				return 0;
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public void add_comment(String username,String comment) throws java.rmi.RemoteException
	{
		BufferedWriter fwr = null;
		File file = new File(".\\chat.txt");
		try {
			fwr = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("MM-dd HH:mm:ss");
		Date date = new Date();
		String reply_comment = sdf.format(date) + "   " + username + " : " + comment ;
		try {
			fwr.append(reply_comment);
			fwr.newLine();
			fwr.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void logout(String username) 
	{
		BufferedWriter fwr = null;
		//BufferedWriter fwt = null;
		//BufferedReader reader = null;
		String str="";
		File file = new File(".\\chat.txt");
		//File fileo = new File(".\\Online.txt");
		//File create_file = new File(".\\temp.txt"); // read online user to temp
		try {
			fwr = new BufferedWriter(new FileWriter(file, true));
			//fwt = new BufferedWriter(new FileWriter(create_file, true));
			//reader = new BufferedReader(new FileReader(fileo));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
		Date date = new Date();
		String reply_comment ="-----   " + username + " left the chat room  " + sdf.format(date)+ "   -----";
		try {
			onlineUserList.remove(username);
			fwr.append(reply_comment);
			fwr.newLine();
			fwr.close();
			/*
			while(true) 
			{
				str = reader.readLine();
				if(str==null) 
					break;
				if(str.compareTo(username)==0)
					continue;
				else {
					fwt.append(str);
					fwt.newLine();
				}
			}
			
			fwt.close();
			reader.close();
			*/
			//fileo.delete();
			//create_file.renameTo(fileo);
			user_change = true;
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public void join(String username) 
	{
		BufferedWriter fwr = null;
		File file = new File(".\\chat.txt");
		try {
			fwr = new BufferedWriter(new FileWriter(file, true));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		SimpleDateFormat sdf = new SimpleDateFormat();
		sdf.applyPattern("yyyy-MM-dd HH:mm:ss a");
		Date date = new Date();
		String reply_comment = "-----   " + username + " join the chat room  " + sdf.format(date) + "   -----";
		try {
			fwr.append(reply_comment);
			fwr.newLine();
			fwr.close();
		}catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public List<String> getOnlineuser() // read online.txt put in userlist array and return userlist to client
	{
		return onlineUserList;
		/*
		BufferedReader reader = null;
		File file = new File(".\\Online.txt");
		String str = "";
		int i = 0;
		try {
			reader = new BufferedReader(new FileReader(file));
			while(true) {
				str = reader.readLine();
				if(str == null)
					break;
				else {
					userlist[i] = str;
					i++;
				}
			}
			return userlist;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		*/
	}
	
	public	boolean check_user_change(String[] online_user)throws java.rmi.RemoteException // online_user = client current userlist coompare it with server userlist if different return true
	{
		int i = 0;
		user_change = false;
		while(true) {
			if(userlist[i]==null||online_user[i]==null)
				break;
			if(userlist[i].compareTo(online_user[i])==0) {
				i++;
				continue;
			}else {
				user_change = true;
				break;
			}
		}
		return user_change;
	}
}