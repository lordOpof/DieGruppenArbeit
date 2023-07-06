import javax.swing.*;
import java.awt.*;

public class Legende extends JFrame {

	public Legende(JFrame relativeFrame) {
		String imagePath = "C:/Users/oskar/Desktop/painWTF/Simulator/legende.png";

		setTitle("Image Display");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		ImageIcon imageIcon = new ImageIcon(imagePath);
		JLabel imageLabel = new JLabel(imageIcon);

		Container contentPane = getContentPane();
		contentPane.add(imageLabel);

		setUndecorated(true);

		pack();

		Point relativeFrameLocation = relativeFrame.getLocation();
		setLocation(
				relativeFrameLocation.x+200,
				relativeFrameLocation.y + relativeFrame.getHeight()-5
		);

		setVisible(true);
	}
}
