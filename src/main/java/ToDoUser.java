import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class ToDoUser {
    public String userId;
    public int id;
    public String title;
    public boolean completed;

    public ToDoUser(String userId, int id, String title, boolean completed) {
        this.userId = userId;
        this.id = id;
        this.title = title;
        this.completed = completed;
    }

    public boolean isCompleted() {
        return completed;
    }

    public String getInfo() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        return gson.toJson(this);
    }
}
