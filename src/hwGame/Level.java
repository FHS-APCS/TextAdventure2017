package hwGame;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
 
import hwGame.Level.Room;
 
public class Level implements Serializable {
	private HashMap< String, Room> rooms;
 
	public Level() {
		rooms = new HashMap< String, Room>();
	}
 
	public boolean saveLevelToFile(String path) {
		try {
			FileOutputStream fileOut = new FileOutputStream(path);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(this);
			out.close();
			fileOut.close();
			System.out.printf("Serialized data is saved in " + path);
			return true;
		} catch (IOException i) {
			i.printStackTrace();
		}
		return false;
	}
 
	public static Level loadFromFile(String path) {
		Level g = null;
		try {
			FileInputStream fileIn = new FileInputStream(path);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			g = (Level) in.readObject();
			in.close();
			fileIn.close();
		} catch (IOException i) {
			i.printStackTrace();
			return g;
		} catch (ClassNotFoundException c) {
			System.out.println("Graph not found");
			c.printStackTrace();
			return g;
		}
		return g;
 
	}
 
	public void addRoom(String name, String description) {
		rooms.put(name, new Room(name, description));
	}
 
	public void addDirectedEdge(String name1, String name2) {
		(getRoom(name1)).addNeighbor(getRoom(name2));
	}
 
	public void addUndirectedEdge(String name1, String name2) {
		(getRoom(name1)).addNeighbor(getRoom(name2));
		(getRoom(name2)).addNeighbor(getRoom(name1));
 
	}
 
	public Room getRoom(String name) {
		return rooms.get(name);
	}
 
	public class Room implements Serializable {
		private String name;
		private HashMap< String, Room> neighbors;
		private String description;
		private List< Item> items;
 
		private Room(String name, String description) {
			neighbors = new HashMap< String, Room>();
			this.name = name;
			this.description = description;
			this.items = new ArrayList< Item>();
 
		}
 
		public void addItem(Item item) {
			items.add(item);
		}
 
		public Item removeItem(String itemName) {
			for (int i = 0; i <  items.size(); i++) {
				if (items.get(i).getName().equals(itemName)) {
					return items.remove(i);
				}
			}
			return null;
		}
 
		public String getItemDisplayList(){
			String output = "";
			for(Item item : items){
				output += item.getName() + " ";
			}
			return output;
		}
		
		public String getDescription() {
			return description;
		}
 
		public void setDescription(String description) {
			this.description = description;
		}
 
		public String getName() {
			return name;
		}
 
		private void addNeighbor(Room n) {
			neighbors.put(n.getName(), n);
		}
 
		public String getNeighborNames() {
			String names = "";
 
			for (String n : neighbors.keySet()) {
				names += (n + " ");
			}
			return names;
 
		}
 
		public Room getNeighbor(String name) {
			for (String n : neighbors.keySet()) {
				if (n.equals(name)) {
					return neighbors.get(n);
				}
			}
			return null;
		}
 
		public boolean hasNeighbor(Room nextRoom) {
			return neighbors.containsKey(nextRoom.getName());
		}
	}
}