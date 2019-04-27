import java.awt.GridLayout;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.sql.Time;
import java.util.Arrays;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import java.util.concurrent.TimeUnit;

public class Foothill {
	
	static ImageIcon redIcon = new ImageIcon(Foothill.class.getResource("/resources/RedCircle.png"));
	static ImageIcon blackIcon = new ImageIcon(Foothill.class.getResource("/resources/BlackCircle.png"));
	static ImageIcon blank = new ImageIcon(Foothill.class.getResource("/resources/Blank.png"));
	static ImageIcon winnerPng = new ImageIcon(Foothill.class.getResource("/resources/Winner.png"));
	static ImageIcon loserPng = new ImageIcon(Foothill.class.getResource("/resources/Loser.png"));

	
	static boolean turn = true;
	

	public static void main(String[] args) throws IOException, InterruptedException {

		//Scanner input = new Scanner(System.in);
		ConnectFour game = new ConnectFour();

		// PvP
		//PvP(input, game);
		
		// PvE v1
		//random(input, game);
		
		// PvE v2
		//randomSmart(input, game);
		
		// PvE v3
		//Smart(input, game);
		
		//EvE v4
		//Eve();
		//Eve2();
		
		// PvP GUI
		//PvpGui(game);
		
		// PvE GUI
		PvEGui(game);
		
		return;
		

	}
	
	public static void PvEGui(ConnectFour game) throws IOException, InterruptedException {
		
		
		
		JFrame window = new JFrame("Connect 4 with AI - Timothy Chandler");
		window.setSize(550, 650);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
//		JFrame winnerWindow = new JFrame("Connect 4 with AI - Timothy Chandler");
//		winnerWindow.setSize(270, 100);
//		winnerWindow.setLocationRelativeTo(null);
//		winnerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		JFrame loserWindow = new JFrame("Connect 4 with AI - Timothy Chandler");
//		loserWindow.setSize(270, 100);
//		loserWindow.setLocationRelativeTo(null);
//		loserWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		
//		JPanel panelWinner = new JPanel(new GridLayout(0,1,0,40));
//		JPanel panelLoser = new JPanel(new GridLayout(0,1,0,40));
//
//		BufferedImage winnerImage = ImageIO.read(new File("C:\\Users\\Tim\\Google Drive\\Workspace\\AI_Connect4\\src\\Winner.png"));
//		BufferedImage loserImage = ImageIO.read(new File("C:\\Users\\Tim\\Google Drive\\Workspace\\AI_Connect4\\src\\Loser.png"));
//
//		winnerWindow.setContentPane(panelWinner);
//		loserWindow.setContentPane(panelLoser);
//		
//		JLabel winnerLabel = new JLabel(new ImageIcon(winnerImage));
//		JLabel loserLabel = new JLabel(new ImageIcon(loserImage));
//		
//		panelWinner.add(winnerLabel);
//		panelLoser.add(loserLabel);
//		
//		winnerWindow.setVisible(true);
//		loserWindow.setVisible(true);
		
		JPanel panel = new JPanel(new GridLayout(7,7,0,40));
		
		window.setContentPane(panel);

		//BufferedImage blank = ImageIO.read(new File("resources/Blank.png"));
		
		JLabel[] labelBlank = new JLabel[42];
		
		for (int i = 0; i < 42; i++) {
			labelBlank[i] = new JLabel(blank);
			panel.add(labelBlank[i]);
		}
		
		JButton[] button = new JButton[7];
		
		for (int i = 0; i < button.length; i++) {
			button[i] = new JButton(String.valueOf(i+1));
			panel.add(button[i]);
		}
		
		for (int i = 0; i < button.length; i++) {
			selectButtonPVE(button[i], labelBlank, i, game.getValidPlays(), game);

		}
		
		window.setVisible(true);	
		
		
	}
	
