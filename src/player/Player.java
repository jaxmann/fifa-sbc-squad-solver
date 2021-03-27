package player;

import java.io.*;
import java.lang.reflect.Field;

public class Player implements Serializable {

    private String name;
    private String team;
    private String nation;
    private String league;
    private BasePosition pos;
    private double price;
    private int rating;
    private CardType version;
    private boolean loyalty;

    public Player(String name, String team, String nation, String league, BasePosition pos, CardType version, double price, int rating, boolean loyalty) {
        this.name = name;
        this.team = team;
        this.nation = nation;
        this.league = league;
        this.pos = pos;
        this.rating = rating;
        this.version = version;
        this.price = price;
        this.loyalty = loyalty;
    }

    public Player(String name, String team, String nation, String league, String pos, CardType version, double price, int rating, boolean loyalty) {
        this.name = name;
        this.team = team;
        this.nation = nation;
        this.league = league;
        this.pos = Position.translateStringPosToBasePosEnum(pos);
        this.rating = rating;
        this.version = version;
        this.price = price;
        this.loyalty = loyalty;
    }

    // for bricks - league/nation/team can be null - make sure to handle this in chem engine
    public Player(String name, String team, String nation, String league, BasePosition pos) {
        this.name = name;
        this.team = team;
        this.nation = nation;
        this.league = league;
        this.pos = pos;
    }

    @Override
    public boolean equals(Object o) {
        if (getClass() != o.getClass()) {
            return false;
        }
        Player p = (Player) o;
        try {
            return this.getName().equals(p.getName()) &&
                    this.getTeam().equals(p.getTeam()) &&
                    this.getNation().equals(p.getNation()) &&
                    this.getLeague().equals(p.getLeague()) &&
                    this.getRating() == p.getRating() &&
                    this.getPrice() == p.getPrice() &&
                    this.getLoyalty() == p.getLoyalty() &&
                    this.getVersion() == p.getVersion() &&
                    this.getPosBase() == p.getPosBase();
        } catch (NullPointerException e) {
            System.out.println("Unable to compare player - NPE");
            return false;
        }

    }

    @Override
    public int hashCode() {
        return getName().hashCode() ^ getTeam().hashCode() ^ getName().hashCode() ^ getNation().hashCode() ^ getLeague().hashCode()
                ^ getVersion().hashCode() ^ getPosBase().hashCode();
    }

    public Player deepClone() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(baos);
            oos.writeObject(this);

            ByteArrayInputStream bais = new ByteArrayInputStream(baos.toByteArray());
            ObjectInputStream ois = new ObjectInputStream(bais);
            return (Player) ois.readObject();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    public String toString() {
        return this.name + " ~ " + this.rating;
    }

    public Player(int rating) {
        this.rating = rating;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public boolean getLoyalty() {
        return loyalty;
    }

    public void setLoyalty(boolean loyalty) {
        this.loyalty = loyalty;
    }

    public String getName() {
        return name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public CardType getVersion() {
        return version;
    }

    public void setVersion(CardType version) {
        this.version = version;
    }

    public BasePosition getPosBase() {
        return pos;
    }

    public void setPosBase(BasePosition pos) {
        this.pos = pos;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }
}
