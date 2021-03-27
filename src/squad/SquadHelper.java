package squad;

import player.CardType;
import player.PlayerLoaderUtil;
import player.PlayerNotFoundException;
import player.BasePosition;
import squad.formation.Formation;
import squad.formation.FormationFactory;
import player.Player;
import player.Position;

import java.io.*;
import java.util.ArrayList;

public class SquadHelper implements Serializable {

    public static Squad create87DefaultSquad() throws Exception {
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player p1 = pl.lookupPlayer("alisson", 90);
            Player p2 = pl.lookupPlayer("laporte", 87);
            Player p3 = pl.lookupPlayer("gikie", 84);
            Player p4 = pl.lookupPlayer("vela", 83);
            Player p5 = pl.lookupPlayer("matuidi", 83);
            Player p6 = pl.lookupPlayer("casemiro", 89);
            Player p7 = pl.lookupPlayer("ter stegen", 90);
            Player p8 = pl.lookupPlayer("grimaldo", 87);
            Player p9 = pl.lookupPlayer("rafa", 83);
            Player p10 = pl.lookupPlayer("strakosha", 83);
            Player p11 = pl.lookupPlayer("gonzalo", 83);

            players.add(p1); //rb
            players.add(p2); //lb
            players.add(p3); //gk
            players.add(p4); //lcb
            players.add(p5); //rcb
            players.add(p6); //rm
            players.add(p7); //cdm
            players.add(p8); //lm
            players.add(p9); //lst
            players.add(p10); //rst
            players.add(p11); //cam

        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }

    public static Squad create43ChemSquad() throws Exception {
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player p1 = pl.lookupPlayer("alisson", 90);
            Player p2 = pl.lookupPlayer("laporte", 87);
            Player p3 = pl.lookupPlayer("gikie", 84);
            Player p4 = pl.lookupPlayer("vela", 83);
            Player p5 = pl.lookupPlayer("matuidi", 83);
            Player p6 = pl.lookupPlayer("casemiro", 89);
            Player p7 = pl.lookupPlayer("ter stegen", 90);
            Player p8 = pl.lookupPlayer("grimaldo", 87);
            Player p9 = pl.lookupPlayer("rafa", 83);
            Player p10 = pl.lookupPlayer("strakosha", 83);
            Player p11 = pl.lookupPlayer("gonzalo", 83); //the juventus one

            players.add(p3); //rb
            players.add(p8); //lb
            players.add(p1); //gk
            players.add(p7); //lcb
            players.add(p2); //rcb
            players.add(p4); //rm
            players.add(p5); //cdm
            players.add(p9); //lm
            players.add(p11); //lst
            players.add(p10); //rst
            players.add(p6); //cam

        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }


    public static Squad create85DefaultSquad() throws Exception { //not implemented
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player p1 = pl.lookupPlayer("alisson", 90);
            Player p2 = pl.lookupPlayer("laporte", 87);
            Player p3 = pl.lookupPlayer("gikie", 84);
            Player p4 = pl.lookupPlayer("vela", 83);
            Player p5 = pl.lookupPlayer("matuidi", 83);
            Player p6 = pl.lookupPlayer("guedes", 81);
            Player p7 = pl.lookupPlayer("varane", 86);
            Player p8 = pl.lookupPlayer("grimaldo", 87);
            Player p9 = pl.lookupPlayer("rafa", 83);
            Player p10 = pl.lookupPlayer("strakosha", 83);
            Player p11 = pl.lookupPlayer("gonzalo", 83);

            players.add(p1); //rb
            players.add(p2); //lb
            players.add(p3); //gk
            players.add(p4); //lcb
            players.add(p5); //rcb
            players.add(p6); //rm
            players.add(p7); //cdm
            players.add(p8); //lm
            players.add(p9); //lst
            players.add(p10); //rst
            players.add(p11); //cam

        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }


    public static Squad create83DefaultSquad() throws Exception {
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player p1 = pl.lookupPlayer("volland", 81);
            Player p2 = pl.lookupPlayer("boateng", 82);
            Player p3 = pl.lookupPlayer("gikie", 84);
            Player p4 = pl.lookupPlayer("vela", 83);
            Player p5 = pl.lookupPlayer("matuidi", 83);
            Player p6 = pl.lookupPlayer("guedes", 81);
            Player p7 = pl.lookupPlayer("varane", 86);
            Player p8 = pl.lookupPlayer("mendy", 83);
            Player p9 = pl.lookupPlayer("rafa", 83);
            Player p10 = pl.lookupPlayer("strakosha", 83);
            Player p11 = pl.lookupPlayer("gonzalo", 83);

            players.add(p1); //rb
            players.add(p2); //lb
            players.add(p3); //gk
            players.add(p4); //lcb
            players.add(p5); //rcb
            players.add(p6); //rm
            players.add(p7); //cdm
            players.add(p8); //lm
            players.add(p9); //lst
            players.add(p10); //rst
            players.add(p11); //cam

        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }

    public static Squad create75DefaultSquad() throws Exception {
        PlayerLoaderUtil pl = PlayerLoaderUtil.getInstance();
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        Player p1 = pl.getAnyPlayerAtExactRating(75);
        Player p2 = pl.getAnyPlayerAtExactRating(75);
        Player p3 = pl.getAnyPlayerAtExactRating(75);
        Player p4 = pl.getAnyPlayerAtExactRating(75);
        Player p5 = pl.getAnyPlayerAtExactRating(75);
        Player p6 = pl.getAnyPlayerAtExactRating(75);
        Player p7 = pl.getAnyPlayerAtExactRating(75);
        Player p8 = pl.getAnyPlayerAtExactRating(75);
        Player p9 = pl.getAnyPlayerAtExactRating(75);
        Player p10 = pl.getAnyPlayerAtExactRating(75);
        Player p11 = pl.getAnyPlayerAtExactRating(75);

        players.add(p1); //rb
        players.add(p2); //lb
        players.add(p3); //gk
        players.add(p4); //lcb
        players.add(p5); //rcb
        players.add(p6); //rm
        players.add(p7); //cdm
        players.add(p8); //lm
        players.add(p9); //lst
        players.add(p10); //rst
        players.add(p11); //cam

        return new Squad(positions, players, f);
    }

    // useful for testing - otherwise can run into assertion issues asserting n german players etc
    // not the "position" param doesn't actually match where it will be inserted into the lineup
    public static Squad createTestSquad(String team, String nation, String league, BasePosition position, CardType cardType, double pricePerPlayer, int rating, boolean loyalty) throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        Player rb = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //rb
        Player lb = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //lb
        Player gk = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //gk
        Player lcb = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //lcb
        Player rcb = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //rcb
        Player rm = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //rm
        Player cdm = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //cdm
        Player lm = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //lm
        Player lst = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //lst
        Player rst = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //rst
        Player cam = new Player("test player", team, nation, league, position, cardType, pricePerPlayer, rating, loyalty); //cam

        players.add(rb); //rb
        players.add(lb); //lb
        players.add(gk); //gk
        players.add(lcb); //lcb
        players.add(rcb); //rcb
        players.add(rm); //rm
        players.add(cdm); //cdm
        players.add(lm); //lm
        players.add(lst); //lst
        players.add(rst); //rst
        players.add(cam); //cam

        return new Squad(positions, players, f);
    }

}