	public static void windowFinished(int winner) throws IOException {
		
		
		if (winner == 0)
			return;
		
		JFrame winnerWindow = new JFrame("Connect 4 with AI - Timothy Chandler");
		winnerWindow.setSize(270, 100);
		winnerWindow.setLocationRelativeTo(null);
		winnerWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JFrame loserWindow = new JFrame("Connect 4 with AI - Timothy Chandler");
		loserWindow.setSize(270, 100);
		loserWindow.setLocationRelativeTo(null);
		loserWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
		JPanel panelWinner = new JPanel(new GridLayout(0,1,0,40));
		JPanel panelLoser = new JPanel(new GridLayout(0,1,0,40));

		//BufferedImage winnerImage = ImageIO.read(new File("resources/Winner.png"));
		//BufferedImage loserImage = ImageIO.read(new File("resources/Loser.png"));

		winnerWindow.setContentPane(panelWinner);
		loserWindow.setContentPane(panelLoser);
		
		JLabel winnerLabel = new JLabel(winnerPng);
		JLabel loserLabel = new JLabel(loserPng);
		
		panelWinner.add(winnerLabel);
		panelLoser.add(loserLabel);
		
		if (winner == 1) {
		winnerWindow.setVisible(true);
		return;
		} else if (winner == 2) {
		loserWindow.setVisible(true);
		return;
		}
		
		
		
	}
	
