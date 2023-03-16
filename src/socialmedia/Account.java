package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

public class Account implements Serializable {
    private static int latestId = 0;
    private final int id;
    private String handle;
    private String description;


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

    public ArrayList<Post> getPosts()
    {
        ArrayList<Post> posts = new ArrayList<>();
        for (Post post: SocialMedia.posts)
        {
            if (Objects.equals(post.getHandle(), handle))
            {
                posts.add(post);
            }

        }
        return posts;
    }

    public int getNumberOfEndorsements()
    {
        int total = 0;
        for (Post post: SocialMedia.posts)
        {
            if (Objects.equals(post.getHandle(), handle))
            {
                total = total + post.getNumberOfEndorsements();
            }

        }
        return total;
    }

}
