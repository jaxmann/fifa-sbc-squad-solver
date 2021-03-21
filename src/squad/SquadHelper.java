package squad;

import player.CardType;
import player.PlayerLoader;
import player.PlayerNotFoundException;
import player.position.BasePosition;
import squad.formation.Formation;
import squad.formation.FormationFactory;
import player.Player;
import player.position.Position;

import java.io.*;
import java.util.ArrayList;

public class SquadHelper implements Serializable {

    public static Squad create87DefaultSquad() throws IOException {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.getPlayer("neto", 84);
            Player kagawa = pl.getPlayer("kagawa", 83);
            Player navas = pl.getPlayer("navas", 87);
            Player gimenez = pl.getPlayer("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.getPlayer("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.getPlayer("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.getPlayer("kroos", 90);
            Player david = pl.getPlayer("david", 89);
            Player iniesta = pl.getPlayer("iniesta", 87);
            Player diego = pl.getPlayer("costa", 85);
            Player isco = pl.getPlayer("isco", 89);

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

    public static Squad create71ChemSquad() throws IOException {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.getPlayer("neto", 84);
            Player kagawa = pl.getPlayer("kagawa", 83);
            Player navas = pl.getPlayer("navas", 87);
            Player gimenez = pl.getPlayer("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.getPlayer("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.getPlayer("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.getPlayer("kroos", 90);
            Player david = pl.getPlayer("david", 89);
            Player iniesta = pl.getPlayer("iniesta", 87);
            Player diego = pl.getPlayer("costa", 85);
            Player isco = pl.getPlayer("isco", 89);

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


    public static Squad create85DefaultSquad() throws IOException { //not implemented
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.getPlayer("neto", 84);
            Player kagawa = pl.getPlayer("kagawa", 83);
            Player navas = pl.getPlayer("navas", 87);
            Player gimenez = pl.getPlayer("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.getPlayer("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.getPlayer("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.getPlayer("kroos", 90);
            Player david = pl.getPlayer("david", 89);
            Player iniesta = pl.getPlayer("iniesta", 87);
            Player diego = pl.getPlayer("costa", 85);
            Player isco = pl.getPlayer("isco", 89);

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


    public static Squad create83DefaultSquad() throws IOException { //not implemented
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player neto = pl.getPlayer("neto", 84);
            Player kagawa = pl.getPlayer("kagawa", 83);
            Player navas = pl.getPlayer("navas", 87);
            Player gimenez = pl.getPlayer("", 84, "uruguay", "CB", "madrid", "");
            Player savic = pl.getPlayer("", 83, "montenegro", "CB", "madrid", "laliga");
            Player lucas = pl.getPlayer("lucas", 83, "spain", "", "madrid", "laliga");
            Player kroos = pl.getPlayer("kroos", 90);
            Player david = pl.getPlayer("david", 89);
            Player iniesta = pl.getPlayer("iniesta", 87);
            Player diego = pl.getPlayer("costa", 85);
            Player isco = pl.getPlayer("isco", 89);

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

    public static Squad create75DefaultSquad() throws IOException {
        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers(true);
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        try {
            Player rb = pl.getPlayer("toljan", 75); //rb
            Player lb = pl.getPlayer("zeegelaar", 75); //lb
            Player gk = pl.getPlayer("olsen", 75); //gk
            Player lcb = pl.getPlayer("mbemba", 75); //lcb
            Player rcb = pl.getPlayer("sebastian", 75); //rcb
            Player rm = pl.getPlayer("conti", 75); //rm
            Player cdm = pl.getPlayer("sanches", 75); //cdm
            Player lm = pl.getPlayer("bruno", 75); //lm
            Player lst = pl.getPlayer("niasse", 75); //lst
            Player rst = pl.getPlayer("diaby", 75); //rst
            Player cam = pl.getPlayer("winks", 75); //cam

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
    public static Squad create75GoldNonRareTestSquad() {
        ArrayList<Player> players = new ArrayList<>();
        FormationFactory ff = new FormationFactory();
        Formation f = ff.getFormation("41212");
        ArrayList<Position> positions = f.getPositions();

        Player rb = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //rb
        Player lb = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //lb
        Player gk = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //gk
        Player lcb = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //lcb
        Player rcb = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //rcb
        Player rm = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //rm
        Player cdm = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //cdm
        Player lm = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //lm
        Player lst = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //lst
        Player rst = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //rst
        Player cam = new Player("test player", "fake team", "fake nation", "fake league", BasePosition.RB, CardType.GOLD_NON_RARE, 800, 75, false); //cam

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

        return new Squad(positions, players, f);
    }

}
