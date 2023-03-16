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

        return "ID: \nHandle:" + account.getHandle() + " \nDescription:" + account.getDescription() + "\nPost count: " + (account.getPosts()).size() + "\nEndorse count:" + account.getNumberOfEndorsements();

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
        boolean found = false;
        for (Account profile : profiles) {
            if (Objects.equals(profile.getHandle(), (handle))) {
                found = true;
                account = profile;
            }
        }
        if (!found) {

            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }

        Post postToEndorse = null;
        boolean postFound = false;
        for (Post post : account.getPosts()) {
            if (Objects.equals(post.getPostId(), id)) {
                if (post instanceof Endorsement) {
                    throw new NotActionablePostException("Endorsement posts are not endorsable.");
                }
                postFound = true;
                postToEndorse = post;
            }
        }
        if(!postFound){
            throw new PostIDNotRecognisedException("There is no post with that id.");
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
            }
        }
        if (!found) {
            throw new HandleNotRecognisedException("Sorry, that handle is not found.");
        }
        Post postToComment = null;
        boolean postFound = false;
        for (Post post : posts) {
            if (Objects.equals(post.getPostId(), id)) {
                if (post instanceof Endorsement) {
                    throw new NotActionablePostException("Endorsement posts are not able to be commented.");
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
        boolean found = false;
        for (Post post : posts)
        {
            if (post.getPostId() == id)
            {
                posts.remove(post);
                found = true;
            }
        }
        if (!found)
        {
            throw new PostIDNotRecognisedException("There is no post with that id to delete.");
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
        return "ID: " + postToShow.getPostId() + "\nAccount: " + postToShow.getHandle() + "\nNo. of Endorsements: " + postToShow.getNumberOfEndorsements() + "| No. of Comments: " + postToShow.getNumberOfComments() + "\n" + postToShow.getMessage();
    }

    public StringBuilder showPostChildrenDetails(int id) throws PostIDNotRecognisedException, NotActionablePostException {
        StringBuilder stringToShow = new StringBuilder();
        Post postToShow = null;
        for (Post post : posts) {
            if (post.getPostId() == id) {
                postToShow = post;
                break;
            }
        }
        if (postToShow == null) {
            throw new PostIDNotRecognisedException("There is no post with that ID to show.");
        }
        if (postToShow instanceof Endorsement) {
            throw new NotActionablePostException("Endorsement posts do not have children.");
        }
        stringToShow.append(showIndividualPost(postToShow.getPostId())).append("\n");

        List<Comment> childPosts = postToShow.getComments();
        if (!childPosts.isEmpty()) {
            for (Comment childPost : childPosts) {
                stringToShow.append("| \n");
                stringToShow.append("| > ").append(showPostChildrenDetails(childPost.getPostId()).toString().trim().replace("\n", "\n   ")).append("\n");
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
        return posts.size() - total;
    }

    @Override
    public int getTotalEndorsmentPosts() {
        int total = 0;
        for (Post post : posts)
        {
            total = total + post.getNumberOfEndorsements();
        }
        return total;
    }

    @Override
    public int getTotalCommentPosts() {
        int total = 0;
        for (Post post : posts)
        {
            total = total + post.getNumberOfComments();
        }
        return total;
    }

    @Override
    public int getMostEndorsedPost() {
        int id = 0;
        for (Post post : posts)
        {
            if (post.getPostId() > id)
            {
                id = post.getPostId();
            }
        }
        return id;
    }

    @Override
    public int getMostEndorsedAccount() {
        int id = 0;
        for (Account profile : profiles)
        {
            if (profile.getNumberOfEndorsements() > id)
            {
                id = profile.getNumberOfEndorsements();
            }
        }
        return id;
    }

    @Override
    public void erasePlatform() {
        profiles.clear();
        posts.clear();
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
