package com.lstec.nqueens;

import org.assertj.core.api.AbstractBooleanAssert;
import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class BoardConditionTest {

    @Test
    public void empty_board_meets_conditions() throws Exception {
        // given
        // when
        // then
        assertConditionsMet(board()).isTrue();
    }

    @Test
    public void queens_on_the_same_row() throws Exception {
        // given
        OneRowSelectedBoard board = board();
        board.putQueen(0, 1);
        board.putQueen(1, 1);

        // when
        // then
        assertConditionsMet(board).isFalse();
    }

    @Test
    public void queens_on_the_same_diagonal() throws Exception {
        // given
        OneRowSelectedBoard board = board();
        board.putQueen(0, 0);
        board.putQueen(1, 1);

        // when
        // then
        assertConditionsMet(board).isFalse();
    }

    @Test
    public void on_diagonal_with_empty_slots() throws Exception {
        // given
        OneRowSelectedBoard board = board();
        board.putQueen(1, 0);
        board.putQueen(4, 3);

        // when
        // then
        assertConditionsMet(board).isFalse();
    }

    @Test
    public void queens_do_not_threaten_each_other() throws Exception {
        // given
        OneRowSelectedBoard board = board();
        board.putQueen(0, 0);
        board.putQueen(1, 2);

        // when
        // then
        assertConditionsMet(board).isTrue();
    }

    @Test
    public void no_threat_but_the_at_the_same_angle() throws Exception {
        // given
        OneRowSelectedBoard board = board();
        board.putQueen(0, 0);
        board.putQueen(1, 2);
        board.putQueen(2, 4);

        // when
        // then
        assertConditionsMet(board).isFalse();
    }

    @Test
    public void the_same_angle_with_empty_slot() throws Exception {
        // given
        OneRowSelectedBoard board = OneRowSelectedBoard.parse(
                        "OOOOXOOO" +
                        "XOOOOOOO" +
                        "OOOOOOOX" +
                        "OOOOOXOO" +
                        "OOXOOOOO" +
                        "OOOOOOXO" +
                        "OXOOOOOO" +
                        "OOOXOOOO");

        // when
        // then
        assertConditionsMet(board, 0, 4).isFalse();
    }

    private AbstractBooleanAssert<?> assertConditionsMet(OneRowSelectedBoard board) {
        return assertThat(board.conditionsMetSoFar()).describedAs(board.toString());
    }

    private AbstractBooleanAssert<?> assertConditionsMet(OneRowSelectedBoard board, int row, int column) {
        return assertThat(board.conditionsMetSoFar(row, column)).describedAs(board.toString());
    }

    private OneRowSelectedBoard board() {
        return new OneRowSelectedBoard(5);
    }
}