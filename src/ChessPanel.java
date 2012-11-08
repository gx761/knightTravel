/**
 * @author    Roy Guo
 * @date      8 Nov 2012
 * @link        
 *  
 * @usage
 *     This class is inherited from JPanel, it provides the UI for users
 *     
 * 
 */
import java.awt.Color;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;

public class ChessPanel extends JPanel {
	/*A frame container used to contain the Panel*/
	private JFrame f = new JFrame();
	/*The buttons matrix displaying all the squares in a board*/
	private JButton button[][] = new JButton[8][8];
	/*Dynamically generated buttons for found solutions  */
	private JButton solutionButton[];
	/*listener added to solution buttons*/
	private SolutionButtonListener solutionButtonListener = new SolutionButtonListener();
	/*listener added to button matrix*/
	private SquareButtonListener squareButtonListener = new SquareButtonListener();
	/*label for start text field*/
	private JLabel start = new JLabel();
	/*label for end text field*/
	private JLabel end = new JLabel();
	/*text field for start square*/
	private JTextField startTextField = new JTextField(10);
	/*text field for end square*/
	private JTextField endTextField = new JTextField(10);
	/*a panel contain all control components*/
	private JPanel controlPanel = new JPanel();
	/*The start button*/
	private JButton startButton = new JButton("Start");
	/*Listener for start button*/
	private StartListener startListener = new StartListener();
	/*The reset Button*/
	private JButton endButton = new JButton("Reset");
	/*Listen for reset button*/
	private EndListener endListener = new EndListener();
	/*The list of solutions*/
	private LinkedList<LinkedList<Square>> solutions = new LinkedList<LinkedList<Square>>();
	/*the number of solutions*/
	private int numberOfSolutions;
	
	/**
	 * constructor
	 */
	public ChessPanel() {
		super(null);
		this.init();

	}
	
	/**
	 * initialise the panel
	 */
	
	public void init()
	{
		for (int i = 0; i < 8; i++) {
			JLabel l = new JLabel();
			String text = (char) (i + 97) + "";
			l.setText(text);
			l.setFont(new Font("Dialog", 1, 18));
			l.setSize(80, 40);
			l.setLocation(i * 80, 8 * 80);
			l.setHorizontalAlignment(JLabel.CENTER);
			add(l);

			JLabel r = new JLabel();
			String text2 = "" + (i + 1);
			r.setText(text2);
			r.setFont(new Font("Dialog", 1, 18));
			r.setSize(40, 80);
			r.setLocation(8 * 80, i * 80);
			r.setHorizontalAlignment(JLabel.CENTER);
			add(r);

			for (int j = 0; j < 8; j++) {
				button[i][j] = new JButton();
				button[i][j].setSize(80, 80);
				button[i][j].setLocation(i * 80, j * 80);
				button[i][j].addActionListener(squareButtonListener);
				if ((i + j) % 2 == 0) {
					button[i][j].setBackground(Color.black);
					button[i][j].setOpaque(true);
				} else {
					button[i][j].setBackground(Color.white);
					button[i][j].setOpaque(true);
				}

				button[i][j].setBorder(BorderFactory
						.createLineBorder(Color.black));
				add(button[i][j]);
			}
		}

		start.setText("Start");
		start.setFont(new Font("Dialog", 1, 25));

		end.setText("End");
		end.setFont(new Font("Dialog", 1, 25));

		startTextField.setForeground(Color.blue);
		startTextField.setFont(new Font("Dialog", 1, 20));
		startTextField.setEditable(false);

		endTextField.setForeground(Color.blue);
		endTextField.setFont(new Font("Dialog", 1, 20));
		endTextField.setEditable(false);
		JPanel labelPanel = new JPanel();
		labelPanel.add(start);
		labelPanel.add(startTextField);
		labelPanel.add(end);
		labelPanel.add(endTextField);

		JPanel buttonPanel = new JPanel();
		startButton.setPreferredSize(new Dimension(80, 40));
		startButton.addActionListener(startListener);
		endButton.setPreferredSize(new Dimension(80, 40));
		endButton.addActionListener(endListener);
		buttonPanel.add(startButton);
		buttonPanel.add(endButton);

		controlPanel.setLayout(new BorderLayout());
		controlPanel.add(labelPanel, BorderLayout.NORTH);
		controlPanel.add(buttonPanel, BorderLayout.SOUTH);
		controlPanel.setLocation(1 * 80, 680);
		controlPanel.setSize(500, 150);
		add(controlPanel);
	}
	
