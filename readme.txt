Requires Java 8 (Using Streams API, lambdas, try-with-resources, multi-catch)

Usage: java com.maxmatveev.cubes.Main [<cubes_file>]

If input file is not provided random of the default ones (provided in the problem archive) will be used

input file format:
exactly 6 descriptions for the sides of the puzzle;
Each of these is 5 lines 5 characters each containing only '.' (empty) or '#' (non-empty) symbols
followed by a blank line

e.g.


======= beginning of the blue puzzle ======
..#..
.###.
#####
.###.
..#..

#.#.#
#####
.###.
#####
#.#.#

..#..
.####
####.
.####
..#..

.#.#.
####.
.####
####.
##.#.

.#.#.
#####
.###.
#####
#.#..

.#.#.
.####
####.
.####
##.##

======= end of the blue puzzle. note that each non-blank line is exactly 5 characters ======