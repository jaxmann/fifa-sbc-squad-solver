import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class main {

    public static void main(String args[]) {



        PlayerLoader pl = new PlayerLoader();
        pl.loadPlayers();

        HashMap<String, ArrayList<Player>> bp = pl.getByNation();

        for (String name: bp.keySet()){

            String key = name.toString();
            Player value = bp.get(name).get(0);
            System.out.println(key + " " + value.getName());

        }


    }

}


