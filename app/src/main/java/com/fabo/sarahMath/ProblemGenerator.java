package com.fabo.sarahMath;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public
class ProblemGenerator {

    public  static int     num;
    public  static int     res;
    public  static int     rows;
    public  static int     cols;
    public  static int     numWrong;
    public  static int     problemCount;
    public  static int     problemsSolved;
    public  static int     numProblems;
    public  static int     lessonFunction; // 0-ADD, 1-SUB, 2-MUL, 3-DIV
    public  static boolean randomProblem;

    List<BasicMath> subSet = new ArrayList<>();
    List<BasicMath> problemSet = new ArrayList<>();

    /**
     * 
     * @param size
     */
    ProblemGenerator(int size) {
        rows           = size;
        cols           = size;
        numWrong       = 0;
        numProblems    = 10;
        problemCount   = 0;
        randomProblem  = false;
        problemsSolved = 0;
    }


    /**
     * 
     * @param n
     */
    public void setNumProblems ( int n) {
      numProblems = n;
    }

    /**
     * 
     * @param p
     */
    public void setRandomProblem ( boolean p) {
      randomProblem = p;
    }

    /**
     * 
     * @param f
     */
    public void setLessonFunction (int f) {
      lessonFunction = f;
    }

    /**
     * 
     * @return
     */
    public int getNumProblems () {
      return (numProblems);
    }

    /**
     * 
     * @return
     */
    public boolean getRandomProblem () {
      return (randomProblem);
    }

    /**
     * 
     * @return
     */
    public BasicMath getProblem () {

        if (problemsSolved < numProblems) {
            if ((lessonFunction == BasicMath.SUB) || (lessonFunction == BasicMath.DIV)) {
                return subSet.get(problemsSolved++);
            } else {
                return problemSet.get(problemsSolved++);
            }
        } else {
            problemsSolved = 0;
            return null;
        }
    }


    /**
     *
     * @return
     */
    public void clearProblemSet () {

        subSet.clear();
        problemSet.clear();
    }

    /**
     *
     * @return
     */
    public int getlessonFunction () {
        return (lessonFunction);
    }

    /**
     * Method to execute the mathmatical function described by the BasicMath
     * object. The basic math has a function, a numerator and a denominator.
     *
     * @param problem - a basic math object.
     * @throws Exception - exception (devide by zero maybe?)
     */
    public int doProblem(BasicMath problem, String answer) {

      int  wrong       = 0;
      Properties p     = System.getProperties();
      String separator = p.getProperty("line.separator");

      num = Integer.parseInt(answer);
      res = problem.doTheMath(problem.getFunction());

      if (res != num) {
        wrong++;
      }
      return wrong;
    }

    /**
     *
     */
    private void shuffleArray () {

      for( int prob = 0; prob < problemSet.size(); prob++ ){
        int newPosition         = (int)(Math.random()*100)%(rows*cols);
        BasicMath tmp           = problemSet.get(newPosition);
        problemSet.set(newPosition, problemSet.get(prob));
        problemSet.set(prob, tmp);
      }
    }

    /**
     *
     */
    public void buildProblemSet () {
        problemCount = rows*cols;
        for( int i = 0, iPlusOne = 1; i < rows; i++, iPlusOne++ ){
            for( int j = 0, jPlusOne = 1; j < cols; j++, jPlusOne++ ){
               int idx = i*(rows)+j;
               problemSet.add(new BasicMath(lessonFunction));
               problemSet.get(idx).setNumerator(iPlusOne);
               problemSet.get(idx).setDenominator(jPlusOne);
            }
        }

        if (randomProblem) {
            shuffleArray ();
        }

        if (lessonFunction == BasicMath.SUB) {
            int iterations = rows*cols;
            for (int i=0; i<iterations; i++) {
                if (problemSet.get(i).getNumerator() >= problemSet.get(i).getDenominator()) {
                    subSet.add(problemSet.get(i));
               }
            }
        }

        if (lessonFunction == BasicMath.DIV) {
            int iterations = rows*cols;
            for (int i=0; i<iterations; i++) {
                if ( (problemSet.get(i).getNumerator() >= problemSet.get(i).getDenominator()) &&
                    ((problemSet.get(i).getNumerator()%problemSet.get(i).getDenominator()) == 0) ) {
                    subSet.add(problemSet.get(i));
                }
            }
        }
    }
};
