package socialmedia;
import java.util.ArrayList;
import java.util.Objects;

public class SocialMedia extends SocialMediaPlatform {

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

}
