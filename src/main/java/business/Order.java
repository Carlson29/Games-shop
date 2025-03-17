package business;

import java.util.Objects;

public class Order {
private String userName;
private String gameName;
private double price;

    public Order(String userName, String gameName, double price) {
        this.userName = userName;
        this.gameName = gameName;
        this.price = price;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return Double.compare(price, order.price) == 0 && Objects.equals(userName, order.userName) && Objects.equals(gameName, order.gameName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(userName, gameName, price);
    }

    @Override
    public String toString() {
        return "Order{" +
                "userName='" + userName + '\'' +
                " gameName='" + gameName + '\'' +
                " price=" + price +
                '}';
    }
}
