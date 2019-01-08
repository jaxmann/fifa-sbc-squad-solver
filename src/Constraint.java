import java.util.ArrayList;

public class Constraint {

    private int minRating;
    private int minChem;

    //if constraintType==MIN_OF_CARDTYPE, then min_of_cardtype is some int and CardType should be set
    private CardType cardType;
    private int min_of_cardtype;
    private int exact_of_cardtype;

    //if constraintType==MIN_OF_CLUB, then min_of_club is some int and club is some string
    private String club;
    private int min_of_club;
    private int exact_of_club;

    //if constraintType==MIN_OF_NATION, then min_of_nation is some int and nation is some string
    private String nation;
    private int min_of_nation;
    private int exact_of_nation;

    //if constraintType==MIN_OF_LEAGUE, then min_of_league is some int and league is some string
    private String league;
    private int min_of_league;
    private int exact_of_league;

    private ArrayList<Brick> bricks;

    private ConstraintType type;

    public Constraint(ConstraintType type) {
        this.type = type;
    }

    public int getMinRating() {
        return minRating;
    }

    public void setMinRating(int minRating) {
        this.minRating = minRating;
    }

    public int getMinChem() {
        return minChem;
    }

    public void setMinChem(int minChem) {
        this.minChem = minChem;
    }

    public CardType getCardType() {
        return cardType;
    }

    public void setCardType(CardType cardType) {
        this.cardType = cardType;
    }

    public int getMin_of_cardtype() {
        return min_of_cardtype;
    }

    public void setMin_of_cardtype(int min_of_cardtype) {
        this.min_of_cardtype = min_of_cardtype;
    }

    public int getExact_of_cardtype() {
        return exact_of_cardtype;
    }

    public void setExact_of_cardtype(int exact_of_cardtype) {
        this.exact_of_cardtype = exact_of_cardtype;
    }

    public String getClub() {
        return club;
    }

    public void setClub(String club) {
        this.club = club;
    }

    public int getMin_of_club() {
        return min_of_club;
    }

    public void setMin_of_club(int min_of_club) {
        this.min_of_club = min_of_club;
    }

    public int getExact_of_club() {
        return exact_of_club;
    }

    public void setExact_of_club(int exact_of_club) {
        this.exact_of_club = exact_of_club;
    }

    public String getNation() {
        return nation;
    }

    public void setNation(String nation) {
        this.nation = nation;
    }

    public int getMin_of_nation() {
        return min_of_nation;
    }

    public void setMin_of_nation(int min_of_nation) {
        this.min_of_nation = min_of_nation;
    }

    public int getExact_of_nation() {
        return exact_of_nation;
    }

    public void setExact_of_nation(int exact_of_nation) {
        this.exact_of_nation = exact_of_nation;
    }

    public String getLeague() {
        return league;
    }

    public void setLeague(String league) {
        this.league = league;
    }

    public int getMin_of_league() {
        return min_of_league;
    }

    public void setMin_of_league(int min_of_league) {
        this.min_of_league = min_of_league;
    }

    public int getExact_of_league() {
        return exact_of_league;
    }

    public void setExact_of_league(int exact_of_league) {
        this.exact_of_league = exact_of_league;
    }

    public ArrayList<Brick> getBricks() {
        return bricks;
    }

    public void setBricks(ArrayList<Brick> bricks) {
        this.bricks = bricks;
    }

    public ConstraintType getConstraintType() {
        return type;
    }

    public void setConstraintType(ConstraintType type) {
        this.type = type;
    }
}

class Constraints {

    private ArrayList<Constraint> constraints;

    public Constraints() {
        this.constraints = new ArrayList<>();
    }

    public ArrayList<Constraint> getConstraints() {
        return constraints;
    }

    public void addConstraint(Constraint constraint) {
        this.constraints.add(constraint);
    }

    public int getMinRating() {
        for (Constraint c: this.constraints) {
            if (c.getConstraintType() == ConstraintType.MINRATING) {
                return c.getMinRating();
            }
        }
        return 0;
    }

    public int getMinChem() {
        for (Constraint c: this.constraints) {
            if (c.getConstraintType() == ConstraintType.MINCHEM) {
                return c.getMinChem();
            }
        }
        return 0;
    }

    public void setConstraints(ArrayList<Constraint> constraints) {
        this.constraints = constraints;
    }
}

enum CardType {

    FUTMAS,
    ICON,
    UCL_COMMON,
    UCL_RARE,
    TOTY,
    TOTY_NOMINEE,
    GOLD_NON_RARE,
    GOLD_RARE,
    SILVER_NON_RARE,
    SILVER_RARE,
    BRONZE_NON_RARE,
    BRONZE_RARE,
    GOLD_IF,
    SILVER_IF,
    BRONZE_IF,
    OTW,
    SCREAM,
    SBC,
    AWARD_WINNER,
    MOTM,
    HERO,
    RECORD_BREAKER,
    NON_IF,
    FUT_CHAMPS,

}

enum ConstraintType {

    MINRATING,
    MINCHEM,
    BRICKS,
    MIN_OF_CARDTYPE,
    EXACT_OF_CARDTYPE,
    MIN_OF_LEAGUE,
    EXACT_OF_LEAGUE,
    MIN_OF_NATION,
    EXACT_OF_NATION,
    MIN_OF_CLUB,
    EXACT_OF_CLUB,
    NUMPLAYERS,
}



class Brick {

    private Position pos;
    private String nation;
    private String league;
    private String club;

    public Brick(Position pos) {
        this.pos = pos;
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
}
