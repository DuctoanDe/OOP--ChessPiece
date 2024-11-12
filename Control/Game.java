package Control;

import ChessPiece.King;
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
        System.out.print("Current player (White/Yellow): ");
        currentPlayer = scanner.nextLine();
    }

    private void switchPlayer() {
        currentPlayer = currentPlayer.equals("White") ? "Yellow" : "White";
    }

    public static void clearScreen() {
        System.out.print("\033[H\033[2J");  // Clear screen in terminals that support it (UNIX based)
        System.out.flush();
    }
    public void start() {
        Scanner scanner = new Scanner(System.in);
        int turnNumber = 0;
        while (turnNumber < 1000) {
            clearScreen();
            System.out.println(String.format("Luot choi thu %d", ++turnNumber));
            System.out.println("Luot cua " + currentPlayer);
            board.printBoard(); // In ra bàn cờ
    
            // Kiểm tra tình trạng chiếu khi bắt đầu lượt chơi của người chơi hiện tại
            King king = (King) board.getKing(currentPlayer);
            if (king != null) {
                if (king.isInCheck(board)) {
                    System.out.println("\u001B[31mDang bi chieu tuong !\u001B[0m");

                    
                    // Kiểm tra chiếu bí (checkmate)
                    if (king.isCheckmate(board)) {
                        System.out.println("Chieu bi! Nguoi choi " + currentPlayer + " thua.");
                        return;  // Kết thúc trò chơi nếu bị chiếu bí
                    }
                }
                else System.out.println("\u001B[31mHien khong co chieu tuong.\u001B[0m");
            }
           
    
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
                    turnCount++;
    
                    // Chuyển lượt sau khi kiểm tra chiếu
                    switchPlayer();
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