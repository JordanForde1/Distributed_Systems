import java.io.*;
import java.net.*;
import java.util.*;

public class Server{
	private static ServerSocket serverSocket;
	private static final int PORT = 1234;
	private static Vector<Socket> clients;

	public static void main(String[] args) throws IOException{
		try{
			clients = new Vector<Socket>();
			serverSocket = new ServerSocket(PORT);
			System.out.println("\nServer set up!");
		}
		catch (IOException ioEx){
			System.out.println("\nUnable to set up port!");
			System.exit(1);
		}

		do{
			Socket client = serverSocket.accept();

			clients.add(client);

			System.out.println("\nNew client accepted.\n");
			ClientHandler handler = new ClientHandler(client, clients);

			handler.start();
		}while (true);
	}
}
		
class ClientHandler extends Thread{
	private Socket client;
	private Scanner input;
	private PrintWriter output;
	private Vector<Socket> clients;

	public ClientHandler(Socket socket, Vector clients){
		this.clients = clients;
		client = socket;

		try{
			input = new Scanner(client.getInputStream());
			output = new PrintWriter(client.getOutputStream(),true);
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}
	}

	public void broadcast(String message){
		for(Socket cli : clients){
			if(cli != client){
				try{
					synchronized(this){
						output = new PrintWriter(cli.getOutputStream(),true);
						output.println("ECHO: " + message);
					}
				}catch(IOException e){
					e.printStackTrace();
				}
			}
		}
	}

	public void run(){
		String received;
		do{
			received = input.nextLine();
			broadcast(received);
		}while (!received.equals("QUIT"));

		try{
			if (client!=null){
				System.out.println("Closing down connection...");
				client.close();
			}
		}
		catch(IOException ioEx){
			System.out.println("Unable to disconnect!");
		}
	}
}
