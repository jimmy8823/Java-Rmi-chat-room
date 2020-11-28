import java.rmi.Remote;

public interface ArithmeticInterface extends Remote
{
	public int register(String username) throws java.rmi.RemoteException;
	
	
}