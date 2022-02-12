package com.streese.jaceptions.jlib;

public class Functions {

  public static double divideXbyY(double x, double y) throws Exception {
    if (y == 0.0) {
      throw new Exception("division by zero is not allowed here");
    }
    return x / y;
  }

}