	public static void selectButtonPVE(JButton button, JLabel[] label, int i, int[] multiplier, ConnectFour game) {
		
		
		button.addMouseListener(new MouseAdapter() {
	
			@Override
	        public void mouseClicked(MouseEvent e) {
				
				if(turn) {
					label[((multiplier[i] -1 ) * 7) + i ].setIcon(redIcon);
					multiplier[i] = multiplier[i] - 1;
					game.select(ConnectFour.PLAYER_1, i+1);
					
					
						try {
							windowFinished(AI.whoWins(game.getGame()));
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
					

						
					
					ConnectFour.displayG(game.getGame());

					
					if (!game.checkWin()) {
						
						int i = AI.makeChoiceSmart(game.getGame(), game.getValidPlays());
						label[((multiplier[i - 1] - 1) * 7) + (i - 1)].setIcon(blackIcon);
						multiplier[i - 1] = multiplier[i - 1] - 1;
						game.select(ConnectFour.PLAYER_2, i);

						//ConnectFour.displayG(game.getGame());

						
						try {
							windowFinished(AI.whoWins(game.getGame()));
						} catch (IOException e2) {
							// TODO Auto-generated catch block
							e2.printStackTrace();
						}
						
					}
					
				} 
					
					
					
				
	        }
			
		});
	}
	
	public static void PvpGui(ConnectFour game) throws IOException {
		
		JFrame window = new JFrame("Number of Chars");
		window.setSize(550, 650);
		window.setLocationRelativeTo(null);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel panel = new JPanel(new GridLayout(7,7,0,40));
		
		window.setContentPane(panel);

		//BufferedImage blank = ImageIO.read(new File("C:\\Users\\Tim\\Google Drive\\Workspace\\AI_Connect4\\src\\Blank.png"));
		
		JLabel[] labelBlank = new JLabel[42];
		
		for (int i = 0; i < 42; i++) {
			labelBlank[i] = new JLabel(blank);
			panel.add(labelBlank[i]);
		}
		
		JButton[] button = new JButton[7];
		
		for (int i = 0; i < button.length; i++) {
			button[i] = new JButton(String.valueOf(i+1));
			panel.add(button[i]);
		}
		
		for (int i = 0; i < button.length; i++) {
			selectButtonPVP(button[i], labelBlank, i, game.getValidPlays());
		}
		
		window.setVisible(true);
		
		
		
		
		
		
		
	}
	
	public static void selectButtonPVP(JButton button, JLabel[] label, int i, int[] multiplier) {
		
		
		button.addMouseListener(new MouseAdapter() {
	
			@Override
	        public void mouseClicked(MouseEvent e) {
				
				if(turn) {
					label[((multiplier[i] -1 ) * 7) + i ].setIcon(redIcon);
					turn = false;
				} else {
					label[((multiplier[i] -1 ) * 7) + i ].setIcon(blackIcon);
					turn = true;
				}
					
					
					
		            multiplier[i] = multiplier[i] - 1;
				
	        }
			
		});
	}
	
	public static void PvP(Scanner input, ConnectFour game) {
		
		while (true) {
			
			int userInput;
			
			game.display();
			System.out.println("Player 1's turn:");
			userInput = input.nextInt();
			game.select(ConnectFour.PLAYER_1, userInput);
			game.changeValid(userInput);

			if (game.checkWin()) {
				game.display();
				return;
			}

			// P2
			game.display();
			System.out.println("Player 2's turn:");
			userInput = input.nextInt();
			game.select(ConnectFour.PLAYER_2, userInput);
			game.changeValid(userInput);

			if (game.checkWin()) {
				game.display();
				return;
			}
		}
	}
	
	public static void randomSmart(Scanner input, ConnectFour game) {

		while (true) {
			
			int userInput;

			game.display();
			System.out.println("Player 1's turn:");
			userInput = input.nextInt();
			game.select(ConnectFour.PLAYER_1, userInput);

			if (game.checkWin()) {
				game.display();
				return;
			}

			// P2
			game.display();

			// userInput = AI.makeChoiceRandom(game.getGame());
			userInput = AI.makeChoiceSmartRandom(game.getGame());
			System.out.println("AI's turn: " + userInput);
			game.select(ConnectFour.PLAYER_2, userInput);

			if (game.checkWin()) {
				game.display();
				return;
			}
		}
	}
	
	public static void random(Scanner input, ConnectFour game) {

		while (true) {
			
			int userInput;

			game.display();
			System.out.println("Player 1's turn:");
			userInput = input.nextInt();
			game.select(ConnectFour.PLAYER_1, userInput);
			game.changeValid(userInput);

			if (game.checkWin()) {
				game.display();
				return;
			}

			// P2
			game.display();

			// userInput = AI.makeChoiceRandom(game.getGame());
			userInput = AI.makeChoiceRandom(game.getGame());
			System.out.println("AI's turn: " + userInput);
			game.select(ConnectFour.PLAYER_2, userInput);
			game.changeValid(userInput);
			
			if (game.checkWin()) {
				game.display();
				return;
			}
		}
	}
	
	public static void Smart(Scanner input, ConnectFour game) {

		while (true) {
			
			int userInput;

			game.display();
			System.out.println("Player 1's turn:");
			userInput = input.nextInt();
			game.select(ConnectFour.PLAYER_1, userInput);
			game.changeValid(userInput);

			if (game.checkWin()) {
				game.display();
				return;
			}

			// P2
			game.display();

			// userInput = AI.makeChoiceRandom(game.getGame());
			userInput = AI.makeChoiceSmart(game.getGame(), game.getValidPlays());
			System.out.println("AI's turn: " + userInput);
			game.select(ConnectFour.PLAYER_2, userInput);
			game.changeValid(userInput);

			if (game.checkWin()) {
				game.display();
				return;
			}
		}
	}
	
	public static int Eve() throws InterruptedException {

		ConnectFour game = new ConnectFour();
		
		int moveCount = 0;
		
		
		while (true) {
			
			if(moveCount == 42)
				return 0;
			
			int userInput;

			game.display();
			System.out.println("Player 1's turn:");
			userInput = AI.makeChoiceSmart2(game.getGame(), game.getValidPlays());
			game.select(ConnectFour.PLAYER_1, userInput);
			game.changeValid(userInput);

			if (game.checkWin()) {
				game.display();
				return 1;
			}
			moveCount++;

			
			
			// P2
			game.display();

			// userInput = AI.makeChoiceRandom(game.getGame());
			userInput = AI.makeChoiceSmart(game.getGame(), game.getValidPlays());
			System.out.println("AI's turn: " + userInput);
			game.select(ConnectFour.PLAYER_2, userInput);
			game.changeValid(userInput);
			
			if (game.checkWin()) {
				game.display();
				return 2;
			}
			moveCount++;
		}
	}
	
	public static int Eve2() {

		ConnectFour game = new ConnectFour();
		
		int moveCount = 0;
		
		
		while (true) {
			
			if(moveCount == 42)
				return 0;
			
			int userInput;

			game.display();

			// userInput = AI.makeChoiceRandom(game.getGame());
			userInput = AI.makeChoiceSmart(game.getGame(), game.getValidPlays());
			System.out.println("AI's turn: " + userInput);
			game.select(ConnectFour.PLAYER_2, userInput);
			game.changeValid(userInput);

			if (game.checkWin()) {
				game.display();
				return 2;
			}
			moveCount++;

			
			game.display();
			System.out.println("Player 1's turn:");
			userInput = AI.makeChoiceSmart2(game.getGame(), game.getValidPlays());
			game.select(ConnectFour.PLAYER_1, userInput);
			game.changeValid(userInput);
			
			
			
			if (game.checkWin()) {
				game.display();
				return 1;
			}
			moveCount++;
		}
	}

}

class ConnectFour {
	
