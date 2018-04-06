import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

public class Display extends Canvas {

	public void paint(Graphics g) {

		Toolkit t = Toolkit.getDefaultToolkit();
		Image i = t.getImage("LJFF.png");
		setBackground(Color.WHITE);
		g.drawImage(i, 140, 20, this);

	}

	public static void main(String[] args) {
		final Console console = new Console();
		console.initialize();
//		System.out.println(console.encrypter.decrypt(console.encrypter.encrypt("abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")));

		Display m = new Display();

		// Sets icon for the window
		Image icon = Toolkit.getDefaultToolkit().getImage("LJFF.png");
		JFrame frame = new JFrame();
		frame.setLocationRelativeTo(null);
		frame.setIconImage(icon);

		// Kills application when window is closed
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		// Creates the center console area
		final JLabel mainLabel = new JLabel();
		mainLabel.setBounds(50, 150, 300, 100);
		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
		frame.add(mainLabel);

		// Label for event name
		JLabel eventLabel = new JLabel();
		eventLabel.setBounds(50, 255, 95, 25);
		eventLabel.setHorizontalAlignment(SwingConstants.LEFT);
		eventLabel.setOpaque(true);
		eventLabel.setBackground(Color.WHITE);
		eventLabel.setText("Event Name:");
		frame.add(eventLabel);

		final JTextField eventName = new JTextField();
		eventName.setBounds(150, 255, 200, 25);
		eventName.setOpaque(true);
		eventName.setBackground(Color.WHITE);
		frame.add(eventName);

		// Push Data Button
		JButton pushButton = new JButton("Push Student Data");
		pushButton.setBounds(25, 300, 150, 50);
		pushButton.setOpaque(true);
		pushButton.setBackground(Color.WHITE);
		pushButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (eventName.getText().isEmpty()) {
					mainLabel.setText("Please enter event name");
				} else {
					console.clearFirebase();
					console.pushStudentData(eventName.getText());
					mainLabel.setText("Event data successfully pushed");
				}
			}
		});
		frame.add(pushButton);

		// Pull Data Button
		JButton getButton = new JButton("Pull Check-in Data");
		getButton.setBounds(225, 300, 150, 50);
		getButton.setOpaque(true);
		getButton.setBackground(Color.WHITE);
		getButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				console.generateCheckInCSV();
				mainLabel.setText("Check-in data successfully pulled");
			}
		});
		frame.add(getButton);

		frame.add(m);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

}