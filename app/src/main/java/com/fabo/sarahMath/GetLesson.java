

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

    public Map<String, Integer> getLesson() { // String prompt, ActivityMainBinding mainBinding, SharedPreferences sharedPreferences) {

        /* mainBinding.textViewResult.setText("Lesson Configuration");
         * mainBinding.textViewHistory.setText(prompt);
         */
        Map<String, Integer> lesson = new HashMap<>();

        // sharedPreferences = this.getSharedPreferences("com.fabo.sarahMath", Context.MODE_PRIVATE);
        // String result = sharedPreferences.getString("result","");

        lesson.put("rows", 20);
        lesson.put("cols", 20);
        lesson.put("level", 1);
        lesson.put("random", 1);
        lesson.put("function", 0);
        lesson.put("problems", 50);

        return lesson;
    }
}