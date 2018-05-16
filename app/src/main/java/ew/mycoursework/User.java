package ew.mycoursework;

public class User {
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    private String name;
    private String login;
    private String password;
    private String type;
    User(String login, String password, String name, String type){
        this.login = login;
        this.password = password;
        this.name = name;
        this.type = type;
    }
}
