package ChessPiece;

import ChessPieceManager.Board;
import Major.Piece;
import java.util.ArrayList;
import java.util.List;

public class Cannon extends Piece {

    private final Board board;

    // Constructor nhận board từ bên ngoài
    public Cannon(int x, int y, String color, Board board) {
        super(x, y, color);
        this.board = board; // Gán board vào biến instance
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        // Kiểm tra di chuyển theo hàng (x == newX) hoặc theo cột (y == newY)
        if (x == newX || y == newY) {
            int obstacleCount = 0; // Đếm số quân cản trên đường đi
            Piece targetPiece = board.getPiece(newX, newY); // Quân cờ tại ô đích

            // Kiểm tra di chuyển theo hàng (x == newX)
            if (x == newX) {
                int startY = Math.min(y, newY);
                int endY = Math.max(y, newY);

                // Duyệt qua các ô giữa vị trí xuất phát và đích để kiểm tra vật cản
                for (int i = startY + 1; i < endY; i++) {
                    if (board.getPiece(x, i) != null) {
                        obstacleCount++;
                        // Nếu có hơn một vật cản, di chuyển không hợp lệ
                        if (obstacleCount > 1) {
                            return false;
                        }
                    }
                }

                // Nếu không có vật cản và ô đích trống, di chuyển hợp lệ
                if (obstacleCount == 0) {
                    return targetPiece == null;
                }

                // Nếu có đúng một vật cản
                if (obstacleCount == 1) {
                    // Cho phép ăn quân ở ô đích nếu là quân đối phương
                    return targetPiece != null && !targetPiece.getColor().equals(color);
                }
            }

            // Kiểm tra di chuyển theo cột (y == newY)
            if (y == newY) {
                int startX = Math.min(x, newX);
                int endX = Math.max(x, newX);

                // Duyệt qua các ô giữa vị trí xuất phát và đích để kiểm tra vật cản
                for (int i = startX + 1; i < endX; i++) {
                    if (board.getPiece(i, y) != null) {
                        obstacleCount++;
                        // Nếu có hơn một vật cản, di chuyển không hợp lệ
                        if (obstacleCount > 1) {
                            return false;
                        }
                    }
                }

                // Nếu không có vật cản và ô đích trống, di chuyển hợp lệ
                if (obstacleCount == 0) {
                    return targetPiece == null;
                }

                // Nếu có đúng một vật cản
                if (obstacleCount == 1) {
                    // Cho phép ăn quân ở ô đích nếu là quân đối phương
                    return targetPiece != null && !targetPiece.getColor().equals(color);
                }
            }
        }

        return false; // Nếu không di chuyển theo hàng hoặc cột
    }

    @Override
    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        // Kiểm tra di chuyển ngang
        for (int i = 0; i < 9; i++) {
            if (i != y) {  // Không kiểm tra chính ô hiện tại
                if (isValidMove(x, i)) {
                    validMoves.add(new int[]{x, i});
                }
            }
        }

        // Kiểm tra di chuyển dọc
        for (int i = 0; i < 10; i++) {
            if (i != x) {  // Không kiểm tra chính ô hiện tại
                if (isValidMove(i, y)) {
                    validMoves.add(new int[]{i, y});
                }
            }
        }

        return validMoves;
    }
}
