public class Post {
    public int userId;
        public int id;
        public String title;
        public String body;

    public Post(User userId, int id, String title, String body) {
        this.userId = userId.getId();
        this.id = id;
        this.title = title;
        this.body = body;
    }

    public int getId() {
        return id;
    }
}
