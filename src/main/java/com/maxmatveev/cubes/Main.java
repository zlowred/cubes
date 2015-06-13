package com.maxmatveev.cubes;

import com.maxmatveev.cubes.model.Puzzle;
import com.maxmatveev.cubes.service.PuzzleLoader;
import com.maxmatveev.cubes.service.SolutionPrinter;
import com.maxmatveev.cubes.solver.Solver;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.List;
import java.util.stream.IntStream;

/**
 * Created by Max Matveev on 12/06/15.
 */
public class Main {
    private static String[] puzzles = {
            "blue.txt",
            "red.txt",
            "violet.txt",
            "olive.txt",
    };

    private static Solver solver;
    private static Puzzle puzzle;

    public static void main(String[] args) throws URISyntaxException, IOException {
        // this is not actually needed; I just want to give a different solution on each run
        List<Integer> initialOrder = Arrays.asList(0, 1, 2, 3, 4, 5);
        // so I shuffle ordered list
        Collections.shuffle(initialOrder);
        // and then convert it to int[6] order which will be used for the permutation generation
        int[] order = initialOrder.stream().mapToInt(Integer::byteValue).toArray();

        File puzzleFile;
        if (args.length > 0) {
            // if there is something on the command line we'll try to use it as a puzzle file name
            puzzleFile = new File(args[0]);
        } else {
            // otherwise we will pick a randome one from the pre-defined list
            URI puzzleUri = Main.class.getClassLoader().getResource(puzzles[((int) (Math.random() * puzzles.length))]).toURI();
            puzzleFile = new File(puzzleUri);
        }

        System.out.println("Using puzzle [" + puzzleFile.getName() + "]\n");

        // load puzzle
        // we'll ignore loading errors as there should be no loader at all as per problem description
        puzzle = new PuzzleLoader().load(puzzleFile);

        // initialize solver
        solver = new Solver();

        // now recursively generate permutations until we generate first valid solution
        // there are just 720 permutations possible so we can try them all in a short time
        int[] permutation = new int[6];
        Puzzle.Variant variants[] = new Puzzle.Variant[6];
        if (permutations(order, permutation, new BitSet(6), 0, variants)) {
            // print solution
            System.out.println(new SolutionPrinter().printSolution(puzzle, permutation, variants));
        } else {
            // no solution was found
            System.out.println("There is no valid solution for the given puzzle");
        }
    }

    private static boolean permutations(int[] order, int[] permutation, BitSet used, int position, Puzzle.Variant variants[]) {
        if (used.cardinality() == 6) {
            // all values in order are used for the permutation
            return solver.verify(puzzle, permutation, variants);
        }
        // iterate through unused permutation values
        for (int x : IntStream.of(order).filter(value -> !used.get(value)).toArray()) {
            // recursively try permutations
            permutation[position] = x;
            used.set(x);
            if (permutations(order, permutation, used, position + 1, variants)) {
                // found a valid one so return
                return true;
            }
            used.clear(x);
        }
        // no solution is found
        return false;
    }
}
