package socialmedia;
import java.io.*;

public class SocialMediaTests {
    public static void main(String[] args) throws NotActionablePostException, PostIDNotRecognisedException, IllegalHandleException, InvalidPostException, HandleNotRecognisedException, InvalidHandleException {
        testStringBuilderThing();
    }

    public static void testStringBuilderThing() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        SocialMedia platform = new SocialMedia();
        platform.createAccount("lyssie");
        platform.createPost("lyssie", "this is my test!");
        platform.createAccount("shannyn");
        platform.commentPost("shannyn", 0, "this is my attempt to comment!");
        platform.commentPost("lyssie", 0, "this is my attempt to comment twice!");
        platform.commentPost("lyssie", 1, "this is my attempt to comment on my first comment!");
        platform.createPost("lyssie", "this is my test 2!");
       // System.out.println(platform.showIndividualPost(0));
        System.out.println(platform.showPostChildrenDetails(0));

        System.out.println(platform.getTotalOriginalPosts());

    }



}
