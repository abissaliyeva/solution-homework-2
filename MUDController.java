package mud;

import java.util.List;
import java.util.Scanner;

public class MUDController{
    private final Player player;
    private boolean running;
    Scanner in;

    public MUDController(Player player) {
        this.player = player;
        this.running = true;
        this.in = new Scanner(System.in);
    }

    public void runGameLoop(){
        System.out.println(">");
        while (running) {
            String input = in.nextLine();
            handleInput(input);
            if (input.equals("quit") || input.equals("exit")) {
                running  = false;
            }
        }
    }


    private void handleInput(String input) {
        String[] split = input.split(" ");
        String command = split[0];

        String argument;
        if (split.length > 1) {
            argument = split[1];
        } else argument = "";

        if (command.equals("look")) {
            lookAround();
        } else if (command.equals("move")) {
            if (argument.isEmpty()) {
                System.out.println("Move where? Use: move <forward|back|left|right>");
                return;
            }
            move(argument);
        } else if (command.equals("pick")) {
            if (argument.equals("up")) {
                pickUp(split[2]);
            } else {
                System.out.println("invalid command");
            }
        } else if (command.equals("inventory")) {
            checkInventory();
        } else if (command.equals("help")) {
            showHelp();
        } else {
            System.out.println("Unknown command.");
        }
    }

    private void lookAround() {
        Room room = player.getCurrentRoom();
        room.describe();
    }

    private void move(String direction) {
        Room room = player.getCurrentRoom();
        Room nextRoom = room.getConnectedRoom(direction);

        if (nextRoom != null) {
            player.setCurrentRoom(nextRoom);
            lookAround();
        } else {
            System.out.println("You can't go that way!");
        }


    }

    private void pickUp(String arg) {
        Room room = player.getCurrentRoom();
        Item item = room.getItem(arg);

        if (item == null) {
            System.out.println("No item named " + arg + " here!");
        } else {
            player.addItem(item);
            room.removeItem(item);
            System.out.println("You pick up the " + arg);
        }
    }

    private void checkInventory() {
        List<Item> inventory = player.getInventory();
        if (inventory.isEmpty()) {
            System.out.println("Your inventory is empty.");
        } else {
            System.out.println("Inventory:");
            for (Item item : inventory) {
                System.out.print(item.getName() + " ");
            }
        }
    }

    private void showHelp() {
        System.out.println("Available commands:");
        System.out.println("look move <forward|back|left|right> pick up inventory help quit/exit");
    }

    public static void main(String[] args) {
        Room entrance = new Room("Entrance Hall", "A large room");
        Room library = new Room("Library", "There are so many books");
        Room dasement = new Room("Basement", "Vry dark and cold atmosphere");

        entrance.connectRoom("forward", library);
        library.connectRoom("back", entrance);
        library.connectRoom("right", dasement);
        dasement.connectRoom("left", library);

        entrance.addItem(new Item("sword"));
        entrance.addItem(new Item("shield"));
        library.addItem(new Item("book"));
        library.addItem(new Item("candle"));
        dasement.addItem(new Item("helmet"));
        dasement.addItem(new Item("armor"));

        Player player = new Player(entrance);
        MUDController controller = new MUDController(player);
        System.out.println("Welcome to the game!");
        controller.runGameLoop();
    }
}
