package server;

import chess.*;

public class Main {
        public static void main(String[] args) {
            // Create a Server object
            Server server = new Server();

            // Run the server on port 8080
            int actualPort = server.run(8080);

            // Print confirmation that the server started
            System.out.println("♕ 240 Chess Server started on port: " + actualPort);

            // Example Chess piece creation for debugging
            var piece = new ChessPiece(ChessGame.TeamColor.WHITE, ChessPiece.PieceType.PAWN);
            System.out.println("Example Chess Piece: " + piece);
        }
}