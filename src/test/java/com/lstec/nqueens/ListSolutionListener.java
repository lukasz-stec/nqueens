package com.lstec.nqueens;

import java.util.ArrayList;
import java.util.List;

public class ListSolutionListener implements SolutionListener {

    private List<OneRowSelectedBoard> solutions = new ArrayList<>();

    public List<OneRowSelectedBoard> solutions() {
        return solutions;
    }

    @Override
    public void solutionFound(OneRowSelectedBoard solution) {
        solutions.add(solution.copy());
    }
}
