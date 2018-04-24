package hwGame;
 
import java.util.ArrayList;
import java.util.List;
 
public class Player {
	private Level.Room currentRoom;
	private String name, description;
	private List< Item> items;
 
	public Player(Level.Room room, String name, String description) {
		this.currentRoom = room;
		this.name = name;
		this.description = description;
		items = new ArrayList< Item>();
 
	}
 
	public void takeItem(Item item) {
		items.add(item);
	}
 
	public Item dropItem(String itemName) {
		for (int i = 0; i <  items.size(); i++) {
			if (items.get(i).getName().equals(itemName)) {
				return items.remove(i);
			}
		}
		return null;
	}
 
	public String getInventory() {
		String output = "";
		for (Item item : items) {
			output += item.getName() + " ";
		}
		return output;
	}
 
	public boolean move(Level.Room nextRoom) {
		if (currentRoom.hasNeighbor(nextRoom)) {
			currentRoom = nextRoom;
			return true;
		}
		return false;
	}
 
	public boolean move(String nextRoom) {
		if (currentRoom.getNeighbor(nextRoom) == null) {
			return false;
		}
		return move(currentRoom.getNeighbor(nextRoom));
	}
 
	public Level.Room getCurrentRoom() {
		return currentRoom;
	}
 
	public void setCurrentRoom(Level.Room currentRoom) {
		this.currentRoom = currentRoom;
	}
 
	public String getName() {
		return name;
	}
 
	public void setName(String name) {
		this.name = name;
	}
 
	public String getDescription() {
		return description;
	}
 
	public void setDescription(String description) {
		this.description = description;
	}
 
}