import java.io.*;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;

public class Client implements Runnable{
    //Veriables
	private static Socket clientSocket = null;
    private static BufferedReader input = null;
    private static PrintStream output = null;
    private static BufferedReader user;
    private static boolean closed = false;
    private static String hostName;
    private static int PORT;


	public static void main(String[] args){
        try{
            //set up the host and port
            hostName = args[0];
            PORT = Integer.parseInt(args[1]);
            InetAddress host = InetAddress.getByName(hostName);
            clientSocket = new Socket(host, PORT);
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintStream(clientSocket.getOutputStream(), true);
            user = new BufferedReader(new InputStreamReader(System.in));
        }

        catch(UnknownHostException uhExc){
            uhExc.printStackTrace();
        }

        catch(IOException ioExc){
            ioExc.printStackTrace();
        }

        if(clientSocket != null && input != null && output != null){
            try{
                new Thread(new Client()).start();

                while(!closed){output.println(user.readLine());}
                input.close();
                output.close();
                user.close();
                clientSocket.close();
            }

            catch(IOException ioExc){
                ioExc.printStackTrace();
            }
        }
    }

	public void run(){
        String talk;

        try{
            while((talk = input.readLine()) != null){
                System.out.println(talk);
            }
        }

        catch(IOException ioExc){
            ioExc.printStackTrace();
        }
        closed = true;
    }
}

