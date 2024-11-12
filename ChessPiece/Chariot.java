package ChessPiece;

import ChessPieceManager.Board;
import Major.Piece;
import java.util.ArrayList;
import java.util.List;

public class Chariot extends Piece {
    private Board board; // Add a reference to the board

    public Chariot(int x, int y, String color, Board board) {
        super(x, y, color);
        this.board = board; // Initialize board here
    }

    @Override
public boolean isValidMove(int newX, int newY) {
    // Kiểm tra xem quân cờ có di chuyển ngang hoặc dọc không
    if (x != newX && y != newY) {
        return false; // Quân xe chỉ di chuyển theo hàng hoặc cột
    }

    // Kiểm tra nếu di chuyển theo cột (x không đổi)
    if (x == newX) {
        int startY = Math.min(y, newY);  // Xác định vị trí bắt đầu di chuyển
        int endY = Math.max(y, newY);    // Xác định vị trí kết thúc di chuyển
        for (int i = startY + 1; i < endY; i++) {
            Piece piece = board.getPiece(x, i);
            if (piece != null) {  // Nếu có vật cản, không thể di chuyển qua
                return false;
            }
        }
    }

    // Kiểm tra nếu di chuyển theo hàng (y không đổi)
    if (y == newY) {
        int startX = Math.min(x, newX);  // Xác định vị trí bắt đầu di chuyển
        int endX = Math.max(x, newX);    // Xác định vị trí kết thúc di chuyển
        for (int i = startX + 1; i < endX; i++) {
            Piece piece = board.getPiece(i, y);
            if (piece != null) {  // Nếu có vật cản, không thể di chuyển qua
                return false;
            }
        }
    }

    // Nếu không có vật cản trên đường đi, kiểm tra nếu có quân cờ của đối phương tại đích
    Piece destinationPiece = board.getPiece(newX, newY);
    if (destinationPiece != null && destinationPiece.getColor().equals(this.color)) {
        return false; // Không thể di chuyển đến ô có quân cờ cùng màu
    }

    return true;  // Nếu tất cả các điều kiện đều hợp lệ, di chuyển hợp lệ
}


@Override
public List<int[]> getValidMoves() {
    List<int[]> validMoves = new ArrayList<>();

    // Di chuyển dọc xuống
    for (int i = x + 1; i < 10; i++) {
        validMoves.add(new int[]{i, y}); // Thêm mọi ô xuống dưới
    }

    // Di chuyển dọc lên
    for (int i = x - 1; i >= 0; i--) {
        validMoves.add(new int[]{i, y}); // Thêm mọi ô lên trên
    }

    // Di chuyển ngang sang phải
    for (int j = y + 1; j < 9; j++) {
        validMoves.add(new int[]{x, j}); // Thêm mọi ô sang phải
    }

    // Di chuyển ngang sang trái
    for (int j = y - 1; j >= 0; j--) {
        validMoves.add(new int[]{x, j}); // Thêm mọi ô sang trái
    }

    return validMoves;
}

}
