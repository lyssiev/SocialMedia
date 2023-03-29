package socialmedia;
import java.io.*;
import java.util.Objects;

/**
 * @author Student numbers: 720027904, 720050952
 * @version 1.0
 *
 * This class tests the functionality of the social media platform implementation
 */
public class SocialMediaTests {

    public static SocialMedia platform = new SocialMedia();
    public static void main(String[] args) throws NotActionablePostException, PostIDNotRecognisedException, InvalidPostException, HandleNotRecognisedException, InvalidHandleException, IOException, ClassNotFoundException, IllegalHandleException, AccountIDNotRecognisedException {
        createAccountTest();
        removeAccountTest();
        changeAccountHandleTest();
        updateAccountDescriptionTest();
        showAccountTest();
        createPostTest();
        endorsePostTest();
        commentPostTest();
        deletePostTest();
        showIndividualPostTest();
        showPostChildrenDetailsTest();
        getNumberOfProfilesTest();
        getTotalOriginalPostsTest();
        getTotalEndorsementPostsTest();
        getTotalCommentPostsTest();
        saveEraseAndLoadPlatformTest();
        getMostEndorsedAccountAndPostTest();
    }

    /**
     * This method tests the changeAccountHandle method of the SocialMedia class.
     *
     * @throws IllegalHandleException       if the new handle is already in use
     * @throws InvalidHandleException       if the new handle is invalid
     * @throws HandleNotRecognisedException if the old handle is not recognised
     */
    private static void changeAccountHandleTest() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
        platform.erasePlatform();
        platform.createAccount("sherya", "description");

        // Test case 1: valid handle change
        platform.changeAccountHandle("sherya", "sherya1");
        assert SocialMedia.profiles.size() == 1 : "the number of accounts should be 1";

