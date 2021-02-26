package server;

import java.io.*;
import java.net.*;
import com.sun.net.httpserver.*;
import handlers.*;


/*
 * Starts server and listens to requests made, then sends them off to the appropriate handlers.
 */
public class Server {
    private static final int MAX_WAITING_CONNECTIONS = 12;

    /**
     * Initializes and runs the server
     * @param portNumber Port number for accepting incoming client connections
     */
    private void run(String portNumber) {
        System.out.println("Initializing HTTP Server");

        HttpServer server;
        try {
            server = HttpServer.create(
                    new InetSocketAddress(Integer.parseInt(portNumber)),
                    MAX_WAITING_CONNECTIONS);
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        server.setExecutor(null);

        System.out.println("Creating contexts");

        server.createContext("/user/register", new RegisterHandler());
        server.createContext("/user/login", new LoginHandler());
        server.createContext("/clear", new ClearHandler());
        server.createContext("/fill", new FillHandler());
        server.createContext("/load", new LoadHandler());
        server.createContext("/person", new PersonHandler());
        server.createContext("/event", new EventHandler());
        server.createContext("/", new FileHandler());

        System.out.println("Starting server");
        server.start();
        System.out.println("Server started");
    }

    /**
     * Main method for server program
     * @param args Port number for accepting incoming client connections
     */
    public static void main(String[] args) {
        String portNumber = args[0];
        new Server().run(portNumber);
    }
}
