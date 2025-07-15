package com.fabo.sarahMath;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import com.fabo.sarahMath.databinding.ActivityMainBinding;

import org.mariuszgromada.math.mxparser.Expression;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    ActivityMainBinding mainBinding;
    String number = null;
    boolean dotControl = false;
    String result = "";
    boolean buttonEqualsControl = false;
    SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainBinding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(mainBinding.getRoot());

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

            if (number == null || number.length() == 1){
                onButtonACClicked();
            }else {

                String lastChar;
                number = number.substring(0,number.length()-1);
                mainBinding.textViewResult.setText(number);
            }

        });

        mainBinding.btnEquals.setOnClickListener(v -> {

            String expressionForCalculate = mainBinding.textViewResult.getText().toString();

            mainBinding.textViewResult.setText(result);

            buttonEqualsControl = true;
        });

        mainBinding.toolbar.setOnMenuItemClickListener(item -> {

            if (item.getItemId() == R.id.settingsItem){
                //intent
                Intent intent = new Intent(MainActivity.this, ChangeThemeActivity.class);
                startActivity(intent);
                return true;
            }else {
                return false;
            }

        });

    }

    @Override
    protected void onResume() {
        super.onResume();

        boolean isDarMode = sharedPreferences.getBoolean("switch",false);
        if (isDarMode){
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
        }else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
        }

    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences.Editor editor = sharedPreferences.edit();

        String resultTextToSave      = mainBinding.textViewResult.getText().toString();
        String historyTextToSave     = mainBinding.textViewHistory.getText().toString();
        String denominatorTextToSave = mainBinding.textViewDenominator.getText().toString();
        String resultToSave = result;
        String numberToSave = number;
        boolean equalToSave = buttonEqualsControl;

        editor.putString("resultText",resultTextToSave);
        editor.putString("numerator",historyTextToSave);
        editor.putString("denominator",denominatorTextToSave);

        editor.putString("result",resultToSave);
        editor.putString("number",numberToSave);
        editor.putBoolean("equal",equalToSave);
        editor.apply();

    }

    @Override
    protected void onStart() {
        super.onStart();


        Map<String, Integer> lessonHash = new HashMap<>();

        GetLesson gl = new GetLesson();
        lessonHash = gl.getLesson();  // make a hash

        ProblemGenerator pg = new ProblemGenerator(20, 20);

        if (lessonHash.get("function").intValue() == 0) {
            pg.setlessonFunction(BasicMath.ADD);
        } else if (lessonHash.get("function").intValue() == 1) {
            pg.setlessonFunction(BasicMath.SUB);
        } else if (lessonHash.get("function").intValue() == 2) {
            pg.setlessonFunction(BasicMath.MUL);
        } else if (lessonHash.get("function").intValue() == 3) {
            pg.setlessonFunction(BasicMath.DIV);
        }
        if (lessonHash.get("random").intValue() == 1) {
            pg.setrandomProblem(true);
        }

        pg.setnumProblems(lessonHash.get("problems").intValue());

        try {
            pg.problemSet();
        } catch (Exception ex) {
            System.out.println("Exception - " + ex.getMessage());
        }

        mainBinding.textViewResult.setText(sharedPreferences.getString("resultText","0"));
        mainBinding.textViewHistory.setText(sharedPreferences.getString("numerator","4"));
        mainBinding.textViewDenominator.setText(sharedPreferences.getString("denominator","+ 4"));

        result = sharedPreferences.getString("result","");
        number = sharedPreferences.getString("number",null);
        buttonEqualsControl = sharedPreferences.getBoolean("equal",false);

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
        mainBinding.textViewHistory.setText("");
        buttonEqualsControl = false;
        result = "";

    }

}






