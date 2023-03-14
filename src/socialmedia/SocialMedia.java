package socialmedia;
import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

//TODO: make sure fits with java naming conventions
public class SocialMedia implements SocialMediaPlatform {

    ArrayList<Account> profiles = new ArrayList<Account>();

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
        // TODO Auto-generated method stub

        boolean found = false;
        Account account = null;

            {
                for (Account profile : profiles) {
                    if (Objects.equals(profile.getHandle(), (handle))){
                        found = true;
                        account = profile;
                    }
                    if(found == false){
                        throw new HandleNotRecognisedException("Sorry, that handle is not found.");
                    }
            }

          // TODO:  do endorse and comment counts
                // int postCount = profiles.getPosts().size();
            int endorseCount = 0;
            for (Post post : profiles.getPosts()) {
                endorseCount += post.getEndorsements().size();
            }

            String template = "<pre>ID:" +
                              " %d\nHandle: %s\nDescription: %s\nPost count: %d\nEndorse count: %d\n</pre>";
            return String.format(template, account.getId(), account.getHandle(), account.getDescription(), postCount, endorseCount);
        }

    }

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
        account.addPost(newPost);
        return account.getId();

    }



}
