import java.io.*;
import java.net.*;
import java.util.*;

public class TCPEchoClient
{
	private static InetAddress host;
	private static final int PORT = 1234;

	public static void main(String[] args)
	{
		try
		{
			host = InetAddress.getLocalHost();
		}
		catch(UnknownHostException uhEx)
		{
			System.out.println("Host ID not found!");
			System.exit(1);
		}
		accessServer();
	}

	private static void accessServer()
	{
		Socket link = null;						//Step 1.

		try
		{
			link = new Socket(host,PORT);		//Step 1.

			Scanner input = new Scanner( link.getInputStream());//Step 2.
			ObjectOutputStream output = new ObjectOutputStream(link.getOutputStream());//Step 2.
			//Set up stream for keyboard entry...
			Scanner userEntry = new Scanner(System.in);

			String message, response;

			String name;
			int age;
			String stringage;
			String school;
			int studentNo;
			String stringstudentNo;


			do
			{
				System.out.print("Enter Name: ");
				name =  userEntry.nextLine();

				System.out.print("Enter age: ");
				stringage =  userEntry.nextLine();
				age = Integer.parseInt(stringage);

				System.out.print("Enter school: ");
				school =  userEntry.nextLine();

				System.out.print("Enter student Number: ");
				stringstudentNo =  userEntry.nextLine();
				studentNo = Integer.parseInt(stringstudentNo);

				Person person = new Person(name,age,school,studentNo);

				output.writeObject(person); 		//Step 3.
				response = input.nextLine();	//Step 3.
				System.out.println("\nSERVER> " + response);
			}while (!name.equals("***CLOSE***"));
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}

		finally
		{
			try
			{
				System.out.println(
							"\n* Closing connection... *");
				link.close();					//Step 4.
			}
			catch(IOException ioEx)
			{
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}
