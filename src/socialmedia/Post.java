package socialmedia;
import java.util.ArrayList;
public class Post {
    private static int nextPostId = 0; // static variable to keep track of the next post id
    private final int postId; // unique numerical identifier for this post
    private final String handle;// the account that posted this post
    private final String message; // the text message of this post
    protected ArrayList<Comment> comments; // list of comments received by this post
    protected ArrayList<Endorsement> endorsements; // list of endorsements received by this post

    public Post(String handle, String message) {
        this.postId = nextPostId++;
        this.handle = handle;
        this.message = message;
        this.comments = new ArrayList<>();
        this.endorsements = new ArrayList<>();
    }

    public int getPostId() {
        return postId;
    }

    public String getHandle() {
        return handle;
    }

    public String getMessage() {
        return message;
    }

    public ArrayList<Comment> getComments() {
        return comments;
    }

    public ArrayList<Endorsement> getEndorsements() {
        return endorsements;
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }

    public void addEndorsement(Endorsement endorsement) {
        endorsements.add(endorsement);
    }
}