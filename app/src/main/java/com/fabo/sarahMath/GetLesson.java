

package com.fabo.sarahMath;
import android.content.SharedPreferences;

import com.fabo.sarahMath.databinding.ActivityMainBinding;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class GetLesson {
    private Map<String, Integer> lesson;
    public GetLesson() {
        lesson = new HashMap<>();
    }

    public Map<String, Integer> getLesson(ActivityMainBinding mainBinding, SharedPreferences sharedPreferences) {

        mainBinding.textViewResult.setText("Lesson Configuration");
        mainBinding.textViewHistory.setText("Level(0: 1-diget,1: 2-diget, 2: 3-diget):");

        String result = sharedPreferences.getString("result","");
        lesson.put("level", Integer.parseInt(result));

        mainBinding.textViewHistory.setText("Random(1-yes, 0-ordered):");

        result = sharedPreferences.getString("result","");
        lesson.put("random",Integer.parseInt(result));

        mainBinding.textViewHistory.setText("Function(0-add, 1-subtract, 2-multiply, 3-divide):");

        result = sharedPreferences.getString("result","");
        lesson.put("function", Integer.parseInt(result));

        mainBinding.textViewHistory.setText("Number of rows:");

        result = sharedPreferences.getString("result","");
        lesson.put("rows", Integer.parseInt(result));

        mainBinding.textViewHistory.setText("Number of columns:");

        result = sharedPreferences.getString("result","");
        lesson.put("cols", Integer.parseInt(result));

        mainBinding.textViewHistory.setText("Number of problems:");

        result = sharedPreferences.getString("result","");
        lesson.put("problems", Integer.parseInt(result));

        return lesson;
    }
}