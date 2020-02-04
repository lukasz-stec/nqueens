package com.lstec.nqueens;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Collection;
import java.util.List;
import java.util.stream.IntStream;

import static java.util.stream.Collectors.toList;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(Parameterized.class)
public class NQueensSolverTest {

    private final int n;

    public NQueensSolverTest(int n) {
        this.n = n;
    }

    @Parameterized.Parameters(name = "{0}")
    public static Collection<Object[]> data() {
        return IntStream.range(7, 15).mapToObj(i -> new Object[] {i}).collect(toList());
    }

    @Test
    public void correctSolutionTo() throws Exception {
        // given
        ListSolutionListener listener = new ListSolutionListener();
        NQueensSolver solver = new NQueensSolver(n, listener);

        // when
        int results = solver.solve();
        System.out.println("n = " + n + ", results = " + results);

        // then
        List<OneRowSelectedBoard> solutions = listener.solutions();
        assertThat(results).isEqualTo(solutions.size());
        solutions.forEach(this::assertValid);
    }

    private void assertValid(OneRowSelectedBoard solution) {
        assertDistinctRows(solution);
        assertDistinctColumns(solution);
        assertDistinctDiagonals(solution);
        assertNo3OnTheSameLine(solution);
    }

    private void assertDistinctDiagonals(OneRowSelectedBoard solution) {
        int n = solution.n();
        // left
        for (int row = 0; row < n; row++) {
            assertOnlyOneOnRightUpDiagonal(solution, row, 0);
            assertOnlyOneOnRightDownDiagonal(solution, row, 0);
        }
        // top
        for (int column = 0; column < n; column++) {
            assertOnlyOneOnRightDownDiagonal(solution, 0, column);
        }
        // bottom
        for (int column = 0; column < n; column++) {
            assertOnlyOneOnRightUpDiagonal(solution, n - 1, column);
        }
    }

    private void assertOnlyOneOnRightUpDiagonal(OneRowSelectedBoard solution, int row, int column) {
        boolean found = false;
        int n = solution.n();
        while (row >= 0 && column < n) {
            if (solution.isSelected(column, row)) {
                assertThat(found).describedAs("row " + row + " col " + column).isFalse();
                found = true;
            }
            row--;
            column++;
        }
    }

    private void assertOnlyOneOnRightDownDiagonal(OneRowSelectedBoard solution, int row, int column) {
        boolean found = false;
        int n = solution.n();
        while (row < n && column < n) {
            if (solution.isSelected(column, row)) {
                assertThat(found).describedAs("row " + row + " col " + column).isFalse();
                found = true;
            }
            row++;
            column++;
        }
    }

    private void assertDistinctColumns(OneRowSelectedBoard solution) {
        boolean[] selectedColumns = new boolean[solution.n()];
        for (int row = 0; row < solution.n(); row++) {
            int selected = solution.columnSelected(row);
            assertThat(selectedColumns[selected]).describedAs("row " + row + " column " + selected).isFalse();
            selectedColumns[selected] = true;
        }
    }

    private void assertDistinctRows(OneRowSelectedBoard solution) {
        boolean[] selectedRows = new boolean[solution.n()];
        for (int column = 0; column < solution.n(); column++) {
            int selected = solution.rowSelected(column);
            assertThat(selectedRows[selected]).describedAs("column " + column + " row " + selected).isFalse();
            selectedRows[selected] = true;
        }
    }

    private void assertNo3OnTheSameLine(OneRowSelectedBoard solution) {
        // for every three queens
        for (int i = 0; i < solution.n(); i++) {
            int firstRow = solution.rowSelected(i);
            for (int j = i + 1; j < solution.n(); j++) {
                int firstTwoColumnDiff = j - i;
                int secondRow = solution.rowSelected(j);
                double firstTwoDiffPerRow = (double) (secondRow - firstRow) / firstTwoColumnDiff;
                for (int k = j + 1; k < solution.n(); k++) {
                    assertThat((double) solution.rowSelected(k))
                            .describedAs(solution.toString() + String.format("\n(%d, %d), (%d, %d), (%d, %d)",
                                    i, firstRow, j, secondRow, k, solution.rowSelected(k)))
                            .isNotEqualTo(firstRow + (k - i) * firstTwoDiffPerRow);
                }
            }
        }
    }
}