        // Test case 2: handle not recognised
        try {
            platform.changeAccountHandle("test", "test1");
            throw new AssertionError("Test failed");
        } catch (HandleNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that is not a recognised handle."));
        }
        // Test case 3: invalid handle
        try {
            platform.changeAccountHandle("sherya1", "sherya 1");
            throw new AssertionError("Test failed");
        } catch (InvalidHandleException e) {
            assert (Objects.equals(e.toString(), "socialmedia.InvalidHandleException: Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty."));
        }
        // Test case 4: illegal handle
        platform.createAccount("sophia");
        try {
            platform.changeAccountHandle("sophia", "sherya1");
            throw new AssertionError("Test failed");
        } catch (IllegalHandleException e) {
            assert (Objects.equals(e.toString(), "socialmedia.IllegalHandleException: Sorry, that handle is already in use."));
        }
    }

    /**
     * This method tests the createAccount method of the SocialMedia class.
     *
     * @throws IllegalHandleException if the handle is already taken
     * @throws InvalidHandleException if the handle is invalid
     */
    public static void createAccountTest() throws IllegalHandleException, InvalidHandleException {
        platform.erasePlatform();

        platform.createAccount("user1");
        assert SocialMedia.profiles.size() == 1 : "the number of accounts should be 1";

        // checking exception is thrown if handle has already been taken
        try {
            platform.createAccount("user1");
            throw new AssertionError("Test failed");
        } catch (IllegalHandleException e) {
            assert (Objects.equals(e.toString(), "socialmedia.IllegalHandleException: Handle already taken, please try again."));
        }

        // checking exception is thrown if the handle is invalid
        try {
            platform.createAccount("user 2");
            throw new AssertionError("Test failed");
        } catch (InvalidHandleException e) {
            assert (Objects.equals(e.toString(), "socialmedia.InvalidHandleException: Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty."));
        }

        // checking exception is thrown if the handle already being used in system
        try {
            platform.createAccount("user1", "This is my Social Media");
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (IllegalHandleException e) {
            String expectedErrorMessage = "Handle already taken";
            assert (Objects.equals(e.toString(), "socialmedia.IllegalHandleException: Handle already taken, please try again."));
        }

        // checking that exception is thrown if the handle is invalid
        try {
            platform.createAccount("user 2", "This is my Social Media");
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (InvalidHandleException e) {
            String expectedErrorMessage = "Handle already taken";
            assert (Objects.equals(e.toString(), "socialmedia.InvalidHandleException: Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty."));
        }
    }

    /**
     * Tests the remove account method
     *
     * @throws IllegalHandleException if the handle is null or contains illegal characters
     * @throws InvalidHandleException if the handle is not unique
     */
    public static void removeAccountTest() throws IllegalHandleException, InvalidHandleException {
        platform.erasePlatform();

        platform.createAccount("Sherya");
        //checking exception is thrown if id invalid
        try {
            platform.removeAccount(2);
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (AccountIDNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.AccountIDNotRecognisedException: Sorry, the account ID is invalid."));
        }

        //checking exception is thrown if handle is invalid
        try {
            platform.removeAccount("John");
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (HandleNotRecognisedException e) {
            String expectedErrorMessage = "Handle already taken";
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, the account handle is invalid."));
        }
    }

    /**
     * Tests the updateAccountDescription() method in the SocialMediaPlatform class
     *  @throws IllegalHandleException       if the given handle is illegal
     *  @throws InvalidHandleException       if the given handle is invalid
     *  @throws HandleNotRecognisedException if the given handle is not recognised
     */
    private static void updateAccountDescriptionTest() throws IllegalHandleException, InvalidHandleException, HandleNotRecognisedException {
        platform.erasePlatform();

        //checks account description gets changed
        platform.createAccount("user1", "This is my Social Media");
        assert Objects.equals(SocialMedia.profiles.get(0).getDescription(), "This is my Social Media");
        platform.updateAccountDescription("user1", "test");
        assert Objects.equals(SocialMedia.profiles.get(0).getDescription(), "test");


        //checks exception is thrown if handle is invalid
        try {
            platform.updateAccountDescription("user", "test");
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (HandleNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not recognised."));
        }
    }

    /**
     *  Tests the showAccount() method in the SocialMediaPlatform class
     *      @throws HandleNotRecognisedException if the given handle is not found
     *      @throws IllegalHandleException if the given handle is illegal
     *      @throws InvalidHandleException if the given handle is invalid
     */
    private static void showAccountTest() throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        platform.erasePlatform();

        platform.createAccount("user1", "hi!");
        String expectedOutput = "ID: 1\nHandle: user1\nDescription: hi!\nPost count: 0\nEndorse count: 0";
        String actualOutput = platform.showAccount("user1");
        assert expectedOutput.equals(actualOutput) : "showAccount() did not return the expected output.";

        try {
            platform.showAccount("user2");
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (HandleNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not found."));
        }
    }

    /**
     * Tests the createPost() method in the SocialMediaPlatform class
     *      @throws IllegalHandleException       if the given handle is illegal
     *      @throws InvalidHandleException       if the given handle is invalid
     *      @throws InvalidPostException         if the given post is invalid
     *      @throws HandleNotRecognisedException if the given handle is not found
     */
    public static void createPostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        assert platform.getTotalOriginalPosts() == 1;
        assert Objects.equals(SocialMedia.posts.get(1).getMessage(), "first post!");

        //checks exception thrown if post invalid
        try {
            platform.createPost("alyssa", ""); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (InvalidPostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.InvalidPostException: Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters."));
        }

        //checks exception thrown if handle not found
        try {
            platform.createPost("", "post test"); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (HandleNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not found."));
        }

    }

    /**
     * Tests the endorsePost method of the SocialMediaPlatform class.
     *
     * @throws IllegalHandleException       if the account handle is invalid
     * @throws InvalidHandleException       if the account handle is null or empty
     * @throws InvalidPostException         if the post text is null or empty
     * @throws HandleNotRecognisedException if the account handle is not recognised
     * @throws NotActionablePostException   if the post is not actionable (i.e. deleted or already an endorsement post)
     * @throws PostIDNotRecognisedException if the post ID is not recognised
     */
    public static void endorsePostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        platform.endorsePost("alyssa", 1);

        assert platform.getTotalEndorsmentPosts() == 1;

        //checking exception is thrown if handle is invalid
        try {
            platform.endorsePost("a", 1);
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (HandleNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not found."));
        }

        //checking exception is thrown if post id is invalid
        try {
            platform.endorsePost("alyssa", 0);
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (NotActionablePostException e) {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not endorsable."));
        }

        //checking exception is thrown if post id is invalid
        try {
            platform.endorsePost("alyssa", 3); // post id 2 is an endorsement post
            throw new AssertionError("Test failed: expected exception was not thrown.");
        } catch (PostIDNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that id."));
        }
    }


    /**
     * Tests the commentPost() method by creating a post and adding a comment to it.
     *
     * @throws IllegalHandleException       if handle is null or empty
     * @throws InvalidHandleException       if handle already exists
     * @throws InvalidPostException         if the post is empty or has more than 100 characters
     * @throws HandleNotRecognisedException if the handle does not exist
     * @throws NotActionablePostException   if the post is an endorsement or a deleted post
     * @throws PostIDNotRecognisedException if the post ID does not exist
     */
    public static void commentPostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException,
            HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");

        platform.commentPost("alyssa", 1, "comment");

        assert Objects.equals(SocialMedia.posts.get(1).getComments().get(0).getMessage(), "comment");

        //checking exception is thrown if the post is invalid
        try {
            platform.commentPost("alyssa", 1, ""); // id does not exist
            throw new AssertionError("Test failed");
        } catch (InvalidPostException e) {
            assert (Objects.equals(e.toString(), "socialmedia.InvalidPostException: Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters."));
        }

        //checking exception is thrown if post ID does not exist
        try {
            platform.commentPost("alyssa", 3, "comment"); // id does not exist
            throw new AssertionError("Test failed");
        } catch (PostIDNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that id."));
        }

        //checking exception is thrown if the handle does not exist
        try {
            platform.commentPost("a", 1, "comment"); // id does not exist
            throw new AssertionError("Test failed");
        } catch (HandleNotRecognisedException e) {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not found."));
        }

        //checking exception is thrown if post is an endorsement or a deleted post
        try {
            platform.commentPost("alyssa", 0, "comment"); // id does not exist
            throw new AssertionError("Test failed");
        } catch (NotActionablePostException e) {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not able to be commented."));
        }
    }

    /**
     * Tests the deletePost method of the SocialMedia platform class.
     * @throws IllegalHandleException       If an illegal handle is used.
     * @throws InvalidHandleException       If an invalid handle is used.
     * @throws InvalidPostException         If an invalid post is used.
     * @throws HandleNotRecognisedException If the handle is not recognised.
     * @throws PostIDNotRecognisedException If the post ID is not recognised.
     */
    public static void deletePostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        platform.createPost("alyssa", "second post!");

        assert platform.getTotalOriginalPosts() == 2;

        platform.deletePost(1);

        assert platform.getTotalOriginalPosts() == 1;

        try {
            platform.deletePost(3); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            // Checks if the correct exception was thrown and the correct error message was displayed.
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that id to delete."));
        }
    }

    /**
     * Tests the showIndividualPost method of the SocialMedia platform class.
     * @throws IllegalHandleException       If an illegal handle is used.
     * @throws InvalidHandleException       If an invalid handle is used.
     * @throws InvalidPostException         If an invalid post is used.
     * @throws HandleNotRecognisedException If the handle is not recognised.
     * @throws PostIDNotRecognisedException If the post ID is not recognised.
     */
    public static void showIndividualPostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        try {
            platform.showIndividualPost(2); // id of a deleted post
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            // Checks if the correct exception was thrown and the correct error message was displayed.
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that ID to show."));
        }

        String expectedOutput = "ID: 1\n" +
                "Account: alyssa\n" +
                "No. of Endorsements: 0 | No. of Comments: 0\n" +
                "first post!";

        String realOutput = platform.showIndividualPost(1).toString();

        assert (Objects.equals(realOutput, expectedOutput));
    }


    /**
     * Tests the method showPostChildrenDetails() to ensure that it is functioning correctly.
     *
     * @throws IllegalHandleException       if the account handle provided is null or empty
     * @throws InvalidHandleException       if the account handle provided is invalid
     * @throws InvalidPostException         if the post content provided is null or empty
     * @throws HandleNotRecognisedException if the handle provided is not recognised
     * @throws NotActionablePostException   if the post is deleted or an endorsement post
     * @throws PostIDNotRecognisedException if the post ID provided is not recognised
     * @throws IOException                  if an error occurs while writing/reading to/from a file
     * @throws ClassNotFoundException       if a class is not found while loading an object from a file
     */
    public static void showPostChildrenDetailsTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException, IOException, ClassNotFoundException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        platform.commentPost("alyssa", 1, "first comment!");
        platform.commentPost("alyssa", 1, "second comment!");
        platform.commentPost("alyssa", 2, "first subcomment on first comment!");
        platform.endorsePost("alyssa", 1);

        //checking comment post is outputted as expected
        String expectedOutput = ("ID: 2\n" +
                "Account: alyssa\n" +
                "No. of Endorsements: 0 | No. of Comments: 1\n" +
                "first comment!\n" +
                "| \n" +
                "| > ID: 4\n" +
                "   Account: alyssa\n" +
                "   No. of Endorsements: 0 | No. of Comments: 0\n" +
                "   first subcomment on first comment!\n");

        String realOutput = platform.showPostChildrenDetails(2).toString();
        assert (Objects.equals(realOutput, expectedOutput));

        //checking full post thread outputs correctly
        expectedOutput = "ID: 1\n" +
                "Account: alyssa\n" +
                "No. of Endorsements: 1 | No. of Comments: 2\n" +
                "first post!\n" +
                "| \n" +
                "| > ID: 2\n" +
                "   Account: alyssa\n" +
                "   No. of Endorsements: 0 | No. of Comments: 1\n" +
                "   first comment!\n" +
                "   | \n" +
                "   | > ID: 4\n" +
                "      Account: alyssa\n" +
                "      No. of Endorsements: 0 | No. of Comments: 0\n" +
                "      first subcomment on first comment!\n" +
                "| \n" +
                "| > ID: 3\n" +
                "   Account: alyssa\n" +
                "   No. of Endorsements: 0 | No. of Comments: 0\n" +
                "   second comment!\n";

        realOutput = platform.showPostChildrenDetails(1).toString();
        assert (Objects.equals(realOutput, expectedOutput));

        platform.deletePost(2);

        // checking output after a post is deleted
        expectedOutput = "ID: 1\n" +
                "Account: alyssa\n" +
                "No. of Endorsements: 1 | No. of Comments: 1\n" +
                "first post!\n" +
                "| \n" +
                "| > ID: 3\n" +
                "   Account: alyssa\n" +
                "   No. of Endorsements: 0 | No. of Comments: 0\n" +
                "   second comment!\n";

        realOutput = platform.showPostChildrenDetails(1).toString();
        assert (Objects.equals(realOutput, expectedOutput));

        //checking whether an exception is thrown if a post that has been deleted is shown
        try {
            platform.showPostChildrenDetails(2); // id of a deleted post
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that ID to show."));
        }

        //checking whether an error is thrown if a non-actionable post is shown
        try {
            platform.showPostChildrenDetails(5); // id of endorsement post created
            throw new AssertionError("Test failed");
        }
        catch (NotActionablePostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not able to be shown."));
        }

        //checking whether an exception is thrown if the generic deleted post is shown
        try {
            platform.showPostChildrenDetails(0); // id of the generic deleted post
            throw new AssertionError("Test failed");
        }
        catch (NotActionablePostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not able to be shown."));
        }

        //checking whether an exception is thrown if a post with an invalid post id is shown
        try {
            platform.showPostChildrenDetails(6); // id of a post that doesn't exist
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that ID to show."));
        }


    }

    /**
     * Tests the number of profiles currently registered on the social media platform.
     *
     * @throws HandleNotRecognisedException if the specified handle is not recognised by the platform
     * @throws IllegalHandleException       if the specified handle is not valid
     * @throws InvalidHandleException       if the specified handle is invalid
     */
    public static void getNumberOfProfilesTest() throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        // Clears the platform to ensure a clean state for the test
        platform.erasePlatform();

        platform.createAccount("user1");
        assert platform.getNumberOfAccounts() == 1; //checks there is an account registered

        platform.createAccount("user2");
        assert platform.getNumberOfAccounts() == 2; //checks that there are two accounts on the platform

        platform.removeAccount("user1");
        assert platform.getNumberOfAccounts() == 1; // checks that there is now one account on the platform
    }

    /**
     * Returns the total number of original posts on the social media platform.
     *
     * @throws IllegalHandleException       if the specified handle is not valid
     * @throws InvalidHandleException       if the specified handle is invalid
     * @throws InvalidPostException         if the specified post is invalid
     * @throws HandleNotRecognisedException if the specified handle is not recognised by the platform
     * @throws NotActionablePostException   if the specified post is not actionable
     * @throws PostIDNotRecognisedException if the specified post ID is not recognised by the platform
     */
    public static void getTotalOriginalPostsTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("user1");
        platform.createPost("user1", "test");
        assert platform.getTotalOriginalPosts() == 1;  // checks that there is one original post on the platform

        platform.endorsePost("user1", 1);
        assert platform.getTotalOriginalPosts() == 1;  // checks that there is still only one original post on the platform as endorsements don't count as original posts


        platform.createPost("user1", "test 2");
        assert platform.getTotalOriginalPosts() == 2;  // Checks that there are now two original posts on the platform
    }

    /**
     * Test to check the method getTotalEndorsementPosts
     *      @throws IllegalHandleException       If the handle used for account creation is invalid.
     *      @throws InvalidHandleException       If the handle used for account creation already exists.
     *      @throws InvalidPostException         If the post being created is invalid.
     *      @throws HandleNotRecognisedException If the handle used is not recognised by the platform.
     *      @throws NotActionablePostException   If the post is not actionable.
     *      @throws PostIDNotRecognisedException If the post ID is not recognised by the platform.
     */
    public static void getTotalEndorsementPostsTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("user1");
        platform.createPost("user1", "test");

        platform.endorsePost("user1", 1);

        assert platform.getTotalEndorsmentPosts() == 1;

        platform.commentPost("user1", 1, "test comment 1");

        assert platform.getTotalEndorsmentPosts() == 1;

        platform.endorsePost("user1", 1);

        assert platform.getTotalEndorsmentPosts() == 2;
    }

    /**
     * Test to check the method getTotalCommentPosts.
     *
     *      @throws IllegalHandleException       If the handle used for account creation is invalid.
     *      @throws InvalidHandleException       If the handle used for account creation already exists.
     *      @throws InvalidPostException         If the post being created is invalid.
     *      @throws HandleNotRecognisedException If the handle used is not recognised by the platform.
     *      @throws NotActionablePostException   If the post is not actionable.
     *      @throws PostIDNotRecognisedException If the post ID is not recognised by the platform.
     */
    public static void getTotalCommentPostsTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("user1");
        platform.createPost("user1", "test");

        platform.commentPost("user1", 1, "test comment 1");

        assert platform.getTotalCommentPosts() == 1;

        platform.commentPost("user1", 1, "test comment 2");

        assert platform.getTotalCommentPosts() == 2;

    }


    /**
     * Tests the getMostEndorsedAccount() and getMostEndorsedPost() methods.
     *
     * @throws IllegalHandleException       If the account handle is illegal.
     * @throws InvalidHandleException       If the account handle is already taken.
     * @throws InvalidPostException         If the post is invalid.
     * @throws HandleNotRecognisedException If the handle is not recognised.
     * @throws NotActionablePostException   If the post is not actionable.
     * @throws PostIDNotRecognisedException If the post ID is not recognised.
     */
    public static void getMostEndorsedAccountAndPostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();
        platform.createAccount("user1");
        platform.createAccount("user2");
        platform.createPost("user1", "first post!");
        platform.createPost("user2", "second post!");
        platform.endorsePost("user1", 1);
        platform.endorsePost("user2", 1);
        platform.endorsePost("user1", 2);


        assert platform.getMostEndorsedAccount() == 1; // first account has an ID of 1
        assert platform.getMostEndorsedPost() == 1; // first (most commented) post has an ID of 1

    }
 /**
  * @throws IllegalHandleException       If the account handle is illegal.
  * @throws InvalidHandleException       If the account handle is already taken.
  * @throws InvalidPostException         If the post is invalid.
  * @throws HandleNotRecognisedException If the handle is not recognised.
  * @throws NotActionablePostException   If the post is not actionable.
  * @throws PostIDNotRecognisedException If the post ID is not recognised.
  * @throws IOException                  If an I/O exception occurred.
  * @throws ClassNotFoundException       If the class was not found during deserialization.
  */
    public static void saveEraseAndLoadPlatformTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException, IOException, ClassNotFoundException {
        platform.erasePlatform();

        platform.createAccount("user1");
        platform.createPost("user1", "first post!");
        platform.createAccount("user2");
        platform.createPost("user2", "second post!");
        platform.commentPost("user2", 1, "comment on first post!"); // post indexing starts at 1 because of deleted posts
        platform.endorsePost("user2", 1);


        //checking all values of the platform are correct
        assert SocialMedia.profiles.size() == 2;
        assert SocialMedia.posts.size() == 5; // 5 posts (2 original, 1 comment, 1 endorsement and the generic "deleted post")
        assert platform.getTotalCommentPosts() == 1;
        assert platform.getTotalEndorsmentPosts() == 1;
        assert platform.getTotalOriginalPosts() == 2; // not including deleted post

        platform.savePlatform("save.ser");

        platform.erasePlatform();

        //checks that values are reset
        assert SocialMedia.profiles.size() == 0;
        assert SocialMedia.posts.size() == 1; // deleted post
        assert platform.getTotalCommentPosts() == 0;
        assert platform.getTotalEndorsmentPosts() == 0;
        assert platform.getTotalOriginalPosts() == 0; // not including deleted post

        platform.loadPlatform("save.ser");

        //checks that values are the same after being loaded
        assert SocialMedia.profiles.size() == 2;
        assert SocialMedia.posts.size() == 5; // 5 posts (2 original, 1 comment, 1 endorsement and the generic "deleted post")
        assert platform.getTotalCommentPosts() == 1;
        assert platform.getTotalEndorsmentPosts() == 1;
        assert platform.getTotalOriginalPosts() == 2; // not including deleted post

    }


}
