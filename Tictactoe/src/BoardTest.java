import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class BoardTest{
static boolean win = false;
static boolean moved = false;
static int board[][][] = new int[4][4][4];
static String winner = "";
static int oneC = 0;
static int twoC = 0;
static int threeC = 0;

public static void main(String[] args) throws FileNotFoundException { // Main
	Scanner reader = new Scanner(System.in);
	System.out.println("Do you want to read a previous board state? (y/n): ");
	String yesNo = reader.next();
	if (yesNo.equals("y") || yesNo.equals("yes")) { // Reads files int lines
		Scanner boardRead = new Scanner(new FileInputStream(args[0]));
		int lines = boardRead.nextInt();
		for (int i = 0; i < lines; i++) {
			String move = "";
			for (int j = 0; j < 3; j++) {
				move += Integer.toString(boardRead.nextInt());
			}
			int player = boardRead.nextInt();
			if (player == 5) {
				makeMove(Integer.parseInt(move));
			} else {
				makeComputerMove(Integer.parseInt(move));
			}
		}
		printboard();
	}

	while (win == false) { // While game is not over, continues
		printboard();
		makeMove(readMove());
		computerAI(0, 0, 0);
	}
}

public static void winAnnounce() { // Announces who wins
	if (winner == "Player") {
		printboard();
		System.out.println("Player has won!");
		win = true;
		System.exit(0);
	} else if (winner == "Computer") {
		printboard();
		System.out.println("Computer has won!");
		win = true;
		System.exit(0);
	}
}

public static int readMove() {
	Scanner reader = new Scanner(System.in);
	System.out.println("Type your move as a three digit number: ");
	int moveRead = reader.nextInt();
	while (validMove(moveRead) == false) {
		System.out.println("Type your move as a three digit number: ");
		moveRead = reader.nextInt();
	}
	return moveRead;
}

public static void printboard() {
	String currentRow = "  ";
	for (int i = 3; i >= 0; i--) {
		for (int j = 3; j >= 0; j--) {
			for (int k = 0; k <= j; k++) {
				System.out.print(" ");
			}
			System.out.print(Integer.toString(i) + Integer.toString(j));
			for (int q = 0; q < 4; q++) {
				if (board[i][j][q] == 0) { // Blank spaces are marked by 0
					currentRow += "_ ";
				} else if (board[i][j][q] == 1) { // Computer plays as O
					currentRow += "O ";
				} else { // Player plays as X
					currentRow += "X ";
				}
			}
			System.out.println(currentRow);
			currentRow = "  ";
		}
		System.out.println();
	}
	System.out.println("     0 1 2 3");
}

public static void checkWin() { // Check if anyone wins
	checkRC();
	checkDiagonals();
	checkVert();
	checkStairs();
	checkCorners();
}

public static void checkSum(int i) { // Checks sum of 4 squares
	if (i == 20) {
		winner = "Player";
		winAnnounce();
	} else if (i == 4) {
		winner = "Computer";
		winAnnounce();
	} else {
		win = false;
	}
}

public static void checkRC() { // Checks 2D Rows and Columns
	int rowSum = 0;
	int colSum = 0;
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				rowSum += board[i][j][k];
				colSum += board[i][k][j];
			}
			checkSum(rowSum);
			checkSum(colSum);
			rowSum = 0;
			colSum = 0;
		}
	}
}

public static void checkDiagonals() { // Checks 2D Diagonals
	int diaSum = 0;
	int k = 0;
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			diaSum += board[i][j][j];
		}
		checkSum(diaSum);
		diaSum = 0;
		for (int q = 3; q > -1; q--) {
			diaSum += board[i][k][q];
			k++;
		}
		k = 0;
		checkSum(diaSum);
		diaSum = 0;
	}
}

public static void checkVert() { // Checks Up/Down Wins
	int vertSum = 0;
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			for (int k = 0; k < 4; k++) {
				vertSum += board[k][j][i];
			}
			checkSum(vertSum);
			vertSum = 0;
		}
	}
}

public static void checkStairs() { // Checks Stairs/3D Diagonals
	int diaSum = 0;
	for (int i = 0; i < 4; i++) {
		for (int j = 0; j < 4; j++) {
			diaSum += board[j][j][i];
		}
		checkSum(diaSum);
		diaSum = 0;
		for (int q = 0; q < 4; q++) {
			diaSum += board[q][i][q];
		}
		checkSum(diaSum);
		diaSum = 0;
		int p = 3;
		for (int k = 0; k < 4; k++) {
			diaSum += board[p][i][k];
			p--;
		}
		p = 3;
		checkSum(diaSum);
		diaSum = 0;
		for (int d = 0; d < 4; d++) {
			diaSum += board[p][d][i];
			p--;
		}
		checkSum(diaSum);
		diaSum = 0;
	}
}

