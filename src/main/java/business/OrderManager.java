package business;

import java.util.ArrayList;

public class OrderManager {
    private ArrayList<Order> orders;

    public OrderManager() {
        orders = new ArrayList<>();
        bootstrapUserList();
    }

    private void bootstrapUserList() {
        Order o1 = new Order("Nathan", "Dragon ball", 15);
        Order o2 = new Order("Evan", "Superman", 15);
        orders.add(o1);
        orders.add(o2);
    }

    public Order addOrder(String userName, String gameName, double price) {
        synchronized(this) {
            if (containsOrder(userName, gameName, price) == false) {
                Order o = new Order(userName, gameName, price);
                orders.add(o);
                return o;
            }
        }
        return null;
    }

    public String getOrders() {
        String output = "";
        synchronized(this) {
            for (int i = 0; i < orders.size(); i++) {
                if (i != orders.size() - 1) {
                    output += orders.get(i).getUserName() + GameService.DELIMITER + orders.get(i).getGameName() + GameService.DELIMITER + orders.get(i).getPrice() + "%%";
                } else {
                    output += orders.get(i).getUserName() + GameService.DELIMITER + orders.get(i).getGameName() + GameService.DELIMITER + orders.get(i).getPrice();
                }

            }
        }
        return output;
    }

    public boolean containsOrder(String userName, String gameName, double price) {
        Order o1 = new Order(userName, gameName, price);
        synchronized(this) {
            for (Order o : orders) {
                if (o1.getUserName().equals(o.getUserName()) && o1.getGameName().equalsIgnoreCase(o.getGameName()) && o1.getPrice() == o.getPrice()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean removeOrder(String userName, String gameName, double price) {
        Order o1 = new Order(userName, gameName, price);
        synchronized(this) {
            for (int i = 0; i < orders.size(); i++) {
                if (o1.getUserName().equalsIgnoreCase(orders.get(i).getUserName()) && o1.getGameName().equalsIgnoreCase(orders.get(i).getGameName()) && o1.getPrice() == orders.get(i).getPrice()) {
                    orders.remove(i);
                    return true;
                }
            }
        }
        return false;
    }

    public Order sellGame(Game game) {
        synchronized(this){
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getGameName().equalsIgnoreCase(game.getGameName())) {
                if (orders.get(i).getPrice() >= game.getPrice()) {
                    Order o = orders.remove(i);
                    return o;
                }
            }
            }
        }
        return null;
    }
}
