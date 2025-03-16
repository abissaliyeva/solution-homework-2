package mud;
import java.util.*;

public class Room {
    private String name;
    private String description;
    private Map<String, Room> connections;
    private List<Item> items;

    public Room(String name, String description) {
        this.name = name;
        this.description = description;
        this.connections = new HashMap<>();
        this.items = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public void connectRoom(String direction, Room room) {
        connections.put(direction, room);
    }

    public Room getConnectedRoom(String direction) {
        return connections.get(direction);
    }

    public void describe() {
        System.out.println("Room: " + name);
        System.out.println("Description: " + description);
        System.out.println("Items:");
        if (items.isEmpty()) {
            System.out.println(("No item"));
        } else {
            for (Item item : items) {
                System.out.print((item.getName()) + " ");
            }
        }
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public void removeItem(Item item) {
        items.remove(item);
    }

    public Item getItem(String itemName) {
        for (Item item : items) {
            if (item.getName().equalsIgnoreCase(itemName)) {
                return item;
            }
        }
        return null;
    }

    public Room cloneEntity() {
        return new Room(this.name,this.description);
    }
}
