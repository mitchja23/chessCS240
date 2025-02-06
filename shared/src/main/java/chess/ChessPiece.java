package chess;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

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
    public enum PieceType {
        KING,
        QUEEN,
        BISHOP,
        KNIGHT,
        ROOK,
        PAWN
    }
    public ChessGame.TeamColor getTeamColor() {
        return pieceColor;
    }
    public PieceType getPieceType() {
        return type;
    }
    public Collection<ChessMove> pieceMoves(ChessBoard board, ChessPosition myPosition) {
        Collection<ChessMove> moves = new ArrayList<>();
        if (type == PieceType.BISHOP) {
            bishopMoves(board, myPosition, moves);
        } else if (type == PieceType.ROOK) {
            rookMoves(board, myPosition, moves);
        } else if (type == PieceType.QUEEN) {
            queenMoves(board, myPosition, moves);
        }else if (type == PieceType.KNIGHT) {
            knightMoves(board, myPosition, moves);
        }else if (type == PieceType.KING) {
            kingMoves(board, myPosition, moves);
        }else if (type == PieceType.PAWN) {
            pawnMoves(board, myPosition, moves);
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
                if (inRange(newPosition)) {
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

    private void rookMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}
        };
        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (true) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row, col);
                if (inRange(newPosition)) {
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

    private void queenMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1},{1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        for (int[] direction : directions) {
            int row = myPosition.getRow();
            int col = myPosition.getColumn();

            while (true) {
                row += direction[0];
                col += direction[1];
                ChessPosition newPosition = new ChessPosition(row, col);
                if (inRange(newPosition)) {
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

    private void knightMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int[][] directions = {
                {2, 1}, {1, 2}, {-1, 2}, {-2, 1}, {-2, -1}, {-1, -2}, {1, -2}, {2, -1}
        };
        for (int[] direction : directions) {
            int row = myPosition.getRow() + direction[0];
            int col = myPosition.getColumn() + direction[1];
            ChessPosition newPosition = new ChessPosition(row, col);
            if (offBoard(newPosition)) {
                continue;
            }
            if (freeSpace(board, newPosition)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            } else if (otherColor(board, newPosition)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
    }
    private void kingMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int[][] directions = {
                {1, 0}, {-1, 0}, {0, 1}, {0, -1}, {1, 1}, {1, -1}, {-1, 1}, {-1, -1}
        };
        for (int[] direction : directions) {
            int row = myPosition.getRow() + direction[0];
            int col = myPosition.getColumn() + direction[1];
            ChessPosition newPosition = new ChessPosition(row, col);
            if (offBoard(newPosition)) {
                continue;
            }
            if (freeSpace(board, newPosition)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            } else if (otherColor(board, newPosition)) {
                moves.add(new ChessMove(myPosition, newPosition, null));
            }
        }
    }
    private void pawnMoves(ChessBoard board, ChessPosition myPosition, Collection<ChessMove> moves) {
        int row = (pieceColor == ChessGame.TeamColor.WHITE) ? 2 : 7;
        int col = (pieceColor == ChessGame.TeamColor.WHITE) ? 1 : -1;
        int promotion = (pieceColor == ChessGame.TeamColor.WHITE) ? 8 : 1;
        ChessPosition forwardOne = new ChessPosition(myPosition.getRow() + col, myPosition.getColumn());
        if (freeSpace(board, forwardOne)) {
            if (forwardOne.getRow() == promotion) {
                addPromotionMoves(myPosition, forwardOne, moves);
            } else {
                moves.add(new ChessMove(myPosition, forwardOne, null));
            }
            if (myPosition.getRow() == row) {
                ChessPosition forwardTwo = new ChessPosition(myPosition.getRow() + 2 * col, myPosition.getColumn());
                if (freeSpace(board, forwardTwo)) {
                    moves.add(new ChessMove(myPosition, forwardTwo, null));
                }
            }
        }
        ChessPosition[] diagonal = {
                new ChessPosition(myPosition.getRow() + col, myPosition.getColumn() - 1),
                new ChessPosition(myPosition.getRow() + col, myPosition.getColumn() + 1)
        };
        for (ChessPosition position : diagonal) {
            if (otherColor(board, position)) {
                if (position.getRow() == promotion) {
                    addPromotionMoves(myPosition, position, moves);
                } else {
                    moves.add(new ChessMove(myPosition, position, null));
                }
            }
        }
    }
    private void addPromotionMoves(ChessPosition start, ChessPosition end, Collection<ChessMove> moves) {
        moves.add(new ChessMove(start, end, ChessPiece.PieceType.QUEEN));
        moves.add(new ChessMove(start, end, ChessPiece.PieceType.BISHOP));
        moves.add(new ChessMove(start, end, ChessPiece.PieceType.KNIGHT));
        moves.add(new ChessMove(start, end, ChessPiece.PieceType.ROOK));
    }

    private boolean freeSpace(ChessBoard board, ChessPosition position) {
        return position.getRow() >= 1 && position.getRow() <= 8 &&
                position.getColumn() >= 1 && position.getColumn() <= 8 &&
                board.getPiece(position) == null;
    }
    private boolean otherColor(ChessBoard board, ChessPosition position) {
        if (offBoard(position)) {
            return false;
        }
        ChessPiece piece = board.getPiece(position);
        return piece != null && piece.getTeamColor() != this.pieceColor;
    }
    private boolean inRange(ChessPosition position) {
        return position.getRow() < 1 || position.getRow() > 8 ||
                position.getColumn() < 1 || position.getColumn() > 8;
    }
    private boolean offBoard(ChessPosition position) {
        int row = position.getRow();
        int col = position.getColumn();
        return row < 1 || row > 8 || col < 1 || col > 8;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        ChessPiece that = (ChessPiece) obj;
        return pieceColor == that.pieceColor && type == that.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(pieceColor, type);
    }

}
