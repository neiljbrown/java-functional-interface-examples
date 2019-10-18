package com.neiljbrown.example;

import static org.assertj.core.api.Assertions.*;

import java.util.List;
import java.util.Map;
import java.util.function.DoubleToIntFunction;
import java.util.function.IntFunction;
import java.util.function.LongToIntFunction;
import java.util.function.ToIntFunction;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import org.junit.jupiter.api.Test;

/**
 * Class containing examples of using the specialised subset of Java built-in {@link FunctionalInterface} interfaces
 * that represent an operation hat accepts a single argument and returns a computed value, which can be applied to, or
 * return _primitive_ data-types (int, long, double).
 * <p>
 * These dedicated specialised Function interfaces need to exist (in addition to and separate from the generic
 * {@link java.util.function.Function} interface) because currently in Java (11) primitive types can't be used in
 * generic type arguments.
 * <p>
 * These out-of-the-box primitive Functions provided by Java are not exhaustive - they just cover the common cases.
 * Examples include -
 * <list>
 * <li>{@link java.util.function.IntFunction} - which defines a Function that _accepts_ a specific primitive type and
 * returns a generic type. Similar interfaces exist that accept other primitive types long, double.</li>
 * <li>{@link java.util.function.ToIntFunction} which defines a Function that accepts a generic type and _returns_ a
 * specific primitive type. Similar interfaces exist that return other primitive types long, double.</li>
 * <li>{@link java.util.function.LongToIntFunction} - which defines a Function that _both_ accepts and returns a
 * specific primitive type (there is no generic type). Similar interfaces exist for other commonly required
 * combinations of primitive functions.</li>
 * </list>
 *
 * <h2>References</h2>
 *
 * @see FunctionExamplesTest
 */
class PrimitiveFunctionSpecialisationExamplesTest {

  /**
   * Provides a basic example of how an {@link IntFunction} can be used to provide a function that operates on
   * (accepts) a primitive int, (and returns any generic type).
   */
  @Test
  public void testIntFunction() {
    IntFunction<Boolean> isCardinal = i -> i >= 0;

    // Invoke function directly in the absence of a good example use of IntFunction in the JDK/Java APIs
    assertThat(isCardinal.apply(-1)).isEqualTo(false);
    assertThat(isCardinal.apply(0)).isEqualTo(true);
    assertThat(isCardinal.apply(1)).isEqualTo(true);

    // Another example which uses the IntFunction as an argument to JDK's IntStream.mapToObject() method
    IntFunction<Long> convertIntToLong = i -> Long.valueOf(i);
    IntStream integers  = IntStream.of(1,2,3,4,5);
    List<Long> longs = integers.mapToObj(convertIntToLong).collect(Collectors.toList());
    assertThat(longs).containsExactly(1L, 2L, 3L, 4L, 5L);
  }

  /**
   * Provides a basic example of how a {@link ToIntFunction} can be used to provide a function that operates on
   * (accepts) any generic type and returns a primitive (int).
   */
  @Test
  public void testToIntFunction() {
    Map<String, Double> driverDistanceKm = Map.of("Daniel", 25.43, "Lewis", 32.16, "Lando", 23.92);

    // ToIntFunction implemented using lambda expression (could be replaced with a method reference) -  In this
    // contrived example the function converts (rounds down) a Double to an int
    ToIntFunction<Double> roundedDownDriverDistanceKm = distance -> distance.intValue();

    Integer totalRoundedDownDriverDistanceKm = driverDistanceKm.values().stream()
      // Example library (Stream API) method that uses a ToIntFunction to convert a stream of values to int
      .mapToInt(roundedDownDriverDistanceKm)
      .sum();

    assertThat(totalRoundedDownDriverDistanceKm).isEqualTo(80);
  }

  /**
   * Provides an example of how to write your own Function type for any given combination of primitive argument and
   * return type.
   * <p>
   * The set of primitive Functions provided out-of-the-box by Java are not exhaustive - they only cover the common
   * cases. There is no out-of-the-box functional interface for, say, a function that takes a byte and returns a short
   * but you can always write your own, as shown in this method.
   */
  @Test
  public void testCustomPrimitiveFunction() {
    // Declare an implementation of the custom ByteToShortFunction (see interface declared below) - this
    // implementation arbitrarily converts a byte by multiplying by 2
    ByteToShortFunction myFunction = b -> (short)(b * 2);

    byte[] bytes = {1, 2, 3, 4};
    // Use the function as a argument to a method that processes data
    short[] shorts = this.transformArray(bytes, myFunction);

    assertThat(shorts).containsExactly((short)2, (short)4, (short)6, (short)8);
  }

  /**
   * A custom {@link FunctionalInterface} that defines any function that accepts and converts a supplied byte (an 8-bit
   * signed two's complement integer) to a short (16-bit signed two's complement integer).
   */
  @FunctionalInterface
  public interface ByteToShortFunction {
    /**
     * A function that converts a byte (an 8-bit signed two's complement integer) to a short (16-bit signed two's
     * complement integer).
     *
     * @param b the byte to convert.
     * @return the generated short.
     */
    short apply(byte b);
  }

  /**
   * Method used to support demonstrating writing a custom primitive function. Also illustrates how Functional
   * Interfaces (implemented using lambda expressions) can be passed as arguments.
   *
   * @param bytes the array of byte to transform
   * @param toShortFunction an instance of {@link ByteToShortFunction} to use to convert each byte.
   * @return the resulting array of short.
   */
  private short[] transformArray(byte[] bytes, ByteToShortFunction toShortFunction) {
    short[] result = new short[bytes.length];
    for (int i = 0; i < bytes.length; i++) {
      result[i] = toShortFunction.apply(bytes[i]);
    }
    return result;
  }
}