	public static final int PLAYER_1 = 1;
	public static final int PLAYER_2 = 2;
	public static final char BLANK = '-';
	

	private int[][] game;
	
	private int[] validPlays = new int[]{6,6,6,6,6,6,6};
	
	public static final String[] wins = new String[]{
			// Horizontal wins
			"50515253", "51525354", "52535455", "53545556",
			"40414243", "41424344", "42434445", "43444546",
			"30313233", "31323334", "32333435", "33343536",
			"20212223", "21222324", "22232425", "23242526",
			"10111213", "11121314", "12131415", "13141516",
			"00010203", "01020304", "02030405", "03040506", 
			// vertical wins
			"00102030","01112131","02122232","03132333","04142434","05152535","06162636",
			"10203040","11213141","12223242","13233343","14243444","15253545","16263646",
			"20304050","21314151","22324252","23334353","24344454","25354555","26364656",
			// 45 Degree wins (low left, high right)(/)
			"50413223","51423324","52433425","53443526",
			"40312213","41322314","42332415","43342516",
			"30211203","31221304","32231405","33241506",
			// 45 Degree wins (high left, low right)(\)
			"00112233","01122334","02132435","03142536",
			"10213243","11223344","12233445","13243546",
			"20314253","21324354","22334455","23344556"
			};
	
	ConnectFour() {
		this.restartGame();
	}

	public void restartGame() {

		this.game = new int[][] { 
		{ 0, 0, 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0, 0, 0 },	
		{ 0, 0, 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0, 0, 0 }, 
		{ 0, 0, 0, 0, 0, 0, 0 } };

	}

	public void select(int player, int choice) {

		for (int i = 0; i < game.length; i++) {
			if (game[5-i][choice-1] == 0) {
				game[5-i][choice-1] = player;
				return;
			}
		}

	}

	public void display() {

		String concat = "";
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if(game[i][j] == 0) {
					concat = concat + BLANK + " ";
				}else {
				concat = concat + game[i][j] + " ";
				}
			}
			concat = concat + "\n";
		}
		System.out.print(concat);
		System.out.println("_____________");
		System.out.println("1 2 3 4 5 6 7");
	}
	
	public static void displayG(int[][] game) {

		String concat = "";
		for (int i = 0; i < 6; i++) {
			for (int j = 0; j < 7; j++) {
				if(game[i][j] == 0) {
					concat = concat + BLANK + " ";
				}else {
				concat = concat + game[i][j] + " ";
				}
			}
			concat = concat + "\n";
		}
		System.out.print(concat);
		System.out.println("_____________");
		System.out.println("1 2 3 4 5 6 7");
	}
	
	public boolean checkWin() {
	   

		
		String position;
		int a,b,c,d,e,f,g,h;
		
		for (int i = 0; i < wins.length; i++) {
			
			position = wins[i];
			
			a = Character.getNumericValue(position.charAt(0));
			b = Character.getNumericValue(position.charAt(1));
			c = Character.getNumericValue(position.charAt(2));
			d = Character.getNumericValue(position.charAt(3));
			
			e = Character.getNumericValue(position.charAt(4));
			f = Character.getNumericValue(position.charAt(5));
			g = Character.getNumericValue(position.charAt(6));
			h = Character.getNumericValue(position.charAt(7));
			
			
			if (game[a][b]== PLAYER_1 && game[c][d]== PLAYER_1 && 
				game[e][f]== PLAYER_1 && game[g][h]== PLAYER_1) {
				System.out.println("********Player 1 wins*****");
				return true;
			} else 
			if (game[a][b]== PLAYER_2 && game[c][d]== PLAYER_2 && 
				game[e][f]== PLAYER_2 && game[g][h]== PLAYER_2) {
				System.out.println("********Player 2 wins*****");
				return true;
			}
			
		}
		return false;
		
	}
	
	public int[][] getGame(){
		
		return game;
	}
	
	public void changeValid(int i) {
		validPlays[i-1] = validPlays[i-1] - 1;
	}

	public int[] getValidPlays() {
		return validPlays;
	}

	public void setValidPlays(int[] validPlays) {
		this.validPlays = validPlays;
	}

}

class AI {

