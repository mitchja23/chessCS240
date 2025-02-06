package chess;

import java.util.Collection;
import java.util.Iterator;

/**
 * For a class that can manage a chess game, making moves on a board
 * <p>
 * Note: You can add to this class, but you may not alter
 * signature of the existing methods.
 */
public class ChessGame {

    private TeamColor colorTurn;
    private ChessBoard board;

    public ChessGame() {
        colorTurn = TeamColor.WHITE;
    }

    /**
     * @return Which team's turn it is
     */
    public TeamColor getTeamTurn() {
        return colorTurn;

    }

    /**
     * Set's which teams turn it is
     *
     * @param team the team whose turn it is
     */


    public void setTeamTurn(TeamColor team) {

        colorTurn = team;
    }

    /**
     * Enum identifying the 2 possible teams in a chess game
     */
    public enum TeamColor {
        WHITE,
        BLACK
    }

    /**
     * Gets a valid moves for a piece at the given location
     *
     * @param startPosition the piece to get valid moves for
     * @return Set of valid moves for requested piece, or null if no piece at
     * startPosition
     */
    public Collection<ChessMove> validMoves(ChessPosition startPosition) {
        ChessBoard board = getBoard();
        ChessPiece piece = board.getPiece(startPosition);

        if (piece == null) {
            return null;
        }

        Collection<ChessMove> possibleMoves = piece.pieceMoves(board, startPosition);

        Iterator<ChessMove> testMoves = possibleMoves.iterator();
        while (testMoves.hasNext()) {
            ChessMove move = testMoves.next();
            ChessBoard currentBoard = board.calcBoard();
            ChessPiece movingPiece = currentBoard.getPiece(startPosition);
            currentBoard.addPiece(move.getEndPosition(), movingPiece);
            currentBoard.addPiece(startPosition, null);

            if (InCheckTest(currentBoard, piece.getTeamColor())) {
                testMoves.remove(); // Remove the move if it leaves the king in check
            }
        }
        return possibleMoves;
    }

    private boolean InCheckTest(ChessBoard board, TeamColor teamColor) {
        ChessPosition king = null;
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);
                if (piece != null && piece.getPieceType() == ChessPiece.PieceType.KING
                        && piece.getTeamColor() == teamColor) {
                    king = position;
                    break;
                }
            }
        }
        for (int row = 1; row <= 8; row++) {
            for (int col = 1; col <= 8; col++) {
                ChessPosition position = new ChessPosition(row, col);
                ChessPiece piece = board.getPiece(position);

                if (piece != null && piece.getTeamColor() != teamColor) {
                    Collection<ChessMove> opponentMoves = piece.pieceMoves(board, position);

                    for (ChessMove move : opponentMoves) {
                        if (move.getEndPosition().equals(king)) {
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }


    /**
     * Makes a move in a chess game
     *
     * @param move chess move to preform
     * @throws InvalidMoveException if move is invalid
     */
    public void makeMove(ChessMove move) throws InvalidMoveException {
        Collection<ChessMove> moves = validMoves(move.getStartPosition());
        if (!moves.contains(move)) {
            throw new InvalidMoveException("Illegal move");
        }
        ChessPiece piece = board.getPiece(move.getStartPosition());
        board.addPiece(move.getEndPosition(), piece);
        board.addPiece(move.getStartPosition(), null);


        ChessPiece opp = board.getPiece(move.getEndPosition());
        if(opp != null && opp.getTeamColor() != colorTurn) {
            board.addPiece(move.getEndPosition(), opp);
        }

        if (piece.getPieceType() == ChessPiece.PieceType.PAWN) {
            int promote = (piece.getTeamColor() == TeamColor.WHITE) ? 8 : 1;
            if (move.getEndPosition().getRow() == promote) {
                ChessPiece.PieceType upgrade = ChessPiece.PieceType.QUEEN;
                ChessPiece newPiece = new ChessPiece(piece.getTeamColor(), upgrade);

                board.addPiece(move.getEndPosition(), newPiece);
            }
        }



        colorTurn = (colorTurn == TeamColor.WHITE) ? TeamColor.BLACK : TeamColor.WHITE;


    }

    /**
     * Determines if the given team is in check
     *
     * @param teamColor which team to check for check
     * @return True if the specified team is in check
     */
    public boolean isInCheck(TeamColor teamColor) {
        if(InCheckTest(board, teamColor)) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * Determines if the given team is in checkmate
     *
     * @param teamColor which team to check for checkmate
     * @return True if the specified team is in checkmate
     */
    public boolean isInCheckmate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Determines if the given team is in stalemate, which here is defined as having
     * no valid moves
     *
     * @param teamColor which team to check for stalemate
     * @return True if the specified team is in stalemate, otherwise false
     */
    public boolean isInStalemate(TeamColor teamColor) {
        throw new RuntimeException("Not implemented");
    }

    /**
     * Sets this game's chessboard with a given board
     *
     * @param board the new board to use
     */
    public void setBoard(ChessBoard board) {
        if (this.board == null) {
            throw new IllegalArgumentException("Already set");
        }

        this.board = board;


    }
    /**
     * Gets the current chessboard
     *
     * @return the chessboard
     */
    public ChessBoard getBoard() {

        return board;
    }
}
