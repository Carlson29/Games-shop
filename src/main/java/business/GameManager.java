package business;

import java.util.HashMap;

public class GameManager {

    HashMap<String, Game> games;

    public GameManager() {
        games = new HashMap<>();
        bootstrapGameList();
    }
    private void bootstrapGameList()
    {
        Game g1 = new Game("Star Wars Outlaws - Xbox Series X", 1, 20);
        Game g2 = new Game("Lego Marvel’s Avengers - Nintendo 3DS", 5, 18);
        Game g3 = new Game("Jumanji The Video Game For Xbox One", 1, 80);
        Game g4 = new Game("Mario vs Donkey Kong - Nintendo Switch", 2, 25);
        games.put(g3.getGameName(), g3);
        games.put(g1.getGameName(), g1);
        games.put(g2.getGameName(), g2);
        games.put(g4.getGameName(), g4);
    }

    /**
     * Adds a new game to the hashmap
     *
     * @param g for Game
     * @return true if the game was added or false if there's a game with the same title in the hashMap
     * @throws NullPointerException if the game is null
     **/

    public boolean add(Game g) {
        if (g == null) {
            throw new NullPointerException("Game can't be null");
        }
        synchronized (games) {
            if (!games.containsKey(g.getGameName())) {
                games.put(g.getGameName(), g);
                return true;
            }
        }
        return false;
    }

    /**
     * removes a game from the hashMap
     *
     * @param gameName, the name of the game
     * @return true if the game exist, and it was removed successfully or false if there's no game with the gameName
     **/
    public boolean remove(String gameName) {
        if (games.containsKey(gameName)) {
            games.remove(gameName);
            return true;
        }
        return false;
    }

    //need function of new order and cancel order
    //need compare price of game to match

}
