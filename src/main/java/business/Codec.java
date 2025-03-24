package business;

public class Codec {
    public Codec() {
    }

    public String decode(String input) {
        String output = "";
        String[] gamesOrders = input.split("\\$\\$");
        String[] games = gamesOrders[0].split("%%");
        String[] orders = gamesOrders[1].split("%%");
        output = " GAMES[";
        for (int i = 0; i < games.length; i++) {
            String[] game = games[i].split(",");
            output += "Game owner=" + game[0] + " Game name=" + game[1] + " Game Price=" + game[2] + ",";
        }
        output += "]";
        output += " ORDERS[";
        for (int i = 0; i < orders.length; i++) {
            String[] order = orders[i].split(",");
            output += "Username=" + order[0] + " Game name=" + order[1] + " Game price=" + order[2] + ",";
        }
        output += "]";
        return output;
    }

    public String decodeMatch(String input) {
        String output = "";
        String[] variable = input.split(":");
        String[] matchVariable = variable[1].split(",");
        if (matchVariable[0].equals(GameService.BUY_ID)) {
            output += " Bought Game ";
        } else {
            output += " Sold Game ";
        }
        output += matchVariable[1] + " from " + matchVariable[3] + " for " + matchVariable[2];

        return output;
    }
}
