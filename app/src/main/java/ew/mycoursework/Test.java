package ew.mycoursework;

import java.util.ArrayList;

public class Test {
    private ArrayList<Question> questions;
    private String name;

    public ArrayList<Question> getQuestions() {
        return questions;
    }

    public String getName() {
        return name;
    }

    Test(String name, ArrayList<Question> questions){
        this.name = name;
        this.questions = questions;
    }
}
