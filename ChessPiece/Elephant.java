package ChessPiece;
import ChessPieceManager.Board;
import Major.Piece;
import java.util.ArrayList;
import java.util.List;

public class Elephant extends Piece {
    private Board board;

    public Elephant(int x, int y, String color, Board board) {
        super(x, y, color);
        this.board = board;
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        // Kiểm tra xem nước đi có phải là di chuyển chéo 2 ô
        if (Math.abs(newX - x) == 2 && Math.abs(newY - y) == 2) {
            // Kiểm tra không vượt qua sông
            if ((color.equals("White") && newX <= 4) || (color.equals("Black") && newX >= 5)) {
                // Kiểm tra quân cản ở giữa đường đi
                int midX = (x + newX) / 2;
                int midY = (y + newY) / 2;
                Piece blocker = board.getPiece(midX, midY);
                return blocker == null; // Nước đi hợp lệ nếu không có quân cản ở giữa
            }
        }
        return false;
    }

    @Override
    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        // Các hướng di chuyển chéo 2 ô của Tượng
        int[][] possibleMoves = {
            {x + 2, y + 2}, {x + 2, y - 2}, {x - 2, y + 2}, {x - 2, y - 2}
        };

        // Kiểm tra các vị trí hợp lệ
        for (int[] move : possibleMoves) {
            int newX = move[0];
            int newY = move[1];

            // Kiểm tra xem nước đi có nằm trong giới hạn bàn cờ không
            if (newX >= 0 && newX < 10 && newY >= 0 && newY < 9) {
                // Đảm bảo không vượt qua sông
                if ((color.equals("White") && newX <= 4) || (color.equals("Black") && newX >= 5)) {
                    // Kiểm tra quân cản ở giữa đường đi
                    int midX = (x + newX) / 2;
                    int midY = (y + newY) / 2;
                    Piece blocker = board.getPiece(midX, midY);
                    if (blocker == null) { // Chỉ thêm vào validMoves nếu không có quân cản ở giữa
                        validMoves.add(new int[]{newX, newY});
                    }
                }
            }
        }

        return validMoves;
    }
}
