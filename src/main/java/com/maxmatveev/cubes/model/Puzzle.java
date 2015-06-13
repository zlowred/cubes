package com.maxmatveev.cubes.model;

/**
 * Cube model. Contains 6 sides; each side is encoded using 5 numeric values 5 bits each.
 * set bit means there is a piece, clear bit means there is a cut
 * rightmost piece is represented by 0th bit; leftmost bit is represented by 4th bit; bits [5..7] are always clear
 * Created by Max Matveev on 12/06/15.
 */
public class Puzzle {
    private byte data[][];

    public Puzzle() {
        data = new byte[6][];
        for (int side = 0; side < 6; side++) {
            data[side] = new byte[5];
        }
    }

    /**
     * Set value for given side at given position
     *
     * @param side
     * @param line
     * @param position
     * @param value
     */
    public void set(int side, int line, int position, int value) {
        if (value != 0) {
            data[side][line] |= (1 << position);
        } else {
            data[side][line] &= (31 ^ (1 << position));
        }
    }

    /**
     * Get value for given side at given position using supplied variant
     *
     * @param side
     * @param line
     * @param position
     * @param variant
     * @return
     */
    public int get(int side, int line, int position, Variant variant) {
        int translatedLine, translatedPosition;
        switch (variant) {
            case NORMAL:
                translatedLine = line;
                translatedPosition = position;
                break;
            case FLIPPED_H:
                translatedLine = line;
                translatedPosition = 4 - position;
                break;
            case FLIPPED_V:
                translatedLine = 4 - line;
                translatedPosition = position;
                break;
            case ROTATED_90:
                translatedLine = 4 - position;
                translatedPosition = line;
                break;
            case ROTATED_180:
                translatedLine = 4 - line;
                translatedPosition = 4 - position;
                break;
            case ROTATED_270:
                translatedLine = position;
                translatedPosition = 4 - line;
                break;
            default:
                return 0;
        }
        return (data[side][translatedLine] >> translatedPosition) & 1;
    }

    public static enum Variant {
        NORMAL,
        FLIPPED_H,
        FLIPPED_V,
        ROTATED_90,
        ROTATED_180,
        ROTATED_270
    }
}
