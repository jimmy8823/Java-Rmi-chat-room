import java.rmi.Remote;
import java.util.List;

public interface ArithmeticInterface extends Remote
{
	public int register(String username) throws java.rmi.RemoteException;
	public int login(String username)throws java.rmi.RemoteException;
	public String[] read_chat()throws java.rmi.RemoteException;
	public int check_have_new_comment(int index)throws java.rmi.RemoteException;
	public void add_comment(String username,String comment) throws java.rmi.RemoteException;
	public void logout(String username) throws java.rmi.RemoteException;
	public void join(String username) throws java.rmi.RemoteException;
	public List<String> getOnlineuser() throws java.rmi.RemoteException;
	public boolean check_user_change(String[] online_user)throws java.rmi.RemoteException;
}