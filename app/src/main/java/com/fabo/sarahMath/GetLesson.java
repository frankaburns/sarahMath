

package com.fabo.sarahMath;
import java.util.HashMap;
import java.util.Map;

public class GetLesson {
    private Map<String, Integer> lesson;
    public GetLesson() {
        lesson = new HashMap<>();
    }

    public Map<String, Integer> getLesson() {

        lesson.put("level", 1);
        lesson.put("random",1);
        lesson.put("function", 0);
        lesson.put("problems", 25);

        return lesson;
    }
}