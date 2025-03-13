package business;

import java.util.Objects;

public class Game {
    private String gameName;
    private int quantity;
    private double price;

    public Game(String gameName, int quantity, double price) {
        this.gameName = gameName;
        this.quantity = quantity;
        this.price = price;
    }

    public String getGameName() {
        return gameName;
    }

    public void setGameName(String gameName) {
        this.gameName = gameName;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Game game = (Game) o;
        return quantity == game.quantity && Double.compare(price, game.price) == 0 && Objects.equals(gameName, game.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(gameName, quantity, price);
    }

    @Override
    public String toString() {
        return "Game{" +
                "gameName='" + gameName + '\'' +
                ", quantity=" + quantity +
                ", price=" + price +
                '}';
    }
}
