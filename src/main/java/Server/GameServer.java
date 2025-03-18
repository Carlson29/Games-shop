package Server;

import business.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServer {
    public static void main(String[] args) {
        try {
            // Set up a connection socket for other programs to connect to
            ServerSocket listeningSocket = new ServerSocket(GameService.PORT);

            boolean continueRunning = true;
            UserManager userManager = new UserManager();
            GameManager gameManager = new GameManager();
            OrderManager orderManager = new OrderManager();
            while (continueRunning) {
                // Step 2) wait for incoming connection and build communications link
                Socket dataSocket = listeningSocket.accept();

                // Step 3) Build output and input objects
                OutputStream out = dataSocket.getOutputStream();
                PrintWriter output = new PrintWriter(new OutputStreamWriter(out));
                boolean userSession = true;
                InputStream in = dataSocket.getInputStream();
                Scanner input = new Scanner(new InputStreamReader(in));
                String incomingMessage = "";
                String response = "";
                User currentUser = null;
                while (userSession) {
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
                                if (info.length == 1) {
                                    currentUser = userManager.searchByUsername(info[0]);
                                    if (currentUser == null) {
                                        currentUser = userManager.addUser(info[0]);
                                    }
                                    response = GameService.USER_CONNECT_RESPONSE;
                                }
                                break;
                            case GameService.ORDER_REQUEST:
                                if (currentUser != null) {
                                    if (info.length == 3) {
                                        try {
                                            double price = Double.parseDouble(info[2]);
                                            if (info[0].equalsIgnoreCase("B")) {
                                                Order o1 = new Order(currentUser.getUserName(), info[1], price);
                                                Game g = gameManager.buyGame(o1);
                                                if (g == null) {
                                                    if (orderManager.addOrder(currentUser.getUserName(), info[1], price) == null) {
                                                        response = "Order already exist";
                                                    } else {
                                                        response = "order was added to list";
                                                    }
                                                } else {
                                                    response = "MATCH" + GameService.ACTION_DELIMITER + "B" + GameService.DELIMITER + info[1] + GameService.DELIMITER + g.getPrice() + GameService.DELIMITER + g.getGameOwner();
                                                }

                                            } else if (info[0].equalsIgnoreCase("S")) {
                                                Game g1 = new Game(info[1], currentUser.getUserName(), price);
                                                Order o1 = orderManager.sellGame(g1);
                                                if (o1 == null) {
                                                    if (gameManager.addGame(info[1], currentUser.getUserName(), price)) {
                                                        response = "Game added to list";
                                                    } else {
                                                        response = "Game already exist";
                                                    }
                                                } else {
                                                    response = "MATCH" + GameService.ACTION_DELIMITER + "S" + GameService.DELIMITER + info[1] + GameService.DELIMITER + o1.getPrice() + GameService.DELIMITER + o1.getUserName();
                                                }

                                            }
                                        } catch (NumberFormatException e) {
                                            response = GameService.INVALID_REQUEST;
                                        }

                                    } else {
                                        response = GameService.INVALID_REQUEST;
                                    }
                                } else {
                                    response = "Not connected";
                                }
                                break;
                            case GameService.VIEW_REQUEST:
                                if (currentUser != null) {
                                    response = "";
                                    // response = "Games";
                                    response += gameManager.getGames() + "$$";
                                    //response += "Orders";
                                    response += orderManager.getOrders();
                                } else {
                                    response = "Not connected";
                                }
                                break;
                            case GameService.CANCEL_REQUEST:
                                if (currentUser != null) {
                                    if (info.length == 3) {
                                        try {
                                            double price = Double.parseDouble(info[2]);
                                            if (info[0].equalsIgnoreCase("B")) {
                                                boolean remove = orderManager.removeOrder(currentUser.getUserName(), info[1], price);
                                                if (remove) {
                                                    response = GameService.CANCEL_CANCELLED_RESPONSE;
                                                } else {
                                                    response = GameService.NOT_FOUND_RESPONSE;
                                                }
                                            } else if (info[0].equalsIgnoreCase("S")) {
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
                                    response = "Not connected";
                                }
                                break;
                            case GameService.END_REQUEST:
                                if (currentUser != null) {
                                    currentUser = null;
                                    userSession = false;
                                    response = GameService.END_RESPONSE;
                                } else {
                                    response = "Not connected";
                                }
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


                // Shut down connection
                dataSocket.close();
            }
            listeningSocket.close();
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }
}
