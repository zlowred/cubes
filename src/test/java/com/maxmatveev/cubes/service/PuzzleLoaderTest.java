package com.maxmatveev.cubes.service;

import com.maxmatveev.cubes.model.Puzzle;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import static org.junit.Assert.assertEquals;

public class PuzzleLoaderTest {
    @Test
    public void testPuzzleLoaded() throws URISyntaxException, IOException {
        Puzzle puzzle = new PuzzleLoader().load(new File(getClass().getClassLoader().getResource("blue.txt").toURI()));
        assertEquals(1, puzzle.get(0, 0, 2, Puzzle.Variant.NORMAL));
        assertEquals(0, puzzle.get(0, 0, 0, Puzzle.Variant.NORMAL));
        assertEquals(0, puzzle.get(2, 2, 4, Puzzle.Variant.NORMAL));
        assertEquals(0, puzzle.get(2, 3, 0, Puzzle.Variant.NORMAL));
        assertEquals(1, puzzle.get(2, 2, 0, Puzzle.Variant.NORMAL));
        assertEquals(1, puzzle.get(2, 3, 4, Puzzle.Variant.NORMAL));
        assertEquals(1, puzzle.get(5, 4, 4, Puzzle.Variant.NORMAL));
        assertEquals(0, puzzle.get(5, 2, 4, Puzzle.Variant.NORMAL));
        assertEquals(1, puzzle.get(5, 4, 0, Puzzle.Variant.NORMAL));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testLoadingWrongPuzzleFails() throws URISyntaxException, IOException {
        new PuzzleLoader().load(new File(getClass().getClassLoader().getResource("textfile.txt").toURI()));
    }

}