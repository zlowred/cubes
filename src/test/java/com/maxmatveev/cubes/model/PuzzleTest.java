package com.maxmatveev.cubes.model;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PuzzleTest {
    @Test
    public void testVariants() {
        Puzzle p = new Puzzle();
        p.set(1, 1, 1, 1);

        assertEquals(1, p.get(1, 1, 1, Puzzle.Variant.NORMAL));
        assertEquals(0, p.get(1, 1, 3, Puzzle.Variant.NORMAL));
        assertEquals(0, p.get(1, 3, 3, Puzzle.Variant.NORMAL));
        assertEquals(0, p.get(1, 3, 1, Puzzle.Variant.NORMAL));

        assertEquals(0, p.get(1, 1, 1, Puzzle.Variant.FLIPPED_V));
        assertEquals(0, p.get(1, 1, 3, Puzzle.Variant.FLIPPED_V));
        assertEquals(0, p.get(1, 3, 3, Puzzle.Variant.FLIPPED_V));
        assertEquals(1, p.get(1, 3, 1, Puzzle.Variant.FLIPPED_V));

        assertEquals(0, p.get(1, 1, 1, Puzzle.Variant.FLIPPED_H));
        assertEquals(1, p.get(1, 1, 3, Puzzle.Variant.FLIPPED_H));
        assertEquals(0, p.get(1, 3, 3, Puzzle.Variant.FLIPPED_H));
        assertEquals(0, p.get(1, 3, 1, Puzzle.Variant.FLIPPED_H));

        assertEquals(0, p.get(1, 1, 1, Puzzle.Variant.ROTATED_90));
        assertEquals(1, p.get(1, 1, 3, Puzzle.Variant.ROTATED_90));
        assertEquals(0, p.get(1, 3, 3, Puzzle.Variant.ROTATED_90));
        assertEquals(0, p.get(1, 3, 1, Puzzle.Variant.ROTATED_90));

        assertEquals(0, p.get(1, 1, 1, Puzzle.Variant.ROTATED_180));
        assertEquals(0, p.get(1, 1, 3, Puzzle.Variant.ROTATED_180));
        assertEquals(1, p.get(1, 3, 3, Puzzle.Variant.ROTATED_180));
        assertEquals(0, p.get(1, 3, 1, Puzzle.Variant.ROTATED_180));

        assertEquals(0, p.get(1, 1, 1, Puzzle.Variant.ROTATED_270));
        assertEquals(0, p.get(1, 1, 3, Puzzle.Variant.ROTATED_270));
        assertEquals(0, p.get(1, 3, 3, Puzzle.Variant.ROTATED_270));
        assertEquals(1, p.get(1, 3, 1, Puzzle.Variant.ROTATED_270));
    }
}