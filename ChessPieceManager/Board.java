package ChessPieceManager;

import ChessPiece.*;
import Major.Piece;
import java.util.ArrayList;
import java.util.List;

public class Board {

    private final Piece[][] board = new Piece[10][9];
     private final List<String> moveHistory = new ArrayList<>(); // Lưu trữ lịch sử các nước đi
    private boolean gameOver = false; // Kiểm tra trạng thái kết thúc trò chơi
    public Board() {
        initializeBoard();
    }

    private void initializeBoard() {
        // Các quân cờ được gán từ hàng đầu tiên đến hàng cuối cùng
    
        // Thêm các quân Tướng
        board[0][4] = new King(0, 4, "White");
        board[9][4] = new King(9, 4, "Yellow");
    
        // Thêm các quân Sĩ
        board[0][3] = new Advisor(0, 3, "White");
        board[0][5] = new Advisor(0, 5, "White");
        board[9][3] = new Advisor(9, 3, "Yellow");
        board[9][5] = new Advisor(9, 5, "Yellow");
    
        // Thêm các quân Tượng
        board[0][2] = new Elephant(0, 2, "White", this);
        board[0][6] = new Elephant(0, 6, "White", this);
        board[9][2] = new Elephant(9, 2, "Yellow", this);
        board[9][6] = new Elephant(9, 6, "Yellow", this);
    
        // Thêm các quân Mã
        board[0][1] = new Horse(0, 1, "White", this);
        board[0][7] = new Horse(0, 7, "White", this);
        board[9][1] = new Horse(9, 1, "Yellow", this);
        board[9][7] = new Horse(9, 7, "Yellow", this);
    
        // Thêm các quân Xe
        board[0][0] = new Chariot(0, 0, "White", this);
        board[0][8] = new Chariot(0, 8, "White", this);
        board[9][0] = new Chariot(9, 0, "Yellow", this);
        board[9][8] = new Chariot(9, 8, "Yellow", this);
    
        // Thêm các quân Pháo
        board[2][1] = new Cannon(2, 1, "White", this);
        board[2][7] = new Cannon(2, 7, "White", this);
        board[7][1] = new Cannon(7, 1, "Yellow", this);
        board[7][7] = new Cannon(7, 7, "Yellow", this);
    
        // Thêm các quân Tốt
        for (int j = 0; j < 9; j += 2) {
            board[3][j] = new Soldier(3, j, "White");
            board[6][j] = new Soldier(6, j, "Yellow");
        }
    }
    
     // Phương thức trả về quân Tướng dựa trên màu
     public King getKing(String color) {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 9; j++) {
                Piece piece = board[i][j];
                // Kiểm tra nếu quân cờ là Tướng và có màu đúng
                if (piece instanceof King && piece.getColor().equals(color)) {
                    return (King) piece;
                }
            }
        }
        return null; // Nếu không tìm thấy quân Tướng cho màu đó
    }
    public boolean isValidMove(int startX, int startY, int endX, int endY) {
        Piece piece = board[startX][startY];
        return piece != null && piece.isValidMove(endX, endY);
    }
    public boolean movePiece(int startX, int startY, int endX, int endY) {
        if (!isValidMove(startX, startY, endX, endY)) {
            System.out.println("Nuoc di khong hop le. Vui long thu lai.");
            return false;
        }
        board[endX][endY] = board[startX][startY]; // Di chuyển quân cờ
        board[startX][startY] = null; // Xóa quân cờ ở vị trí cũ
        board[endX][endY].move(endX, endY); // Di chuyển quân cờ (cập nhật vị trí mới)
    
        // Lưu nước đi vào lịch sử
        // Giả sử bạn đã khai báo biến turnCount để đếm lượt chơi
        int turnCount = 0;
        moveHistory.add(String.format("Luot choi thu %d: Quan co %s tu (%d, %d) den (%d, %d)", 
                              ++turnCount,
                              board[endX][endY].getSymbol(), 
                              startX + 1, startY + 1, endX + 1, endY + 1));
        return true;
    }
    
    
    
    public void printBoard() {
        System.out.println("Ban co:");
    
        // In chỉ số cột từ 1 đến 9
        System.out.print("   ");
        for (int j = 0; j < 9; j++) {
            System.out.print("  " + (j + 1) + "   ");
        }
        System.out.println();
    
        System.out.println("  ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐");
    
        for (int i = 0; i < 10; i++) {
            // In chỉ số hàng từ 1 đến 10
            System.out.print((i + 1) + (i < 9 ? " " : ""));  // Thêm khoảng trống sau chỉ số cho hàng từ 1 đến 9
    
            for (int j = 0; j < 9; j++) {
                if (board[i][j] == null) {
                    System.out.print("│     "); // Ô trống
                } else {
                    System.out.print("│  " + board[i][j].getSymbol() + "  "); // Quân cờ
                }
            }
            System.out.println("│");
    
            // Phân cách các hàng
            if (i == 4) {
                System.out.println("  └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘");
                System.out.println("  │                      Sông                           │");
                System.out.println("  ┌─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┬─────┐");

            } else if (i < 9) {
                System.out.println("  ├─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┼─────┤");
            }
        }
    
        System.out.println("  └─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┴─────┘");
    }
    
    
    
    public Piece getPiece(int x, int y) {
        if (x >= 0 && x < 10 && y >= 0 && y < 9) { // Kiểm tra nếu tọa độ hợp lệ
            return board[x][y]; // Trả về quân cờ tại vị trí (x, y)
        }
        return null; // Trả về null nếu vị trí không hợp lệ
    }
    
    
    // Phương thức hiển thị các nước đi hợp lệ của quân cờ
    public String showValidMoves(int x, int y) {
        Piece selectedPiece = getPiece(x, y);
    
        if (selectedPiece == null) {
            return "Khong co quan co nao tai vi tri (" + (x + 1) + ", " + (y + 1) + ").";
        }
    
        // Lấy danh sách các nước đi hợp lệ cho quân cờ
        StringBuilder moves = new StringBuilder("Cac nuoc di hop le cho " + selectedPiece.getSymbol() + " tai vi tri (" + (x + 1) + ", " + (y + 1) + "): ");
        boolean hasMoves = false;
    
        for (int i = 0; i < 10; i++) {  // Giới hạn lại phạm vi của bàn cờ (0-7)
            for (int j = 0; j < 9; j++) {
    
                // Kiểm tra điều kiện di chuyển hợp lệ đã được xử lý trong lớp quân cờ
                if (selectedPiece.isValidMove(i, j)) {
    
                    // Kiểm tra xem có quân cờ cùng màu trên ô đích, bỏ qua nếu có
                    Piece targetPiece = getPiece(i, j);
                    if (targetPiece != null && targetPiece.getColor().equals(selectedPiece.getColor())) {
                        continue;
                    }
    
                    // Nếu quân cờ có thể di chuyển tới ô này, in ra
                    moves.append("(").append(i + 1).append(", ").append(j + 1).append(") ");
                    hasMoves = true;
                }
            }
        }
    
        // Kiểm tra nếu không có nước đi hợp lệ nào
        if (!hasMoves) {
            moves.append("Khong co nuoc di nao hop le.");
        }
    
        return moves.toString();
    }
    
    // Hiển thị lịch sử các nước đi
    public void showMoveHistory() {
        if (moveHistory.isEmpty()) {
            System.out.println("Chua co lich su nao.");
            return;
        }
        System.out.println("Lich su cac nuoc di:");
        System.out.println();
        for (String move : moveHistory) {
            System.out.println(move);
        }
    }
    

}


