public class BiddingItems{
	private String item;
	private float startingPrice;
	private float newPrice;
	private boolean itemIsSold;
	private ClientHandler highestBidder;

	public BiddingItems(String item, float startingPrice){
		this.setItem(item);
		this.setStartingPrice(startingPrice);
		this.setNewPrice(newPrice);
		this.setItemIsSold(false);
}

public String getItem(){
	return item;
}

public void setItem(String Item){
	this.item = Item;
}

public float getStartingPrice(){
	return startingPrice;
}

public void setStartingPrice(float startingPrice){
	this.startingPrice = startingPrice;
}

public float getNewPrice(){
	return newPrice;
}

public void setNewPrice(float newPrice){
	this.newPrice = newPrice;
}

public boolean getItemIsSold(){
	return itemIsSold;
}

public void setItemIsSold(boolean ItemIsSold){
	this.itemIsSold = ItemIsSold;
}

public ClientHandler getHighestBidder()
{
    return highestBidder;
}

public void setHighestBidder(ClientHandler highestBidder)
{
    this.highestBidder = highestBidder;
}

}