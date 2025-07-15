package com.fabo.sarahMath;
import java.util.Properties;

public
class ProblemGenerator {

    public  static int     num;
    public  static int     mag;
    public  static int     res;
    public  static int     rows;
    public  static int     cols;
    public  static int     numWrong;
    public  static int     problemCount;
    public  static int     problemsSolved;
    public  static int     numProblems;
    public  static int     lessonFunction; // 0-ADD, 1-SUB, 2-MUL, 3-DIV
    public static boolean randomProblem;


    /**
     * 
     * @param rows
     * @param cols
     */
    ProblemGenerator(int rows, int cols) {  
      numWrong       = 0;
      problemCount   = 0;
      problemsSolved = 0;
      numProblems    = 25;
    
      randomProblem = false;

      subSet        = new BasicMath[rows*cols];
      problemSet    = new BasicMath[rows*cols];

    }


    /**
     * 
     * @param n
     */
    public void setnumProblems ( int n) {
      numProblems = n;
    }

    /**
     * 
     * @param p
     */
    public void setrandomProblem ( boolean p) {
      randomProblem = p;
    }

    /**
     * 
     * @param f
     */
    public void setlessonFunction (int f) {
      lessonFunction = f;
    }

    /**
     * 
     * @return
     */
    public int getnumProblems () {
      return (numProblems);
    }

    /**
     * 
     * @return
     */
    public boolean getrandomProblem () {
      return (randomProblem);
    }

    /**
     * 
     * @return
     */
    public BasicMath getProblem () {
        if (problemsSolved < numProblems) {
            if ((lessonFunction == BasicMath.SUB) || (lessonFunction == BasicMath.DIV)) {
                return subSet[problemsSolved++];
            } else {
                return problemSet[problemsSolved];
            }
        } else {
            return (null);
        }
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
    public int doProblem(BasicMath problem, String answer) throws Exception {

      int  wrong       = 0;
      Properties p     = System.getProperties();
      String separator = p.getProperty("line.separator");

      num = Integer.parseInt(answer);
      mag =
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

      for( int prob = 0; prob < problemSet.length; prob++ ){
        int newPosition         = (int)(Math.random()*100)%(rows*cols);
        BasicMath tmp           = problemSet[newPosition];
        problemSet[newPosition] = problemSet[prob];
        problemSet[prob]        = tmp;
      }
    }

    /**
     * 
     * @param rows
     * @param cols
     */
    private void buildProblemSet (int rows, int cols) {
      problemCount = rows*cols;
      for( int i = 0, iPlusOne = 1; i < rows; i++, iPlusOne++ ){     
        for( int j = 0, jPlusOne = 1; j < cols; j++, jPlusOne++ ){
          int idx = i*(rows)+j;
          problemSet[idx] = new BasicMath(lessonFunction);
          problemSet[idx].setNumerator(iPlusOne);
          problemSet[idx].setDenominator(jPlusOne);
        }
      }

      if (randomProblem) {
        shuffleArray ();
      }

      if (lessonFunction == BasicMath.SUB) {
        int iterations = rows*cols;
        for (int i=0; i<iterations; i++) {
          if (problemSet[i].getNumerator() >= problemSet[i].getDenominator()) {
            subSet[problemCount] = new BasicMath(problemSet[i].getFunction());
            subSet[problemCount].setNumerator(problemSet[i].getNumerator());
            subSet[problemCount++].setDenominator(problemSet[i].getDenominator());
         }
        }     
      }

      if (lessonFunction == BasicMath.DIV) {
        int iterations = rows*cols;
        for (int i=0; i<iterations; i++) {
          if ( (problemSet[i].getNumerator() >= problemSet[i].getDenominator()) &&
               ((problemSet[i].getNumerator()%problemSet[i].getDenominator()) == 0) ) {
            subSet[problemCount] = new BasicMath(problemSet[i].getFunction());
            subSet[problemCount].setNumerator(problemSet[i].getNumerator());
            subSet[problemCount++].setDenominator(problemSet[i].getDenominator());
         }
        }     
      }
    }

    private static BasicMath[] subSet;
    private static BasicMath[] problemSet;

    static {
    }
};
