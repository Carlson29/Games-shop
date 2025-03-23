package Client;

import business.GameService;
import business.OrderManager;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Client {

    //private static boolean shuttingDown = false;
    private static String choice = "-1";
    private static boolean validClient = true;
    private static boolean connectUsername = true;

    public static void main(String[] args) {
        Scanner userInput = new Scanner(System.in);
        //loggedIn = false;
        while (validClient) {
            // Requests a connection
            try (Socket dataSocket = new Socket(GameService.HOST, GameService.PORT)) {

                // Sets up communication lines
                // Create a Scanner to receive messages
                // Create a Printwriter to send messages
                try (Scanner input = new Scanner(dataSocket.getInputStream());
                     PrintWriter output = new PrintWriter(dataSocket.getOutputStream())) {
                    boolean validSession = true;
                    // Repeated:
                    while (validSession) {
                        // Ask user for information to be sent
                        System.out.println("Please enter a message to be sent (Send EXIT to end):");
                        String message = generateRequest(userInput);
                        if (message != null) {
                            // Send message to server
                            output.println(message);
                            // Flush message through to server
                            output.flush();

                            // Receive message from server
                            String response = input.nextLine();
                            // Display result to user
                            //System.out.println("Received from server: " + response);

                            //User response
                            if (choice.equalsIgnoreCase("1") && response.equals(GameService.USER_CONNECT_RESPONSE)) {
                                System.out.println("Username connect.");
                            }

                            //Send Order response
                            if (choice.equalsIgnoreCase("2") && response.equals(GameService.ORDER_ADDED_RESPONSE)) {
                                System.out.println("Order added.");
                            }
                            if (choice.equalsIgnoreCase("2") && response.equals(GameService.ORDER_EXIST_RESPONSE)) {
                                System.out.println("Order exist.");
                            }
                            if (choice.equalsIgnoreCase("2") && response.equals(GameService.ORDER_MATCH_RESPONSE)) {
                                System.out.println("Order Match.");
                            }

                            //Cancel response
                            if (choice.equalsIgnoreCase("3") && response.equals(GameService.CANCEL_CANCELLED_RESPONSE)) {
                                System.out.println("Order Cancelled.");
                            }
                            if (choice.equalsIgnoreCase("3") && response.equals(GameService.NOT_FOUND_RESPONSE)) {
                                System.out.println("Order Not Found.");
                            }

                            //View game list
                            if (choice.equalsIgnoreCase("4") && response.equals(GameService.NOT_FOUND_RESPONSE) == false) {
//                                Game decoded = Film.decode(response, FilmService.DELIMITER);
//                                System.out.println(decoded);
                                OrderManager oM = new OrderManager();
                               response= oM.decode(response);
                                System.out.println(response);
                            }

                            //Exit response
                            if (choice.equalsIgnoreCase("0") && response.equals(GameService.END_RESPONSE)) {
                                System.out.println("Goodbye, you're exit.");
                                connectUsername = false;
                                validClient = false;
                            }

                            //Invalid request response
                            if (response.equals(GameService.NOT_FOUND_RESPONSE)) {
                                System.out.println("Please try again, this is invalid choice.");
                            }
                        }
                    }
                }
            } catch (UnknownHostException e) {
                System.out.println("Host cannot be found at this moment. Try again later");
            } catch (IOException e) {
                System.out.println("An IO Exception occurred: " + e.getMessage());
            }
        }
        // Close connection to server
    }

    public static void displayMenu() {
        System.out.println("0) End");
        System.out.println("1) Connect Username");
        System.out.println("2) Send Order");
        System.out.println("3) Cancel Order");
        System.out.println("4) View Order");

    }

    public static String generateRequest(Scanner userInput) {
        boolean valid = false;
        String request = null;

        while (!valid) {
            displayMenu();
            choice = userInput.nextLine();

            String username;
            String gameName;
            String gameStatus;
            double price;

            switch (choice) {
                case "0":
                    System.out.println("Terminate this session?");
                    request = GameService.END_REQUEST;
                    break;
                case "1":
                    System.out.println("Connect Username: ");
                    System.out.println("Enter username: ");
                    username = userInput.nextLine();
                    request = GameService.USER_REQUEST + GameService.ACTION_DELIMITER + username;
                    break;
                case "2":
                    if(connectUsername){
                        System.out.println("Send Order: ");
                        //gameStatus = getValidStatus(userInput,"Enter B(Buy) or S(Sell)");
                        System.out.println("Enter B(Buy) or S(Sell): ");
                        gameStatus = userInput.nextLine();
                        System.out.println("Enter Game name: ");
                        gameName = userInput.nextLine();
                        System.out.println("Enter price: ");
                        price = userInput.nextDouble();
                        request = GameService.ORDER_REQUEST + GameService.ACTION_DELIMITER + gameStatus + GameService.DELIMITER + gameName + GameService.DELIMITER + price;
                    }
                    break;
                case "3":
                    if(connectUsername) {
                        System.out.println("Cancel Order: ");
//                        gameStatus = getValidStatus(userInput,"Enter B(Buy) or S(Sell)");
                        System.out.println("Enter B(Buy) or S(Sell): ");
                        gameStatus = userInput.nextLine();
                        System.out.println("Enter Game name: ");
                        gameName = userInput.nextLine();
                        System.out.println("Enter price: ");
                        price = userInput.nextDouble();
                        request = GameService.CANCEL_REQUEST + GameService.ACTION_DELIMITER + gameStatus + GameService.DELIMITER + gameName + GameService.DELIMITER + price;
                    }
                    break;
                case "4":
                    if(connectUsername){
                        System.out.println("View Order: ");
                        request = GameService.VIEW_REQUEST;
                    }
                    break;
                default:
                    System.out.println("Please select one of the stated options!");
                    System.out.println("------------------------------------");
                    continue;
            }
            valid = true;
        }
        return request;
    }

//    public static String getValidStatus(Scanner userInput, String prompt) {
//        boolean valid = false;
//        String status = null;
//        while (!valid) {
//            System.out.println(prompt);
//            try {
//                status = userInput.nextLine();
//                if (status.equalsIgnoreCase("B") && status.equalsIgnoreCase("S")) {
//                    valid = true;
//                }
//            } catch (InputMismatchException e) {
//                System.out.println("Please enter valid status ID: B(Buy) or S(Sell). ");
//                userInput.nextLine();
//            }
//        }
//        userInput.nextLine();
//        return status;
//    }
}
