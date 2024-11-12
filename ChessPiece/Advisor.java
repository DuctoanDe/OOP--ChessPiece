package ChessPiece;
import Major.Piece;
import java.util.ArrayList;
import java.util.List;

public class Advisor extends Piece {
    public Advisor(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        // Di chuyển 1 ô chéo
        if (Math.abs(newX - x) == 1 && Math.abs(newY - y) == 1) {
            // Kiểm tra quân cờ màu trắng (trong cung của mình)
            if (color.equals("White")) {
                return newX >= 0 && newX <= 2 && newY >= 3 && newY <= 5;
            }
            // Kiểm tra quân cờ màu đen (trong cung của mình)
            if (color.equals("Black")) {
                return newX >= 7 && newX <= 9 && newY >= 3 && newY <= 5;
            }
        }
        return false;
    }

    @Override
    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        // Các hướng di chuyển của quân sĩ: chéo
        int[][] possibleMoves = {{-1, -1}, {-1, 1}, {1, -1}, {1, 1}};
        
        for (int[] move : possibleMoves) {
            int newX = this.x + move[0];
            int newY = this.y + move[1];

            // Kiểm tra các giới hạn của cung để đảm bảo nước đi hợp lệ
            if (color.equals("White") && newX >= 0 && newX <= 2 && newY >= 3 && newY <= 5) {
                validMoves.add(new int[]{newX, newY});
            } else if (color.equals("Black") && newX >= 7 && newX <= 9 && newY >= 3 && newY <= 5) {
                validMoves.add(new int[]{newX, newY});
            }
        }

        return validMoves;
    }
}
