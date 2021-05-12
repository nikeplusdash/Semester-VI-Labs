package com.zespr.q1;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.adapter.FragmentStateAdapter;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;

public class QuizFragment extends Fragment {
    public ViewPager2 vp2;
    private static ArrayList<QuizModel> qm;
    private int[] ans;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        qm = new ArrayList<>();
        qm.add(new QuizModel("Will I ever give you up?",new ArrayList<String>(Arrays.asList("Yes","No","Maybe","None of these")),1));
        qm.add(new QuizModel("Will I ever let you up?",new ArrayList<String>(Arrays.asList("Yes","No","Maybe","None of these")),1));
        qm.add(new QuizModel("Will I ever run around?",new ArrayList<String>(Arrays.asList("Yes","No","Maybe","None of these")),1));
        qm.add(new QuizModel("Will I ever hurt you?",new ArrayList<String>(Arrays.asList("Yes","No","Maybe","None of these")),1));
        qm.add(new QuizModel("Will I ever make you cry?",new ArrayList<String>(Arrays.asList("Yes","No","Maybe","None of these")),1));
        qm.add(new QuizModel("Will I ever say goodbye?",new ArrayList<String>(Arrays.asList("Yes","No","Maybe","None of these")),1));
        qm.add(new QuizModel("Will I ever tell a lie?",new ArrayList<String>(Arrays.asList("Yes","No","Maybe","None of these")),1));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_quiz, container, false);
        vp2 = v.findViewById(R.id.vp);
        vp2.setAdapter(new QuizAdapter(getFragmentManager(), getLifecycle(), this));
        vp2.setUserInputEnabled(false);
        ans = new int[qm.size()];
        return v;
    }

    public int getScore() {
        int sum = 0;
        for(int i:ans) sum+=i;
        return sum;
    }

    public int getTotal() {
        return ans.length;
    }

    public void setScore(int position, boolean isCorrect) {
        ans[position] = isCorrect?1:0;
    }

    public static class QuizAdapter extends FragmentStateAdapter {
        private final QuizFragment ctx;
        public QuizAdapter(@NonNull @NotNull FragmentManager fragmentManager, @NonNull @NotNull Lifecycle lifecycle, QuizFragment ct) {
            super(fragmentManager, lifecycle);
            ctx = ct;
        }

        @NotNull
        @Override
        public Fragment createFragment(int position) {
            return new QuizLayoutFragment(qm.get(position),position == 0,position == qm.size() - 1,position,ctx);
        }

        @Override
        public int getItemCount() {
            return qm.size();
        }
    }
}