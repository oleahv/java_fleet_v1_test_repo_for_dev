package server;

import LoginInfo.Credentials;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.net.http.HttpResponse.BodyHandlers;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.util.Base64;
import java.util.Map;
import java.util.concurrent.CompletableFuture;




// This class is based on ChatGPT from openai.com
public class WebServer {



  /*  // Oauth client ID and secret
    private static final String CLIENT_ID = "53ea2ef0-8fbc-4b98-b3b7-edbafebe5ca4";
    private static final String CLIENT_SECRET = "GgmBzs2Lo9UKGcnc6ftuAg0AzzQ63OYE";

    // The URL to High Mobility's authorization endpoint
    //private static final String AUTHORIZATION_ENDPOINT = "https://api.high-mobility.com/v1/oauth/authorize";
    private static final String AUTHORIZATION_ENDPOINT = "https://sandbox.owner-panel.high-mobility.com/oauth/new";


    // The redirect URI that you registered with High Mobility when you created your client ID and secret
    private static final String REDIRECT_URI = "http://localhost:8080/callback";
*/


        public void RunWebServer() {
        Credentials credentials = new Credentials();
        // TODO: &scope=hm.fleets.manage
        // Step 1: Redirect the user to High Mobility's authorization endpoint to request permission for your application to access their resources
       // https://sandbox.owner-panel.high-mobility.com/hm_cloud/o/bb8545d7-22bd-4bbc-b3e4-58a84a5328ac/oauth?app_id=50A018C136CF9BB7CE539590&client_id=e52b2864-7611-4f26-94e4-d13f7039f25d&redirect_uri=http%3A%2F%2Fhm-postman.local

        String authorizationUrl = credentials.getAUTHORIZATION_ENDPOINT() + "?client_id=" + credentials.getOAuthClientId() + "&redirect_uri=" + credentials.getREDIRECT_URI() + "&response_type=code&scope=hm.fleets.manage";
        System.out.println("Please visit this URL to authorize your application: " + authorizationUrl);

        // Step 2: Implement web server to handle incoming requests on the redirect URI
            HttpServer server = null;
            try {
                server = HttpServer.create(new InetSocketAddress(8080), 0);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            server.createContext("/callback", new HttpHandler() {
            @Override
            public void handle(HttpExchange exchange) throws IOException {
                String query = exchange.getRequestURI().getQuery();
                String authorizationCode = query.substring(query.indexOf("=") + 1);

                // Step 3: Exchange authorization code for access token using High Mobility's token endpoint
                HttpClient client = HttpClient.newHttpClient();
                HttpRequest request = HttpRequest.newBuilder()
                        .uri(URI.create("https://sandbox.api.high-mobility.com/v1/access_tokens"))
                        .header("Content-Type", "application/x-www-form-urlencoded")
                        .POST(HttpRequest.BodyPublishers.ofString("grant_type=authorization_code&code=" + authorizationCode + "&redirect_uri=" + credentials.getREDIRECT_URI()))
                        .header("Authorization", "Basic " + Base64.getEncoder().encodeToString((credentials.getOAuthClientId() + ":" + credentials.getOAuthClientSecret()).getBytes()))
                        .build();

                CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, BodyHandlers.ofString());
                futureResponse.thenApply(HttpResponse::body)
                        .thenAccept(System.out::println)
                        .join();

                String response = "Authorization code: " + authorizationCode;
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, response.getBytes().length);
                exchange.getResponseBody().write(response.getBytes());
                exchange.close();
            }
        });
        server.start();

        // Step 4: After getting the access token, use it to make requests to the Fleet API
        String accessToken = "";

        try {
            File file = new File("src/main/java/accessTokens/" + credentials.getVin() + "_vehicleAccess.json");
            String jsonContent = Files.readString(file.toPath(), StandardCharsets.UTF_8);
            JsonObject jsonObject = JsonParser.parseString(jsonContent).getAsJsonObject();
            JsonObject accessJsonObject = jsonObject.get("accessToken").getAsJsonObject();
            //JsonObject jsonObject = parser.parse(new FileReader(file)).getAsJsonObject();

            accessToken = accessJsonObject.get("access_token").getAsString();

  /*          JsonObject jsonObject = parser.parse(new FileReader(file)).getAsJsonObject();
            JsonObject mutableJsonObject = new JsonObject();
            for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                mutableJsonObject.add(entry.getKey(), entry.getValue());
            }
            accessToken = mutableJsonObject.get("accessToken").getAsString();
*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        /*// Example request to get list of vehicles
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                //.uri(URI.create("https://api.high-mobility.com/v1/fleet/vehicles"))
                .uri(URI.create("https://sandbox.api.high-mobility.com/v1/fleet/vehicles"))
                .header("Authorization", "Bearer " + accessToken)
                .build();

        CompletableFuture<HttpResponse<String>> futureResponse = client.sendAsync(request, BodyHandlers.ofString());
        futureResponse.thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();

         */
    }
}
