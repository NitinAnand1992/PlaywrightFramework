package testcases.api.us.pojo;

public class Credentials {
    private String username;
    private String password;

    // Constructor
    public Credentials(String username, String password) {
        this.username = username;
        this.password = password;
    }

    // Getters
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }
}
