package ew.mycoursework;

import java.util.ArrayList;

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

    public ArrayList<String> getResults() {
        return results;
    }

    public void setResults(ArrayList<String> results) {
        this.results = results;
    }

    private ArrayList<String> results;




    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    private String id;

    User(String login, String password, String name, String type, String id, ArrayList<String> results){
        this.login = login;
        this.password = password;
        this.name = name;
        this.type = type;
        this.id = id;
        this.results = results;
    }

    User(String[] data){
        this.login = data[0];
        this.password = data[1];
        this.name = data[2];
        this.type = data[3];
    }

    void addResult(String result) {
        results.add(result);
    }
}
