package constraint;

import player.position.Position;

public class Brick {

    private Position pos;
    private String nation;
    private String league;
    private String club;

    public Brick(Position pos) {
        this.pos = pos;
    }

    public Brick(Position pos, String nation) {
        this.pos = pos;
        this.nation = nation;
    }

    public Brick(Position pos, String nation, String league) {
        this.pos = pos;
        this.nation = nation;
        this.league = league;
    }

    public Brick(Position pos, String nation, String league, String club) {
        this.pos = pos;
        this.nation = nation;
        this.league = league;
        this.club = club;
    }

    public Position getPos() {
        return pos;
    }

    public void setPos(Position pos) {
        this.pos = pos;
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

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    // alias to getClub because idk why i wasn't consistent earlier
    public String getTeam() {
        return club;
    }

    public void setTeam(String club) {
        this.club = club;
    }
}