import java.io.*;
import java.net.*;
import java.util.*;

public class TCPEchoServer
{
   private static ServerSocket servSock;
   private static final int PORT = 1234;

   public static void main(String[] args)
   {
      System.out.println("Opening port...\n");
      try
      {
         servSock = new ServerSocket(PORT);      //Step 1.
      }
      catch(IOException ioEx)
      {
         System.out.println("Unable to attach to port!");
         System.exit(1);
      }
      do
      {
         handleClient();
      }while (true);
   }

   private static void handleClient()
   {
      Socket link = null;                        //Step 2.

      try
      {
         link = servSock.accept();               //Step 2.

         ObjectInputStream input = new ObjectInputStream(link.getInputStream()); //Step 3.
         PrintWriter output = new PrintWriter(link.getOutputStream(),true); //Step 3.
         Person person = (Person) input.readObject();
         int numMessages = 0;

         while (!person.equals("***CLOSE***"))
         {
            System.out.println("Message received.\n");
            numMessages++;
            System.out.println("Name: " + person.getName());
            System.out.println("\nAge: " + person.getAge());
            System.out.println("\nSchool: " + person.getSchool());
            System.out.println("\nStudent No: " + person.getstudentNo());
            output.println("Message " + numMessages+ ": " + "Person Name" + person.getName() + "Person Age" + person.getAge() + "Person School" + person.getSchool() + "Person Student number" + person.getstudentNo());   //Step 4.
            person = (Person) input.readObject();
         }
         output.println(numMessages+ " messages received.");//Step 4.
		}
		catch(IOException ioEx)
		{
			ioEx.printStackTrace();
		}

      catch(ClassNotFoundException ioEx)
      {
         System.out.println("Done");
      }

		finally
		{
			try
			{
				System.out.println(
								"\n* Closing connection... *");
				link.close();				    //Step 5.
			}
			catch(IOException ioEx)
			{
				System.out.println("Unable to disconnect!");
				System.exit(1);
			}
		}
	}
}
