// Quân tốt
package ChessPiece;
import Major.Piece;
import java.util.ArrayList;
import java.util.List;

public class Soldier extends Piece {
    public Soldier(int x, int y, String color) {
        super(x, y, color);
    }

    @Override
    public boolean isValidMove(int newX, int newY) {
        if (color.equals("White")) {
            return (newX == x + 1 && newY == y) || (x > 4 && Math.abs(newY - y) == 1 && newX == x);
        } else {
            return (newX == x - 1 && newY == y) || (x < 5 && Math.abs(newY - y) == 1 && newX == x);
        }
    }

    @Override
    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        if (color.equals("White")) {
            // Di chuyển lên 1 ô
            if (x + 1 < 10) {
                validMoves.add(new int[]{x + 1, y});
            }
            // Nếu đã qua sông, có thể di chuyển sang ngang
            if (x > 4) {
                if (y - 1 >= 0) {
                    validMoves.add(new int[]{x, y - 1});
                }
                if (y + 1 < 9) {
                    validMoves.add(new int[]{x, y + 1});
                }
            }
        } else {
            // Di chuyển xuống 1 ô
            if (x - 1 >= 0) {
                validMoves.add(new int[]{x - 1, y});
            }
            // Nếu đã qua sông, có thể di chuyển sang ngang
            if (x < 5) {
                if (y - 1 >= 0) {
                    validMoves.add(new int[]{x, y - 1});
                }
                if (y + 1 < 9) {
                    validMoves.add(new int[]{x, y + 1});
                }
            }
        }
        return validMoves;
    }
}
