package game2048;

import java.util.Formatter;
import java.util.Observable;


/**
 * The state of a game of 2048.
 */

public class Model extends Observable {
    /**
     * Largest piece value.
     */
    public static final int MAX_PIECE = 2048;
    /**
     * Current contents of the board.
     */
    private Board board;
    /**
     * Current score.
     */
    private int score;
    /**
     * Maximum score so far.  Updated when game ends.
     */
    private int maxScore;

    /* Coordinate System: column C, row R of the board (where row 0,
     * column 0 is the lower-left corner of the board) will correspond
     * to board.tile(c, r).  Be careful! It works like (x, y) coordinates.
     */
    /**
     * True iff game is ended.
     */
    private boolean gameOver;


    /**
     * A new 2048 game on a board of size SIZE with no pieces
     * and score 0.
     */

    public Model(int size) {
        Board board1 = new Board(size);
        this.board = board1;
        this.score = 0;
        this.maxScore = 0;
    }


    /**
     * A new 2048 game where RAWVALUES contain the values of the tiles
     * (0 if null). VALUES is indexed by (row, col) with (0, 0) corresponding
     * to the bottom-left corner. Used for testing purposes.
     */

    public Model(int[][] rawValues, int score, int maxScore, boolean gameOver) {
        Board board1 = new Board(rawValues, score);
        this.board = board1;
        this.maxScore = maxScore;
        this.gameOver = gameOver;
    }

    /**
     * Determine whether game is over.
     */
    private static boolean checkGameOver(Board b) {
        return maxTileExists(b) || !atLeastOneMoveExists(b);
    }

