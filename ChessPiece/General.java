//Quân tướng
package ChessPiece;
import Major.Piece;
import ChessPieceManager.Board;
import java.util.ArrayList;
import java.util.List;

public class General extends Piece {
    public General(int x, int y, String color) {
        super(x, y, color);
    }
    @Override
    public boolean isValidMove(int newX, int newY) {
        // Tướng chỉ di chuyển 1 ô trong phạm vi hình vuông
        return Math.abs(newX - x) + Math.abs(newY - y) == 1 &&
               ((color.equals("White") && newX >= 1 && newX <= 3 && newY >= 4 && newY <= 6) ||
                (color.equals("Black") && newX >= 8 && newX <= 10 && newY >= 4 && newY <= 6));
    }
    
    @Override
    public List<int[]> getValidMoves() {
        List<int[]> validMoves = new ArrayList<>();

        // Quy tắc di chuyển của Tướng, chỉ di chuyển trong cung và di chuyển 1 ô
        int[][] possibleMoves = {{-1, 0}, {1, 0}, {0, -1}, {0, 1}};
        for (int[] move : possibleMoves) {
            int newX = this.x + move[0];
            int newY = this.y + move[1];

            // Kiểm tra các giới hạn của cung để đảm bảo nước đi hợp lệ
            if ((newX >= 7 && newX <= 9 && newY >= 3 && newY <= 5) ||  // Vùng của Tướng bên đen
                (newX >= 0 && newX <= 2 && newY >= 3 && newY <= 5)) {  // Vùng của Tướng bên trắng
                validMoves.add(new int[]{newX, newY});
            }
        }
        return validMoves;
    }
         // Hàm kiểm tra chiếu tướng
         public boolean isCheckmate( Board board ) {
            // Kiểm tra xem bên có quân Tướng màu 'color' có bị chiếu bí không.
            // Ở đây, bạn cần xây dựng logic kiểm tra chiếu bí.
            // Giả sử chúng ta kiểm tra nếu Tướng của bên color có thể bị tấn công
            // và không còn cách nào để di chuyển ra ngoài phạm vi chiếu.
    
            int kingX = -1, kingY = -1;
            // Tìm vị trí của quân Tướng
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    Piece piece = board.getPiece(i, j);
                    if (piece instanceof General && piece.getColor().equals(color)) {
                        kingX = i;
                        kingY = j;
                        break;
                    }
                }
                if (kingX != -1) break;
            }
                // Kiểm tra xem Tướng có đang bị chiếu không
                if (!isUnderAttack(kingX, kingY, board, color)) {
                    return false;
                }
        
                // Kiểm tra tất cả các nước đi hợp lệ của Tướng
                for (int[] move : getValidMoves()) {
                    int newX = kingX + move[0];
                    int newY = kingY + move[1];
        
                    // Kiểm tra nếu nước đi thoát khỏi chiếu
                    if (isValidMove(newX, newY) && !isUnderAttack(newX, newY, board, color)) {
                        return false;
                    }
                }
        
                // Không có nước đi nào thoát khỏi chiếu, vậy là chiếu bí
                return true;
            }
            // Hàm kiểm tra nếu một ô bị quân đối phương tấn công
        private boolean isUnderAttack(int x, int y, Board board, String color) {
            for (int i = 0; i < 10; i++) {
                for (int j = 0; j < 9; j++) {
                    Piece piece = board.getPiece(i, j);
                    if (piece != null && !piece.getColor().equals(color)) {
                        if (piece.isValidMove(x, y)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

}

