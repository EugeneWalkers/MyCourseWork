package ew.mycoursework;

public class Question {
    private String text;

    public String getText() {
        return text;
    }

    public String[] getAnswers() {
        return answers;
    }

    public int getRightAnswer() {
        return rightAnswer;
    }

    private String[] answers;
    private int rightAnswer;
    Question(String text, String[] answers, int rightAnswer){
        this.text = text;
        this.answers = answers;
        this.rightAnswer = rightAnswer;
    }
}
