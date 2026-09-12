import java.util.Random;

public class a1 {
    public static String playRound(String playerMove, String computerMove) {
        if (playerMove.equals(computerMove)) {
            return "Draw";
        }
        if ((playerMove.equals("Rock") && computerMove.equals("Scissors")) ||
            (playerMove.equals("Paper") && computerMove.equals("Rock")) ||
            (playerMove.equals("Scissors") && computerMove.equals("Paper"))) {
            return "Player Wins";
        }
        return "Computer Wins";
    }

    public static void main(String[] args) {
        String[] options = {"Rock", "Paper", "Scissors"};
        Random random = new Random();
        int rounds = 5;
        int wins = 0;
        int losses = 0;
        int draws = 0;

        String[] playerMoves = {"Rock", "Paper", "Scissors", "Rock", "Paper"};
        
        System.out.println("Round | Player Move | Computer Move | Result");
        
        for (int i = 0; i < rounds; i++) {
            String pMove = playerMoves[i];
            String cMove = options[random.nextInt(3)];
            String result = playRound(pMove, cMove);
            
            if (result.equals("Player Wins")) {
                wins++;
            } else if (result.equals("Computer Wins")) {
                losses++;
            } else {
                draws++;
            }
            
            System.out.printf("Round %d | Player: %s | Computer: %s | %s\n", (i + 1), pMove, cMove, result);
        }
        
        double winPercentage = ((double) wins / rounds) * 100;
        System.out.printf("Wins: %d | Losses: %d | Draws: %d | Win %% = %.1f%%\n", wins, losses, draws, winPercentage);
    }
}