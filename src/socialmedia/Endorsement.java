package socialmedia;

/**
 *  @author Student numbers: 720027904, 720050952
 *  @version 1.0
 *
 *  Represents an endorsement of a Post in the UoK social media platform.
 */
public class Endorsement extends Post {
    private final int originalPostId;

    /**
     * Constructor for an endorsement.
     * @param handle The handle of the user endorsing the post.
     * @param message The message of the endorsement.
     * @param originalPostId The ID of the post being endorsed.
     */
    public Endorsement(String handle, String message, int originalPostId) {
        super(handle, message);
        this.originalPostId = originalPostId;
        this.actionable = false;
    }

    /**
     * Gets the ID of the post being endorsed.
     * @return The ID of the post being endorsed.
     */
    public int getOriginalPostId()
    {
        return originalPostId;
    }

    /**
     * Overrides the addEndorsement method of the Post class to do nothing,
     * as endorsements cannot be endorsed further.
     * @param endorsement The endorsement being added.
     */
    @Override
    public void addEndorsement(Endorsement endorsement) {
        // do nothing, cannot be endorsed
    }

    /**
     * Overrides the addComment method of the Post class to do nothing,
     * as endorsements cannot be commented on.
     * @param comment The comment being added.
     */
    @Override
    public void addComment(Comment comment) {
        // do nothing, cannot be commented
    }
}
