import java.rmi.*;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
 
public class ChatServer extends UnicastRemoteObject implements ChatServerInterface{
	
	private ArrayList<ChatClienInterface> v =new ArrayList();	
	public ChatServer() throws RemoteException{}
	private String lastMessage;

	public boolean login(ChatClienInterface a) throws RemoteException{	
		System.out.println(a.getName() + "  got connected....");	
		a.tell("You have Connected successfully.");
	    for(int i=0;i<v.size();i++){
    		try{
    			ChatClienInterface tmp=(ChatClienInterface)v.get(i);
    			tmp.tell(a.getName() + " has connected");
    			}	
   			catch(Exception e){

    		}

		}

		v.add(a);
		return true;		
	}
	
	public void publish(String s, ChatClienInterface a) throws RemoteException{
	    System.out.println(s);

	    	if(s.startsWith("join")){
	    		a.setGroup(s.substring(5, s.length()));
	    	}

	    	else if(s.startsWith("lookup"))
	    	{
	    		for(int i=0;i<v.size();i++){
	    			a.tell(v.get(i).getName() + " is in group: " + v.get(i).getGroup());
	    		}
	    	}

	    	else{
		    	if(a.getGroup().equals("")){
		    		a.tell("Join group....");
		    	}

		    	else{
		    		if(s.startsWith("last message")){
		    			a.tell(lastMessage);
		    		}

		    		else
		    		{
		    			lastMessage = s;
		    			for(int i=0;i<v.size();i++){
		    			if(!v.get(i).equals(a))
		    			{
		    				if (v.get(i).getGroup().equals(a.getGroup())){

		    				try{
			    				ChatClienInterface tmp=(ChatClienInterface)v.get(i);
			    				tmp.tell(s);
			    			}	
			   				catch(Exception e){

			    			}

		    				}
		    			}
		    		}
		    		}
		  		 }
			}
	    }

	public ArrayList getConnected() throws RemoteException{
		return v;
	}
}
