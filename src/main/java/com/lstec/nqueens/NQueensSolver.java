package com.lstec.nqueens;

public class NQueensSolver {

    private final int n;

    private final SolutionListener listener;
    private final OneRowSelectedBoard board;

    public NQueensSolver(int n, SolutionListener listener) {
        this.n = n;
        this.listener = listener;
        this.board = new OneRowSelectedBoard(n);
    }

    public int solve() {
        return findSolutionFromColumn(0);
    }

    private int findSolutionFromColumn(int column) {
        boolean isLastColumn = column == n - 1;
        int solutionCount = 0;
        for (int row = 0; row < n; row++) {
            board.putQueen(column, row);
            if (board.conditionsMetSoFar(row, column)) {
                if (isLastColumn) {
                    solutionCount++;
                    listener.solutionFound(board);
                } else {
                    solutionCount += findSolutionFromColumn(column + 1);
                }
            }
            board.removeQueen(column, row);
        }
        return solutionCount;
    }
}
