package com.neiljbrown.example;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDateTime;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Class containing examples of using Java's {@link Supplier} interface which represents an operation with zero
 * operands/args that returns a result.
 * <p>
 * The {@link Supplier} functional interface defines a type of Function that supplies (returns) a value without
 * requiring any arguments. It provides a level of indirection that is typically used to defer the (lazy)
 * generation or retrieval of a value until it is definitely needed, at the time it is needed.
 * <p>
 * Similarly to {@link java.util.function.Function} and {@link java.util.function.BiFunction} there are
 * specializations of Supplier functional interface to support primitive return types, such as  BooleanSupplier,
 * DoubleSupplier, LongSupplier and IntSupplier.
 *
 * <h2>References</h2>
 */
public class SupplierExamplesTest {

  /**
   * Provides an example of using a {@link Supplier} to defer the retrieval of the value that is used as a fallback
   * alternative value to an Optional (potentially null) value, as supported by the JDK's
   * {@link Optional#orElseGet(Supplier)} method.
   */
  @Test
  public void testDeferRetrievalOfOptionalValue()  {
    // Simulate a scenario in which an instance of some fictional reference data is supplied as a method argument of
    // type Optional because it might be null depending on whether it's been previously retrieved from the database
    Map<String,Object> settings = Math.random() > 0.5 ? null : Collections.emptyMap();
    Optional<Map<String,Object>> optionalSettings = Optional.ofNullable(settings);
    FakeSettingsDao settingsDao = new FakeSettingsDao();

    // The JDK's Optional class provides an orElseGet(Supplier) method for use when the value of the Optional is
    // not present (null), which allows the generation or retrieval of the alternative value to be deferred, e.g.
    settings = optionalSettings.orElseGet(
      // A Supplier implemented using a lambda expression; could be replaced with method ref settingsDao::querySettings
      () -> settingsDao.querySettings()
    );

    assertThat(settings).isNotNull();

    // Contrast the above to the non-lazy equivalent Optional<T>.orElse(T) method which doesn't use a Supplier but
    // instead requires the value immediately
    optionalSettings.orElse(settingsDao.querySettings());
  }

  class FakeSettingsDao {
    public Map<String,Object> querySettings() {
      return Collections.emptyMap();
    }
  }

  /**
   * Provides an example of how a {@link Supplier} function might be used to wrap logic to generate a value that is
   * computationally expensive to execute and therefore best deferred until needed.
   */
  @Test
  public void testExpensiveSupplier() {
    // Declare an implementation and instance of Supplier  using a lambda expression
    Supplier<Double> randomDoubleSupplier = () ->  Math.random(); // Could be shortened to method reference Math::random

    Double randomDouble1 = randomDoubleSupplier.get();
    Double randomDouble2 = randomDoubleSupplier.get();

    assertThat(randomDouble1).isNotEqualTo(randomDouble2);
  }

  /**
   * Provides an example of how a {@link Supplier} function might be used to wrap logic to defer the generation of a
   * value until it's required.
   *
   * @throws Exception if an unexpected error occurs on execution of this test.
   */
  @Test
  public void testLazySupplier() throws Exception {
    // Declare an implementation and instance of Supplier using a method reference
    Supplier<LocalDateTime> currentTimeSupplier = LocalDateTime::now;

    LocalDateTime now = LocalDateTime.now(); // Current time after declaring but before invoking Supplier
    LocalDateTime localDateTime = currentTimeSupplier.get();

    // Proof that Supplier returns value  on invocation rather than declaration
    assertThat(localDateTime).isAfter(now);
  }

  /**
   * Provides an example of using a {@link Supplier} as an argument to the Stream API's
   * {@link Stream#generate(Supplier)} method to provide the operation (encapsulate the logic) for generating a sequence
   * of values. In this example the Supplier is used to generate a Fibonacci sequence of numbers (each number is the sum
   * of the two preceding ones, starting from 0 and 1).
   * <p>
   * Note that to be useful as a generator, a Supplier implemented as a lambda expression usually needs some sort of
   * external state. In this case, its state is comprised of the two last generated numbers in the sequence.
   */
  @Test
  public void testFibonacciSequenceGenerator() {
    // External state used by Supplier. Last two numbers in generated sequence. Stored as an array rather than discrete
    // longs to satisfy constraint that all external variables used inside a lambda have to be effectively final
    long[] fibLastTwoValues = {0L, 1L};

    Stream<Long> fibonacci = Stream.generate(
      // Supplier function implemented as lambda expression which generates Fibonacci sequence - the implemented SAM
      // accepts no inputs but the function itself relies on external state
      () -> {
        //System.out.print("Previous two values "  + fibLastTwoValues[0] + ", " + fibLastTwoValues[1] + "; ");
        long result = fibLastTwoValues[1];
        long fib3 = fibLastTwoValues[0] + fibLastTwoValues[1];
        fibLastTwoValues[0] = fibLastTwoValues[1];
        fibLastTwoValues[1] = fib3;
        //System.out.print("Result "  + result + "; ");
        //System.out.println("Updated last two values "  + fibLastTwoValues[0] + ", " + fibLastTwoValues[1]);
        return result;
      }
    ).limit(50);
    List<Long> fibList = fibonacci.collect(Collectors.toList());

    assertThat(fibList).hasSize(50);
    assertThat(fibList.get(0)).isEqualTo(1);
    assertThat(fibList.get(49)).isEqualTo(12586269025L);
  }
}