package socialmedia;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * @author Student numbers: 720027904, 720050952
 * @version 1.0
 *
 * Represents a post in the UoK social media platform.
 */
public class Post implements Serializable {

    /**
     *  Variable keeping track of the next post id.
     */
    private static int nextPostId = 0;

    /**
     * Unique numerical identifier for this post.
     */
    private final int postId;

    /**
     * The account that posted this post.
     */
    private final String handle;

    /**
     * The text message of this post.
     */
    private final String message;

    /**
     * ArrayList of comments received by this post.
     */
    protected ArrayList<Comment> comments;

    /**
     * ArrayList of endorsements received by this post.
     */
    protected ArrayList<Endorsement> endorsements;

    /**
     * Shows whether this post is actionable or not.
     */
    protected boolean actionable = true;

    /**
     * Constructs a new Post object with the given handle and message.
     *
     * @param handle The account that posted this post.
     * @param message The text message of this post.
     */
    public Post(String handle, String message) {
        this.postId = nextPostId++;
        this.handle = handle;
        this.message = message;
        this.comments = new ArrayList<>();
        this.endorsements = new ArrayList<>();
    }

    /**
     * Returns the unique numerical identifier for this post.
     *
     * @return The post id.
     */
    public int getPostId() {
        return postId;
    }

    /**
     * Returns the account that posted this post.
     *
     * @return The handle.
     */
    public String getHandle() {
        return handle;
    }

    /**
     * Returns the text message of this post.
     *
     * @return The message.
     */
    public String getMessage() {
        return message;
    }

    /**
     * Returns an arraylist of comments received by this post.
     *
     * @return The arraylist of comments.
     */
    public ArrayList<Comment> getComments() {
        return comments;
    }

    /**
     * Adds a new comment to this post.
     *
     * @param comment The comment to be added.
     */
    public void addComment(Comment comment) {
        comments.add(comment);
    }

    /**
     * Adds a new endorsement to this post.
     *
     * @param endorsement The endorsement to be added.
     */
    public void addEndorsement(Endorsement endorsement) {
        endorsements.add(endorsement);
    }

    /**
     * Returns the number of endorsements received by this post.
     *
     * @return The number of endorsements.
     */
    public int getNumberOfEndorsements() {
        return endorsements.size();
    }

    /**
     * Returns the number of comments received by this post.
     *
     * @return The number of comments.
     */
    public int getNumberOfComments() {
        return comments.size();
    }

    /**
     * Sets whether this post is actionable or not.
     *
     * @param actionable The value to be set.
     */
    public void setActionable(boolean actionable) {
        this.actionable = actionable;
    }

    /**
     * Returns whether this post is actionable or not.
     *
     * @return The value of the actionable flag.
     */
    public boolean getActionable() {
        return this.actionable;
    }

    /**
     * Resets the static variable nextPostId to 1, used in resetting the platform.
     */
    public void reset()
    {
        nextPostId = 1;
    }
}