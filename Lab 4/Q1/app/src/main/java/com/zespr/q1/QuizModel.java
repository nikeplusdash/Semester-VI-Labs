package com.zespr.q1;

import java.util.ArrayList;
import java.util.Arrays;

public class QuizModel {
    private String Questions;
    private ArrayList<String> Options;
    private int Answers;

    public QuizModel(String questions, ArrayList<String> options, int answers) {
        Questions = questions;
        Options = options;
        Answers = answers;
    }

    public String getQuestion() {
        return Questions;
    }

    public ArrayList<String> getOptions() {
        return Options;
    }

    public int getAnswer() {
        return Answers;
    }
}
