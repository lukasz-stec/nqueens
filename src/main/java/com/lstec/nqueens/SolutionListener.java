package com.lstec.nqueens;

/**
 * Callback used to notify on new solution found by solver. Can be used to gather all solutions or print them.
 *
 */
public interface SolutionListener {

    SolutionListener NOOP = new NoOpSolutionListener();

    /**
     *
     * Invoked every time new solution is found. <code>solution</code> is only valid during this method invocation so
     * need to be copied if one wants to save it somewhere.
     */
    void solutionFound(OneRowSelectedBoard solution);

    class NoOpSolutionListener implements SolutionListener {

        @Override
        public void solutionFound(OneRowSelectedBoard solution) {

        }
    }
}
