/* Copyright © 2024 Jose Chavez. All Rights Reserved. */
package com.rangerforce.concurrency;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.params.provider.Arguments;

public final class FibonacciNumbers {
    public static final List<Integer[]> fibonacciNumbers;

    static {
        fibonacciNumbers = new ArrayList<>();
        fibonacciNumbers.add(new Integer[] {0, 0});
        fibonacciNumbers.add(new Integer[] {1, 1});
        fibonacciNumbers.add(new Integer[] {2, 1});
        fibonacciNumbers.add(new Integer[] {3, 2});
        fibonacciNumbers.add(new Integer[] {4, 3});
        fibonacciNumbers.add(new Integer[] {5, 5});
        fibonacciNumbers.add(new Integer[] {6, 8});
        fibonacciNumbers.add(new Integer[] {7, 13});
        fibonacciNumbers.add(new Integer[] {8, 21});
        fibonacciNumbers.add(new Integer[] {9, 34});
        fibonacciNumbers.add(new Integer[] {10, 55});
        fibonacciNumbers.add(new Integer[] {11, 89});
        fibonacciNumbers.add(new Integer[] {12, 144});
        fibonacciNumbers.add(new Integer[] {13, 233});
        fibonacciNumbers.add(new Integer[] {14, 377});
        fibonacciNumbers.add(new Integer[] {15, 610});
        fibonacciNumbers.add(new Integer[] {16, 987});
        fibonacciNumbers.add(new Integer[] {17, 1597});
        fibonacciNumbers.add(new Integer[] {18, 2584});
        fibonacciNumbers.add(new Integer[] {19, 4181});
        fibonacciNumbers.add(new Integer[] {20, 6765});
        fibonacciNumbers.add(new Integer[] {21, 10946});
    }

    private FibonacciNumbers() {}

    private static Stream<Arguments> fibonacciNumbers() {
        return fibonacciNumbers.stream().map(Arguments::of);
    }
}
