package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Student numbers: 720027904, 720050952
 * @version 1.0
 *
 * Represents a UoK social media account.
 */
public class Account implements Serializable {
    private static int latestId = 0;
    private final int id;
    private String handle;
    private String description;

    /**
     * Creates an account with the given handle.
     * @param handle the handle for the account
     */
    public Account(String handle) {
        this.id = ++latestId;
        this.handle = handle;
    }

    /**
     * Creates an account with the given handle and description.
     * @param handle the handle for the account
     * @param description a description for the account
     */
    public Account(String handle, String description) {
        this.id = ++latestId;
        this.handle = handle;
        this.description = description;
    }

    /**
     * Returns the ID of the account.
     * @return the ID of the account
     */
    public int getId() {
        return id;
    }

    /**
     * Returns the handle of the account.
     * @return the handle of the account
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Sets the handle of the account.
     * @param handle the new handle for the account
     */
    public void setHandle(String handle) {
        this.handle = handle;
    }

    /**
     * Returns the description of the account.
     * @return the description of the account
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets the description of the account.
     * @param description the new description for the account
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Returns an ArrayList of posts made by the account.
     * @return an ArrayList of posts made by the account
     */
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

    /**
     * Returns the total number of endorsements received by the account.
     * @return the total number of endorsements received by the account
     */
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

    /**
     * Resets the latest ID to 0.
     */
    public void reset()
    {
        latestId = 0;
    }
}
