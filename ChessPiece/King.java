package ChessPiece;

import Major.Piece;
import ChessPieceManager.Board;
import java.util.ArrayList;
import java.util.List;

public class King extends Piece {
    public King(int x, int y, String color) {
        super(x, y, color);
    }

    // Phương thức kiểm tra nước đi hợp lệ cho Tướng
    @Override
    public boolean isValidMove(int newX, int newY) {
        // Tướng chỉ được di chuyển 1 ô ngang hoặc dọc trong phạm vi của cung
        return Math.abs(newX - x) + Math.abs(newY - y) == 1 &&
               ((color.equals("White") && newX >= 0 && newX <= 2 && newY >= 3 && newY <= 5) || // Vùng cung của Tướng trắng
                (color.equals("Black") && newX >= 7 && newX <= 9 && newY >= 3 && newY <= 5));  // Vùng cung của Tướng đen
    }

    // Phương thức trả về danh sách các nước đi hợp lệ cho Tướng
    @Override
    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        // Các nước đi có thể của Tướng: lên, xuống, trái, phải
        int[][] possibleMoves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : possibleMoves) {
            int newX = this.x + move[0];
            int newY = this.y + move[1];
            if (isValidMove(newX, newY)) {
                validMoves.add(new int[] {newX, newY});
            }
        }
        return validMoves;
    }

    // Phương thức kiểm tra xem Tướng có đang bị chiếu hay không
    public boolean isInCheck(Board board) {
        return isUnderAttack(this.x, this.y, board, this.color);
    }

    // Phương thức kiểm tra chiếu bí
    public boolean isCheckmate(Board board) {
        // Nếu Tướng không bị chiếu, không phải chiếu bí, trả về false
        if (!isInCheck(board)) {
            return false;
        }

        // Kiểm tra nếu Tướng có nước đi nào hợp lệ để thoát chiếu
        for (int[] move : getValidMoves()) {
            int newX = move[0];
            int newY = move[1];
            if (isValidMove(newX, newY) && !isUnderAttack(newX, newY, board, this.color)) {
                return false; // Nếu có nước thoát, không phải chiếu bí
            }
        }

        // Kiểm tra các quân đồng minh xem có nước đi nào để chặn hoặc giải cứu Tướng không
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board.getPiece(i, j);
                // Chỉ kiểm tra các quân cờ có cùng màu với Tướng
                if (piece != null && piece.getColor().equals(this.color)) {
                    List<int[]> moves = piece.getValidMoves();
                    for (int[] move : moves) {
                        int targetX = move[0];
                        int targetY = move[1];
                        Piece targetPiece = board.getPiece(targetX, targetY);

                        // Nếu ô đích không có quân cùng màu, giả lập nước đi để xem có thể ngăn chiếu không
                        if (targetPiece == null || !targetPiece.getColor().equals(this.color)) {
                            if (!isUnderAttack(x, y, board, color)) {
                                return false; // Nếu có nước cứu Tướng, không phải chiếu bí
                            }
                        }
                    }
                }
            }
        }

        // Nếu không có nước đi nào để cứu Tướng, trả về true (chiếu bí)
        return true;
    }

    // Phương thức kiểm tra xem vị trí (x, y) có bị quân đối phương tấn công không
    private boolean isUnderAttack(int x, int y, Board board, String color) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board.getPiece(i, j);
                // Kiểm tra các quân đối phương
                if (piece != null && !piece.getColor().equals(color)) {
                    // Nếu quân đối phương có nước đi hợp lệ đến vị trí (x, y), vị trí đó đang bị tấn công
                    if (piece.isValidMove(x, y)) {
                        return true;
                    }
                }
            }
        }
        // Không có quân đối phương nào đe dọa vị trí (x, y)
        return false;
    }
}