    /**
     * Returns true if at least one space on the Board is empty.
     * Empty spaces are stored as null.
     */
    public static boolean emptySpaceExists(Board b) {
        for (int row = 0; row < b.size(); row++) {
            for (int col = 0; col < b.size(); col++) {
                Tile a = b.tile(col, row);

                if (a == null) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if any tile is equal to the maximum valid value.
     * Maximum valid value is given by MAX_PIECE. Note that
     * given a Tile object t, we get its value with t.value().
     */
    public static boolean maxTileExists(Board b) {
        for (int row = 0; row < b.size(); row++) {
            for (int col = 0; col < b.size(); col++) {

                Tile a = b.tile(col, row);
                if (a == null) {
                    continue;
                }
                if (a.value() == 2048) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Returns true if there are any valid moves on the board.
     * There are two ways that there can be valid moves:
     * 1. There is at least one empty space on the board.
     * 2. There are two adjacent tiles with the same value.
     */
    // Tile: value()
    // Side: none
    // Model:
    // Board: setViewingPerspective: set s to north, tile: return current tile, move: move to (,) and return if merge is true.
    public static boolean atLeastOneMoveExists(Board b) {
        Tile comp1, comp2, comp3, comp4;

        if (emptySpaceExists(b)) {
            return true;

        } else {
            for (int row = 0; row < b.size(); row++) {
                for (int col = 0; col < b.size(); col++) {
                    Tile a = b.tile(col, row);

                    // central 4 tiles
                    if ((row == 1 || row == 2) && (col == 1 || col == 2)) {
                        comp1 = b.tile(col, row - 1);
                        comp2 = b.tile(col, row + 1);
                        comp3 = b.tile(col - 1, row);
                        comp4 = b.tile(col + 1, row);
                        if (a.value() == comp1.value() || a.value() == comp2.value() || a.value() == comp3.value() || a.value() == comp4.value()) {
                            return true;
                        }
                    }

                    // edge 8 tiles
                    if (col == 0) {
                        if (row == 1 || row == 2) {
                            comp1 = b.tile(col, row - 1);
                            comp2 = b.tile(col, row + 1);
                            comp3 = b.tile(col + 1, row);
                            if (a.value() == comp1.value() || a.value() == comp2.value() || a.value() == comp3.value()) {
                                return true;
                            }
                        }
                    }
                    if (col == 3) {
                        if (row == 1 || row == 2) {
                            comp1 = b.tile(col, row - 1);
                            comp2 = b.tile(col, row + 1);
                            comp3 = b.tile(col - 1, row);
                            if (a.value() == comp1.value() || a.value() == comp2.value() || a.value() == comp3.value()) {
                                return true;
                            }
                        }
                    }
                    if (row == 0) {
                        if (col == 1 || col == 2) {
                            comp1 = b.tile(col, row + 1);
                            comp2 = b.tile(col - 1, row);
                            comp3 = b.tile(col + 1, row);
                            if (a.value() == comp1.value() || a.value() == comp2.value() || a.value() == comp3.value()) {
                                return true;
                            }
                        }
                    }
                    if (row == 3) {
                        if (col == 1 || col == 2) {
                            comp1 = b.tile(col, row - 1);
                            comp2 = b.tile(col - 1, row);
                            comp3 = b.tile(col + 1, row);
                            if (a.value() == comp1.value() || a.value() == comp2.value() || a.value() == comp3.value()) {
                                return true;
                            }
                        }
                    }

                    // 4 edges
                    if (row == 0 && col == 0) {
                        comp1 = b.tile(col, row + 1);
                        comp2 = b.tile(col + 1, row);
                        if (a.value() == comp1.value() || a.value() == comp2.value()) {
                            return true;
                        }
                    }
                    if (row == 0 && col == 3) {
                        comp1 = b.tile(col, row + 1);
                        comp2 = b.tile(col - 1, row);
                        if (a.value() == comp1.value() || a.value() == comp2.value()) {
                            return true;
                        }
                    }
                    if (row == 3 && col == 0) {
                        comp1 = b.tile(col, row - 1);
                        comp2 = b.tile(col + 1, row);
                        if (a.value() == comp1.value() || a.value() == comp2.value()) {
                            return true;
                        }
                    }
                    if (row == 3 && col == 3) {
                        comp1 = b.tile(col, row - 1);
                        comp2 = b.tile(col - 1, row);
                        if (a.value() == comp1.value() || a.value() == comp2.value()) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }
    }

    /**
     * Return the current Tile at (COL, ROW), where 0 <= ROW < size(),
     * 0 <= COL < size(). Returns null if there is no tile there.
     * Used for testing. Should be deprecated and removed.
     */
    public Tile tile(int col, int row) {
        return board.tile(col, row);
    }

    /**
     * Return the number of squares on one side of the board.
     * Used for testing. Should be deprecated and removed.
     */
    public int size() {
        return board.size();
    }

    /**
     * Return true iff the game is over (there are no moves, or
     * there is a tile with value 2048 on the board).
     */
    public boolean gameOver() {
        checkGameOver();
        if (gameOver) {
            maxScore = Math.max(score, maxScore);
        }
        return gameOver;
    }

    /**
     * Return the current score.
     */
    public int score() {
        return score;
    }

    /**
     * Return the current maximum game score (updated at end of game).
     */
    public int maxScore() {
        return maxScore;
    }

    /**
     * Clear the board to empty and reset the score.
     */
    public void clear() {
        score = 0;
        gameOver = false;
        board.clear();
        setChanged();
    }

    /**
     * Add TILE to the board. There must be no Tile currently at the
     * same position.
     */
    public void addTile(Tile tile) {
        board.addTile(tile);
        checkGameOver();
        setChanged();
    }

    /**
     * Tilt the board toward SIDE. Return true if this changes the board.
     * <p>
     * 1. If two Tile objects are adjacent in the direction of motion and have
     * the same value, they are merged into one Tile of twice the original
     * value and that new value is added to the score instance variable
     * 2. A tile that is the result of a merge will not merge again on that
     * tilt. So each move, every tile will only ever be part of at most one
     * merge (perhaps zero).
     * 3. When three adjacent tiles in the direction of motion have the same
     * value, then the leading two tiles in the direction of motion merge,
     * and the trailing tile does not.
     */

    // Tile: value()
    // Side: none
    // Model:
    // Board: setViewingPerspective: set s to north, tile: return current tile, move: move to (,) and return if merge is true.
    public boolean tilt(Side side) {
        board.setViewingPerspective(side);
        boolean changed;
        changed = false;

        Tile currTile, NextTile;
        int rowIndex;
        boolean bindex, EveryTileNull;

        // For loop 1
        // Outcome: check first to fourth column
        for (int col = 0; col < this.board.size(); col++) {

            //For loop 2
            // Outcome: iterate from top to bottom row.
            for (int row = this.board.size() - 2; row >= 0; row--) {
                currTile = this.board.tile(col, row);

                // if the curr tile is 0, skip this position.
                // Outcome: no point to check if 0 moves
                if (currTile == null) {
                    continue;
                }

                // While loop
                // goal: check moving condition, if okay, put curr to above possible position and merge if possible.
                // Spec: iterate the row from top that is above this currTile.
                rowIndex = this.board.size() - 1;
                while (rowIndex > row) {
                    NextTile = this.board.tile(col, rowIndex);

                    // if Nextile is null, move currTile to NextTile.
                    if (NextTile == null) {
                        bindex = this.board.move(col, rowIndex, currTile);
                        //NextTile.setMarker(bindex);
                        changed = true;
                        break;
                    }

                    // if NextTile is not null
                    // Outcome: Merge if curr.value == Next.value.
                    else {
                        // if NextTile's mergeMarker equal to false then continue checking, if true skip this position.
                        if (NextTile.getMarker() == false) {

                            // check if there is any not null tile in between curr and Next. Only proceed if every in between tile is null.
                            EveryTileNull = true;
                            for (int k = row + 1; k < rowIndex; k++) {
                                 if (this.board.tile(col, k) != null) {
                                     EveryTileNull = false;
                                }
                            }
                            if (EveryTileNull == true) {

                                // If Next has the same value as the curr, call move (curr to Next) to set Next mergeMarker. Board changes set changed = true.
                                if (NextTile.value() == currTile.value()) {
                                    bindex = this.board.move(col, rowIndex, currTile);
                                    this.board.tile(col, rowIndex).setMarker(bindex);
                                    changed = true;

                                    // if move return true, this.score = NestTile.value()
                                    if (bindex) {
                                        this.score += NextTile.value();
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    rowIndex--;
                }
            }
        }
        for (int row = 0; row < this.board.size(); row++) {
            for (int col = 0; col < this.board.size(); col++) {
                if (this.board.tile(col,row) == null) {
                    continue;
                }
                this.board.tile(col,row).setMarker(false);
            }
        }

        board.setViewingPerspective(Side.NORTH);

        checkGameOver();
        if (changed) {
            setChanged();
        }
        return changed;
    }

    /**
     * Checks if the game is over and sets the gameOver variable
     * appropriately.
     */
    private void checkGameOver() {
        gameOver = checkGameOver(board);
    }

    @Override
    /** Returns the model as a string, used for debugging. */
    public String toString() {
        Formatter out = new Formatter();
        out.format("%n[%n");
        for (int row = size() - 1; row >= 0; row -= 1) {
            for (int col = 0; col < size(); col += 1) {
                if (tile(col, row) == null) {
                    out.format("|    ");
                } else {
                    out.format("|%4d", tile(col, row).value());
                }
            }
            out.format("|%n");
        }
        String over = gameOver() ? "over" : "not over";
        out.format("] %d (max: %d) (game is %s) %n", score(), maxScore(), over);
        return out.toString();
    }

    @Override
    /** Returns whether two models are equal. */
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        } else if (getClass() != o.getClass()) {
            return false;
        } else {
            return toString().equals(o.toString());
        }
    }

    @Override
    /** Returns hash code of Model’s string. */
    public int hashCode() {
        return toString().hashCode();
    }
}