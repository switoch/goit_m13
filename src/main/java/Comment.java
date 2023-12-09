public class Comment {
        public int postId;
        public int id;
        public String name;
        public String email;
        public String body;

    public Comment(Post postId, int id, String name, String email, String body) {
        this.postId = postId.getId();
        this.id = id;
        this.name = name;
        this.email = email;
        this.body = body;
    }
}
