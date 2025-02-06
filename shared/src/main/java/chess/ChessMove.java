package chess;

import java.util.Objects;

/**
 * Represents moving a chess piece on a chessboard
 */
public class ChessMove {
    private final ChessPosition startPosition;
    private final ChessPosition endPosition;
    private final ChessPiece.PieceType promotionPiece;

    public ChessMove(ChessPosition startPosition, ChessPosition endPosition, ChessPiece.PieceType promotionPiece) {
        this.startPosition = startPosition;
        this.endPosition = endPosition;
        this.promotionPiece = promotionPiece;
    }
    public ChessPosition getStartPosition() {
        return startPosition;
    }
    public ChessPosition getEndPosition() {
        return endPosition;
    }
    public ChessPiece.PieceType getPromotionPiece() {
        return promotionPiece;
    }
    @Override
    public boolean equals(Object move) {
        if (this == move) return true;
        if (move == null || getClass() != move.getClass()) return false;
        ChessMove that = (ChessMove) move;
        return Objects.equals(startPosition, that.startPosition) &&
                Objects.equals(endPosition, that.endPosition) &&
                Objects.equals(promotionPiece, that.promotionPiece);
    }
    @Override
    public int hashCode() {
        return Objects.hash(startPosition, endPosition, promotionPiece);
    }
}
