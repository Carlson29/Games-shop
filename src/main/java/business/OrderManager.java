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
        if (containsOrder(userName, gameName, price) == false) {
            Order o = new Order(userName, gameName, price);
            orders.add(o);
            return o;
        }
        return null;
    }

    public String getOrders() {
        String output = "";
        for (Order o : orders){
            output += o.toString() + GameService.DELIMITER + "";
        }
        return output;
    }

    public boolean containsOrder(String userName, String gameName, double price) {
        Order o1 = new Order(userName, gameName, price);
        for (Order o : orders) {
            if (o1.equals(o)) {
                return true;
            }
        }
        return false;
    }

    public boolean removeOrder(String userName, String gameName, double price) {
        Order o1 = new Order(userName, gameName, price);
        for (int i = 0; i < orders.size(); i++) {
            if (o1.equals(orders.get(i))) {
                orders.remove(i);
                return true;
            }
        }
        return false;
    }

    public Order sellGame(Game game) {
        for (int i = 0; i < orders.size(); i++) {
            if (orders.get(i).getGameName().equalsIgnoreCase(game.getGameName())) {
                if (orders.get(i).getPrice() >= game.getPrice()) {
                    Order o = orders.remove(i);
                    return o;
                }
            }
        }
        return null;
    }
}