public static void checkCorners() { // Checks Corners of cube connections
	int corSum = 0;
	int corSumTwo = 0;
	int corSumThree = 0;
	int corSumFour = 0;
	int k = 3;
	for (int i = 0; i < 4; i++) {
		corSum += board[i][i][i];
		corSumTwo += board[i][i][k];
		corSumThree += board[k][i][i];
		corSumFour += board[k][i][k];
		k--;
	}
	checkSum(corSum);
	checkSum(corSumTwo);
	checkSum(corSumThree);
	checkSum(corSumFour);
}

public static boolean validMove(int move) { // Checks if move is valid
	int digitOne = move / 10 / 10 % 100;
	int digitTwo = move / 10 % 10;
	int digitThree = move % 10;
	if (digitOne > 3 || digitTwo > 3 || digitThree > 3) {
		return false;
	}
	if (board[digitOne][digitTwo][digitThree] != 0) {
		return false;
	} else {
		return true;
	}
}

public static void makeMove(int move) { // Moves Player to move space
	board[move / 10 / 10 % 100][move / 10 % 10][move % 10] = 5;
	checkWin();
	int deadChecker = 0;
	for (int i = 0; i < 4; i++){
		for (int j = 0; j < 4; j++){
			for (int k = 0; k < 4; k++){
				deadChecker += board[i][j][k];
			}
		}
	}
	if (deadChecker == 192){
		printboard();
		System.out.println("Good game, It's a draw!");
		System.exit(0);
	}
	if (win == true) {
		printboard();
	}
}

public static void makeComputerMove(int move) { // Moves Computer to space
	board[move / 10 / 10 % 100][move / 10 % 10][move % 10] = 1;
	checkWin();
	if (win == true)
		printboard();
}

public static void computerAI(int x, int y, int z) {
	if (moved == false) {
		completer(x, y, z);
	}
	if (moved == false) {
		forks(x, y, z);
	}
	if (moved == false) {
		deadMover();
	}
	moved = false;
}

public static void deadMover() {
	// Selects a random non-dead spot to place a move
	Random rand = new Random();
	int xCord = rand.nextInt(4);
	int yCord = rand.nextInt(4);
	int zCord = rand.nextInt(4);
	boolean dead = true;
	while (validMove(xCord * 100 + yCord * 10 + zCord) == false) {
		xCord = rand.nextInt(4);
		yCord = rand.nextInt(4);
		zCord = rand.nextInt(4);
		if (validMove(xCord * 100 + yCord * 10 + zCord)) {
			dead = deadCheck(xCord, yCord, zCord);
		}
		if (dead == false) {
			break;
		}
	}
	makeComputerMove(xCord * 100 + yCord * 10 + zCord);
	moved = true;
}

