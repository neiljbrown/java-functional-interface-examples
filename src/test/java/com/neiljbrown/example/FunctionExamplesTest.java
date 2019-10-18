/*
 *  Copyright 2019-present the original author or authors.
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package com.neiljbrown.example;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.junit.jupiter.api.Test;

/**
 * Class containing examples of using Java's simplest class of Java functional interface - {@link Function} which
 * represents an operation on a _single_ operand that produces a result.
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
 * @see ConsumerExamplesTest
 * @see SupplierExamplesTest
 */
public class FunctionExamplesTest {

  /**
   * Provides a basic example of how a {@link Function} is implemented using a lambda expression.
   */
  @Test
  public void testFunction() {
    // Declaration of a Function that accepts an argument of type String and returns a value of type Integer
    Function<String, Integer> myFunction = s -> s.length();

    // Example usage of a Function in the standard Java library is as an argument to the Map.computeIfAbsent method
    // which returns a value for a given key if it exists, else calculates a value, adds it to map and returns it.
    Map<String, Integer> nameLengthMap = new HashMap<>();
    String name = "Joe";
    Integer value = nameLengthMap.computeIfAbsent(name, myFunction);
    assertThat(value).isEqualTo(name.length());
    assertThat(nameLengthMap.get(name)).isEqualTo(name.length());

    // Method References can also be used to implement Function types to support code reuse. (The Function is
    // implemented using an (instance) method of the object that is provided as the first argument to the function's
    // SAM, in this case a String).
    myFunction = String::length;
    value = nameLengthMap.computeIfAbsent("Joe", myFunction);
    assertThat(value).isEqualTo(name.length());
  }

  /**
   * Provides an example of how multiple {@link Function} can be combined and applied (in sequence) as one, using
   * the {@link Function#compose(Function)} method.
   * <p>
   * {@link Function} provides a default implementation of the {@link Function#compose(Function)} method that allows
   * you to combine several functions into one and execute them sequentially. The method returns a new 'composed'
   * Function that first applies a supplied ('before') function to its input (which itself could be a composed
   * Function), and then applies itself to the result.
   */
  @Test
  public void testCompose() {
    Function<Integer, String> intToString = Object::toString; // Integer::toString can't be used as overloaded ambiguity
    Function<String, String> quote = s -> "'" + s + "'";

    // Create a Function composed from 2 other functions - which applies the supplied function first, then itself
    Function<Integer, String> quoteIntToString = quote.compose(intToString);
    assertEquals("'42'", quoteIntToString.apply(42));
  }
}