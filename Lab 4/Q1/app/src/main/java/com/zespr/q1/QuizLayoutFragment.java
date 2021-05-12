package com.zespr.q1;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;

public class QuizLayoutFragment extends Fragment {
    private final String Question;
    private final ArrayList<String> Option;
    private final int Answer;
    private final QuizFragment ctx;
    private final boolean isFirst,isLast;
    private final int idx;
    private ArrayList<Integer> a;

    public QuizLayoutFragment(QuizModel qm, boolean isFirst, boolean isLast,int position, QuizFragment ctx) {
        Question = qm.getQuestion();
        Option = qm.getOptions();
        Answer = qm.getAnswer();
        idx = position;
        this.isFirst = isFirst;
        this.isLast = isLast;
        this.ctx = ctx;

        a = new ArrayList<>(Arrays.asList(0,1,2,3));
        Collections.shuffle(a);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_quiz_layout, container, false);
        ((TextView) view.findViewById(R.id.textView)).setText(Question);
        RadioGroup rg = view.findViewById(R.id.radioGroup);
        ArrayList<String> shuffled = new ArrayList<>();
        for(String i: Option) shuffled.add(i);
        Collections.shuffle(shuffled);
        for(String option: shuffled) {
            RadioButton rb = new RadioButton(getContext());
            RadioGroup.LayoutParams par = new RadioGroup.LayoutParams(RadioGroup.LayoutParams.MATCH_PARENT, RadioGroup.LayoutParams.WRAP_CONTENT);
            par.setMargins(5,30,5,30);
            rb.setId(View.generateViewId());
            rb.setText(option);
            rb.setLayoutParams(par);
            rg.addView(rb);
        }
        Button submit = view.findViewById(R.id.button), goback = view.findViewById(R.id.button2);
        if(isFirst) goback.setEnabled(false);
        if(isLast) submit.setText("Submit");
        goback.setOnClickListener(v -> ctx.vp2.setCurrentItem(ctx.vp2.getCurrentItem()-1));
        submit.setOnClickListener(v -> {
            int id = rg.getCheckedRadioButtonId();
            if(id != -1) {
                String answer = ((RadioButton) view.findViewById(id)).getText().toString();
                ctx.setScore(idx,answer.equals(Option.get(Answer)));
                if(!isLast) ctx.vp2.setCurrentItem(ctx.vp2.getCurrentItem()+1);
                if(isLast) {
                    new AlertDialog.Builder(getContext())
                            .setTitle("Confirm")
                            .setMessage("Are you sure you want to submit?")
                            .setPositiveButton("Yes",(d,w) -> {
                                Bundle transfer = new Bundle();
                                transfer.putInt("score",ctx.getScore());
                                transfer.putInt("total",ctx.getTotal());
                                Navigation.findNavController(view).navigate(R.id.action_quizFragment_to_resultFragment,transfer);
                            })
                            .setNegativeButton("No",(d,w) -> {})
                            .create().show();
                }
            }
        });
        return view;
    }
}