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

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import org.junit.jupiter.api.Test;

/**
 * Class containing examples of using Java's {@link Consumer} interfaces which represents an operation on one
 * operand that does _not_ produce a result.
 * <p>
 * The {@link Consumer} functional interface defines a type of function that accepts a single input (argument) but
 * returns no value. As such it is expected to result in a side effect(s). Similarly to
 * {@link java.util.function.Function} there are specialised versions of the Consumer functional interface that support
 * accepting primitive arguments, such as IntConsumer, LongConsumer and DoubleConsumer.
 * <p>
 * The {@link BiConsumer} functional interface defines an operation that accepts two input arguments, and also
 * returns no result. There are also specialised versions of BiConsumer that support accepting one primitive, and one
 * generified argument, such as ObjIntConsumer, ObjLongConsumer and ObjDoubleConsumer.
 *
 * <h2>References</h2>
 */
public class ConsumerExamplesTest {

  /**
   * Provides an example of using a {@link Consumer} to process a list of elements, using
   * {@link List#forEach(Consumer)}, in this case printing them to the console.
   */
  @Test
  public void testConsumeList()  {
    List<String> racers = List.of("Lando", "Daniel", "Lewis");
    racers.forEach(racer -> System.out.println("Hi " + racer + "."));
  }

  /**
   * Provides an example of using a {@link BiConsumer} to process the entries (key and value pairs) of a map,
   * using {@link Map#forEach(BiConsumer)}, in this case printing them to the console.
   */
  @Test
  public void testConsumeMap()  {
    Map<String, String> racerTeams = Map.of("Lando", "McLaren", "Daniel", "Renault", "Lewis", "Mercedes");
    racerTeams.forEach((racer, team) -> System.out.println(racer + " races for team " + team + "."));
  }
}