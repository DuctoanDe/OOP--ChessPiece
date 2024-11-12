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
               (newX >= 0 && newX < 10 && newY >= 0 && newY < 9);
    }

    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();
    
        // Các nước đi có thể của quân Mã (hình chữ "L")
        int[][] possibleMoves = {
            {x + 2, y + 1}, {x + 2, y - 1}, {x - 2, y + 1}, {x - 2, y - 1}, // Di chuyển 2 ô theo trục x và 1 ô theo trục y
            {x + 1, y + 2}, {x + 1, y - 2}, {x - 1, y + 2}, {x - 1, y - 2}  // Di chuyển 1 ô theo trục x và 2 ô theo trục y
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
    
                // Xác định vị trí của quân cản tùy theo hướng di chuyển của quân Mã
                int blockerX = x, blockerY = y;
                if (Math.abs(newX - x) == 2 && Math.abs(newY - y) == 1) { // Di chuyển ngang 2, dọc 1
                    blockerX = x + (newX - x) / 2;
                } else if (Math.abs(newX - x) == 1 && Math.abs(newY - y) == 2) { // Di chuyển dọc 2, ngang 1
                    blockerY = y + (newY - y) / 2;
                }

                // Kiểm tra xem có quân cờ nào cản trở trên đường đi không
                Piece blockerPiece = board.getPiece(blockerX, blockerY);
                if (blockerPiece != null) {
                    continue; // Nếu có quân cờ cản trở ngay bên cạnh, không thêm vào validMoves
                }

                // Thêm vào validMoves nếu vị trí không bị chiếm hoặc cản trở
                validMoves.add(new int[]{newX, newY});
            }
        }
    
        return validMoves;
    }
}
