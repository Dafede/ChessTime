package es.wander.chesstime;

public class UserSession {

	static String User = "";
	static String Password = "";

	static String usersChallengedActual = "";

	static String game1User1 = "";
	static String game1User2 = "";
	static String game2User1 = "";
	static String game2User2 = "";
	static String game3User1 = "";
	static String game3User2 = "";
	static boolean turnPlayer=false;

	int chessPositionSave[][] = new int[8][8];	

	public void setChess(int chess[][]) {
		System.out.println("SAVING"+chessPositionSave[0][0]);
		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				chessPositionSave[i][j] = chess[i][j];
			}
		}
	}

	public int[][] getChess() {
		return chessPositionSave;
	}
}
