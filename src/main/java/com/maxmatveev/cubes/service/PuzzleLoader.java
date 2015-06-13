package com.maxmatveev.cubes.service;

import com.maxmatveev.cubes.model.Puzzle;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.stream.Stream;

/**
 * Created by Max Matveev on 12/06/15.
 */
public class PuzzleLoader {
    public Puzzle load(File file) throws IOException {
        try (Stream<String> lines = Files.lines(file.toPath())) {
            // convert lines in the data file into a string array filtering out empty lines
            String[] puzzleLines = lines.filter(line -> line.length() == 5).toArray(String[]::new);
            // there should be exactly 30 lines
            if (puzzleLines.length != 30) {
                throw new IllegalArgumentException("Wrong input file format");
            }
            // loop through the array to build puzzle
            Puzzle puzzle = new Puzzle();
            for (int i = 0; i < 30; i++) {
                for (int pos = 0; pos < 5; pos++) {
                    puzzle.set(i / 5, i % 5, pos, puzzleLines[i].charAt(pos) == '#' ? 1 : 0);
                }
            }
            return puzzle;
        }
    }
}
