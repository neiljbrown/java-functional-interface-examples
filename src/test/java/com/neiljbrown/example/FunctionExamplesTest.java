package com.neiljbrown.example;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

/**
 * Class containing examples of using Java's simplest class of Java functional interface - {@link Function}.
 * <p>
 * One of the simplest classes of Java functional interface is one containing a method that receives a _single_ value
 * _and_ returns another. This function of a single argument is represented by the Java core library's
 * {@link java.util.function.Function} interface, which is parameterized by the types of its argument (T) and a
 * return value (R).
 * <p>
 * A {@link Function} actually combines the capabilities of the two simpler Java functional interfaces
 * {@link java.util.function.Consumer} and {@link java.util.function.Supplier}.
 *
 * <h2>References</h2>
 *
 * @see SupplierExamplesTest
 */
public class FunctionExamplesTest {

  /**
   * Provides a basic example of how a {@link Function} is declared and used a lambda expression.
   */
  @Test
  public void testFunction() {
    // Declaration of a Function that accepts an argument of type String and returns a value of type Integer
    Function<String, Integer> myFunction = s -> s.length();

    // An example usage of a Function type in the standard Java library is as an argument to the Map.computeIfAbsent
    // method, which returns a value from a map by key but calculates a value if a key is not already present in a map.
    Map<String, Integer> nameLengthMap = new HashMap<>();
    String name = "Joe";
    Integer value = nameLengthMap.computeIfAbsent(name, myFunction);
    assertThat(value).isEqualTo(name.length());

    // Method References can also be used as Function types to support code reuse. (The Function is dynamically
    // implemented using an (instance) method of the object that is provided as the first argument to the
    // function's SAM, in this case a String).
    myFunction = String::length;
    value = nameLengthMap.computeIfAbsent("Joe", myFunction);
    assertThat(value).isEqualTo(name.length());
  }

  /**
   * Provides an example of how multiple {@link Function} can be combined and applied (in sequence) as one, using
   * the {@link Function#compose(Function)} method.
   * <p>
   * {@link Function} provides a default implementation of the {@link Function#compose(Function)} method that allows
   * you  to combine several functions into one and execute them sequentially. The method returns a new 'composed'
   * Function that first applies a supplied ('before') function to its input (which itself could be a composed
   * Function), and then applies itself to the result.
   */
  @Test
  public void testCompose() {
    Function<Integer, String> intToString = Object::toString; // Integer::toString can't be used as overloaded ambiguity
    Function<String, String> quote = s -> "'" + s + "'";

    // Create a Function composed from 2 other functions - which applies supplied function first, then this function
    Function<Integer, String> quoteIntToString = quote.compose(intToString);
    assertEquals("'42'", quoteIntToString.apply(42));
  }
}