import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;
 
public class ChatClient  extends UnicastRemoteObject implements ChatClienInterface{
	private String roomName;
	private String name;
	public ChatClient (String n) throws RemoteException {
		name=n;
		roomName = "";
		ChatServerInterface server = new ChatServer();
		Scanner scanner = new Scanner(System.in);
		try {
			String chatboi;
			server = (ChatServerInterface) Naming.lookup("rmi://localhost/Groupchat");
			server.login(this);
			do {
				chatboi = scanner.nextLine();
				server.publish(chatboi,this);
			}
			
			while (!chatboi.equals("EXIT"));
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void tell(String st) throws RemoteException{
		System.out.println(st);
	}
	public String getName() throws RemoteException{
		return name;
	}

	public void setGroup(String roomName) throws RemoteException{
		this.roomName = roomName;
	}

	public String getGroup() throws RemoteException{
		return roomName;
	}
}
