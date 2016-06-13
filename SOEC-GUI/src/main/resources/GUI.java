import java.awt.EventQueue;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JButton;

public class GUI {

	private JFrame frame;
	private JTextField textField;
	private JTextField textField_1;
	private JTextField textField_2;
	private JTextField textField_3;
	private JLabel lblAnkunftland;
	private JLabel lblAnkunftstadt;
	private JLabel lblKundennummer;
	private JTextField textField_4;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					GUI window = new GUI();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public GUI() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 600, 400);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		textField = new JTextField();
		textField.setBounds(38, 31, 114, 19);
		frame.getContentPane().add(textField);
		textField.setColumns(10);
		
		textField_1 = new JTextField();
		textField_1.setBounds(165, 31, 114, 19);
		frame.getContentPane().add(textField_1);
		textField_1.setColumns(10);
		
		textField_2 = new JTextField();
		textField_2.setBounds(38, 77, 114, 19);
		frame.getContentPane().add(textField_2);
		textField_2.setColumns(10);
		
		textField_3 = new JTextField();
		textField_3.setBounds(165, 77, 114, 19);
		frame.getContentPane().add(textField_3);
		textField_3.setColumns(10);
		
		JLabel lblAbflugland = new JLabel("Abflugland");
		lblAbflugland.setFont(new Font("DejaVu Sans Light", Font.PLAIN, 10));
		lblAbflugland.setBounds(48, 12, 91, 15);
		frame.getContentPane().add(lblAbflugland);
		
		JLabel lblAbflugstadt = new JLabel("Abflugstadt");
		lblAbflugstadt.setFont(new Font("DejaVu Sans Light", Font.PLAIN, 10));
		lblAbflugstadt.setBounds(175, 12, 70, 15);
		frame.getContentPane().add(lblAbflugstadt);
		
		lblAnkunftland = new JLabel("Ankunftland");
		lblAnkunftland.setFont(new Font("DejaVu Sans Light", Font.PLAIN, 10));
		lblAnkunftland.setBounds(48, 65, 91, 15);
		frame.getContentPane().add(lblAnkunftland);
		
		lblAnkunftstadt = new JLabel("Ankunftstadt");
		lblAnkunftstadt.setFont(new Font("DejaVu Sans Light", Font.PLAIN, 10));
		lblAnkunftstadt.setBounds(175, 64, 70, 15);
		frame.getContentPane().add(lblAnkunftstadt);
		
		lblKundennummer = new JLabel("Kundennummer");
		lblKundennummer.setFont(new Font("DejaVu Sans Light", Font.PLAIN, 10));
		lblKundennummer.setBounds(38, 117, 91, 15);
		frame.getContentPane().add(lblKundennummer);
		
		textField_4 = new JTextField();
		textField_4.setBounds(147, 114, 132, 19);
		frame.getContentPane().add(textField_4);
		textField_4.setColumns(10);
		
		JButton btnSuche = new JButton("Suche");
		btnSuche.setBounds(38, 154, 117, 25);
		frame.getContentPane().add(btnSuche);
	}
}
