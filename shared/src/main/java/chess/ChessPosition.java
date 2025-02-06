package chess;

import java.util.Objects;

public class ChessPosition {
    private final int row;
    private final int col;

    public ChessPosition(int row, int col) {
        this.row = row;
        this.col = col;
    }
    public int getRow() {
        return row;
    }
    public int getColumn() {
        return col;
    }
    @Override
    public boolean equals(Object pos) {
        if (this == pos)
            return true;
        else if (pos == null || getClass() != pos.getClass())
            return false;
        ChessPosition that = (ChessPosition) pos;
            return row == that.row && col == that.col;
    }
    @Override
    public int hashCode() {
        return Objects.hash(row, col);
    }
}
