package Model;

public class User {
    private String Username, Password, Mobile_Number;

    public User(){

    }

    public User(String username, String password, String mobile_Number) {
        Username = username;
        Password = password;
        Mobile_Number = mobile_Number;
    }

    public String getUsername() {
        return Username;
    }

    public void setUsername(String username) {
        Username = username;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public String getMobile_Number() {
        return Mobile_Number;
    }

    public void setMobile_Number(String mobile_Number) {
        Mobile_Number = mobile_Number;
    }
}
