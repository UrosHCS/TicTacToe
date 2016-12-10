package game;

import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Game implements MouseListener {
	
	private int[][] gameMatrix; //3x3 matrix of number representations (0, 1, -1)
	private FieldLabel[][] labelMatrix;//3x3 matrix of labels (fields)
	private boolean gameOver;
	private static Game instance;
	
	private Game() {
		gameMatrix = new int[3][3]; //filled with zeros as needed
		labelMatrix = new FieldLabel[3][3];
		gameOver = false;
	}
	
	public static Game getInstance() {
		if (instance == null) {
			instance = new Game();
		}
		return instance;
	}
	
	public void fillLabelMatrix() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				labelMatrix[row][column] = new FieldLabel("", SwingConstants.CENTER);
			}
		}
		
	}

	public void buildGUI() {
		JFrame theFrame = new JFrame("X-O");
	    theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	    theFrame.setBounds(50,50,400,400);
	    
	    JMenuBar menuBar = new JMenuBar();
        JMenu fileMenu = new JMenu("File");
        JMenuItem newGameItem = new JMenuItem("New Game");
        JMenuItem exitItem = new JMenuItem("Exit Game");
        
        newGameItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				gameOver = false;
				for (int row = 0; row < 3; row++) {
					for (int column = 0; column < 3; column++) {
						labelMatrix[row][column].setText("");
						labelMatrix[row][column].setForeground(Color.BLACK);
						gameMatrix[row][column] = 0;
					}
				}
			}
        });
        
        exitItem.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			}
        });
        
        fileMenu.add(newGameItem);      
        fileMenu.add(exitItem);
        menuBar.add(fileMenu);
        theFrame.setJMenuBar(menuBar);
	    
	    GridLayout gLayout = new GridLayout(3, 3);
	    JPanel mainPanel = new JPanel(gLayout);
		mainPanel.setBorder(BorderFactory.createEmptyBorder(10,10,10,10));
				
		//Add FieldLabels from the matrix to the panel
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				mainPanel.add(labelMatrix[row][column]);
			}

		}
		
		theFrame.getContentPane().add(mainPanel);
	    theFrame.setVisible(true);
	}
	
	private void computerMove() {

		boolean computerMadeAMove = false;
		if (gameOver == false) {
			//Prodji svih 7 (x) mogucih "trojki" koje dovode do pobede (ima ih 7)
			for (int x = 0; x <= 7; x++) {
				// Potrazi prvo da li negde imaju 2 oksa i trece prazno mesto i popuni ga sa oks
				if (Matrix.arraySum(gameMatrix, x) == -2) {
					int[] zeroCoordinates = Matrix.indexOfZero(gameMatrix, x);
					gameMatrix[zeroCoordinates[0]][zeroCoordinates[1]] = -1;
					labelMatrix[zeroCoordinates[0]][zeroCoordinates[1]].setText("O");
					computerMadeAMove = true;
					break;
				}
			}
			// Zatim potrazi da li negde imaju 2 iksa i trece prazno mesto i popuni ga sa oks
			if (!computerMadeAMove) {
				for (int x = 0; x <= 7; x++) {
					if (Matrix.arraySum(gameMatrix, x) == 2) {
						int[] zeroCoordinates = Matrix.indexOfZero(gameMatrix, x);
						gameMatrix[zeroCoordinates[0]][zeroCoordinates[1]] = -1;
						labelMatrix[zeroCoordinates[0]][zeroCoordinates[1]].setText("O");
						computerMadeAMove = true;
						break;
					}
				}
			}
			//Ako ne, onda napravi random potez
			if (!computerMadeAMove) {
				computerRandomMove();
				computerMadeAMove = true;
			}
		}

	}

	private void computerRandomMove() {
		int randomRow = (int) (Math.random() * 3);
		int randomColumn = (int) (Math.random() * 3);
		if (gameMatrix[randomRow][randomColumn] == 0) {
			gameMatrix[randomRow][randomColumn] = -1;
			labelMatrix[randomRow][randomColumn].setText("O");
		} else
			computerRandomMove();
	}

	private boolean isTheBoardFull() {
		for (int row = 0; row < 3; row++) {
			for (int column = 0; column < 3; column++) {
				if (gameMatrix[row][column] == 0) {
					return false;
				}
			}
		}
		gameOver = true;
		return true;
	}

	private void didSome1Win(int three) {
		// Prodji svih 7 (x) mogucih "trojki" koje dovode do pobede (ima ih 7)
		for (int x = 0; x <= 7; x++) {
			if (Matrix.arraySum(gameMatrix, x) == three) {
				if (x >= 0 && x <= 2) {
					for (int i = 0; i < 3; i++) {
						labelMatrix[x][i].setForeground(Color.RED);
					}
				} else if (x >= 3 && x <= 5) {
					for (int i = 0; i < 3; i++) {
						labelMatrix[i][x - 3].setForeground(Color.RED);
					}
				} else if (x == 6) {
					for (int i = 0; i < 3; i++) {
						labelMatrix[i][i].setForeground(Color.RED);
					}
				} else if (x == 7) {
					for (int i = 0; i < 3; i++) {
						labelMatrix[2 - i][i].setForeground(Color.RED);
					}
				}
				gameOver = true;
				break;
			}
		}
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}
	@Override
	public void mouseEntered(MouseEvent arg0) {}
	@Override
	public void mouseExited(MouseEvent arg0) {}
	@Override
	public void mousePressed(MouseEvent arg0) {}
	
	@Override
	public void mouseReleased(MouseEvent e) {
		FieldLabel clickedLabel = (FieldLabel) e.getSource();
		if (clickedLabel.getText().equals("") && gameOver == false) {
			clickedLabel.setText("X");
			gameMatrix[clickedLabel.getRow()][clickedLabel.getColumn()] = 1;
			didSome1Win(3);
			if (!isTheBoardFull()) {
				computerMove();
			}
			didSome1Win(-3);
		}
		
	}
}
