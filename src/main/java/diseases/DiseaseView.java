package diseases;

import static constants.Constants.BLACK_OUTLINE;
import static constants.Constants.BLUE_OUTLINE;
import static constants.Constants.RED_OUTLINE;
import static constants.Constants.YELLOW_OUTLINE;
import static constants.Constants.OFFSET_30;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class DiseaseView extends JPanel {
	FlowLayout curedLayout = new FlowLayout(FlowLayout.CENTER, OFFSET_30, 0);

	public void drawPanel() {
		JPanel bluePanel = new JPanel();
		JPanel yellowPanel = new JPanel();
		JPanel blackPanel = new JPanel();
		JPanel redPanel = new JPanel();
		JLabel cureBlue = new JLabel();
		JLabel cureYellow = new JLabel();
		JLabel cureBlack = new JLabel();
		JLabel cureRed = new JLabel();
		ImageIcon cureBlueImg = new ImageIcon();
		ImageIcon cureYellowImg = new ImageIcon();
		ImageIcon cureBlackImg = new ImageIcon();
		ImageIcon cureRedImg = new ImageIcon();
		Image image = null;

		image = this.setImage(BLUE_OUTLINE);
		cureBlueImg = new ImageIcon(image);
		cureBlue.setIcon(cureBlueImg);
		bluePanel.add(cureBlue);

		image = this.setImage(YELLOW_OUTLINE);
		cureYellowImg = new ImageIcon(image);
		cureYellow.setIcon(cureYellowImg);
		yellowPanel.add(cureYellow);

		image = this.setImage(BLACK_OUTLINE);
		cureBlackImg = new ImageIcon(image);
		cureBlack.setIcon(cureBlackImg);
		blackPanel.add(cureBlack);

		image = this.setImage(RED_OUTLINE);
		cureRedImg = new ImageIcon(image);
		cureRed.setIcon(cureRedImg);
		redPanel.add(cureRed);

		this.setLayout(curedLayout);
		this.add(bluePanel);
		this.add(yellowPanel);
		this.add(blackPanel);
		this.add(redPanel);
	}
	
	private Image setImage(String filepath) {
		Image image = null;

		try {
			File file = new File(filepath);
			image = ImageIO.read(file);
		} catch (IOException e) {
			System.out.println(e.getMessage());
			System.exit(0);
		}

		return image;
	}
}
