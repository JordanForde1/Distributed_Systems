import java.io.*;
import java.net.*;
import java.util.*;

public class ConsumerClient
{
	private static InetAddress host;
	private static String hostName = "localhost";
	private static final int PORT = 1234;

	public static void main(String[] args)
	{
		try
		{
			host = InetAddress.getByName(hostName);;
		}
		catch(UnknownHostException uhEx)
		{
			System.out.println("\nHost ID not found!\n");
			System.exit(1);
		}
		sendMessages();
	}

	private static void sendMessages()
	{
		Socket socket = null;

		try
		{
			socket = new Socket(host,PORT);

			Scanner networkInput = new Scanner(socket.getInputStream());
			PrintWriter networkOutput = new PrintWriter(socket.getOutputStream(),true);

			//Set up stream for keyboard entry...
			Scanner userEntry = new Scanner(System.in);

			String message, response;
			do
			{
				System.out.print("Enter 1 or 0 ('QUIT' to exit): ");
				message =  userEntry.nextLine();

				if ( message.equals("1") || message.equals("0")){
					networkOutput.println(message);
					response = networkInput.nextLine();
					System.out.println("\nSERVER> " + response);
				}
				else{
					System.out.println("\nCan Ye Not Read Pal!");
				}

			}while (!message.equals("QUIT"));
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}

		finally
		{
			try
			{
				System.out.println("\nClosing connection...");
				socket.close();
			}
			catch(IOException ioEx)
			{
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}

