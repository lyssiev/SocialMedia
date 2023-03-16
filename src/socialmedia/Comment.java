package socialmedia;
import java.util.ArrayList;

public class Comment extends Post {
    private int originalPostId;

    public Comment(String handle, int originalPostId, String message) {
        super(handle, message);
        this.originalPostId = originalPostId;
    }

    public int getOriginalPostId() {
        return originalPostId;
    }

}