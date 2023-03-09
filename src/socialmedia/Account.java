package socialmedia;

public class Account {
    private static int latestId = 0;
    private int id;
    private String handle;
    private String description;

    public Account(String handle, String description) {
        this.id = ++latestId;
        this.handle = handle;
        this.description = description;
    }

    // getters and setters here
}
