package Server;

import business.*;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Handler implements Runnable {

    private final Socket dataSocket;
    private User currentUser;
    private UserManager userManager;
    private GameManager gameManager;
    private OrderManager orderManager;
    private boolean userSession = true;

    public Handler(Socket dataSocket, UserManager userManager, GameManager gameManager, OrderManager orderManager) {
        this.dataSocket = dataSocket;
        this.userManager = userManager;
        this.gameManager = gameManager;
        this.orderManager = orderManager;
    }


    @Override
    public void run() {
        try (dataSocket) {
            try (Scanner input = new Scanner(dataSocket.getInputStream());
                 PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                while (userSession) {
                    String incomingMessage = "";
                    String response = GameService.INVALID_REQUEST;
                    incomingMessage = input.nextLine();
                    System.out.println("Server received: " + incomingMessage);
                    String[] tokens = incomingMessage.split(GameService.ACTION_DELIMITER);
                    String action = "";
                    if (tokens.length >= 1) {
                        action = tokens[0];
                        int index = incomingMessage.indexOf(":");
                        incomingMessage = incomingMessage.substring(index + 1);
                        String[] info = incomingMessage.split(GameService.DELIMITER);
                        switch (action) {
                            case GameService.USER_REQUEST:
                                response = connect(info);
                                break;
                            case GameService.ORDER_REQUEST:
                                response = order(info);
                                break;
                            case GameService.VIEW_REQUEST:
                                response = view(info);
                                //Returns the order like
                                //games are on the left and orders are on the right, games and orders and seperated by $$
                                //gameowner, gameName, price %%  gameowner, gameName, price $$ username, gamename, price %% username, gamename, price
                                break;
                            case GameService.CANCEL_REQUEST:
                                response = cancel(info);
                                break;
                            case GameService.END_REQUEST:

                                response = end(info);
                                break;
                            default:
                                response = GameService.INVALID_REQUEST;
                                break;
                        }

                    }
                    // Send back the computed response
                    output.println(response);
                    output.flush();
                }
            } catch (IOException e) {
                System.out.println("IOException occurred on server socket");
                System.out.println(e.getMessage());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }


    public String connect(String[] info) {
        String response = "";
        if (info.length == 1) {
            currentUser = userManager.searchByUsername(info[0]);
            if (currentUser == null) {
                currentUser = userManager.addUser(info[0]);
            }
            response = GameService.USER_CONNECT_RESPONSE;
        } else {
            response = GameService.INVALID_REQUEST;
        }
        return response;
    }

    public String order(String[] info) {
        String response = "";
        if (currentUser != null) {
            if (info.length == 3) {
                try {
                    double price = Double.parseDouble(info[2]);
                    if (info[0].equalsIgnoreCase(GameService.BUY_ID)) {
                        Order o1 = new Order(currentUser.getUserName(), info[1], price);
                        Game g = gameManager.buyGame(o1);
                        if (g == null) {
                            if (orderManager.addOrder(currentUser.getUserName(), info[1], price) == null) {
                                response = GameService.ORDER_EXIST_RESPONSE;
                            } else {
                                response = GameService.ORDER_ADDED_RESPONSE;
                            }
                        } else {
                            response = GameService.ORDER_MATCH_RESPONSE + GameService.ACTION_DELIMITER + GameService.BUY_ID + GameService.DELIMITER + info[1] + GameService.DELIMITER + g.getPrice() + GameService.DELIMITER + g.getGameOwner();
                        }

                    } else if (info[0].equalsIgnoreCase(GameService.SELL_ID)) {
                        Game g1 = new Game(info[1], currentUser.getUserName(), price);
                        Order o1 = orderManager.sellGame(g1);
                        if (o1 == null) {
                            if (gameManager.addGame(info[1], currentUser.getUserName(), price)) {
                                response = GameService.GAME_ADDED_RESPONSE;
                            } else {
                                response = GameService.GAME_EXIST_RESPONSE;
                            }
                        } else {
                            response = GameService.ORDER_MATCH_RESPONSE + GameService.ACTION_DELIMITER + GameService.SELL_ID + GameService.DELIMITER + info[1] + GameService.DELIMITER + o1.getPrice() + GameService.DELIMITER + o1.getUserName();
                        }

                    }
                } catch (NumberFormatException e) {
                    response = GameService.INVALID_REQUEST;
                }

            } else {
                response = GameService.INVALID_REQUEST;
            }
        } else {
            response = GameService.NOT_CONNECTED_RESPONSE;
        }
        return response;
    }

    public String view(String[] info) {
        String response = "";
        if (currentUser != null) {
            response = "";
            // response = "Games";
            response += gameManager.getGames() + GameService.GAME_ORDER_DELIMITER;
            //response += "Orders";
            response += orderManager.getOrders();
        } else {
            response = GameService.NOT_CONNECTED_RESPONSE;
        }
        return response;
    }

    public String cancel(String[] info) {
        String response = "";
        if (currentUser != null) {
            if (info.length == 3) {
                try {
                    double price = Double.parseDouble(info[2]);
                    if (info[0].equalsIgnoreCase(GameService.BUY_ID)) {
                        boolean remove = orderManager.removeOrder(currentUser.getUserName(), info[1], price);
                        if (remove) {
                            response = GameService.CANCEL_CANCELLED_RESPONSE;
                        } else {
                            response = GameService.NOT_FOUND_RESPONSE;
                        }
                    } else if (info[0].equalsIgnoreCase(GameService.SELL_ID)) {
                        Game g = gameManager.removeGame(info[1], currentUser.getUserName(), price);
                        if (g == null) {
                            response = GameService.NOT_FOUND_RESPONSE;
                        } else {
                            response = GameService.CANCEL_CANCELLED_RESPONSE;
                        }
                    }
                } catch (NumberFormatException e) {
                    response = GameService.INVALID_REQUEST;
                }
            } else {
                response = GameService.INVALID_REQUEST;
            }
        } else {
            response = GameService.NOT_CONNECTED_RESPONSE;
        }
        return response;
    }

    public String end(String[] info) {
        String response = "";
        if (currentUser != null) {
            currentUser = null;
            userSession = false;
            response = GameService.END_RESPONSE;
        } else {
            response = GameService.NOT_CONNECTED_RESPONSE;
        }
        return response;
    }
}
