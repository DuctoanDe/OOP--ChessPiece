package ChessPiece;

import ChessPieceManager.Board;
import Major.Piece;
import java.util.ArrayList;
import java.util.List;

public class Horse extends Piece {
    private Board board;

    public Horse(int x, int y, String color, Board board) {
        super(x, y, color);
        this.board = board; // Khởi tạo đối tượng board
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        // Tính toán khoảng cách di chuyển
        int dx = Math.abs(newX - x);
        int dy = Math.abs(newY - y);

        // Kiểm tra di chuyển hợp lệ cho quân Mã và đảm bảo tọa độ trong phạm vi 0-8 (0 đến 8 vì mảng từ 0)
        return ((dx == 2 && dy == 1) || (dx == 1 && dy == 2)) &&
                (newX >= 0 && newX < 10 && newY >= 0 && newY < 9) &&
                !isBlocked(x, y, newX, newY); // Thêm kiểm tra quân cản
    }

    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        // Các nước đi có thể của quân Mã (hình chữ "L")
        int[][] possibleMoves = {
                { x + 2, y + 1 }, { x + 2, y - 1 }, { x - 2, y + 1 }, { x - 2, y - 1 }, // Di chuyển 2 ô theo trục x và 1 ô theo trục y
                { x + 1, y + 2 }, { x + 1, y - 2 }, { x - 1, y + 2 }, { x - 1, y - 2 }  // Di chuyển 1 ô theo trục x và 2 ô theo trục y
        };

        // Kiểm tra các vị trí có thể di chuyển và đảm bảo không ra ngoài bàn cờ
        for (int[] move : possibleMoves) {
            int newX = move[0];
            int newY = move[1];

            // Kiểm tra xem vị trí mới có hợp lệ không (nằm trong bàn cờ và không có quân cờ của mình)
            if (newX >= 0 && newX < 10 && newY >= 0 && newY < 9) {
                // Kiểm tra nếu ô đích có quân của mình (không thể di chuyển vào ô của quân mình)
                Piece targetPiece = board.getPiece(newX, newY);
                if (targetPiece != null && targetPiece.getColor().equals(this.getColor())) {
                    continue; // Nếu có quân cờ của mình, không thêm vào validMoves
                }

                // Sử dụng hàm isBlocked để kiểm tra chặn
                if (!isBlocked(x, y, newX, newY)) {
                    validMoves.add(new int[] { newX, newY });
                }
            }
        }

        return validMoves;
    }

    // Kiểm tra xem có quân cản (ví dụ "Bồ") tại vị trí giữa không
    private boolean isBlocked(int x, int y, int newX, int newY) {
        int blockerX = x, blockerY = y;

        // Xác định vị trí giữa (blockerX, blockerY) tùy thuộc vào hướng đi
        if (Math.abs(newX - x) == 2 && Math.abs(newY - y) == 1) { // Ngang 2, dọc 1
            blockerX = x + (newX - x) / 2;
        } else if (Math.abs(newX - x) == 1 && Math.abs(newY - y) == 2) { // Dọc 2, ngang 1
            blockerY = y + (newY - y) / 2;
        }

        // Kiểm tra có quân cản tại vị trí giữa không
        Piece blockerPiece = board.getPiece(blockerX, blockerY);
        return blockerPiece != null; // Trả về true nếu có quân cản
    }
}
