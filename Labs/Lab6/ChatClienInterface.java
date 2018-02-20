import java.rmi.*;
 
public interface ChatClienInterface extends Remote{	
	public void tell (String name)throws RemoteException ;
	public String getName()throws RemoteException ;
	public void setGroup(String roomName) throws RemoteException;
	public String getGroup()throws RemoteException ; 
}
