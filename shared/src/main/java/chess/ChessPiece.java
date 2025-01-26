package chess;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Represents a single chess piece
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessPiece {

    private final ChessGame.TeamColor pieceColor;
    private final PieceType type;

    public ChessPiece(ChessGame.TeamColor pieceColor, ChessPiece.PieceType type) {
        this.pieceColor = pieceColor;
        this.type = type;
    }

    /**
     * The various different chess piece options
     */
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }

    /**
     * @return Which team this chess piece belongs to
     */
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }

    /**
     * @return which type of chess piece this piece is
     */
    public PieceType getPieceType() {
        return type;
    }

    /**
     * Calculates all the positions a chess piece can move to
     * Does not take into account moves that are illegal due to leaving the king in
     * danger
     *
     * @return Collection of valid moves
     */
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();

        if (type == PieceType.BISHOP) {
            bishopMoves(board, myPosition, moves);
        }
        return moves;
    }
    private void bishopMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int[][] directions = {
                {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (true) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row, col);
                if (!inRange(newPosition)) {
                    break;
                }
                if (freeSpace(board, newPosition)) {
                    moves.add(new ChessMove(myPosition, newPosition, null));
                } else {
                    if (otherColor(board, newPosition)) {
                        moves.add(new ChessMove(myPosition, newPosition, null));
                    }
                    break;
                }
            }
        }
    }




    private boolean freeSpace(ChessBoard board, ChessPosition position) {
        return position.getRow() >= 1 && position.getRow() <= 8 &&
                position.getColumn() >= 1 && position.getColumn() <= 8 &&
                board.getPiece(position) == null;
    }

    private boolean otherColor(ChessBoard board, ChessPosition position) {
        ChessPiece piece = board.getPiece(position);
        return piece != null && piece.getTeamColor() != this.pieceColor;
    }
    private boolean inRange(ChessPosition position) {
        return position.getRow() >= 1 && position.getRow() <= 8 &&
                position.getColumn() >= 1 && position.getColumn() <= 8;
    }





}
