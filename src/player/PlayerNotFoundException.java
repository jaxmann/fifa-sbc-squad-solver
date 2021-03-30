package player;

public class PlayerNotFoundException extends Exception {

    private String name;

    public PlayerNotFoundException(String name) {
        this.name = name;
    }

    @Override
    public String getMessage() {
        return "not able to find player: " + this.name;
    }
}