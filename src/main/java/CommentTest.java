import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import org.jsoup.Jsoup;

import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;
import java.util.stream.Collectors;

public class CommentTest {
    public static void main(String[] args) throws IOException {
        int userId = 3;
        Type typePost = TypeToken.getParameterized(List.class, Post.class)
                .getType();
        List<Post> postList =  new Gson().fromJson(connect(userId, "users/", "/posts"), typePost);
        List<Integer> ids = postList.stream()
                .map(Post::getId)
                .peek(System.out::println)
                .toList();
        Type typeComment = TypeToken.getParameterized(List.class, Comment.class)
                .getType();

        for(int id:ids) {
            FileWriter writer = new FileWriter("user-" + userId +"-post-"+id+"-comments.json");
            List<Comment> commentList = new Gson().fromJson(connect(id, "posts/", "/comments"), typeComment);
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            writer.write(gson.toJson(commentList));
            writer.flush();
            writer.close();
        }

    }

    public static String connect(int id, String folder, String path) throws IOException {
        String url = "https://jsonplaceholder.typicode.com/" + folder + id + path;
        return Jsoup.connect(url)
                .ignoreContentType(true)
                .get()
                .body()
                .text();
    }
}
