import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Timer;
import java.util.TimerTask;
import java.io.*;
import java.util.Iterator;

public class Server implements Runnable{

    //Veriables
    private static ServerSocket serverSocket = null ;
    private static int PORT;
    private Socket clientSocket = null;
    private BiddingItems currentItem;
    private ArrayList<ClientHandler> clientArray = new ArrayList<ClientHandler>();
    private ArrayList<BiddingItems> item = new ArrayList<BiddingItems>();
    private Thread thread = null;
    private Timer itemTimer = new Timer();
    private int Index = 0;
    Scanner scanner = new Scanner(System.in);
    int seconds = 0;
    boolean newTimer = true;
    boolean noItems = false;

    public static void main(String args[]){
        PORT = Integer.parseInt(args[0]);
        new Server();
    }

    public Server(){
        try{
            //Server connects and enter in items for auction
            serverSocket = new ServerSocket(PORT);
            System.out.println("\nServer set up!\n\nPlease enter at lest 5 items and price them.\nWhen you are finish connect the clients and then type EXIT in the Server.");
            EnterItems();

            while(item.isEmpty() || item.size() < 5){
                System.out.println("Please enter items into the auction.");
                EnterItems();
            }

            //Allow clients to connect
            if (thread == null){
                thread = new Thread(this);
                thread.start();
            }
        }
        //Fails to connect
        catch (IOException ioEx){
            System.out.println("\nUnable to set up port!");
            System.exit(1);
        }
    }

    //Enteing items for action
    private void EnterItems(){
        String ItemName;
        float StartingPrice;
        do{
            System.out.println("\nPlease enter items you want to auction or EXIT to quit");
            ItemName = scanner.nextLine();

            if(ItemName.equals("EXIT")){
                break;
            }

            System.out.println("Please Enter An Amount:");
            while(true){
                try{
                        StartingPrice = Float.parseFloat(scanner.nextLine());
                        break;
                    }

                catch(Exception exc){
                        System.out.println("\nPlease enter A Vaild Amount for the item: ");
                    }
            }
        BiddingItems items = new BiddingItems (ItemName, StartingPrice);
        item.add(items);
        }
    //When user is done entering items
    while(ItemName != "EXIT");
    }

    //Server thread for incoming clients
    public void run(){
        while(thread != null){
            try{
                clientSocket = serverSocket.accept();
                System.out.println("Client Connected");
                ClientHandler client = new ClientHandler(clientSocket, clientArray, this);
                clientArray.add(client);
                client.start();
                currentItem = item.get(Index);
                displayItem();
            }

            catch(IOException ioExc){
                ioExc.printStackTrace();
            }
            //Starts the time and rests it
            if(newTimer){
                startTimer();
                newTimer = false;
            }
        }
    }
 
    private void startTimer(){ 
        itemTimer.schedule(new TimerTask(){
            @Override
            public void run(){
                //Check if item has sold
                seconds++;
                if(seconds % 60 == 0){
                if(currentItem.getNewPrice() > currentItem.getStartingPrice()){
                    currentItem.setItemIsSold(true);
                    synchronized(this){
                    for(ClientHandler client : clientArray){
                        client.getOutput().println("" + currentItem.getItem() + 
                        " sold to " + currentItem.getHighestBidder().getClientDetails() + 
                        "!\n");
                        }
                      }
                    }

                //Item did not sell
                else{
                synchronized(this){
                for(ClientHandler client : clientArray){
                    client.getOutput().println("" + currentItem.getItem() + 
                    " did not sell. Will be auctioned again.\n");
                    }
                  }
                }
                int count = 0;

                do{
                //resetting index
                itemIndex();
                currentItem = item.get(Index);
                count++;
                if(count > item.size()){
                    noItems = true;
                    break;
                    }
                }

                while(currentItem.getItemIsSold());

                //All items sold
                if(noItems){
                    synchronized(this){
                    for(ClientHandler client : clientArray){
                        client.getOutput().println("All items sold.");
                        }
                    }
                itemTimer.cancel();
                }

                else{
                displayItem();
                seconds = 0;
                }
              }
            }
        }, 0,1 * 1000);
    }

    //Bid on an item
    public void PlaceBid(float amount, ClientHandler clientbid){
        if(amount > currentItem.getNewPrice()){
            currentItem.setNewPrice(amount);
            currentItem.setHighestBidder(clientbid);
            seconds = 0;
            displayAmount();
        }

        else{
            clientbid.getOutput().println("Needs to be greater then " 
            + currentItem.getNewPrice() + ".\n");
        }
    }

    //Show the highest bid and client who placed it
    public void displayAmount(){
        synchronized(this){
            for( ClientHandler client: clientArray){
                 client.getOutput().println("Bid of " + currentItem.getNewPrice() +
                  " placed by " + currentItem.getHighestBidder().getClientDetails() + 
                  " for " + currentItem.getItem() + 
                  ".\n");
            }
        }
    }

    //The item on sale and its starting price
    private void displayItem(){
        synchronized(this){
            for(ClientHandler client : clientArray){
                client.getOutput().println("" + currentItem.getItem() +
                " price starts at " + currentItem.getStartingPrice() +
                ".\n");
            }
        }
    }

    //Incress the index
    private void itemIndex(){
        Index++;
        if(Index == item.size())
        {
            Index = 0;
        }
    }
}

class ClientHandler extends Thread{
    //Veriables
    private String clientDetails = null;
    private final ArrayList<ClientHandler> clientArray;
    private Socket clientSocket = null;
    private BufferedReader input = null;
    private PrintStream  output = null;
    private boolean Connected = false;
    Server server;
    

    public ClientHandler(Socket clientSocket, ArrayList<ClientHandler> clientArray,Server server){
        this.clientSocket = clientSocket;
        this.clientArray = clientArray;
        this.server = server;

        try{
            input = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            output = new PrintStream(clientSocket.getOutputStream(),true);
        }
        catch(IOException ioEx){
            ioEx.printStackTrace();
        }
    }

    public void run(){
        try{
            synchronized(this){
                output.println("Please enter your name.\n");
                clientDetails = input.readLine();

                //A client has joined
                for(ClientHandler client : clientArray){
                    if(client != this){
                        Connected = true;
                        client.output.println(clientDetails + " has joined.\n");
                    }
                }
            }

            //Let the client know what is happening
            output.println("Please enter your bid. Items will on sale for 60 seconds and reset if a bid is entered.\nType EXIT to leave the auction or enter a bid.\n");

            while(true){
                String bid = input.readLine();
                if(!bid.equals("null")){
                    if(bid.equals("EXIT")){
                        break;
                    }
                    float bidAmount = Float.parseFloat(bid);
                    server.PlaceBid(bidAmount, this);
                }
            }

            //A client has left
            synchronized(this){
                for(ClientHandler client : clientArray){
                    if(client != this){
                        client.output.println("Client " + clientDetails + 
                        " has left.");
                    }
                }
            }

            //Removing client from list
            synchronized(this){
                Iterator<ClientHandler> iterator = clientArray.iterator();

                while(iterator.hasNext()){
                    ClientHandler clientHandler = iterator.next();

                    if(clientHandler == this){
                        iterator.remove();
                    }
                }
            }
            input.close();
            output.close();
            clientSocket.close();
        }
        catch(IOException ioExc){
            ioExc.printStackTrace();
        }
    }
    
    public PrintStream getOutput(){
        return output;
    }
    public String getClientDetails(){
        return clientDetails;
    }
}