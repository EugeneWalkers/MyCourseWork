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
    Question(String[] textToParce){
        this.text = textToParce[0];
        this.rightAnswer = Integer.parseInt(textToParce[textToParce.length - 1]);
        answers = new String[textToParce.length - 2];
        System.arraycopy(textToParce, 1, answers, 1, textToParce.length - 2);
    }
}
