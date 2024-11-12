// Lớp Piece: Đại diện cho 1 quân cờ
package Major;

import java.util.List;

public abstract class Piece {

    protected int x, y; // Vị trí trên bàn cờ
    protected String color; // Màu quân cờ: "White" hoặc "Black"

    public Piece(int x, int y, String color) {
        this.x = x;
        this.y = y;
        this.color = color;
    }

    // Kiểm tra nước đi có hợp lệ
    public abstract boolean isValidMove(int newX, int newY);
    public abstract List<int[]> getValidMoves(); // Phương thức trừu tượng bắt buộc lớp con cài đặt
    // Phương thức cho phép di chuyển tới vị trí mới của quân cờ
    public void move(int newX, int newY) {
        this.x = newX;
        this.y = newY;
    }

    // Trả về màu của quân cờ
    public String getColor() {
        return color;
    }

    public String getSymbol() {
        String symbol = this.getClass().getSimpleName().substring(0, 1);
        String coloredSymbol;
    
        // Sử dụng mã màu dựa trên màu quân cờ
        if (color.equals("White")) {
            coloredSymbol = "\u001B[37m" + symbol + "\u001B[0m"; // Màu trắng cho quân trắng

        } else {
            coloredSymbol = "\u001B[33m" + symbol + "\u001B[0m"; // Màu trắng cho quân đen


        }
    
        return coloredSymbol; 
    }
    
}
