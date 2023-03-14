package socialmedia;
import java.util.ArrayList;

public class Comment extends Post {

    private final int commentId;
    private final int postID;

    private static int nextCommentId = 0;
    public Comment(String handle, int id, String message) {
        super(handle, message);
        this.commentId = nextCommentId++;
        this.postID = id;
        this.endorsements = new ArrayList<>();
        this.comments = new ArrayList<>();
    }

    public int getCommentId() {
        return commentId;
    }

    public String getHandle() {
        return super.getHandle();
    }

    public void addEndorsement(Endorsement endorsement) {
        super.addEndorsement(endorsement);
    }

    public void addComment(Comment comment) {
        comments.add(comment);
    }
}