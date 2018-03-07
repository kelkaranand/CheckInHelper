import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

public class Display extends Canvas {

	public void paint(Graphics g) {

		Toolkit t = Toolkit.getDefaultToolkit();
		 Image i=t.getImage("LJFF.png");
		 setBackground(Color.WHITE);
		 g.drawImage(i,140,20,this);
//		 setForeground(Color.BLACK);
//		 g.drawRect(20, 145, 160, 225);

	}

	public static void main(String[] args) {
		final Console console=new Console();
		
		Display m = new Display();

		Image icon = Toolkit.getDefaultToolkit().getImage("LJFF.png");
		JFrame frame = new JFrame();
		frame.setIconImage(icon);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

//		JLabel mainLabel = new JLabel();
//		mainLabel
//				.setText("<html><div style='text-align: center;'>Check-in Helper</div></html>");
//		mainLabel.setBounds(50, 50, 700, 500);
//		mainLabel.setHorizontalAlignment(SwingConstants.CENTER);
//		frame.add(mainLabel);

		JButton pushButton = new JButton("Push Student Data");
		pushButton.setBounds(25, 300, 150, 50);
		pushButton.setBackground(Color.WHITE);
		pushButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				console.clearFirebase();
				console.pushStudentData("Test Event");
			}
		});
		frame.add(pushButton);
		
		JButton getButton =new JButton("Pull Check-in Data");
		getButton.setBounds(225, 300, 150, 50);
		getButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				console.generateCheckInCSV();
			}
		});
		frame.add(getButton);
		
		
		
		frame.add(m);
		frame.setSize(400, 400);
		frame.setVisible(true);
	}

}