	public static int makeChoiceRandom(int[][] game) {

		int[][] gameCopy = twoDemArrayCopy(game);

		// player 2 is AI, 1 is person
		for (int i = 1; i <= 7; i++) {

			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);

			gameCopy1 = twoDemArrayCopy(select(gameCopy1, 2, i).clone());
			if (whoWins(gameCopy1) == 2) {
				return i;
			}

		}

		return randomWithRange(1, 7);
	}

	public static int makeChoiceSmartRandom(int[][] game) {

		int[] choice = new int[] { 100, 200, 300, 400, 500, 600, 700 };
		int numLoss = 0;

		int[][] gameCopy = twoDemArrayCopy(game);

		// Copy array, then check to see if there is a
		// possible immediate win.
		// Will also check which moves will result in
		// an immediate loss.

		System.out.println("Step1");
		for (int i = 1; i <= choice.length; i++) {

			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = twoDemArrayCopy(select(gameCopy1, ConnectFour.PLAYER_2, i).clone());
			if (whoWins(gameCopy1) == ConnectFour.PLAYER_2) {
				System.out.println("win");
				return i;
			} else {
				// Check bad moves(if player 1 wins directly after)
				for (int j = 1; j <= choice.length; j++) {
					int[][] gameCopy11 = twoDemArrayCopy(gameCopy1);
					gameCopy11 = twoDemArrayCopy(select(gameCopy11, ConnectFour.PLAYER_1, j).clone());
					if (whoWins(gameCopy1) == ConnectFour.PLAYER_1) {
						choice[j] = choice[j] - 10000;
						numLoss++;
					}
				}
			}
		}

		System.out.println("Step2");
		// See which moves would result in P1 winning if we don't move there. (block
		// win)
		
		for (int i = 1; i <= choice.length; i++) {
			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = twoDemArrayCopy(select(gameCopy1, ConnectFour.PLAYER_1, i).clone());
			if (whoWins(gameCopy1) == ConnectFour.PLAYER_1) {
				System.out.println("(block)");
				return i;
			}
		}

		System.out.println("random");
		Arrays.sort(choice);

		char pick = Integer.toString(choice[randomWithRange(1 + numLoss, 6)]).charAt(0);

		int done = Integer.parseInt(String.valueOf(pick));

		System.out.println(done);
		return done;
	}

	public static int makeChoiceSmart(int[][] game, int[] valid) {

	   
	   
	   
		// Initialize 7 picks
		Picks[] options = new Picks[7];
		for (int i = 0; i < options.length; i++)
			options[i] = new Picks(i + 1);

		// clone data to keep it safe
		int[][] gameCopy = twoDemArrayCopy(game);
		int[] validCopy = valid.clone();

		// Copy array, then check to see if there is a
		// possible immediate win.
		// Will also check which moves will result in
		// an immediate loss.
		System.out.println("Step1");
		for (int i = 1; i <= options.length; i++) {

			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = makeTempMove(gameCopy1, ConnectFour.PLAYER_2, i);

			if (whoWins(gameCopy1) == ConnectFour.PLAYER_2) {
				System.out.println("win");
				return i;
			} else {
				// Check bad moves(if player 1 wins directly after)
				for (int j = 1; j <= options.length; j++) {
					int[][] gameCopy11 = twoDemArrayCopy(gameCopy1);
					gameCopy11 = twoDemArrayCopy(select(gameCopy11, ConnectFour.PLAYER_1, j).clone());
					if (whoWins(gameCopy11) == ConnectFour.PLAYER_1) {
						options[j-1].setWeight(-5000);
					}
				}
			}
		}

		System.out.println("Step2");
		
		// See which moves would result in P1 winning if we don't move there. (block
		// win)
		
		for (int i = 1; i <= options.length; i++) {
			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = twoDemArrayCopy(select(gameCopy1, ConnectFour.PLAYER_1, i).clone());
			if (whoWins(gameCopy1) == ConnectFour.PLAYER_1) {
				System.out.println("(block)");
				return i;
			}
		}

		System.out.println("Step 3");

		// The weighting system
		for (int i = 1; i <= options.length; i++) {

			// Only playing valid moves
			if (validCopy[i-1] == 0) {
				options[i-1].setWeight(-10000);
				continue;
				}
			int[] validCopy1 = validCopy.clone();
			validCopy1[i-1] = validCopy1[i-1]-1;
			
			
			// First AI Goes
			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = makeTempMove(gameCopy1, ConnectFour.PLAYER_2, i);

			if (whoWins(gameCopy1) == ConnectFour.PLAYER_2) {
				options[i-1].setWeight(options[i-1].getWeight() + 8);
				continue;
			}
			
			// If the AI doesn't win, then check if player
			for (int j = 1; j <= options.length; j++) {
				
				if (validCopy1[j-1] == 0) 
					continue;
				
				int[] validCopy11 = validCopy1.clone();
				validCopy11[i-1] = validCopy11[i-1]-1;

				int[][] gameCopy11 = twoDemArrayCopy(gameCopy1);
				gameCopy11 = makeTempMove(gameCopy11, ConnectFour.PLAYER_1, j);

				if (whoWins(gameCopy11) == ConnectFour.PLAYER_1) {
					options[i-1].setWeight(options[i-1].getWeight() - 8);
					continue;
				}

				// Back to AI if player does not win
				for (int h = 1; h <= options.length; h++) {
					
					// Only playing valid moves
					if (validCopy11[h-1] == 0) 
						continue;
					int[] validCopy2 = validCopy11.clone();
					validCopy2[i-1] = validCopy2[i-1]-1;

					int[][] gameCopy2 = twoDemArrayCopy(gameCopy11);
					gameCopy2 = makeTempMove(gameCopy2, ConnectFour.PLAYER_2, h);

					if (whoWins(gameCopy2) == ConnectFour.PLAYER_2) {
						options[i-1].setWeight(options[i-1].getWeight() + 4);
						continue;
					}
					
					// back to Player
					for (int k = 1; k <= gameCopy.length; k++) {

						if (validCopy2[k-1] == 0) 
							continue;
						int[] validCopy22 = validCopy2.clone();
						validCopy22[i-1] = validCopy22[i-1]-1;

						int[][] gameCopy22 = twoDemArrayCopy(gameCopy2);
						gameCopy22 = makeTempMove(gameCopy22, ConnectFour.PLAYER_1, k);

						if (whoWins(gameCopy22) == ConnectFour.PLAYER_1) {
							options[i-1].setWeight(options[i-1].getWeight() - 4);
							continue;
						}						
					}
				}
			}
		}
		
		
		
		int largestWeighted = Integer.MIN_VALUE;
		int locationWeight = 1;
		
		
		// Find best answer
		for (int j = 0; j < options.length; j++) {
			if (options[j].getWeight() > largestWeighted) {
				largestWeighted = options[j].getWeight();
				locationWeight = j;
			}
		}
		
		// How many are best?
		int numberOfLargest = 0;
		for (int j = 0; j < options.length; j++) {
			if (largestWeighted == options[j].getWeight())
				numberOfLargest++;
		}
		
		// More than 1? Randomize the selection among them
		if (numberOfLargest > 1) {
			
			int[] bestPicks = new int[numberOfLargest];
			int bestPicksPlace = 0;
			
			for (int k = 0; k < options.length; k++) {
				if (largestWeighted == options[k].getWeight()) {
					bestPicks[bestPicksPlace] = options[k].getSelection();
					bestPicksPlace++;
				}
			}
			
			int randomPick = randomWithRange(0, numberOfLargest-1);
			
			Picks.print(options);
			
			return bestPicks[randomPick];

		}

		Picks.print(options);

		
		return options[locationWeight].getSelection();
	}
	
	public static int makeChoiceSmart2(int[][] game, int[] valid) {

		// Initialize 7 picks
		Picks[] options = new Picks[7];
		for (int i = 0; i < options.length; i++)
			options[i] = new Picks(i + 1);

		// clone data to keep it safe
		int[][] gameCopy = twoDemArrayCopy(game);
		int[] validCopy = valid.clone();

		// Copy array, then check to see if there is a
		// possible immediate win.
		// Will also check which moves will result in
		// an immediate loss.
		System.out.println("Step1");
		for (int i = 1; i <= options.length; i++) {

			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = makeTempMove(gameCopy1, ConnectFour.PLAYER_1, i);

			if (whoWins(gameCopy1) == ConnectFour.PLAYER_1) {
				System.out.println("win");
				return i;
			} else {
				// Check bad moves(if player 1 wins directly after)
				for (int j = 1; j <= options.length; j++) {
					int[][] gameCopy11 = twoDemArrayCopy(gameCopy1);
					gameCopy11 = twoDemArrayCopy(select(gameCopy11, ConnectFour.PLAYER_2, j).clone());
					if (whoWins(gameCopy11) == ConnectFour.PLAYER_2) {
						options[j-1].setWeight(-5000);
					}
				}
			}
		}

		System.out.println("Step2");
		// See which moves would result in P1 winning if we don't move there. (block
		// win)
		for (int i = 1; i <= options.length; i++) {
			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = twoDemArrayCopy(select(gameCopy1, ConnectFour.PLAYER_2, i).clone());
			if (whoWins(gameCopy1) == ConnectFour.PLAYER_2) {
				System.out.println("(block)");
				return i;
			}
		}

		System.out.println("Step 3");

		// The weighting system
		for (int i = 1; i <= options.length; i++) {

			// Only playing valid moves
			if (validCopy[i-1] == 0) {
				options[i-1].setWeight(-10000);
				continue;
				}
			int[] validCopy1 = validCopy.clone();
			validCopy1[i-1] = validCopy1[i-1]-1;
			
			
			// First AI Goes
			int[][] gameCopy1 = twoDemArrayCopy(gameCopy);
			gameCopy1 = makeTempMove(gameCopy1, ConnectFour.PLAYER_1, i);

			if (whoWins(gameCopy1) == ConnectFour.PLAYER_1) {
				options[i-1].setWeight(options[i-1].getWeight() + 8);
				continue;
			}
			
			// If the AI doesn't win, then check if player
			for (int j = 1; j <= options.length; j++) {
				
				if (validCopy1[j-1] == 0) 
					continue;
				
				int[] validCopy11 = validCopy1.clone();
				validCopy11[i-1] = validCopy11[i-1]-1;

				int[][] gameCopy11 = twoDemArrayCopy(gameCopy1);
				gameCopy11 = makeTempMove(gameCopy11, ConnectFour.PLAYER_2, j);

				if (whoWins(gameCopy11) == ConnectFour.PLAYER_2) {
					options[i-1].setWeight(options[i-1].getWeight() - 8);
					continue;
				}

				// Back to AI if player does not win
				for (int h = 1; h <= options.length; h++) {
					
					// Only playing valid moves
					if (validCopy11[h-1] == 0) 
						continue;
					int[] validCopy2 = validCopy11.clone();
					validCopy2[i-1] = validCopy2[i-1]-1;

					int[][] gameCopy2 = twoDemArrayCopy(gameCopy11);
					gameCopy2 = makeTempMove(gameCopy2, ConnectFour.PLAYER_1, h);

					if (whoWins(gameCopy2) == ConnectFour.PLAYER_1) {
						options[i-1].setWeight(options[i-1].getWeight() + 4);
						continue;
					}
					
					// back to Player
					for (int k = 1; k <= gameCopy.length; k++) {

						if (validCopy2[k-1] == 0) 
							continue;
						int[] validCopy22 = validCopy2.clone();
						validCopy22[i-1] = validCopy22[i-1]-1;

						int[][] gameCopy22 = twoDemArrayCopy(gameCopy2);
						gameCopy22 = makeTempMove(gameCopy22, ConnectFour.PLAYER_2, k);

						if (whoWins(gameCopy22) == ConnectFour.PLAYER_2) {
							options[i-1].setWeight(options[i-1].getWeight() - 4);
							continue;
						}	
						
						// AI
						for (int l = 1; l <= gameCopy22.length; l++) {
							
							// Only playing valid moves
							if (validCopy22[h-1] == 0) 
								continue;
							int[] validCopy3 = validCopy22.clone();
							validCopy3[i-1] = validCopy3[i-1]-1;

							int[][] gameCopy3 = twoDemArrayCopy(gameCopy11);
							gameCopy3 = makeTempMove(gameCopy3, ConnectFour.PLAYER_1, l);

							if (whoWins(gameCopy3) == ConnectFour.PLAYER_1) {
								options[i-1].setWeight(options[i-1].getWeight() + 2);
								continue;
							}
							// Player
							for (int m = 1; m <= gameCopy22.length; m++) {
								if (validCopy3[k-1] == 0) 
									continue;
								int[] validCopy33 = validCopy3.clone();
								validCopy33[i-1] = validCopy33[i-1]-1;

								int[][] gameCopy33 = twoDemArrayCopy(gameCopy3);
								gameCopy33 = makeTempMove(gameCopy33, ConnectFour.PLAYER_2, m);

								if (whoWins(gameCopy33) == ConnectFour.PLAYER_2) {
									options[i-1].setWeight(options[i-1].getWeight() - 2);
									continue;
								}	
							}
						}
						
					}
				}
			}
		}
		
		
		
		int largestWeighted = Integer.MIN_VALUE;
		int locationWeight = 1;
		
		
		// Find best answer
		for (int j = 0; j < options.length; j++) {
			if (options[j].getWeight() > largestWeighted) {
				largestWeighted = options[j].getWeight();
				locationWeight = j;
			}
		}
		
		// How many are best?
		int numberOfLargest = 0;
		for (int j = 0; j < options.length; j++) {
			if (largestWeighted == options[j].getWeight())
				numberOfLargest++;
		}
		
		// More than 1? Randomize the selection among them
		if (numberOfLargest > 1) {
			
			int[] bestPicks = new int[numberOfLargest];
			int bestPicksPlace = 0;
			
			for (int k = 0; k < options.length; k++) {
				if (largestWeighted == options[k].getWeight()) {
					bestPicks[bestPicksPlace] = options[k].getSelection();
					bestPicksPlace++;
				}
			}
			
			int randomPick = randomWithRange(0, numberOfLargest-1);
			
			Picks.print(options);
			
			return bestPicks[randomPick];

		}

		Picks.print(options);

		
		return options[locationWeight].getSelection();
	}
	
	public static int whoWins(int[][] game) {

		String position;
		int a, b, c, d, e, f, g, h;

		for (int i = 0; i < ConnectFour.wins.length; i++) {

			position = ConnectFour.wins[i];

			a = Character.getNumericValue(position.charAt(0));
			b = Character.getNumericValue(position.charAt(1));
			c = Character.getNumericValue(position.charAt(2));
			d = Character.getNumericValue(position.charAt(3));

			e = Character.getNumericValue(position.charAt(4));
			f = Character.getNumericValue(position.charAt(5));
			g = Character.getNumericValue(position.charAt(6));
			h = Character.getNumericValue(position.charAt(7));

			if (game[a][b] == ConnectFour.PLAYER_1 && game[c][d] == ConnectFour.PLAYER_1
					&& game[e][f] == ConnectFour.PLAYER_1 && game[g][h] == ConnectFour.PLAYER_1) {
				return ConnectFour.PLAYER_1;
			} else if (game[a][b] == ConnectFour.PLAYER_2 && game[c][d] == ConnectFour.PLAYER_2
					&& game[e][f] == ConnectFour.PLAYER_2 && game[g][h] == ConnectFour.PLAYER_2) {
				return ConnectFour.PLAYER_2;
			}

		}

		return 0;
	}

	public static int[][] select(int[][] game, int player, int choice) {

		for (int i = 0; i < game.length; i++) {
			if (game[5 - i][choice - 1] == 0) {
				game[5 - i][choice - 1] = player;
				break;
			}
		}
		return game;
	}

	public static int randomWithRange(int min, int max) {
		int range = (max - min) + 1;
		return (int) (Math.random() * range) + min;
	}

	public static int[][] twoDemArrayCopy(int[][] matrix) {

		int[][] myInt = new int[matrix.length][];
		for (int i = 0; i < matrix.length; i++)
			myInt[i] = matrix[i].clone();

		return myInt;
	}
	
	public static int[][] makeTempMove(int[][] game, int player, int location){
		
		int[][] gameCopy = twoDemArrayCopy(game);
		gameCopy = twoDemArrayCopy(select(gameCopy, player, location).clone());
		
		return gameCopy;
	}

}

class Picks{
	
	private int weight;
	private int selection;
	
	Picks(int selection){
		this.selection = selection;
		this.weight = 0;
	}
	
	public int getWeight() {
		return weight;
	}
	public void setWeight(int weight) {
		this.weight = weight;
	}
	public int getSelection() {
		return selection;
	}
	public void setSelection(int selection) {
		this.selection = selection;
	}
	public static void print(Picks[] pick) {
		
		for (int i = 0; i < pick.length; i++) {
			System.out.println(pick[i].selection + " " + pick[i].weight);
		}
		
	}
	
}

