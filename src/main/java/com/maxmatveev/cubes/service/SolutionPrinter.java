package com.maxmatveev.cubes.service;

import com.maxmatveev.cubes.model.Puzzle;

/**
 * Created by Max Matveev on 12/06/15.
 */
public class SolutionPrinter {
    /**
     * Formats given permutation of the provided puzzle into a human-readable string
     *
     * @param puzzle
     * @param permutation
     * @param variants
     * @return
     */
    public String printSolution(Puzzle puzzle, int[] permutation, Puzzle.Variant[] variants) {
        StringBuilder result = new StringBuilder();
        // built top-part of the flattened cube T-shape
        for (int line = 0; line < 5; line++) {
            for (int piece = 0; piece < 3; piece++) {
                for (int pos = 0; pos < 5; pos++) {
                    if (puzzle.get(permutation[piece], line, pos, variants[piece]) == 1) {
                        result.append("[]");
                    } else {
                        result.append("  ");
                    }
                }
                result.append("  ");
            }
            result.append('\n');
        }
        result.append('\n');
        // build bottom part
        for (int piece = 3; piece < 6; piece++) {
            for (int line = 0; line < 5; line++) {
                result.append("            ");
                for (int pos = 0; pos < 5; pos++) {
                    if (puzzle.get(permutation[piece], line, pos, variants[piece]) == 1) {
                        result.append("[]");
                    } else {
                        result.append("  ");
                    }
                }
                result.append('\n');
            }
            result.append('\n');
        }
        return result.toString();
    }
}
