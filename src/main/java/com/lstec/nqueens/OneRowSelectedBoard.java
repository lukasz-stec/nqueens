package com.lstec.nqueens;

import java.util.Arrays;

public class OneRowSelectedBoard {

    private static final int EMPTY = Integer.MIN_VALUE;
    private final boolean[][] board;
    private final int[] selectedRow;

    public OneRowSelectedBoard(int n) {
        this.board = new boolean[n][n];
        this.selectedRow = new int[n];
        Arrays.fill(selectedRow, EMPTY);
    }

    private OneRowSelectedBoard(boolean[][] board) {
        this(board, calculateRows(board));
    }

    public int columnSelected(int rowNum) {
        boolean[] row = board[rowNum];
        int selected = 0;
        while (selected < row.length && !row[selected]) {
            selected++;
        }
        if (selected == row.length) {
            throw new IllegalArgumentException("row " + rowNum + " does not have column selected " +
                    Arrays.toString(row));
        }
        return selected;
    }

    public boolean isSelected(int column, int row) {
        return board[row][column];
    }

    private static int[] calculateRows(boolean[][] board) {
        int[] rows = new int[board.length];
        for (int i = 0; i < board.length; i++) {
            int row = 0;
            while (row <= board.length && !board[i][row]) {
                row++;
            }

            rows[i] = row < board.length ? row : EMPTY;
        }
        return rows;
    }

    private OneRowSelectedBoard(boolean[][] board, int[] selectedRow) {
        this.board = board;
        this.selectedRow = selectedRow;
    }

    public static OneRowSelectedBoard parse(String boardString) {
        int n = (int) Math.sqrt(boardString.length());
        boolean[][] board = new boolean[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                board[i][j] = boardString.charAt(i * n + j) == 'X';
            }
        }
        return new OneRowSelectedBoard(board);
    }

    public OneRowSelectedBoard copy() {
        return new OneRowSelectedBoard(copy(board), Arrays.copyOf(selectedRow, selectedRow.length));
    }

    public int n() {
        return board.length;
    }

    public int rowSelected(int column) {
        return selectedRow[column];
    }

    public OneRowSelectedBoard removeQueen(int column, int row) {
        board[row][column] = false;
        selectedRow[column] = EMPTY;
        return this;
    }

    public OneRowSelectedBoard putQueen(int column, int row) {
        board[row][column] = true;
        selectedRow[column] = row;
        return this;
    }

    public boolean conditionsMetSoFar(int lastRow, int lastColumn) {
        // checking if any queen on the same row
        for (int i = 0; i < lastColumn; i++) {
            if (selectedRow[i] == lastRow) {
                return false;
            }
        }

        // checking diagonal
        for (int diagonalColumn = lastColumn - 1, lowerDiagonalRow = lastRow - 1, upperDiagonalRow = lastRow + 1;
             diagonalColumn >= 0;
             diagonalColumn--, lowerDiagonalRow--, upperDiagonalRow++) {

            if (selectedRow[diagonalColumn] == lowerDiagonalRow || selectedRow[diagonalColumn] == upperDiagonalRow) {
                return false;
            }
        }

        // checking any angle collisions
        for (int angleColumn = lastColumn - 1; angleColumn > 0; angleColumn--) {
            if (!checkAngle(lastColumn, angleColumn)) {
                return false;
            }
        }
        return true;
    }

    public boolean conditionsMetSoFar() {
        int lastColumn = selectedRow.length - 1;
        while (lastColumn > 0 && isEmpty(lastColumn)) {
            lastColumn--;
        }
        return conditionsMetSoFar(selectedRow[lastColumn], lastColumn);
    }

    private boolean isEmpty(int column) {
        return selectedRow[column] == EMPTY;
    }

    private boolean checkAngle(int lastColumn, int angleColumn) {
        int angleSelectedRow = selectedRow[angleColumn];
        if (selectedRow[lastColumn] == EMPTY || angleSelectedRow == EMPTY) {
            // missing queen
            return true;
        }
        int rowDiff = selectedRow[lastColumn] - angleSelectedRow;
        int columnDiff = lastColumn - angleColumn;
        for (int column = angleColumn - 1, thirdColumnDiff = 1; column >= 0; column--, thirdColumnDiff++) {
            int thirdRowDiff = angleSelectedRow - selectedRow[column];
            // a/b = c/d <=> a*d = c * b
            if (rowDiff * thirdColumnDiff == columnDiff * thirdRowDiff) {
                return false;
            }
        }
        return true;
    }

    private static boolean[][] copy(boolean[][] org) {
        final boolean[][] res = new boolean[org.length][];
        for (int i = 0; i < org.length; i++) {
            res[i] = Arrays.copyOf(org[i], org[i].length);
        }
        return res;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        for (int i = 0; i < board.length; i++) {
            boolean[] row = board[i];
            for (int column = 0; column < row.length; column++) {
                sb.append(row[column] ? "X" : "O");
            }
            if (i < board.length - 1) {
                sb.append("\n");
            }
        }
        return sb.toString();
    }
}
