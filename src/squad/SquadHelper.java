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
        PlayerLoaderUtil pl = new PlayerLoaderUtil();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.lookupPlayerByNameAndRating("neto", 84);
            Player kagawa = pl.lookupPlayerByNameAndRating("kagawa", 83);
            Player navas = pl.lookupPlayerByNameAndRating("navas", 87);
            Player gimenez = pl.lookupPlayerByNameAndRating("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.lookupPlayerByNameAndRating("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.lookupPlayerByNameAndRating("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.lookupPlayerByNameAndRating("kroos", 90);
            Player david = pl.lookupPlayerByNameAndRating("david", 89);
            Player iniesta = pl.lookupPlayerByNameAndRating("iniesta", 87);
            Player diego = pl.lookupPlayerByNameAndRating("costa", 85);
            Player isco = pl.lookupPlayerByNameAndRating("isco", 89);

            players.add(neto); //rb
            players.add(kagawa); //lb
            players.add(navas); //gk
            players.add(gimenez); //lcb
            players.add(savic); //rcb
            players.add(lucas); //rm
            players.add(kroos); //cdm
            players.add(david); //lm
            players.add(iniesta); //lst
            players.add(diego); //rst
            players.add(isco); //cam

            for (Player p : players) {
                System.out.println(p.toString());
            }
        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }

    public static Squad create71ChemSquad() throws Exception {
        PlayerLoaderUtil pl = new PlayerLoaderUtil();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.lookupPlayerByNameAndRating("neto", 84);
            Player kagawa = pl.lookupPlayerByNameAndRating("kagawa", 83);
            Player navas = pl.lookupPlayerByNameAndRating("navas", 87);
            Player gimenez = pl.lookupPlayerByNameAndRating("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.lookupPlayerByNameAndRating("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.lookupPlayerByNameAndRating("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.lookupPlayerByNameAndRating("kroos", 90);
            Player david = pl.lookupPlayerByNameAndRating("david", 89);
            Player iniesta = pl.lookupPlayerByNameAndRating("iniesta", 87);
            Player diego = pl.lookupPlayerByNameAndRating("costa", 85);
            Player isco = pl.lookupPlayerByNameAndRating("isco", 89);

            players.add(neto); //rb
            players.add(kagawa); //lb
            players.add(navas); //gk
            players.add(gimenez); //lcb
            players.add(savic); //rcb
            players.add(lucas); //rm
            players.add(kroos); //cdm
            players.add(david); //lm
            players.add(iniesta); //lst
            players.add(diego); //rst
            players.add(isco); //cam

            for (Player p : players) {
                System.out.println(p.toString());
            }
        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }


    public static Squad create85DefaultSquad() throws Exception { //not implemented
        PlayerLoaderUtil pl = new PlayerLoaderUtil();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.lookupPlayerByNameAndRating("neto", 84);
            Player kagawa = pl.lookupPlayerByNameAndRating("kagawa", 83);
            Player navas = pl.lookupPlayerByNameAndRating("navas", 87);
            Player gimenez = pl.lookupPlayerByNameAndRating("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.lookupPlayerByNameAndRating("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.lookupPlayerByNameAndRating("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.lookupPlayerByNameAndRating("kroos", 90);
            Player david = pl.lookupPlayerByNameAndRating("david", 89);
            Player iniesta = pl.lookupPlayerByNameAndRating("iniesta", 87);
            Player diego = pl.lookupPlayerByNameAndRating("costa", 85);
            Player isco = pl.lookupPlayerByNameAndRating("isco", 89);

            players.add(neto); //rb
            players.add(kagawa); //lb
            players.add(navas); //gk
            players.add(gimenez); //lcb
            players.add(savic); //rcb
            players.add(lucas); //rm
            players.add(kroos); //cdm
            players.add(david); //lm
            players.add(iniesta); //lst
            players.add(diego); //rst
            players.add(isco); //cam

            for (Player p : players) {
                System.out.println(p.toString());
            }
        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }


    public static Squad create83DefaultSquad() throws Exception { //not implemented
        PlayerLoaderUtil pl = new PlayerLoaderUtil();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.lookupPlayerByNameAndRating("neto", 84);
            Player kagawa = pl.lookupPlayerByNameAndRating("kagawa", 83);
            Player navas = pl.lookupPlayerByNameAndRating("navas", 87);
            Player gimenez = pl.lookupPlayerByNameAndRating("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.lookupPlayerByNameAndRating("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.lookupPlayerByNameAndRating("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.lookupPlayerByNameAndRating("kroos", 90);
            Player david = pl.lookupPlayerByNameAndRating("david", 89);
            Player iniesta = pl.lookupPlayerByNameAndRating("iniesta", 87);
            Player diego = pl.lookupPlayerByNameAndRating("costa", 85);
            Player isco = pl.lookupPlayerByNameAndRating("isco", 89);

            players.add(neto); //rb
            players.add(kagawa); //lb
            players.add(navas); //gk
            players.add(gimenez); //lcb
            players.add(savic); //rcb
            players.add(lucas); //rm
            players.add(kroos); //cdm
            players.add(david); //lm
            players.add(iniesta); //lst
            players.add(diego); //rst
            players.add(isco); //cam

            for (Player p : players) {
                System.out.println(p.toString());
            }
        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }

    public static Squad create75DefaultSquad() throws Exception {
        PlayerLoaderUtil pl = new PlayerLoaderUtil();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player rb = pl.lookupPlayerByNameAndRating("toljan", 75); //rb
            Player lb = pl.lookupPlayerByNameAndRating("zeegelaar", 75); //lb
            Player gk = pl.lookupPlayerByNameAndRating("olsen", 75); //gk
            Player lcb = pl.lookupPlayerByNameAndRating("mbemba", 75); //lcb
            Player rcb = pl.lookupPlayerByNameAndRating("sebastian", 75); //rcb
            Player rm = pl.lookupPlayerByNameAndRating("conti", 75); //rm
            Player cdm = pl.lookupPlayerByNameAndRating("sanches", 75); //cdm
            Player lm = pl.lookupPlayerByNameAndRating("bruno", 75); //lm
            Player lst = pl.lookupPlayerByNameAndRating("niasse", 75); //lst
            Player rst = pl.lookupPlayerByNameAndRating("diaby", 75); //rst
            Player cam = pl.lookupPlayerByNameAndRating("winks", 75); //cam

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

            for (Player p : players) {
                System.out.println(p.toString());
            }
        } catch (PlayerNotFoundException e) {
            e.printName();
        }

        return new Squad(positions, players, f);
    }

    // useful for testing - otherwise can run into assertion issues asserting n german players etc
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
