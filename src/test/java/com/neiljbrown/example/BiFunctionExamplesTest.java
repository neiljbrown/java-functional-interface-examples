package com.neiljbrown.example;

import static org.assertj.core.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

import org.junit.jupiter.api.Test;

/**
 * Class containing example usage of Java's {@link java.util.function.BiFunction} interface.
 * <p>
 * As described in {@link FunctionExamplesTest}, a {@link java.util.function.Function} type only accepts one argument
 * (and returns a value)]. To support functions with 2 arguments, Java defines a separate, additional interface with
 * the "Bi" keyword in its name - {@link BiFunction}. It is described as a "two-arity" specialization of
 * {@link java.util.function.Function}, where the term 'arity' means the no. of arguments or operands.
 *
 * <h2>References</h2>
 *
 * @see FunctionExamplesTest
 */
public class BiFunctionExamplesTest {

  /**
   * Provides an example of a typical use of a BiFunction interface in conjunction with standard Java API's
   * {@link Map#replaceAll} method, which supports replacing all values in a map with some computed value.
   * <p>
   * The following example declares an implementation of a BiFunction to process set of employee salaries. It
   * receives the map entry (key and current value)and uses it to calculate a new value for the salary and return it.
   */
  @Test
  public void testBiFunction() {
    Map<String, BigDecimal> salaries = new HashMap<>( // Create an immutable Map from the following immutable Map -
      Map.of(
      "Lewis", new BigDecimal("50000.00"),
      "Dan", new BigDecimal("45000.00"),
      "Lando", new BigDecimal("30000.00")
    ));

    // Use a lambda expression to declare an implementation of a BiFunction - which gives Lando a 10% raise :)
    BiFunction<String, BigDecimal, BigDecimal> salaryAdjuster  = (name, salary) ->
      name.equals("Lando") ?
        salary.add(salary.multiply(BigDecimal.valueOf(10)).divide(BigDecimal.valueOf(100), RoundingMode.HALF_EVEN)) :
        salary;

    // Use/apply the BiFunction, in this example using the JDK's Map.replaceAll(BiFunction) method -
    salaries.replaceAll(salaryAdjuster);

    assertThat(salaries).containsOnly(
      entry("Lewis",salaries.get("Lewis")),
      entry("Dan",salaries.get("Dan")),
      entry("Lando", new BigDecimal("33000.00"))
    );
  }
}