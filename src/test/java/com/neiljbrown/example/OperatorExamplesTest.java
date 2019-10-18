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

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BinaryOperator;
import java.util.function.ToIntFunction;
import java.util.function.UnaryOperator;

import org.junit.jupiter.api.Test;

/**
 * Operators are specialisations of a function that receive and return the same type/class of value. This class
 * contains examples of using Java's functional interfaces for operators including the
 * {@link java.util.function.UnaryOperator} and {@link java.util.function.BinaryOperator}.
 *
 * <h2>UnaryOperator</h2>
 * The {@link UnaryOperator} interface represents an operation on a _single_ operand that produces a result of the
 * same type. As such, it is a (sub-classing) specialisation of {@link java.util.function.Function}.
 * <p>
 * Similarly to {@link java.util.function.Function} there are specialised versions of the UnaryOperator functional
 * interface to support (accepting and returning) a primitive type, namely IntUnaryOperator, LongUnaryOperator,
 * DoubleUnaryOperator.
 *
 * <h2>BinaryOperator</h2>
 * The {@link BinaryOperator} interface represents an operation on two operands of the same type, producing a result
 * of the same type as the operands. As such, it is a (sub-classing) specialisation of
 * {@link java.util.function.BiFunction}.
 * <p>
 * Similarly to {@link java.util.function.BiFunction} there are specialised versions of the BinaryOperator functional
 * interface to support (accepting and returning) a primitive type of argument, namely IntBinaryOperator,
 * LongBinaryOperator, DoubleBinaryOperator.
 *
 * <h2>References</h2>
 */
public class OperatorExamplesTest {

  /**
   * Provides an example of using an implementation of an {@link UnaryOperator} to replace every value in a list with
   * a new value of the same type, by supplying it as an argument to the library method
   * {@link java.util.List#replaceAll(UnaryOperator)}.
   */
  @Test
  public void testUnaryOperatorCollectionReplaceAll()  {
    List<String> racers = new ArrayList<>( // This example only works with a mutable/modifiable list
      List.of("Norris", "Ricicardo", "Hamilton")
    );
    racers.replaceAll(
      surname -> surname.toUpperCase()  // UnaryOperator implemented using a Lambda expression
    );

    assertThat(racers).containsExactly("NORRIS", "RICICARDO", "HAMILTON");

    // Like other functional interfaces, a UnaryOperator can also be implemented using a Method Reference if its
    // implementation/body only invokes a method on the first param to the method  -
    racers.replaceAll(String::toLowerCase);
    assertThat(racers).containsExactly("norris", "ricicardo", "hamilton");
  }

  /**
   * Provides an example of using an implementation of an {@link BinaryOperator} to reduce a list of values, in this
   * case aggregate them by summing their values, by supplying the operator as an argument to the Stream API's
   * {@link java.util.stream.Stream#reduce(BinaryOperator)} method. (Note, in practice, this operation can be more
   * simply achieved using the Stream API's Collector {@link java.util.stream.Collectors#summingInt(ToIntFunction)}.
   */
  @Test
  public void testBinaryOperatorReduction() {
    List<Integer> values = List.of(1, 3, 5, 7, 9);
    int sum = values.stream().reduce(0,
      // BinaryOperator implemented using a lambda expression - Reduces two supplied values in this case by summing them
      (i1, i2) -> i1 + i2
    );

    assertThat(sum).isEqualTo(25);
  }
}