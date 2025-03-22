package Client;

import business.GameService;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {

    //private static boolean shuttingDown = false;
    private static String choice = "-1";
    private static boolean connectUsername = true;

    public static void main(String[] args) {
        try
        {
            // Step 1 (on consumer side) - Establish channel of communication
            Socket dataSocket = new Socket(GameService.HOST, GameService.PORT);

            // Step 3) Build output and input objects
            OutputStream out = dataSocket.getOutputStream();
            PrintWriter output = new PrintWriter(new OutputStreamWriter(out));

            InputStream in = dataSocket.getInputStream();
            Scanner input = new Scanner(new InputStreamReader(in));

            Scanner keyboard = new Scanner(System.in);
            String message = "";
            boolean state =true;
            while(state)
            {
                System.out.println("Please enter a message:");
                message = keyboard.nextLine();

                // Exchange messages with provider
                output.println(message);
                output.flush();

                String response = input.nextLine();
                System.out.println("Response: " + response);
            }
            dataSocket.close();
        }catch(Exception e)
        {
            System.out.println("An error occurred: "  + e.getMessage());
        }
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
            //String gameOwner;
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
                        System.out.println("Game to Buy or Sell (Enter B or S): ");
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
                        System.out.println("Game to Buy or Sell (Enter B or S): ");
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
}
