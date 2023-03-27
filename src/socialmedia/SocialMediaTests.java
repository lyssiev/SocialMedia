package socialmedia;
import java.io.*;
import java.util.Objects;


//TODO: make sure when deleting account, all comments and endorsements also removed
//TODO: make sure that the outputting works of the tree of comments on a post
//TODO: check that when posts removed they are formatted correctly and error messages work correctly
public class SocialMediaTests {

    public static SocialMedia platform = new SocialMedia();
    public static void main(String[] args) throws NotActionablePostException, PostIDNotRecognisedException, InvalidPostException, HandleNotRecognisedException, InvalidHandleException, IOException, ClassNotFoundException, IllegalHandleException {
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


    public static void createPostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException {
        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        assert platform.getTotalOriginalPosts() == 1;
        assert Objects.equals(SocialMedia.posts.get(1).getMessage(), "first post!");

        try {
            platform.createPost("alyssa", ""); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (InvalidPostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.InvalidPostException: Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters."));
        }

        try {
            platform.createPost("", "post test"); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (HandleNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not found."));
        }

    }

    public static void endorsePostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        platform.endorsePost("alyssa", 1);


        assert platform.getTotalEndorsmentPosts() == 1;

        try {
            platform.endorsePost("a", 1); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (HandleNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not found."));
        }

        try {
            platform.endorsePost("alyssa", 0); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (NotActionablePostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not endorsable."));
        }

        try {
            platform.endorsePost("alyssa", 0); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (NotActionablePostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not endorsable."));
        }

        try {
            platform.endorsePost("alyssa", 3); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that id."));
        }

    }


    public static void commentPostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");

        platform.commentPost("alyssa", 1, "comment");

        assert Objects.equals(SocialMedia.posts.get(1).getComments().get(0).getMessage(), "comment");

        try {
            platform.commentPost("alyssa", 1, ""); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (InvalidPostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.InvalidPostException: Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters."));
        }

        try {
            platform.commentPost("alyssa", 3, "comment"); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that id."));
        }

        try {
            platform.commentPost("a", 1, "comment"); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (HandleNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.HandleNotRecognisedException: Sorry, that handle is not found."));
        }

        try {
            platform.commentPost("alyssa", 0, "comment"); // id does not exist
            throw new AssertionError("Test failed");
        }
        catch (NotActionablePostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not able to be commented."));
        }

    }
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
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that id to delete."));
        }

    }

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
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that ID to show."));
        }

        String expectedOutput = "ID: 1\n" +
                "Account: alyssa\n" +
                "No. of Endorsements: 0 | No. of Comments: 0\n" +
                "first post!";

        String realOutput = platform.showIndividualPost(1).toString();

        assert (Objects.equals(realOutput, expectedOutput));

    }

    public static void showPostChildrenDetailsTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException, IOException, ClassNotFoundException {
        platform.erasePlatform();

        platform.createAccount("alyssa");
        platform.createPost("alyssa", "first post!");
        platform.commentPost("alyssa", 1, "first comment!");
        platform.commentPost("alyssa", 1, "second comment!");
        platform.commentPost("alyssa", 2, "first subcomment on first comment!");
        platform.endorsePost("alyssa", 1);


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

        try {
            platform.showPostChildrenDetails(2); // id of a deleted post
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that ID to show."));
        }

        try {
            platform.showPostChildrenDetails(5); // id of endorsement post created
            throw new AssertionError("Test failed");
        }
        catch (NotActionablePostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not able to be shown."));
        }

        try {
            platform.showPostChildrenDetails(0); // id of the generic deleted post
            throw new AssertionError("Test failed");
        }
        catch (NotActionablePostException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.NotActionablePostException: Endorsement posts or deleted posts are not able to be shown."));
        }

        try {
            platform.showPostChildrenDetails(6); // id of a post that doesn't exist
            throw new AssertionError("Test failed");
        }
        catch (PostIDNotRecognisedException e)
        {
            assert (Objects.equals(e.toString(), "socialmedia.PostIDNotRecognisedException: There is no post with that ID to show."));
        }


    }

    public static void getNumberOfProfilesTest() throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {
        platform.erasePlatform();
        platform.createAccount("user1");

        assert platform.getNumberOfAccounts() == 1;

        platform.createAccount("user2");

        assert platform.getNumberOfAccounts() == 2;

        platform.removeAccount("user1");

        assert platform.getNumberOfAccounts() == 1;
    }
    public static void getTotalOriginalPostsTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("user1");
        platform.createPost("user1", "test");
        assert platform.getTotalOriginalPosts() == 1;

        platform.endorsePost("user1", 1);

        assert platform.getTotalOriginalPosts() == 1;

        platform.createPost("user1", "test 2");

        assert platform.getTotalOriginalPosts() == 2;
    }


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

    public static void getTotalCommentPostsTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
        platform.erasePlatform();

        platform.createAccount("user1");
        platform.createPost("user1", "test");

        platform.commentPost("user1", 1, "test comment 1");

        assert platform.getTotalCommentPosts() == 1;

        platform.commentPost("user1", 1, "test comment 2");

        assert platform.getTotalCommentPosts() == 2;

    }


    public static void getMostEndorsedAccountAndPostTest() throws IllegalHandleException, InvalidHandleException, InvalidPostException, HandleNotRecognisedException, NotActionablePostException, PostIDNotRecognisedException {
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

        assert platform.getMostEndorsedAccount() == 1; // first account has an ID of 1
        assert platform.getMostEndorsedPost() == 1; // first (most commented) post has an ID of 1

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
        assert platform.getTotalOriginalPosts() == 2; // not including deleted post

        platform.savePlatform("save.ser");

        platform.erasePlatform();

        assert SocialMedia.profiles.size() == 0;
        assert SocialMedia.posts.size() == 1; // deleted post
        assert platform.getTotalCommentPosts() == 0;
        assert platform.getTotalEndorsmentPosts() == 0;
        assert platform.getTotalOriginalPosts() == 0; // not including deleted post

        platform.loadPlatform("save.ser");

        assert SocialMedia.profiles.size() == 2;
        assert SocialMedia.posts.size() == 5; // 5 posts (2 original, 1 comment, 1 endorsement and the generic "deleted post")
        assert platform.getTotalCommentPosts() == 1;
        assert platform.getTotalEndorsmentPosts() == 1;
        assert platform.getTotalOriginalPosts() == 2; // not including deleted post

    }


}
