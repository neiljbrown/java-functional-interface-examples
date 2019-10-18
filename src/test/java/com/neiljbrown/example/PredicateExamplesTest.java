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
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Class containing examples of using Java's {@link Predicate} interface which represents an operation on a single
 * operand/argument that returns a boolean result.
 * <p>
 * The {@link Predicate} functional interface is  a specialisaton of the {@link java.util.function.Function}
 * interface that receives a single generified value and always  returns a boolean value.
 * <p>
 * Similarly to {@link java.util.function.Function} there are specialised versions of the Predicate functional
 * interface that support accepting primitive arguments, such as IntPredicate, LongPredicate and DoublePredicate.
 *
 * <h2>References</h2>
 */
public class PredicateExamplesTest {

  /**
   * Provides an example of the one of the most common usages of a {@link Predicate} - filtering a collection using
   * the Stream API.
   */
  @Test
  public void testFilterList()  {
    List<String> filteredRacers = Stream.of("Lando", "Daniel", "Lewis")
      // Use a Predicate implemented using a Lambda expression to filter the stream
      .filter(racer -> racer.startsWith("L"))
      .collect(Collectors.toList());

    assertThat(filteredRacers).containsOnly("Lando", "Lewis");
  }
}