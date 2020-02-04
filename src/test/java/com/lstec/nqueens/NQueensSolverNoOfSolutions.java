package com.lstec.nqueens;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.Collection;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;

@RunWith(Parameterized.class)
public class NQueensSolverNoOfSolutions {

    private final int n;

    public NQueensSolverNoOfSolutions(int n) {
        this.n = n;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return IntStream.range(7, 19).mapToObj(i -> new Object[] {i}).collect(toList());
    }

    @Test
    @Ignore
    public void evaluateSolutionsFor() {
        // given
        NQueensSolver solver = new NQueensSolver(n, SolutionListener.NOOP);

        // when
        // then
        long start = System.nanoTime();
        int results = solver.solve();
        long elapsed = System.nanoTime() - start;
        System.out.println("| " + n + " | " + results + " | " +
                Duration.ofNanos(elapsed).truncatedTo(ChronoUnit.MILLIS) + " |");
    }
}