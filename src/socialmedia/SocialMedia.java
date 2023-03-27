package socialmedia;
import java.io.IOException;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.ObjectInputStream;

//TODO: make sure fits with java naming conventions
//TODO: write 30 tests
public class SocialMedia implements SocialMediaPlatform {

    public static ArrayList<Account> profiles = new ArrayList<>();
    public static ArrayList<Post> posts = new ArrayList<>();

    public final static Post deletedPost = new Post("", "The original content was removed from the system and is no longer available.");

    public SocialMedia()
    {
        posts.add(deletedPost);
        deletedPost.setActionable(false);
    }
    @Override
    public int createAccount(String handle) throws IllegalHandleException, InvalidHandleException {

        for (Account profile : profiles)
        {
            if (profile.getHandle().equals(handle)){
                throw new IllegalHandleException("Handle already taken, please try again.");
            }
        }
        if (handle.length() > 30 || handle.contains(" ") || handle == null)
        {
            throw new InvalidHandleException("Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty.");
        }

        Account account = new Account(handle);
        profiles.add(account);
        return account.getId();
    }

    @Override
    public int createAccount(String handle, String description) throws IllegalHandleException, InvalidHandleException {

        for (Account profile : profiles) {
            if (profile.getHandle().equals(handle)) {
                throw new IllegalHandleException("Handle already taken, please try again.");
            }
        }
        if (handle.length() > 30 || handle.contains(" ") || handle == null) {
            throw new InvalidHandleException("Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty.");
        }
        Account account = new Account(handle, description);
        profiles.add(account);
        return account.getId();
    }
    @Override
    public void removeAccount(int id) throws AccountIDNotRecognisedException {
       boolean found = false;
       Account accountToDelete = null;
        for (Account profile : profiles) {
            if (profile.getId() == id) {
                found = true;
                accountToDelete = profile;
            }
        }
        if (!found)
        {
            throw new AccountIDNotRecognisedException("Sorry, the account ID is invalid.");
        }
        else {
            profiles.remove(accountToDelete);
            for (Post post : posts)
            {
                if (Objects.equals(post.getHandle(), accountToDelete.getHandle()))
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
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), handle)) {
                found = true;
                accountToDelete = profile;
            }
        }

        if (!found)
        {
            throw new HandleNotRecognisedException("Sorry, the account handle is invalid.");
        }
        else {
            profiles.remove(accountToDelete);
            for (Post post : posts)
            {
                if (Objects.equals(post.getHandle(), accountToDelete.getHandle()))
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
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), oldHandle)) {
                found = true;
                accountToChange = profile;
            }
            if (Objects.equals(profile.getHandle(), newHandle)) {
                throw new IllegalHandleException("Sorry, that handle is already in use.");
            }
        }

        if (!found)
        {
            throw new HandleNotRecognisedException("Sorry, that is not a recognised handle.");
        }
        else if (newHandle.length() > 30 || newHandle.contains(" ") || newHandle == null) {
            throw new InvalidHandleException("Handle invalid. Please make sure it has less than 30 characters, no white spaces and is not empty.");
        }
        else {
            accountToChange.setHandle(newHandle);
        }


    }

    @Override
    public void updateAccountDescription(String handle, String description) throws HandleNotRecognisedException {
        boolean found = false;
        Account accountToChange = null;
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), handle)) {
                found = true;
                accountToChange = profile;
            }
        }
        if (!found)
        {
            throw new HandleNotRecognisedException("Sorry, that handle is not recognised.");
        }
        accountToChange.setDescription(description);

    }

    @Override
    public String showAccount(String handle) throws HandleNotRecognisedException {

        boolean found = false;
        Account account = null;
            for (Account profile : profiles) {
                if (Objects.equals(profile.getHandle(), (handle))) {
                    found = true;
                    account = profile;
                }

            }

        if (!found) {
            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }

        return "ID: " + account.getId() + "\nHandle: " + account.getHandle() + " \nDescription: " + account.getDescription() + "\nPost count: " + (account.getPosts()).size() + "\nEndorse count: " + account.getNumberOfEndorsements();

    }
