package com.neiljbrown.example;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

/**
 * Class containing examples of using Java's {@link Predicate} interface.
 * <p>
 * The {@link Predicate} functional interface is  a specialisaton of the {@link java.util.function.Function} that
 * receives a single generified value and returns a boolean value.
 * <p>
 * Similarly to {@link java.util.function.Function} there are specialised versions of the Predicate functional
 * interface that support accepting primitive arguments, such as IntPredicate, LongPredicate and DoublePredicate.
 *
 * <h2>References</h2>
 */
public class PredicateExamplesTest {

  /**
   * Provides an example of the one of the most common usages of a {@link Predicate} - to filter a collection using
   * the Stream API.
   */
  @Test
  public void testFilterList()  {
    List<String> filteredRacers = Stream.of("Lando", "Daniel", "Lewis")
      .filter(racer -> racer.startsWith("L"))
      .collect(Collectors.toList());

    assertThat(filteredRacers).containsOnly("Lando", "Lewis");
  }
}