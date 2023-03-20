package socialmedia;
import java.io.*;
import java.util.Objects;


//TODO: make sure when deleting account, all comments and endorsements also removed
//TODO: make sure that the outputting works of the tree of comments on a post
//TODO: check that when posts removed they are formatted correctly and error messages work correctly
public class SocialMediaTests {

    public static SocialMedia platform = new SocialMedia();
    public static void main(String[] args) throws NotActionablePostException, PostIDNotRecognisedException, InvalidPostException, HandleNotRecognisedException, InvalidHandleException, IOException, ClassNotFoundException, IllegalHandleException {
        //testStringBuilderThing();
        // createAccountTest();
       saveEraseAndLoadPlatformTest();
       getMostEndorsedAccountTest();
    }

    public static void testStringBuilderThing() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException, IOException, ClassNotFoundException {

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        platform.commentPost("alyssa", 1, "first comment!");
        platform.commentPost("alyssa", 1, "second comment!");
        platform.commentPost("alyssa", 2, "first subcomment on first comment!");

        for (Post post : SocialMedia.posts)
        {
            System.out.println("id: " + post.getPostId() + " message: " + post.getMessage());
        }
        platform.deletePost(2);
        System.out.println(platform.showPostChildrenDetails(1));


    }

    public static void createAccountTest() throws IllegalHandleException, InvalidHandleException {
        platform.createAccount("user1");
        assert SocialMedia.profiles.size() == 1 : "the number of accounts should be 1";

        try {
            platform.createAccount("user1");
            throw new AssertionError("Test failed");
        }
        catch (IllegalHandleException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.IllegalHandleException: Handle already taken, please try again."));
        }

    }

    public static void getMostEndorsedAccountTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();
        platform.createAccount("user1");
        platform.createAccount("user2");
        platform.createPost("user1", "first post!");
        platform.createPost("user2", "second post!");
        platform.endorsePost("user1", 1);
        platform.endorsePost("user2", 1);
        platform.endorsePost("user1", 2);

        //for (Account profile : SocialMedia.profiles)
        //{
        // System.out.println(platform.showAccount(profile.getHandle()));
        //}

        assert platform.getMostEndorsedAccount() == 1; // account index starts at 0

    }

    public static void saveEraseAndLoadPlatformTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException, IOException, ClassNotFoundException {
        platform.erasePlatform();

        platform.createAccount("user1");
        platform.createPost("user1", "first post!");
        platform.createAccount("user2");
        platform.createPost("user2", "second post!");
        platform.commentPost("user2", 1, "comment on first post!"); // post indexing starts at 1 because of deleted posts
        platform.endorsePost("user2", 1);

        assert SocialMedia.profiles.size() == 2;
        assert SocialMedia.posts.size() == 5; // 5 posts (2 original, 1 comment, 1 endorsement and the generic "deleted post")
        assert platform.getTotalCommentPosts() == 1;
        assert platform.getTotalEndorsmentPosts() == 1;
        assert platform.getTotalOriginalPosts() == 3;

        platform.savePlatform("save.ser");

        platform.erasePlatform();

        assert SocialMedia.profiles.size() == 0;
        assert SocialMedia.posts.size() == 1; // deleted post
        assert platform.getTotalCommentPosts() == 0;
        assert platform.getTotalEndorsmentPosts() == 0;
        assert platform.getTotalOriginalPosts() == 1; // deleted post

        platform.loadPlatform("save.ser");

        assert SocialMedia.profiles.size() == 2;
        assert SocialMedia.posts.size() == 5; // 5 posts (2 original, 1 comment, 1 endorsement and the generic "deleted post")
        assert platform.getTotalCommentPosts() == 1;
        assert platform.getTotalEndorsmentPosts() == 1;
        assert platform.getTotalOriginalPosts() == 3;

    }


}
