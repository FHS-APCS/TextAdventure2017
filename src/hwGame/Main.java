package hwGame;
 
import java.util.Scanner;
 
import hwGame.Level.Room;
 
public class Main {
 
	public static void main(String[] args) {
		Level g = new Level();
		g.addRoom("hall", "A long, dark, and spookledly corridor");
		g.addRoom("closet", "A plain old boring closet");
		g.addRoom("dungeon", "An eerie, chilling dungeon");
 
		g.addDirectedEdge("hall", "dungeon");
		g.addUndirectedEdge("hall", "closet");
 
		Item Coin = new Item("Coin", " a shiny coin");
		Item potion = new Item("Potion", " a strange green liquid");
 
		g.getRoom("closet").addItem(Coin);
		g.getRoom("closet").addItem(potion);
 
		Player player = new Player(g.getRoom("hall"), "Bob", "the tester");
 
		String response = "";
		Scanner in = new Scanner(System.in);
 
		listCommands();
 
		do {
			System.out.println("You're currently in the " + player.getCurrentRoom().getName());
			System.out.println("What do you want to do? >");
 
			response = in.nextLine();
 
			if (response.substring(0, 2).equals("go")) {
				Level.Room nextRoom = player.getCurrentRoom().getNeighbor(response.substring(4, response.indexOf(">")));
				boolean successful = player.move(nextRoom);
				if (!successful) {
					System.out.println("You can't go there, try again");
				} else {
					System.out.println("You have entered " + player.getCurrentRoom().getDescription());
				}
			} else if (response.equals("look")) {
				System.out.println("Neighbors: " + player.getCurrentRoom().getNeighborNames());
				System.out.println("Items: " + player.getCurrentRoom().getItemDisplayList());
			} else if (response.substring(0, 4).equals("save")) {
				g.saveLevelToFile(response.substring(6, response.indexOf(">")));
			} else if (response.substring(0, 3).equals("add")) {
				g.addRoom(response.substring(5, response.indexOf(">")), "A player added room");
				g.addUndirectedEdge(response.substring(5, response.indexOf(">")), player.getCurrentRoom().getName());
				System.out.println("Added: " + response.substring(5, response.indexOf(">")));
 
			} else if (response.substring(0, 4).equals("bond")) {
				g.addDirectedEdge(player.getCurrentRoom().getName(), response.substring(6, response.indexOf(">")));
				System.out.println("Connected to: " + response.substring(6, response.indexOf(">")));
 
			} else if (response.substring(0, 4).equals("take")) {
				Item item = player.getCurrentRoom().removeItem(response.substring(6, response.indexOf(">")));
				if (item == null) {
					System.out.println("Item does not exist");
				} else {
					player.takeItem(item);
					System.out.println("You took the " + response.substring(6, response.indexOf(">")));
				}
			} else if (response.substring(0, 4).equals("drop")) {
				Item item = player.dropItem(response.substring(6, response.indexOf(">")));
				if (item == null) {
					System.out.println("You do not have that");
				} else {
					player.getCurrentRoom().addItem(item);
					System.out.println("You dropped the " + response.substring(6, response.indexOf(">")));
				}
 
			} else if (response.equals("quit")) {
				System.out.println("Game Over");
				System.exit(0);
			} else {
				listCommands();
			}
 
		} while (!response.equals("quit"));
		System.out.println("Game Over");
 
	}
 
	public static void listCommands() {
		System.out.println("Type 'go < roomName>' to travel to a new room");
		System.out.println("Type 'look' to display all neighbors of your current room");
		System.out.println("Type 'add < roomName>' to add a new room connected to yours");
		System.out.println("Type 'bond < roomName>' to add a connection to a new room");
		System.out.println("TYpe 'Take < itemName>' to pick up an item");
		System.out.println("TYpe 'Drop < itemName>' to drop an item");
		System.out.println("Type 'quit' to leave");
		System.out.println("Type 'save < path> to save your game");
 
	}
 
}