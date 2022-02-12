package com.streese.jaceptions.backend

import com.streese.jaceptions.jlib.Functions

import scala.annotation.experimental
import scala.language.experimental.saferExceptions

@experimental
object Main extends App {

  // This is mandatory because of the `throws Exception` part below
  try
    println(divideXbyYScala(1.0, 0.0))
  catch
    case e: Exception => println("tried to divide by zero, didn't you?")

  // Nothing is mandatory here as we do not use `throws Exception`
  divideXbyYJavaLib(1.0, 0.0)

  // Putting the `throws Exception` part here is mandatory, the compiler will complain if it's not there
  def divideXbyYScala(x: Double, y: Double): Double throws Exception =
    if y == 0.0 then throw new Exception()
    x / y

  // Putting the `throws Exception` part here is allowed but not mandatory, this compiles either way. Additionally one
  // can put any subtype of `Exception` in the `throws <e>` part here and the code will still compile
  def divideXbyYJavaLib(x: Double, y: Double): Double =
    Functions.divideXbyY(x, y)

}
