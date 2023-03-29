package socialmedia;

/**
 *  @author Student numbers: 720027904, 720050952
 *  @version 1.0
 *
 *  Represents a comment of a Post in the UoK social media platform.
 */
public class Comment extends Post {
    Post originalPost;

    /**
     * Constructor for creating a new comment.
     *
     * @param handle          the handle of the account making the comment
     * @param originalPostId  the ID of the post being commented on
     * @param message         the message of the comment
     */
    public Comment(String handle, int originalPostId, String message) {
        super(handle, message);
        Post postToFind = null;
        for (Post post : SocialMedia.posts) {
            if (post.getPostId() == originalPostId) {
                postToFind = post;
                break;
            }
        }
        this.originalPost = postToFind;
    }

    /**
     * Returns the post being commented on.
     *
     * @return the post being commented on
     */
    public Post getOriginalPost() {
        return originalPost;
    }

    /**
     * Sets the post being commented on.
     *
     * @param originalPostId the ID of the post being commented on
     */
    public void setOriginalPostId(int originalPostId) {
        for (Post post : SocialMedia.posts) {
            if (post.getPostId() == originalPostId) {
                this.originalPost = post;
                break;
            }
        }
    }
}
