package business;

import java.util.Objects;

public class Game {
    private String gameName;
    private String gameOwner;
    private double price;

    public Game(String gameName, String gameOwner, double price) {
        this.gameName = gameName;
        this.gameOwner = gameOwner;
        this.price = price;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }


    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getGameOwner() {
        return gameOwner;
    }

    public void setGameOwner(String gameOwner) {
        this.gameOwner = gameOwner;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return Double.compare(price, game.price) == 0 && Objects.equals(gameName, game.gameName) && Objects.equals(gameOwner, game.gameOwner);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, gameOwner, price);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                " gameOwner='" + gameOwner + '\'' +
                " price=" + price +
                '}';
    }
}
