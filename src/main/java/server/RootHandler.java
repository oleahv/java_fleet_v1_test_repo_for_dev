package server;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;

//https://www.codeproject.com/Tips/1040097/Create-a-Simple-Web-Server-in-Java-HTTP-Server
public class RootHandler implements HttpHandler {
    @Override

    public void handle(HttpExchange he) throws IOException {
      /*  String response = "<h1>Server start success
        if you see this message</h1>" + "<h1>Port: " + port + "</h1>";
        he.sendResponseHeaders(200, response.length());
        OutputStream os = he.getResponseBody();
        os.write(response.getBytes());
        os.close();
    */
    }

}
