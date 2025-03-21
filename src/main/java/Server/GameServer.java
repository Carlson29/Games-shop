package Server;

import business.*;

import java.io.*;
import java.net.BindException;
import java.net.ServerSocket;
import java.net.Socket;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Scanner;

public class GameServer {
    private static UserManager userManager;
    private static GameManager gameManager;
    private static OrderManager orderManager;
    private static boolean serverState = true;


    public static void main(String[] args) {



        try (ServerSocket listeningSocket = new ServerSocket(GameService.PORT)) {
           userManager = new UserManager();
           gameManager = new GameManager();
           orderManager= new OrderManager();
         while (serverState) {
             Socket dataSocket = listeningSocket.accept();
             Handler handler = new Handler(dataSocket, userManager, gameManager, orderManager);
             Thread wrapper = new Thread(handler);
             wrapper.start();
         }

        } catch (BindException e) {
            System.out.println("BindException occurred when attempting to bind to port " + GameService.PORT);
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println("IOException occurred on server socket");
            System.out.println(e.getMessage());
        }


    }
}