public static void completer(int x, int y, int z) {
	// If there is a possible win, go for it
	// If there is a possible loss, block it
	int one = 0, two = 0, three = 0, four = 0;
	int five = 0, six = 0, seven = 0, eight = 0;
	int nine = 0, ten = 0, eleven = 0, twelve = 0;
	int thirteen = 0;
	for (x = 3; x >= 0; x--) {
		for (y = 3; y >= 0; y--) {
			for (z = 3; y >= 0; z--) {
				one = two = three = four = 0;
				five = six = seven = eight = 0;
				nine = ten = eleven = twelve = 0;
				thirteen = 0;

				for (int a = 3; a >= 0; a--) {
					one += board[a][y][z];
					two += board[x][a][z];
					three += board[x][y][a];
					int b = Math.abs(3 - a);
					if ((x == y) && (z == (3 - x))) {
						four += board[a][a][b];
					} else
						four = 6;
					if ((y == z) && (x == (3 - y))) {
						five += board[b][a][a];
					} else
						five = 6;
					if ((x == z) && (y == (3 - x))) {
						six += board[a][b][a];
					} else
						six = 6;
					if ((x == y) && (x == z)) {
						seven += board[a][a][a];
					} else
						seven = 6;
					if (y == z) {
						eight += board[x][a][a];
					} else
						eight = 6;
					if (y + z == 3) {
						nine += board[x][a][b];
					} else
						nine = 6;
					if (x == z) {
						ten += board[a][y][a];
					} else {
						ten = 6;
					}
					if (x + z == 3) {
						eleven += board[a][y][b];
					} else
						eleven = 6;
					if (x == y) {
						twelve += board[a][a][z];
					} else {
						twelve = 6;
					}
					if (x + y == 3)
						thirteen += board[a][b][z];
					else
						thirteen = 6;
					if (a == 0)
						break;
				}
				if ((one == 3 || one == 15 || two == 3 || two == 15 || three == 3 || three == 15 || four == 3
						|| four == 15 || five == 3 || five == 15 || six == 3 || six == 15 || seven == 3
						|| seven == 15 || eight == 3 || eight == 15 || nine == 3 || nine == 15 || ten == 3
						|| ten == 15 || eleven == 3 || eleven == 15 || twelve == 3 || twelve == 15 || thirteen == 3
						|| thirteen == 15) && board[x][y][z] == 0)
					break;
				if (z == 0)
					break;
			}
			if ((one == 3 || one == 15 || two == 3 || two == 15 || three == 3 || three == 15 || four == 3
					|| four == 15 || five == 3 || five == 15 || six == 3 || six == 15 || seven == 3 || seven == 15
					|| eight == 3 || eight == 15 || nine == 3 || nine == 15 || ten == 3 || ten == 15 || eleven == 3
					|| eleven == 15 || twelve == 3 || twelve == 15 || thirteen == 3 || thirteen == 15)
					&& board[x][y][z] == 0)
				break;
			if (y == 0)
				break;
		}

		if ((one == 3 || one == 15 || two == 3 || two == 15 || three == 3 || three == 15 || four == 3 || four == 15
				|| five == 3 || five == 15 || six == 3 || six == 15 || seven == 3 || seven == 15 || eight == 3
				|| eight == 15 || nine == 3 || nine == 15 || ten == 3 || ten == 15 || eleven == 3 || eleven == 15
				|| twelve == 3 || twelve == 15 || thirteen == 3 || thirteen == 15) && board[x][y][z] == 0)
			break;
		if (x == 0)
			break;
	}
	if ((one == 3 || one == 15 || two == 3 || two == 15 || three == 3 || three == 15 || four == 3 || four == 15
			|| five == 3 || five == 15 || six == 3 || six == 15 || seven == 3 || seven == 15 || eight == 3
			|| eight == 15 || nine == 3 || nine == 15 || ten == 3 || ten == 15 || eleven == 3 || eleven == 15
			|| twelve == 3 || twelve == 15 || thirteen == 3 || thirteen == 15) && board[x][y][z] == 0) {
		makeComputerMove(x * 100 + y * 10 + z);
		moved = true;
	}
}

public static boolean deadCheck(int x, int y, int z) {
	// Checks if there are any live lines and plays on them
	int one = 0, two = 0, three = 0, four = 0;
	int five = 0, six = 0, seven = 0, eight = 0;
	int nine = 0, ten = 0, eleven = 0, twelve = 0;
	int thirteen = 0;
	for (int a = 3; a >= 0; a--) {
		one += board[a][y][z];
		two += board[x][a][z];
		three += board[x][y][a];
		int b = Math.abs(3 - a);
		if ((x == y) && (z == (3 - x))) {
			four += board[a][a][b];
		} else
			four = 6;
		if ((y == z) && (x == (3 - y))) {
			five += board[b][a][a];
		} else
			five = 6;
		if ((x == z) && (y == (3 - x))) {
			six += board[a][b][a];
		} else
			six = 6;
		if ((x == y) && (x == z)) {
			seven += board[a][a][a];
		} else
			seven = 6;
		if (y == z) {
			eight += board[x][a][a];
		} else
			eight = 6;
		if (y + z == 3) {
			nine += board[x][a][b];
		} else
			nine = 6;
		if (x == z) {
			ten += board[a][y][a];
		} else {
			ten = 6;
		}
		if (x + z == 3) {
			eleven += board[a][y][b];
		} else
			eleven = 6;
		if (x == y) {
			twelve += board[a][a][z];
		} else {
			twelve = 6;
		}
		if (x + y == 3)
			thirteen += board[a][b][z];
		else
			thirteen = 6;
		if (a == 0)
			break;
	}
	if (one > 5 && two > 5 && three > 5 && four > 5 && five > 5 && six > 5 && seven > 5 && eight > 5 && nine > 5
			&& ten > 5 && eleven > 5 && twelve > 5 && thirteen > 5) {
		System.out.println("There are no good moves, let's draw");
		System.exit(0);
	}
	if (one < 5 || two < 5 || three < 5 || four < 5 || five < 5 || six < 5 || seven < 5 || eight < 5 || nine < 5
			|| ten < 5 || eleven < 5 || twelve < 5 || thirteen < 5) {
		return true;
	} else {
		return false;
	}
}

