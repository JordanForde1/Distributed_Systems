import java.rmi.RemoteException;

public class startclient{

	public static void main(String[] args){
		try{
			ChatClient chatclient = new ChatClient(args[0]);
		}

		catch(RemoteException e){
			e.printStackTrace();
		}
		
	}
}