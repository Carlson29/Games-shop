package Client;

import business.GameService;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client2 {

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
}
