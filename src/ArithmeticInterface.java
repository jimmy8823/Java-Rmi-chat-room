import java.rmi.Remote;

public interface ArithmeticInterface extends Remote
{
	public int register(String username) throws java.rmi.RemoteException;
	public int login(String username)throws java.rmi.RemoteException;
	public String[] read_chat()throws java.rmi.RemoteException;
	public int check_have_new_comment(int index)throws java.rmi.RemoteException;
}