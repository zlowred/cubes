package com.maxmatveev.cubes.solver;

import com.maxmatveev.cubes.model.Puzzle;

/**
 * Created by Max Matveev on 12/06/15.
 */
public class Solver {
    /*
    This is "flat" representation of unfolded cube when drawn on piece of paper.
    Number in grid means number of solid pieces in that spot (e.g. there should be a
    T-shaped part containing of 1's with no zeroes inside (e.g. gaps)
    and no cells > 1 (e.g. we will not be able to fold two solid pieces in one spot in cube
     */
    private static final String solution =
            "1111111111111\n" +
                    "1111111111111\n" +
                    "1111111111111\n" +
                    "1111111111111\n" +
                    "1111111111111\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n" +
                    "0000011100000\n";

    /**
     * Verifies if puzzle is solvable in the following configuration:
     * [A] [B] [C]
     *     [D]
     *     [E]
     *     [F]
     * Where [A] size number in puzzle corresponds to the value of permutation[0], [B] corresponds to permutation[1] etc
     * <p/>
     * Example:
     * permutation = {2, 4, 0, 3, 1, 5}
     * Solution being verified =
     * [2] [4] [0]
     *     [3]
     *     [1]
     *     [5]
     * variants contains current configuration of pieces (e.g. rotated, flipped over ...)
     *
     * @param puzzle
     * @param permutation
     * @param variants
     * @return
     */
    public boolean verify(Puzzle puzzle, int[] permutation, Puzzle.Variant variants[]) {
        return tryVariants(puzzle, permutation, variants, 0);
    }

    /**
     * Here we will recursively iterate through all possible orientations for each piece
     * and verify if puzzle is solvable in given configuration
     *
     * @param puzzle
     * @param permutation
     * @param variants
     * @param position
     * @return
     */
    private boolean tryVariants(Puzzle puzzle, int[] permutation, Puzzle.Variant[] variants, int position) {
        if (position == 6) {
            // there are variants for each of 6 pieces; now check if puzzle is solvable in this configuration
            return verifyVariant(puzzle, permutation, variants);
        }
        // try all variants for given position and recursively call for the next position
        for (Puzzle.Variant v : Puzzle.Variant.values()) {
            variants[position] = v;
            if (tryVariants(puzzle, permutation, variants, position + 1)) {
                // stop on the first successful solution
                return true;
            }
        }
        // no solutions were found for any variants in given permutation
        return false;
    }

    /**
     * Verify single variant with given permutation and rotation/flip variants
     * <p/>
     * there is a translation of the edges of bottom pieces in set to the
     * upper part of the canvas which looks like below:
     *
     *                              +<<<<<<<<<<+
     *                              |+<<<<<<<<+^
     *                              ||+<<<<<<+^^
     *                              |||      ^^^
     *                     +---+---+---+     ^^^
     *                     |   |   |   |<<<+ ^^^
     *                     | 0 | 1 | 2 |<<+^ ^^^
     *                     |   |   |   |<+^^ ^^^
     *                     +---+---+---+ ^^^ ^^^
     *                         |   |+^^  ^^^ ^^^
     *                         | 3 |>+^  ^^^ ^^^
     *                         |   |>>+  ^^^ ^^^
     *                         +---+     ^^^ ^^^
     *                         |   |>>>>>+^^ ^^^
     *                         | 4 |>>>>>>+^ ^^^
     *                         |   |>>>>>>>+ ^^^
     *                         +---+         ^^^
     *                         |   |>>>>>>>>>+^^
     *                         | 5 |>>>>>>>>>>+^
     *                         |   |>>>>>>>>>>>+
     *                         +---+
     *
     * only right part of the translation is shown; left is symmetric
     *
     * This solution is a bit easier to understand; but performance-wise
     * I would be just XOR-ing numbers into array and then see that I have set/cleared
     * bits in expected places
     *
     * @param puzzle
     * @param permutation
     * @param variants
     * @return
     */
    private boolean verifyVariant(Puzzle puzzle, int[] permutation, Puzzle.Variant[] variants) {
        // initialize 'canvas' to verify solution with zeroes
        char buf[][] = new char[16][];
        for (int i = 0; i < 16; i++) {
            buf[i] = new char[13];
            for (int j = 0; j < 13; j++) {
                buf[i][j] = '0';
            }
        }
        // add top row of pieces with each two neighbouring pieces intersecting
        for (int ln = 0; ln < 5; ln++) {
            for (int x = 0; x < 15; x++) {
                int piece = x / 5;
                int pos = x % 5;
                int offset = (x - piece);
                buf[ln][offset] += puzzle.get(permutation[piece], ln, pos, variants[piece]);
            }
        }
        // add bottom part of the 'T' shape in flattened cube representation
        // we're only taking 3 middle pieces of each part because edges will be translated to the upper part
        // of the canvas
        for (int piece = 3; piece < 6; piece++) {
            for (int ln = 0; ln < 5; ln++) {
                for (int pos = 1; pos < 4; pos++) {
                    // also wrap bottom-most line so that it translates to the top-most one
                    int lnOffset = (4 + (piece - 3) * 4 + ln) % 16;
                    int offset = 4 + pos;
                    buf[lnOffset][offset] += puzzle.get(permutation[piece], ln, pos, variants[piece]);
                }
            }
        }
        // translate edges of the bottom pieces to the top part of the canvas
        for (int pos = 0; pos < 5; pos++) {
            buf[4][pos] += puzzle.get(permutation[3], 4 - pos, 0, variants[3]);
            buf[4][pos + 8] += puzzle.get(permutation[3], pos, 4, variants[3]);
            buf[pos][0] += puzzle.get(permutation[4], 4 - pos, 0, variants[4]);
            buf[pos][12] += puzzle.get(permutation[4], 4 - pos, 4, variants[4]);
            buf[0][pos] += puzzle.get(permutation[5], pos, 0, variants[5]);
            buf[0][12 - pos] += puzzle.get(permutation[5], pos, 4, variants[5]);
        }
        // convert into String representation. There are faster ways to check
        // but this one is easier to understand because it is a "visual" one
        StringBuilder candidate = new StringBuilder();
        for (char[] line : buf) {
            candidate.append(new String(line)).append('\n');
        }
        return solution.equals(candidate.toString());
    }
}
