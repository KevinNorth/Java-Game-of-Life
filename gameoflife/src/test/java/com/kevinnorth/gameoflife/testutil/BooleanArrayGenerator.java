package com.kevinnorth.gameoflife.testutil;

/**
 * Generates all possible boolean arrays of a specified length. This allows us to use parameterized
 * tests to test all possible configurations of cells' neighbors in some of our Game of Life logic
 * bits.
 */
public final class BooleanArrayGenerator {
  /**
   * Generates all possible boolean arrays of length <code>numBooleans</code>.
   *
   * @param numBooleans The length of each boolean array to generate.
   * @return All possible boolean arrays of the specified length.
   */
  public static boolean[][] generateBooleanArrays(int numBooleans) {
    if (numBooleans < 0) {
      throw new IllegalArgumentException("Must generate a positive number of booleans");
    }

    if (numBooleans > 31) {
      // We could use a more robust implementation, but in our use case,
      // we're only going to use numBooleans <= 8
      throw new IllegalArgumentException("Can't generate boolean arrays longer than 31 bits");
    }

    int powerOfTwo = (int) Math.pow(2, numBooleans);
    boolean[][] allPermutationsOfBooleans = new boolean[powerOfTwo][numBooleans];

    // Borrowed from https://stackoverflow.com/a/27008250/473792
    for (int i = 0; i < powerOfTwo; i++) {
      String bin = Integer.toBinaryString(i);
      while (bin.length() < numBooleans) bin = "0" + bin;
      char[] chars = bin.toCharArray();
      for (int j = 0; j < chars.length; j++) {
        allPermutationsOfBooleans[i][j] = chars[j] == '0' ? true : false;
      }
    }

    return allPermutationsOfBooleans;
  }
}
