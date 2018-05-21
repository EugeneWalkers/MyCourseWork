package ew.mycoursework;

import android.support.annotation.NonNull;

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

    Test (@NonNull String name,@NonNull String[] questions){
        this.name = name;
        this.questions = new ArrayList<>();
        for (String q:questions){
            String[] question = q.split(", ");
            this.questions.add(new Question(question));
        }

    }
}
