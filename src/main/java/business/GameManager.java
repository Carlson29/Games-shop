package business;

import java.util.ArrayList;

public class GameManager {

    private ArrayList<Game> games;

    public GameManager() {
        games = new ArrayList<Game>();
        bootstrapGameList();
    }

    private void bootstrapGameList() {
        Game g1 = new Game("Star Wars Outlaws - Xbox Series X", "John", 20);
        Game g2 = new Game("Lego Marvel’s Avengers - Nintendo 3DS", "Sam", 18);
        Game g3 = new Game("Jumanji The Video Game For Xbox One", "Joanne", 80);
        Game g4 = new Game("Mario vs Donkey Kong - Nintendo Switch", "Paul", 25);
        games.add(g3);
        games.add(g1);
        games.add(g2);
        games.add(g4);
    }

    public String getGames() {
        String output = "";
        synchronized(this) {
            for (int i = 0; i < games.size(); i++) {
                if (i != games.size() - 1) {
                    output += games.get(i).getGameOwner() + GameService.DELIMITER + games.get(i).getGameName() + GameService.DELIMITER + games.get(i).getPrice() + "%%";
                } else {
                    output += games.get(i).getGameOwner() + GameService.DELIMITER + games.get(i).getGameName() + GameService.DELIMITER + games.get(i).getPrice();
                }
            }
        }
        return output;
    }

    public boolean addGame(String gameName, String gameOwner, double price) {
        synchronized(this) {
            if (containsGame(gameName, gameOwner, price) == false) {
                Game g = new Game(gameName, gameOwner, price);
                games.add(g);
                return true;
            }
        }
        return false;
    }

    public boolean containsGame(String gameName, String gameOwner, double price) {
        synchronized(this) {
            Game g = new Game(gameName, gameOwner, price);
            for (Game g1 : games) {
                if (g.getGameName().equalsIgnoreCase(g1.getGameName()) && g.getGameOwner().equalsIgnoreCase(g1.getGameOwner()) && g.getPrice() == g1.getPrice()) {
                    return true;
                }
            }
        }
        return false;
    }

    public Game removeGame(String gameName, String gameOwner, double price) {
        Game game = new Game(gameName, gameOwner, price);
        synchronized(this) {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getGameOwner().equalsIgnoreCase(game.getGameOwner()) && games.get(i).getGameName().equalsIgnoreCase(game.getGameName()) && games.get(i).getPrice() == game.getPrice()) {
                    Game g = games.remove(i);
                    return g;
                }
            }
        }
        return null;
    }

    public Game buyGame(Order order) {
        synchronized(this) {
            for (int i = 0; i < games.size(); i++) {
                if (games.get(i).getGameName().equalsIgnoreCase(order.getGameName()) && games.get(i).getPrice() <= order.getPrice()) {
                    Game g = games.remove(i);
                    return g;
                }
            }
        }
        return null;
    }

    //need function of new order and cancel order
    //need compare price of game to match

}
