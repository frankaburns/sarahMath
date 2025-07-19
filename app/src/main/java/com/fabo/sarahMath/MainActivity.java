package com.fabo.sarahMath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.fabo.sarahMath.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    int numWrong = 0;
    int problemStep = 0;
    int problemSteps = 5;
    String blank = " ";
    String functionStr;
    String number = null;
    String result = "";
    boolean makingNewProblemSet = false;
    boolean buttonEqualsControl = false;
    BasicMath problem = null;
    ProblemGenerator pg = null;
    ActivityMainBinding mainBinding;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

        pg = new ProblemGenerator(20);

        mainBinding.textViewResult.setText("0");

        sharedPreferences = this.getSharedPreferences("com.fabo.sarahMath", Context.MODE_PRIVATE);

        mainBinding.btn0.setOnClickListener(v -> {
            onNumberClicked("0");
        });
        mainBinding.btn1.setOnClickListener(v -> {
            onNumberClicked("1");
        });
        mainBinding.btn2.setOnClickListener(v -> {
            onNumberClicked("2");
        });
        mainBinding.btn3.setOnClickListener(v -> {
            onNumberClicked("3");
        });
        mainBinding.btn4.setOnClickListener(v -> {
            onNumberClicked("4");
        });
        mainBinding.btn5.setOnClickListener(v -> {
            onNumberClicked("5");
        });
        mainBinding.btn6.setOnClickListener(v -> {
            onNumberClicked("6");
        });
        mainBinding.btn7.setOnClickListener(v -> {
            onNumberClicked("7");
        });
        mainBinding.btn8.setOnClickListener(v -> {
            onNumberClicked("8");
        });
        mainBinding.btn9.setOnClickListener(v -> {
            onNumberClicked("9");
        });

        mainBinding.btnDel.setOnClickListener(v -> {

            if (number == null || number.length() == 1) {
                onButtonACClicked();
            } else {

                String lastChar;
                number = number.substring(0, number.length() - 1);
                mainBinding.textViewResult.setText(number);
            }

        });

        mainBinding.btnEquals.setOnClickListener(v -> {

            // mainBinding.textViewComment.setText(blank);
            mainBinding.textViewResult.setText(result);
            if (!makingNewProblemSet) {
                if (problem != null) {
                    if (problem.doTheMath(problem.getFunction()) != Integer.parseInt(number)) {
                        numWrong++;
                        String msg = "Please try again.";
                        mainBinding.textViewResult.setText(msg);
                    } else {
                        problem = pg.getProblem();
                    }
                    if (problem == null) {
                        makingNewProblemSet = true;
                    } else {
                        mainBinding.textViewHistory.setText(Integer.toString(problem.getNumerator()));
                        String denom = functionStr + problem.getDenominator();
                        mainBinding.textViewDenominator.setText(denom);
                    }
                }
            }
            if (makingNewProblemSet) {
                if (problemStep < problemSteps) {
                    String config = "Lesson Configuration";
                    String level = "(Enter Level(0: 1-diget,1: 2-diget, 2: 3-diget):";
                    String random = "Enter Random(1-yes, 0-ordered):";
                    String function = "Enter Function(0-add, 1-subtract, 2-multiply, 3-divide):";
                    String num = "Enter Number of problems";
                    if (problemStep == 0) {
                        int val = pg.getNumProblems() - numWrong;
                        String score = "You scored: " + val + "/" + pg.getNumProblems();
                        mainBinding.textViewHistory.setText(config);
                        mainBinding.textViewDenominator.setText(blank);
                        mainBinding.textViewComment.setText(score);
                    } else if (problemStep == 1) {
                        mainBinding.textViewHistory.setText(config);
                        mainBinding.textViewDenominator.setText(blank);
                        mainBinding.textViewComment.setText(level);

                    } else if (problemStep == 2) {
                        mainBinding.textViewHistory.setText(config);
                        mainBinding.textViewDenominator.setText(blank);
                        mainBinding.textViewComment.setText(random);

                    } else if (problemStep == 3) {
                        pg.setRandomProblem(Objects.equals(number, "1"));
                        mainBinding.textViewHistory.setText(config);
                        mainBinding.textViewDenominator.setText(blank);
                        mainBinding.textViewComment.setText(function);

                    } else if (problemStep == 4) {
                        mainBinding.textViewHistory.setText(config);
                        mainBinding.textViewDenominator.setText(blank);
                        mainBinding.textViewComment.setText(num);

                        if(number == "0") {
                            pg.setLessonFunction(BasicMath.ADD);
                            functionStr = "+ ";
                        } else if(number == "1") {
                            pg.setLessonFunction(BasicMath.SUB);
                            functionStr = "- ";
                        } else if(number == "2") {
                            pg.setLessonFunction(BasicMath.MUL);
                            functionStr = "x ";
                        } else if(number == "3") {
                            pg.setLessonFunction(BasicMath.DIV);
                            functionStr = "/ ";
                        }
                    }

                    problemStep++;
                } else {
                    pg.setNumProblems(Integer.parseInt(number));
                    makingNewProblemSet = false;
                    problemStep = 0;
                    numWrong = 0;
                    pg.clearProblemSet();
                    pg.buildProblemSet();
                    problem = pg.getProblem();
                    mainBinding.textViewComment.setText(blank);
                    mainBinding.textViewHistory.setText(Integer.toString(problem.getNumerator()));
                    String denom = functionStr + problem.getDenominator();
                    mainBinding.textViewDenominator.setText(denom);
                }
            }
            buttonEqualsControl = true;
        });

        mainBinding.toolbar.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.settingsItem) {
                //intent
                Intent intent = new Intent(MainActivity.this, ChangeThemeActivity.class);
                startActivity(intent);
                return true;
            } else {
                return false;
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isDarMode = sharedPreferences.getBoolean("switch", false);
        if (isDarMode) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String resultTextToSave = mainBinding.textViewResult.getText().toString();
        String historyTextToSave = mainBinding.textViewHistory.getText().toString();
        String commentTextToSave = Objects.requireNonNull(mainBinding.textViewComment).getText().toString();
        String denominatorTextToSave = Objects.requireNonNull(mainBinding.textViewDenominator).getText().toString();
        String resultToSave = result;
        String numberToSave = number;
        boolean equalToSave = buttonEqualsControl;

        editor.putString("resultText", resultTextToSave);
        editor.putString("numerator", historyTextToSave);
        editor.putString("comment", commentTextToSave);
        editor.putString("denominator", denominatorTextToSave);

        editor.putString("result", resultToSave);
        editor.putString("number", numberToSave);
        editor.putBoolean("equal", equalToSave);
        editor.apply();

    }

    @Override
    protected void onStart() {
        super.onStart();

        mainBinding.textViewResult.setText(sharedPreferences.getString("resultText", "0"));

        result = sharedPreferences.getString("result", "");
        number = sharedPreferences.getString("number", null);
        buttonEqualsControl = sharedPreferences.getBoolean("equal", false);

        makingNewProblemSet = true;

    }

    public void onNumberClicked(String clickedNumber){
        if (number == null || buttonEqualsControl){
            number = clickedNumber;
        }else {
            number += clickedNumber;
        }
        mainBinding.textViewResult.setText(number);
        buttonEqualsControl = false;
    }

    public void onButtonACClicked(){

        number = null;
        mainBinding.textViewResult.setText("0");
        buttonEqualsControl = false;
        result = "";

    }

}






