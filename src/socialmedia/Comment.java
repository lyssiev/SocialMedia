package socialmedia;
import java.util.ArrayList;

public class Comment extends Post {
    Post originalPost;

    public Comment(String handle, int originalPostId, String message) {
        super(handle, message);
        Post postToFind = null;
        for (Post post : SocialMedia.posts)
        {
            if (post.getPostId() == originalPostId)
            {
                postToFind = post;
            }
        }
        this.originalPost = postToFind;
    }

    public Post getOriginalPost() {
        return originalPost;
    }

    public void setOriginalPostId(int originalPostId) {
        for (Post post : SocialMedia.posts) {
            if (post.getPostId() == originalPostId) {
                this.originalPost = post;
                break;
            }
        }
    }

}