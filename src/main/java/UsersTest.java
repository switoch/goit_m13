import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

public class UsersTest {
    public final static String url = "https://jsonplaceholder.typicode.com/users";
    public static String jsonString;

    public static HttpClient client;

    public static void main(String[] args) throws IOException, InterruptedException {

        jsonString = getAllUsers();
        System.out.println("jsonString = " + jsonString);

        User.Address.Geo geo = new User.Address.Geo("123", "456");
        User.Address address = new User.Address("Topolya", "2", "Dnipro", "49040", geo);
        User.Company company = new User.Company("goit_company", "Glory to Ukraine", "bs");
        User newUser = new User("goit", "module13", "bakkofolmo@gufum.com", address, "+38093", "https://www.edu.goit.global/", company);

        newUser.setUsername("goit_updated");


        client = HttpClient.newBuilder()
                .version(HttpClient.Version.HTTP_1_1)
                .build();

        createUser(newUser);
        updateUser(newUser, 6);
        deleteUser(11);
        System.out.println("User with id 6 = " + getUserById(newUser, 6));
        System.out.println("username = " + getUserByUsername(newUser, "Samantha"));
    }

    public static void updateUser(User user, int id) throws IOException, InterruptedException {
        HttpRequest putRequest = HttpRequest.newBuilder()
                .uri(URI.create(url + "/" + id))
                .header("Content-Type", "application/json")
                .PUT(HttpRequest.BodyPublishers.ofString(user.getInfo()))
                .build();

        HttpResponse<String> putResponse = client.send(putRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + putResponse.statusCode());
        System.out.println("response = " + putResponse);
        System.out.println("response.body() = " + putResponse.body());

    }

    public static void createUser(User user) throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(user.getInfo()))
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + postResponse.statusCode());
        System.out.println("response = " + postResponse);
        System.out.println("response.body() = " + postResponse.body());

    }

    public static void deleteUser(int id) throws IOException, InterruptedException {
        HttpRequest postRequest = HttpRequest.newBuilder()
                .uri(URI.create(url + "/" + id))
                .header("Content-Type", "application/json")
                .DELETE()
                .build();

        HttpResponse<String> postResponse = client.send(postRequest, HttpResponse.BodyHandlers.ofString());
        System.out.println("response.statusCode() = " + postResponse.statusCode());
        System.out.println("response = " + postResponse);
    }

    public static String getUserById(User user, int id) {
        Type type = TypeToken.getParameterized(List.class, User.class)
                .getType();
        List<User> list = new Gson().fromJson(jsonString, type);
        return list.stream()
                .filter(u -> u.getId() == id)
                .findFirst()
                .orElse(user)
                .getInfo();
    }

    public static String getUserByUsername(User user, String username) {
        Type type = TypeToken.getParameterized(List.class, User.class)
                .getType();
        List<User> list = new Gson().fromJson(jsonString, type);
        return list.stream()
                .filter(u -> u.getUsername().equals(username))
                .findFirst()
                .orElse(user)
                .getInfo();
    }

    public static String getAllUsers() throws IOException {
        return Jsoup.connect(url)
                .ignoreContentType(true)
                .get()
                .body()
                .text();
    }
}
