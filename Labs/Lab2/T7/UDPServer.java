import java.net.*;
import java.io.*;

public class UDPServer{
    public static void main(String args[]){
    	DatagramSocket aSocket = null;
        int randomNumber = (int) (Math.random() * 100);
		try{
	    	aSocket = new DatagramSocket(6789);
					// create socket at agreed port
			byte[] buffer = new byte[1000];
 			while(true){
 				DatagramPacket request = new DatagramPacket(buffer, buffer.length);
  				aSocket.receive(request);

                String string = new String(request.getData(), request.getOffset(), request.getLength());
                
                if (Integer.parseInt(string) > randomNumber){
                    System.out.println("LOWER than " + string);
                }

                else if(Integer.parseInt(string) < randomNumber){
                    System.out.println("HIGHER than " + string);
                }
                else if(Integer.parseInt(string) == randomNumber){
                    System.out.println("CORRECT! number is " + string);
                }

    			DatagramPacket reply = new DatagramPacket(request.getData(), request.getLength(),
    				request.getAddress(), request.getPort());
    			aSocket.send(reply);
    		}
		}
        catch (SocketException e){System.out.println("Socket: " + e.getMessage());
		}
        catch (IOException e) {System.out.println("IO: " + e.getMessage());
		}
        finally {if(aSocket != null) aSocket.close();}
    }
}
