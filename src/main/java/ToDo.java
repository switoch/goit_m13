import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class ToDo {
    public static void main(String[] args) throws IOException {
        Type type = TypeToken.getParameterized(List.class, ToDoUser.class)
                .getType();
        List<ToDoUser> usersList =  new Gson().fromJson(connect(1), type);
        List<ToDoUser> result = usersList.stream()
                .filter(f -> !f.isCompleted())
                .toList();
        for(ToDoUser user: result){
            System.out.println(user.getInfo());
        }
    }

    public static String connect(int id) throws IOException {
        String url = "https://jsonplaceholder.typicode.com/users/" + id + "/todos";
        return Jsoup.connect(url)
                .ignoreContentType(true)
                .get()
                .body()
                .text();
    }
}
