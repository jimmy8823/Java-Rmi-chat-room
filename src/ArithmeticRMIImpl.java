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

public class ArithmeticRMIImpl extends UnicastRemoteObject implements ArithmeticInterface
{
	// This implementation must have a public constructor.
	// The constructor throws a RemoteException.
	public ArithmeticRMIImpl() throws java.rmi.RemoteException
	{
		super(); 	// Use constructor of parent class
	}
		
	public int register(String username) throws java.rmi.RemoteException
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

	public int login()throws java.rmi.RemoteException
	{
		
	}
}