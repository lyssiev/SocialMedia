package socialmedia;
import java.io.*;
import java.util.Objects;


//TODO: make sure when deleting account, all comments and endorsements also removed
//TODO: make sure that the outputting works of the tree of comments on a post
//TODO: check that when posts removed they are formatted correctly and error messages work correctly
public class SocialMediaTests {

    public static SocialMedia platform = new SocialMedia();
    public static void main(String[] args) throws NotActionablePostException, PostIDNotRecognisedException, InvalidPostException, HandleNotRecognisedException, InvalidHandleException, IOException, ClassNotFoundException, IllegalHandleException {
       testStringBuilderThing();
       // createAccountTest();
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



}