public static void forks(int x, int y, int z) {
	// If there's a fork, play it
	// If there will be a fork for the user, disrupt it
	int one = 0, two = 0, three = 0, four = 0;
	int five = 0, six = 0, seven = 0, eight = 0;
	int nine = 0, ten = 0, eleven = 0, twelve = 0;
	int thirteen = 0;
	for (x = 3; x >= 0; x--) {
		for (y = 3; y >= 0; y--) {
			for (z = 3; y >= 0; z--) {
				one = two = three = four = 0;
				five = six = seven = eight = 0;
				nine = ten = eleven = twelve = 0;
				thirteen = 0;

				for (int a = 3; a >= 0; a--) {
					one += board[a][y][z];
					two += board[x][a][z];
					three += board[x][y][a];
					int b = Math.abs(3 - a);
					if ((x == y) && (z == (3 - x))) {
						four += board[a][a][b];
					}
					if ((y == z) && (x == (3 - y))) {
						five += board[b][a][a];
					}
					if ((x == z) && (y == (3 - x))) {
						six += board[a][b][a];
					}
					if ((x == y) && (x == z)) {
						seven += board[a][a][a];
					}
					if (y == z) {
						eight += board[x][a][a];
					}
					if (y + z == 3) {
						nine += board[x][a][b];
					}
					if (x == z) {
						ten += board[a][y][a];
					}
					if (x + z == 3) {
						eleven += board[a][y][b];
					}
					if (x == y) {
						twelve += board[a][a][z];
					}
					if (x + y == 3)
						thirteen += board[a][b][z];
					if (a == 0)
						break;
				}
				if ((one == 2 && (two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
					|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
					break;
				if ((one == 10 && (two == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
					|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
					break;
				if ((two == 2 && (one == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
					|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
					break;
				if ((two == 10 && (one == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
					|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
					break;
				if ((three == 2 && (two == 2 || one == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
					|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
					break;
				if ((three == 10 && (two == 10 || one == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
					|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
					break;
				if (z == 0)
					break;
			}
			if ((one == 2 && (two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
				|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
				break;
			if ((one == 10 && (two == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
				|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
				break;
			if ((two == 2 && (one == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
				|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
				break;
			if ((two == 10 && (one == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
				|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
				break;
			if ((three == 2 && (two == 2 || one == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
				|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
				break;
			if ((three == 10 && (two == 10 || one == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
				|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
				break;
			if (y == 0)
				break;
		}
		if ((one == 2 && (two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
				|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
				break;
			if ((one == 10 && (two == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
				|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
				break;
			if ((two == 2 && (one == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
				|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
				break;
			if ((two == 10 && (one == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
				|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
				break;
			if ((three == 2 && (two == 2 || one == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
				|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0)
				break;
			if ((three == 10 && (two == 10 || one == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
				|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0)
				break;
		if (x == 0)
			break;
	}
	if ((one == 2 && (two == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
		|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0){
		makeComputerMove(x* 100 + y * 10 + z);
		moved = true;
	}
	if ((one == 10 && (two == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
		|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0){
		makeComputerMove(x* 100 + y * 10 + z);			
		moved = true;
	}
	if ((two == 2 && (one == 2 || three == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
		|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0){
		makeComputerMove(x* 100 + y * 10 + z);
		moved = true;
	}
	if ((two == 10 && (one == 10 || three == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
		|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0){
		makeComputerMove(x* 100 + y * 10 + z);
		moved = true;
	}
	if ((three == 2 && (two == 2 || one == 2 || four == 2 || five == 2 || six == 2 || seven == 2 || eight == 2
		|| nine == 2 || ten == 2 || eleven == 2 || twelve == 2 || thirteen == 2)) && board[x][y][z] == 0){
		makeComputerMove(x* 100 + y * 10 + z);
		moved = true;
	}
	if ((three == 10 && (two == 10 || one == 10 || four == 10 || five == 10 || six == 10 || seven == 10 || eight == 10
		|| nine == 10 || ten == 10 || eleven == 10 || twelve == 10 || thirteen == 10)) && board[x][y][z] == 0){
		makeComputerMove(x* 100 + y * 10 + z);
		moved = true;
	}
}


{
	
			
			
			
			
			
			}

	}
