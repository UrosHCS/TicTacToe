package game;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JLabel;

public class FieldLabel extends JLabel {

	private static final Font XO_FONT = new Font("Arial", Font.BOLD, 80);
	
	//keep track of row and column number
	private static int rowToSet = 0;
	private static int columnToSet = 0;
	
	//every label needs a row and a column
	private int row = 0;
	private int column = 0;
	
	public FieldLabel(String text, int position) {
		super(text, position);
		setFont(XO_FONT);
		setBorder(BorderFactory.createLineBorder(Color.BLACK));
		addMouseListener(Game.getInstance());
		
		//assign row and column
		if (columnToSet == 3) {
			
			columnToSet = 0;
			rowToSet++;
			
			column = columnToSet;
			columnToSet++;
			row = rowToSet;
			
		} else {
			column = columnToSet;
			columnToSet++;
			row = rowToSet;
		}
	}

	public int getRow() {
		return row;
	}

	public int getColumn() {
		return column;
	}
	
}