	/**
	 * 
	 * Listener class for all Square buttons
	 *
	 */
	public class SquareButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			for (int i = 0; i < 8; i++) {
				for (int j = 0; j < 8; j++) {
					/*get indexs of button clicked by user*/
					if (button[i][j] == e.getSource()) {
						/*Transfrom the indexs to ideal string and push it to textfields*/
						String text = (char) (i + 97) + "" + (j + 1);
						/*if the start field is empty, fill it first*/
						if (startTextField.getText().isEmpty()
								|| startTextField.getText() == null) {
							startTextField.setText(text);
							button[i][j].setBackground(Color.blue);
							/*fill the end text field secondly*/
						} else if (endTextField.getText().isEmpty()
								|| endTextField.getText() == null) {
							endTextField.setText(text);
							button[i][j].setBackground(Color.yellow);
						} 
						/*if both text fields are filled, pop up a notification*/
						  else {
						
							JOptionPane.showMessageDialog(null,
									"Please reset first");
						}

					}
				}
			}
		}

	}
	
	/**
	 * 
	 * custom listener class for start button
	 *
	 */
	public class StartListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			/*Validate the inputs*/
			if (startTextField.getText() == null
					|| startTextField.getText().trim().equals("")
					|| endTextField.getText() == ""
					|| endTextField.getText().trim().equals(""))
				JOptionPane.showMessageDialog(null,
						"Please select start and end squares");
			else {
				
				/*disable the start button, trasform the inputs into coordinates, set up two squares and pass them to Board object*/
				startButton.setEnabled(false);
				Board board = new Board();
				int startX = (int) (startTextField.getText().charAt(0) - 96);
				int startY = (int) (startTextField.getText().charAt(1) - 48);
				int endX = (int) (endTextField.getText().charAt(0) - 96);
				int endY = (int) (endTextField.getText().charAt(1) - 48);

				Square start = new Square(startX, startY, false);
				Square end = new Square(endX, endY, false);
				
				/*set the solutions returned by run function in board*/
				solutions = board.run(start, end);
				
				/*set the number of solutions*/
				numberOfSolutions = solutions.size();
				solutionButton = new JButton[numberOfSolutions];
				
				/*set solution buttons*/
				generateSolutionButtons();
				/*refresh the panel*/
				validate();
				repaint();

			}
		}

	}
	
	/**
	 * function to set solution buttons to the panel
	 */
	private void generateSolutionButtons()
	{
		for (int i = 0; i < numberOfSolutions; i++) {
			solutionButton[i] = new JButton();
			String temp = "Path" + (i + 1);
			solutionButton[i].setText(temp);
			solutionButton[i].setSize(80, 20);
			solutionButton[i].setLocation((i%2==0)?700:780,(((i%2)==0)?i:(i-1)) * 12);
			solutionButton[i].addActionListener(solutionButtonListener);
			add(solutionButton[i]);
		}
	}
	
	
	/**
	 * 
	 * Custom listener class for all solution buttons
	 *
	 */
	public class SolutionButtonListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			/*get the button being clicked*/
			for (int i = 0; i < numberOfSolutions; i++) {
				if (solutionButton[i] == e.getSource()) {
			/*get corresponding solution from the solution list */		
					LinkedList<Square> solution = solutions.get(i);
					clearBoard();
			/*loop through all square buttons and set buttons corresponding to the coordinates of squares in solution list*/		
					for (int a = 0; a < 8; a++) {
						for (int b = 0; b < 8; b++) {
							for (int c = 0; c < solution.size(); c++) {
								if ((a + 1) == solution.get(c).getX()
										&& (b + 1) == solution.get(c).getY()) {
									if (c == 0)
										button[a][b].setBackground(Color.blue);
									else if (c == (solution.size() - 1))
										button[a][b]
												.setBackground(Color.yellow);
									else {
										button[a][b].setBackground(Color.red);
										button[a][b].setText(c + "");
										button[a][b].setFont(new Font("Dialog",
												1, 23));
									}
								}
							}
						}
					}
				}
			}
		}
	}
	
	/**
	 * Custom listener Class for all reset buttons
	 * initilize the panel 
	 */
	public class EndListener implements ActionListener {

		public void actionPerformed(ActionEvent e) {
			clearBoard();
			clearText();
			clearSolutionButtons();
			startButton.setEnabled(true);

		}

	}
	
	
	/**
	 * function for formating all square buttons
	 */
	public void clearBoard() {

		for (int i = 0; i < 8; i++) {
			for (int j = 0; j < 8; j++) {
				if ((i + j) % 2 == 0) {
					button[i][j].setBackground(Color.black);
					button[i][j].setOpaque(true);
					button[i][j].setText("");
				} else {
					button[i][j].setBackground(Color.white);
					button[i][j].setOpaque(true);
					button[i][j].setText("");
				}

			}
		}
	}
	
	/**
	 * function for clearing all text fields
	 */

	public void clearText() {
		startTextField.setText("");
		endTextField.setText("");

	}
	
	/**
	 * function for removing all solution buttons
	 */
	public void clearSolutionButtons() {
		for (int i = 0; i < numberOfSolutions; i++) {
			remove(solutionButton[i]);
		}
		validate();
		repaint();

	}
	
	/**
	 *create new frame and add the panel into it 
	 */
	public void display() {
		f.setSize(900, 900);
		f.setLocationRelativeTo(null);
		f.add(new ChessPanel());
		f.setVisible(true);
	}

}