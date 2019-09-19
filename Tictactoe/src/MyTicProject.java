import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class MyTicProject{
		static Scanner theBoard = new Scanner(System.in);
		static String choice;
		static String myName;
		
		static int Board[][][] = new int[3][3][3];
		static boolean theWinner = false;
		static boolean theMove = false;
		static String winner = "";

		public static void main(String[] args)throws FileNotFoundException
			{

			{
				System.out.println("welcome to 3D tic-tac-toe");
				Scanner input = new Scanner(System.in);
				System.out.println("would you like to start? (y/n)");
				String theChoice = input.next();
				if (theChoice.contentEquals("y") || theChoice.equals("yes"))
					{
					
						Scanner readTheBoard = new Scanner(new FileInputStream(args[0]));
						int lines = readTheBoard.nextInt();
						
						for (int i = 0; i < lines; i++)
							{
								String move = "";
							
						for (int j = 0; j < lines; j++)
							{
								move += Integer.toString(readTheBoard.nextInt());
							}
						int player = readTheBoard.nextInt();
						
						if (player == 5)
							{
								makeMove(Integer.parseInt(move));
							}
						else
							{
								makeComputerGo(Integer.parseInt(move));
							}
						
					}
					showBoard();
					}
				while (theWinner == false)
					{
						showBoard();
						makeMove(movePeice());
						computerAI(0, 0, 0);
					}}
			}

		public static void announceWin()
			{
				if (winner == "player")
					{
						showBoard();
						System.out.println("You have won!");
						theWinner = true;
						System.exit(0);
					}
				else if (winner == "computer")
					{
						System.out.println("unfortunatly the computer has won");
						theWinner = true;
						System.exit(0);
					}
			}

		public static int movePeice()
			{
				Scanner input = new Scanner(System.in);
				System.out.println("input your move as a 3 digit number");
				int moveReader = input.nextInt();
				while (validMove(moveReader) == false)
					{
						System.out.println("enter it as a three digit number");
						moveReader = input.nextInt();
					}
				return moveReader;
			}

		public static void showBoard()
			{
				String theRow = " ";
				for (int i = 3; i >= 0; i++)
					{
						for (int j = 3; j >= 0; j++)
							{
								for (int f = 3; f >= 0; f++)
									{
										System.out.println(" ");
									}
								System.out.println(Integer.toString(i) + Integer.toString(j));
								for (int k = 0; k < 3; k++)
									{
										if (Board[i][j][k] == 0)
											{
												theRow += "_";
											}
										else if (Board[i][j][k] == 1)
											{
												theRow += "O";
											}
										else
											{
												theRow += "X";
											}
									}
								System.out.println(theRow);
								theRow = " ";
							}
						System.out.println();
					}
				System.out.println("      0 1 2 3");
			}

		public static void ifWin()
			{
				lookRC();
				lookDiagonals();
				lookVertical();
				lookStairs();
				lookCorners();

			}

		public static void theSum(int i)
			{
				if (i == 20)
					{
						winner = "Player";
						announceWin();

					}
				else if (i == 4)
					{
						winner = "computer";
						announceWin();

					}
				else
					{
						theWinner = false;
					}
			}

		private static void lookRC()
			{
				// TODO Auto-generated method stub
				int row = 0;
				int col = 0;
				for (int i = 0; i < 4; i++)
					{
						for (int j = 0; j < 4; j++)
							{
								for (int f = 0; f < 4; f++)
									{
										row += Board[i][j][f];
										col += Board[i][f][j];
									}
								theSum(row);
								theSum(col);
								row = 0;
								col = 0;
							}
					}

			}

		private static void lookDiagonals()
			{
				int sum = 0;
				int f = 0;
				for (int i = 0; i < 4; i++)
					{
						for (int j = 0; j < 4; j++)
							{
								sum += Board[i][j][j];

							}
						theSum(sum);
						sum = 0;
						for (int k = 3; k > -1; k--)
							{
								sum += Board[i][f][k];
								f++;

							}
						f = 0;
						theSum(sum);
						sum = 0;
					}
			}

		public static void lookVertical()
			{
				int sum = 0;
				for (int i = 0; i < 4; i++)
					{
						for (int j = 0; j < 4; j++)
							{
								for (int k = 0; k < 4; k++)
									{
										sum += Board[k][j][i];
									}
								theSum(sum);
								sum = 0;
							}
					}
			}

		public static void lookStairs()
			{
int sum = 0;
for (int i = 0; i < 4; i++) {
	for (int j = 0; j < 4; j++) {
		sum += Board[j][j][i];}
	
	theSum(sum);
	sum = 0;
for(int k =0; k<4;k++) {
	sum+= Board[k][i][k];
}
		theSum(sum);
		sum = 0;
		int p = 3;
			for(int k =0;k<4;k++) {
				sum += Board[p][i][k];
			p--;
			}
		p=3;
			theSum(sum);
		sum=0;
		for(int f = 0; f<4; f++) {
			sum += Board[p][f][i];
			p--;
		}}
		theSum(sum);
		sum = 0;
			
			
			}
		public static boolean validMove(int move)
			{ // Checks if move is valid
				int digitOne = move / 10 / 10 % 100;
				int digitTwo = move / 10 % 10;
				int digitThree = move % 10;
				if (digitOne > 2 || digitTwo > 2 || digitThree > 2)
					{
						return false;
					}
				if (Board[digitOne][digitTwo][digitThree] != 0)
					{
						return false;
					}
				else
					{
						return true;
					}
			}

		public static void makeMove(int move)
			{ // Moves Player to move space
				Board[move / 10 / 10 % 100][move / 10 % 10][move % 10] = 5;
				ifWin();
				int checker = 0;
				for (int i = 0; i <=3; i++)
					{
						for (int j = 0; j <=3; j++)
							{
								for (int k = 0; k <=3; k++)
									{
										checker += Board[i][j][k];

									}
							}
					}
				if (checker== 192)
					{
						showBoard();
						System.out.println("unfortunately its a draw");
						System.exit(0);
					}

				if (theWinner == true)
					{
						showBoard();
					}
			}

		private static void makeComputerGo(int move)
			{
Board[move/10/10%100][move/10%10][move%10]=1;
			announceWin();
			if(theWinner==true) {
				showBoard();
			}
			}

		private static void computerAI(int i, int j, int k)
			{
			if(theMove==false) {
				checkerAI(i,j,k);
			}else if(theMove==false) {
				forksAI(i,j,k);
			}else if(theMove==false) {
				moverAI(i,j,k);
			}

			}

		private static void moverAI(int i, int j, int k)
			{
				Random coord = new Random();
				int coordinateX = coord.nextInt(4);
				int coordinateY = coord.nextInt(4);
				int coordinateZ = coord.nextInt(4);
				boolean intellect = true;
						while(validMove(coordinateX * 100+ coordinateY *10 + coordinateZ)==false);
				 coordinateX= coord.nextInt(4);
				 coordinateY=coord.nextInt(4);
				 coordinateZ=coord.nextInt(4);
				 if(validMove(coordinateX*100+coordinateY*10+coordinateZ)) {
					 intellect=theChecker(coordinateX,coordinateY,coordinateZ);
				 }
				 if(intellect == false) {
					 
				 }
				 makeComputerGo(coordinateX*100+coordinateY*10+coordinateZ);
			theMove = true;
			}

		private static boolean theChecker(int coordinateX, int coordinateY, int coordinateZ)
			{
				// TODO Auto-generated method stub
				return false;
			}

		private static void forksAI(int i, int j, int k)
			{
				// TODO Auto-generated method stub
				
			}

		private static void checkerAI(int i, int j, int k)
			{
				// TODO Auto-generated method stub
				
			}

		public static void lookCorners()
			{
int sum = 0;
int sum1 = 0;
int sum2= 0;
int sum3 = 0;
	int f =3;
	for(int i = 0; i<=3;i++) {
		sum += Board[i][i][i];
		sum1+= Board[i][i][f];
		sum2+= Board[f][i][i];
		sum3+= Board[f][i][f];
	f--;
	}
	theSum(sum);
		theSum(sum1);
		theSum(sum2);
		theSum(sum3);
			}

	}
