import java.rmi.*;
import java.util.*;
 
public interface ChatServerInterface extends Remote{	
	public boolean login (ChatClienInterface a)throws RemoteException ;
	public void publish (String s,ChatClienInterface a)throws RemoteException ;
	public ArrayList getConnected() throws RemoteException ;
}