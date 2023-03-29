package socialmedia;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

/**
 *  @author Student numbers: 720027904, 720050952
 *  @version 1.0
 *
 *  Implementation of the SocialMediaPlatform interface
 */
public class SocialMedia implements SocialMediaPlatform {

    public static ArrayList<Account> profiles = new ArrayList<>();
    public static ArrayList<Post> posts = new ArrayList<>();

    public final static Post deletedPost = new Post("", "The original content was removed from the system and is no longer available.");

    public SocialMedia()
    {
        posts.add(deletedPost); // adds the generic deleted post to the posts list when platform is instantiated
        deletedPost.setActionable(false); //makes sure deleted post cannot be endorsed or commented
    }
    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {

        for (Account profile : profiles)
        {
            if (profile.getHandle().equals(handle)){
                throw new IllegalHandleException("Handle already taken, please try again."); // checks if handle is taken
            }
        }
        if (handle.length() > 30 || handle.contains(" ") || handle == null) // checking if it is valid
        {
            throw new InvalidHandleException("Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty.");
        }

        Account account = new Account(handle);
        profiles.add(account); // adds to list
        return account.getId();
    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {

        for (Account profile : profiles) {
            if (profile.getHandle().equals(handle)) { //checks if handle is taken
                throw new IllegalHandleException("Handle already taken, please try again.");
            }
        }
        if (handle.length() > 30 || handle.contains(" ") || handle == null) { //checks if handle is valid
            throw new InvalidHandleException("Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty.");
        }
        Account account = new Account(handle, description);
        profiles.add(account); // adds to list
        return account.getId();
    }
    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
       boolean found = false;
       Account accountToDelete = null;
        for (Account profile : profiles) { // checks through profiles to find the account that matches the one passed in
            if (profile.getId() == id) {
                found = true;
                accountToDelete = profile; // stores profile object
            }
        }
        if (!found)
        {
            throw new AccountIDNotRecognisedException("Sorry, the account ID is invalid."); // could not find matching id
        }
        else {
            profiles.remove(accountToDelete);
            for (Post post : posts)
            {
                if (Objects.equals(post.getHandle(), accountToDelete.getHandle())) // removes all posts (including comments and endorsements) by the account
                {
                    posts.remove(post);
                }
            }
        }

    }

    @Override
    public void removeAccount(String handle) throws HandleNotRecognisedException {
        boolean found = false;
        Account accountToDelete = null;
        for (Account profile : profiles) { // checks to find account
            if (Objects.equals(profile.getHandle(), handle)) {
                found = true;
                accountToDelete = profile;
            }
        }

        if (!found) // if the handle is not in the system
        {
            throw new HandleNotRecognisedException("Sorry, the account handle is invalid.");
        }
        else {
            profiles.remove(accountToDelete);
            for (Post post : posts)
            {
                if (Objects.equals(post.getHandle(), accountToDelete.getHandle())) // removes all posts
                {
                    posts.remove(post);
                }
            }
        }

    }

    @Override
    public void changeAccountHandle(String oldHandle, String newHandle)
            throws HandleNotRecognisedException, IllegalHandleException, InvalidHandleException {

        boolean found = false;
        Account accountToChange = null;
        for (Account profile : profiles) { //checks all profiles to see if can find a matching account with the same handle
            if (Objects.equals(profile.getHandle(), oldHandle)) {
                found = true;
                accountToChange = profile;
            }
            if (Objects.equals(profile.getHandle(), newHandle)) {  // checks that handle is not in use
                throw new IllegalHandleException("Sorry, that handle is already in use.");
            }
        }

        if (!found) // checks whether the handle has been found
        {
            throw new HandleNotRecognisedException("Sorry, that is not a recognised handle.");
        }
        else if (newHandle.length() > 30 || newHandle.contains(" ") || newHandle == null) { // checks handle is valid
            throw new InvalidHandleException("Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty.");
        }
        else {
            accountToChange.setHandle(newHandle); //updates handle
        }


    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        boolean found = false;
        Account accountToChange = null;
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), handle)) { // finds account using handle
                found = true;
                accountToChange = profile;
            }
        }
        if (!found)
        {
            throw new HandleNotRecognisedException("Sorry, that handle is not recognised."); // checks if not recognised
        }
        accountToChange.setDescription(description); // updates description

    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {

        boolean found = false;
        Account account = null;
            for (Account profile : profiles) {
                if (Objects.equals(profile.getHandle(), (handle))) { // finds account using handle
                    found = true;
                    account = profile;
                }

            }

        if (!found) { // thrown if not account not found using the handle
            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }

        // string output
        return "ID: " + account.getId() + "\nHandle: " + account.getHandle() + "\nDescription: " + account.getDescription() + "\nPost count: " + (account.getPosts()).size() + "\nEndorse count: " + account.getNumberOfEndorsements();

    }


@Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        boolean found = false;
        Account account = null;

        if (Objects.equals(message, "") || message.length() > 100) //checks the message is valid
        {
            throw new InvalidPostException("Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters.");
        }

        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), (handle))) { // finds account using handle
                found = true;
                account = profile;

            }
        }

        if (!found) { // checks account is found

            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }

        Post newPost = new Post(handle, message);
        posts.add(newPost); // adds post to list
        return account.getId();

    }

    @Override
    public int endorsePost(String handle, int id) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        Account account = null;
        for (Account profile : profiles) { // finding account using handle
            if (Objects.equals(profile.getHandle(), (handle))) {
                account = profile;
            }
        }
        if (account == null) {

            throw new HandleNotRecognisedException("Sorry, that handle is not found."); // checks handle exists
        }

        Post postToEndorse = null;
        for (Post post : posts) {
            if (Objects.equals(post.getPostId(), id)) { // finding post using id
                postToEndorse = post;
            }
        }

        if(postToEndorse == null){
            throw new PostIDNotRecognisedException("There is no post with that id."); // checks post exists
        }

        if (!postToEndorse.getActionable()) {
            throw new NotActionablePostException("Endorsement posts or deleted posts are not endorsable."); // checks post to endorse is actionable
        }

        String message = "EP@" + handle + ": " + postToEndorse.getMessage();
        Endorsement newEndorsement = new Endorsement(handle, message, postToEndorse.getPostId()); // new object
        postToEndorse.addEndorsement(newEndorsement);

        posts.add(newEndorsement); // adds to posts list
        return newEndorsement.getPostId();
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        if (Objects.equals(message, "") || message.length() > 100) // checks message is valid
        {
            throw new InvalidPostException("Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters.");
        }

        boolean found = false;
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), (handle))) { // finding profile using handle
                found = true;
                break;
            }
        }
        if (!found) {
            throw new HandleNotRecognisedException("Sorry, that handle is not found."); //checks account exists
        }
        Post postToComment = null;
        boolean postFound = false;
        for (Post post : posts) {
            if (Objects.equals(post.getPostId(), id)) {
                if (!post.getActionable()) {
                    throw new NotActionablePostException("Endorsement posts or deleted posts are not able to be commented."); // checks not endorsement or deleted post
                }
                postFound = true;
                postToComment = post;
            }
        }
        if(!postFound){
            throw new PostIDNotRecognisedException("There is no post with that id."); // checks post to comment exists
        }

        Comment newComment = new Comment(handle, postToComment.getPostId(), message); // new object
        postToComment.addComment(newComment); // adds to post that the comment is on's list
        posts.add(newComment); // adds to all posts list
        return newComment.getPostId();
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        Post postToDelete = null;
        for (Post post : posts) // find post using id
        {
            if (post.getPostId() == id)
            {
                postToDelete = post;
            }
        }
        if (postToDelete == null)
        {
            throw new PostIDNotRecognisedException("There is no post with that id to delete."); // checks post exists
        }
        else {
            for (Comment comment : postToDelete.comments)
            {
                comment.setOriginalPostId(deletedPost.getPostId()); // sets all the comments of the post to delete to the "generic deleted post" object
            }
            for (Post endorsement : posts)
            {
                if (endorsement instanceof Endorsement)
                {
                    if (postToDelete.getPostId() == ((Endorsement) endorsement).getOriginalPostId()) // remove all endorsement posts of the deleted post from the post list
                    {
                        posts.remove(endorsement);
                    }
                }
            }
            if (postToDelete instanceof Comment)
            {
                Post originalPost = ((Comment) postToDelete).getOriginalPost(); // if the post to delete is a comment, delete the comment from the original post
                originalPost.comments.remove(postToDelete);
            }
            posts.remove(postToDelete); // remove from full list
        }
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        Post postToShow = null;
        for (Post post : posts) // checks post id is valid
        {
            if (post.getPostId() == id)
            {
                postToShow = post;
            }
        }
        if (postToShow == null)
        {
            throw new PostIDNotRecognisedException("There is no post with that ID to show.");
        }

        // string to return
        return "ID: " + postToShow.getPostId() + "\nAccount: " + postToShow.getHandle() + "\nNo. of Endorsements: " + postToShow.getNumberOfEndorsements() + " | No. of Comments: " + postToShow.getNumberOfComments() + "\n" + postToShow.getMessage();
    }

    public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
        StringBuilder stringToShow = new StringBuilder();
        Post postToShow = null;

        // Iterate through all posts to find the post with the given ID
        for (Post post : posts) {
            if (post.getPostId() == id) {
                // Check if the post is actionable (not an endorsement or deleted post)
                if (!post.getActionable()) {
                    throw new NotActionablePostException("Endorsement posts or deleted posts are not able to be shown.");
                }
                postToShow = post;
                break;
            }
        }

        // Throw an exception if the post with the given ID was not found
        if (postToShow == null) {
            throw new PostIDNotRecognisedException("There is no post with that ID to show.");
        }

        // Append the details of the parent post to the string builder
        stringToShow.append(showIndividualPost(postToShow.getPostId())).append("\n");

        // Get the child posts of the parent post
        List<Comment> childPosts = postToShow.getComments();

        // If there are child posts, iterate through them and append their details to the string builder
        if (!childPosts.isEmpty()) {
            for (Comment childPost : childPosts) {
                // Ignore deleted posts
                if (childPost != deletedPost)
                {
                    // Use recursion to get the details of the child post and append them to the string builder
                    stringToShow.append("| \n");
                    stringToShow.append("| > ").append(showPostChildrenDetails(childPost.getPostId()).toString().trim().replace("\n", "\n   ")).append("\n");
                }
            }
        }

        return stringToShow;
    }



    @Override
    public int getNumberOfAccounts() {
       return profiles.size(); // returns size of list
    }

    @Override
    public int getTotalOriginalPosts() {
        int total = 0;
        for (Post post : posts)
        {
            if (post instanceof Endorsement || post instanceof Comment) // checks how many endorsement and comment posts there are
            {
                total = total + 1;
            }
        }
        return posts.size() - 1 - total; //deleted post is 1 extra in list, take away from total size
    }

    @Override
    public int getTotalEndorsmentPosts() {
        int total = 0;
        for (Post post : posts)
        {
            if (post instanceof Endorsement) // checks if post is an endorsement object
            {
                total = total + 1;
            }
        }
        return total;
    }

    @Override
    public int getTotalCommentPosts() {
        int total = 0;
        for (Post post : posts)
        {
            if (post instanceof Comment) // checks if post is a comment object
            {
                total = total + 1;
            }
        }
        return total;
    }

    @Override
    public int getMostEndorsedPost() {
        int id = 0;
        int mostEndorsements = 0;
        for (Post post : posts)
        {
            if (post.getNumberOfEndorsements() > mostEndorsements) // replaces as most endorsements
            {
                mostEndorsements = post.getNumberOfEndorsements();
                id = post.getPostId(); // replaces most endorsed post
            }
        }
        return id;
    }

    @Override
    public int getMostEndorsedAccount() {
        int numberOfEndorsements = 0;
        int id = 0;
        for (Account profile : profiles)
        {
            if (profile.getNumberOfEndorsements() > numberOfEndorsements) // replaces as most endorsed
            {
                numberOfEndorsements = profile.getNumberOfEndorsements();
                id = profile.getId(); // replaces most endorsed account
            }
        }
        return id;
    }

    @Override
    public void erasePlatform() {

        Account account = new Account("reset");
        account.reset();
        account = null;

        Post post = new Post("reset", "reset"); //making a new post to use the method reset to reset the counter
        post.reset(); //makes sure the variable "nextPostId" stays private
        post = null; //removing the reference for JVM garbage collector

        profiles.clear();
        posts.clear();

        posts.add(deletedPost);
    }

    @Override
    public void savePlatform(String filename) throws IOException {
        // Create a file output stream and an object output stream to write to the file
        FileOutputStream fileOut = new FileOutputStream(filename);
        ObjectOutputStream objectOut = new ObjectOutputStream(fileOut);
        objectOut.writeObject(profiles);
        objectOut.writeObject(posts);
        objectOut.close();
        fileOut.close();

    }

    @Override
    public void loadPlatform(String filename) throws IOException, ClassNotFoundException {
        // remove the generic deleted post added when erasing the platform
        posts.remove(deletedPost);

        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            // Load the accounts and posts from the serialized file
            profiles = (ArrayList<Account>) inputStream.readObject();
            posts = (ArrayList<Post>) inputStream.readObject();
        } catch (IOException e) {
            // if there is an error reading the file
            throw new IOException("Error reading file.", e);
        } catch (ClassNotFoundException e) {
            // if the class of the serialized objects cannot be found
            throw new ClassNotFoundException("Class not found when loading file.", e);
        }
    }


}