@Override
    public int createPost(String handle, String message) throws HandleNotRecognisedException, InvalidPostException {
        boolean found = false;
        Account account = null;

        if (Objects.equals(message, "") || message.length() > 100)
        {
            throw new InvalidPostException("Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters.");
        }

        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), (handle))) {
                found = true;
                account = profile;

            }
        }

        if (!found) {

            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }

        Post newPost = new Post(handle, message);
        posts.add(newPost);
        return account.getId();

    }

    @Override
    public int endorsePost(String handle, int id) throws HandleNotRecognisedException, PostIDNotRecognisedException, NotActionablePostException {
        Account account = null;
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), (handle))) {
                account = profile;
            }
        }
        if (account == null) {

            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }

        Post postToEndorse = null;
        for (Post post : posts) {
            if (Objects.equals(post.getPostId(), id)) {
                postToEndorse = post;
            }
        }

        if(postToEndorse == null){
            throw new PostIDNotRecognisedException("There is no post with that id.");
        }

        if (!postToEndorse.getActionable()) {
            throw new NotActionablePostException("Endorsement posts or deleted posts are not endorsable.");
        }

        String message = "EP@" + handle + ": " + postToEndorse.getMessage();
        Endorsement newEndorsement = new Endorsement(handle, message, postToEndorse.getPostId());
        postToEndorse.addEndorsement(newEndorsement);

        posts.add(newEndorsement);
        return newEndorsement.getPostId();
    }

    @Override
    public int commentPost(String handle, int id, String message) throws HandleNotRecognisedException,
            PostIDNotRecognisedException, NotActionablePostException, InvalidPostException {
        if (Objects.equals(message, "") || message.length() > 100)
        {
            throw new InvalidPostException("Sorry, the post is invalid. Make sure it is not empty or does not have more than 100 characters.");
        }

        boolean found = false;
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), (handle))) {
                found = true;
                break;
            }
        }
        if (!found) {
            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }
        Post postToComment = null;
        boolean postFound = false;
        for (Post post : posts) {
            if (Objects.equals(post.getPostId(), id)) {
                if (!post.getActionable()) {
                    throw new NotActionablePostException("Endorsement posts or deleted posts are not able to be commented.");
                }
                postFound = true;
                postToComment = post;
            }
        }
        if(!postFound){
            throw new PostIDNotRecognisedException("There is no post with that id.");
        }

        Comment newComment = new Comment(handle, postToComment.getPostId(), message);
        postToComment.addComment(newComment);
        posts.add(newComment);
        return newComment.getPostId();
    }

    @Override
    public void deletePost(int id) throws PostIDNotRecognisedException {
        Post postToDelete = null;
        for (Post post : posts)
        {
            if (post.getPostId() == id)
            {
                postToDelete = post;
            }
        }
        if (postToDelete == null)
        {
            throw new PostIDNotRecognisedException("There is no post with that id to delete.");
        }
        else {
            for (Comment comment : postToDelete.comments)
            {
                comment.setOriginalPostId(deletedPost.getPostId());
            }
            for (Post endorsement : posts)
            {
                if (endorsement instanceof Endorsement)
                {
                    if (postToDelete.getPostId() == ((Endorsement) endorsement).getOriginalPostId())
                    {
                        posts.remove(endorsement);
                    }
                }
            }
            if (postToDelete instanceof Comment)
            {
                Post originalPost = ((Comment) postToDelete).getOriginalPost();
                originalPost.comments.remove(postToDelete);
            }
            posts.remove(postToDelete);
        }
    }

    @Override
    public String showIndividualPost(int id) throws PostIDNotRecognisedException {
        Post postToShow = null;
        for (Post post : posts)
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
        return "ID: " + postToShow.getPostId() + "\nAccount: " + postToShow.getHandle() + "\nNo. of Endorsements: " + postToShow.getNumberOfEndorsements() + " | No. of Comments: " + postToShow.getNumberOfComments() + "\n" + postToShow.getMessage();
    }

    public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
        StringBuilder stringToShow = new StringBuilder();
        Post postToShow = null;
        for (Post post : posts) {
            if (post.getPostId() == id) {
                if (!post.getActionable()) {
                    throw new NotActionablePostException("Endorsement posts or deleted posts are not able to be shown.");
                }
                postToShow = post;
                break;
            }
        }

        if (postToShow == null) {
            throw new PostIDNotRecognisedException("There is no post with that ID to show.");
        }
        stringToShow.append(showIndividualPost(postToShow.getPostId())).append("\n");

        List<Comment> childPosts = postToShow.getComments();
        if (!childPosts.isEmpty()) {
            for (Comment childPost : childPosts) {
                if (childPost != deletedPost)
                {
                    stringToShow.append("| \n");
                    stringToShow.append("| > ").append(showPostChildrenDetails(childPost.getPostId()).toString().trim().replace("\n", "\n   ")).append("\n");
                }
            }
        }

        return stringToShow;
    }


    @Override
    public int getNumberOfAccounts() {
       return profiles.size();
    }

    @Override
    public int getTotalOriginalPosts() {
        int total = 0;
        for (Post post : posts)
        {
            if (post instanceof Endorsement || post instanceof Comment)
            {
                total = total + 1;
            }
        }
        return posts.size() - 1 - total; //deleted post is extra
    }

    @Override
    public int getTotalEndorsmentPosts() {
        int total = 0;
        for (Post post : posts)
        {
            if (post instanceof Endorsement)
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
            if (post instanceof Comment)
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
            if (post.getNumberOfEndorsements() > mostEndorsements)
            {
                mostEndorsements = post.getNumberOfEndorsements();
                id = post.getPostId();
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
            if (profile.getNumberOfEndorsements() > numberOfEndorsements)
            {
                numberOfEndorsements = profile.getNumberOfEndorsements();
                id = profile.getId();
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
        posts.remove(deletedPost);
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(filename))) {
            profiles = (ArrayList<Account>) inputStream.readObject();
            posts = (ArrayList<Post>) inputStream.readObject();
        } catch (IOException e) {
            throw new IOException("Error reading file.", e);
        } catch (ClassNotFoundException e) {
            throw new ClassNotFoundException("Class not found when loading file.", e);
        }
    }

}
