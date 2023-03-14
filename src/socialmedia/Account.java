package socialmedia;

import java.util.ArrayList;

public class Account {
    private static int latestId = 0;
    private final int id;
    private String handle;
    private String description;

    private ArrayList<Post> posts;

    public Account(String handle) {
        this.id = ++latestId;
        this.handle = handle;
    }
    public Account(String handle, String description) {
        this.id = ++latestId;
        this.handle = handle;
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public String getHandle() {
        return handle;
    }

    public void setHandle(String handle) {
        this.handle = handle;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void addPost(Post post){
        posts.add(post);
    }

}
