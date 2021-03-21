package player;

public class PlayerNotFoundException extends Exception {

    private String name;

    public PlayerNotFoundException(String name) {
        this.name = name;
    }

    public void printName() {
        System.out.println("not able to find player: " + this.name);
    }
}