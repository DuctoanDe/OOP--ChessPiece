package Control;

import ChessPieceManager.Board;
import Major.Piece;
import java.util.Scanner;

public class Game {

    private final Board board;
    private String currentPlayer;
    private int turnCount = 0;

    public Game() {
        board = new Board();
        Scanner scanner = new Scanner(System.in);
        System.out.print("Current player (White/Black): ");
        currentPlayer = scanner.nextLine();
    }

    private void switchPlayer() {
        // Nếu current player là white thì chuyển lượt sang black, và ngược lại
        currentPlayer = currentPlayer.equals("White") ? "Black" : "White";
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  // Clear screen in terminals that support it (UNIX based)
        System.out.flush();
    }

    public void start() {
        Scanner scanner = new Scanner(System.in);
        int turnNumber = 0;
        while (turnNumber < 8) {
            clearScreen();
            System.out.println(String.format("Luot choi thu %d", ++turnNumber));
            System.out.println("Luot cua " + currentPlayer);
            board.printBoard(); // In ra bàn cờ
    
            int startX, startY, endX, endY;
            boolean validMove = false;
    
            // Vòng lặp để đảm bảo người chơi nhập đúng nước đi
            while (!validMove) {
                System.out.print("Chon quan co muon di (vi tri bat dau): ");
                startX = scanner.nextInt() - 1;  // Điều chỉnh về chỉ số 0
                startY = scanner.nextInt() - 1;
    
                // Kiểm tra quân cờ tại vị trí bắt đầu
                Piece selectedPiece = board.getPiece(startX, startY);
                if (selectedPiece == null || !selectedPiece.getColor().equals(currentPlayer)) {
                    System.out.println("Vi tri khong co quan co hoac khong hop le. Vui long thu lai.");
                    continue;
                }
    
                // In ra các bước đi hợp lệ của quân cờ đã chọn
                System.out.println(board.showValidMoves(startX, startY));
    
                System.out.print("Nhap vao vi tri dich ma ban: ");
                endX = scanner.nextInt() - 1;  // Điều chỉnh về chỉ số 0
                endY = scanner.nextInt() - 1;
    
                // Kiểm tra quân cờ tại vị trí đích
                Piece targetPiece = board.getPiece(endX, endY);
                if (targetPiece != null && targetPiece.getColor().equals(currentPlayer)) {
                    System.out.println("Vi tri dich da co quan co cua ban. Vui long thu lai.");
                    continue;
                }
    
                // Thực hiện di chuyển nếu hợp lệ
                if (board.movePiece(startX, startY, endX, endY)) {
                    validMove = true;
                    // In ra bước di chuyển sau khi hoàn thành
                    System.out.println("Quan co " + selectedPiece.getSymbol() + " da di chuyen tu (" + (startX + 1) + ", " + (startY + 1) + ") toi (" + (endX + 1) + ", " + (endY + 1) + ").");
                    switchPlayer(); // Chuyển lượt chỉ khi nước đi hợp lệ
                    turnCount++;
                } else {
                    System.out.println("Nuoc di khong hop le. Vui long thu lai.");
                }
            }
    
            board.printBoard(); // In lại bàn cờ sau mỗi lượt chơi
        }
        System.out.println();
        board.showMoveHistory();  // Hiển thị lịch sử các bước đi sau khi kết thúc trò chơi
        System.out.println("Tro choi ket thuc.");
        scanner.close();
    }
}
