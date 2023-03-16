package socialmedia;
import java.io.*;


//TODO: make sure when deleting account, all comments and endorsements also removed
//TODO: make sure that the outputting works of the tree of comments on a post
//TODO: check that when posts removed they are formatted correctly and error messages work correctly
public class SocialMediaTests {
    public static void main(String[] args) throws NotActionablePostException, PostIDNotRecognisedException, IllegalHandleException, InvalidPostException, HandleNotRecognisedException, InvalidHandleException, IOException, ClassNotFoundException {
        testStringBuilderThing();
    }

    public static void createAccountTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException, IOException, ClassNotFoundException {
        SocialMediaPlatform platform = new SocialMediaPlatform();
          String handle = "John ";
          String description = "This is my social media ";
            int accountId = platform.createAccount(handle, description);

            // Assert that account ID is greater than zero
            assertTrue(accountId > 0);

            // Assert that the account was created with the correct handle and description
            SocialMediaAccount account = platform.getAccount(accountId);
            assertEquals(handle, account.getHandle());
            assertEquals(description, account.getDescription());


    }



}
