package game;

import static constants.Game.BIOHAZARD_IMG;
import static constants.Game.CUSTOM_GRAY_1;
import static constants.Game.INITIAL_NUM_RESEARCH_STATIONS;
import static constants.Game.INFECTION_RATE_TEXT;
import static constants.Game.INITIAL_NUM_OUTBREAKS;
import static constants.Game.OUTBREAK_IMG;
import static constants.Game.RESEARCH_STATION_IMG;
import static constants.Game.OFFSET_10;

import java.awt.FlowLayout;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class GameInfoView extends JPanel {
	FlowLayout infectionLayout = new FlowLayout(FlowLayout.CENTER, OFFSET_10, 0);

	public void drawPanel() {
		JPanel researchPanel = new JPanel();
		JPanel outbreakPanel = new JPanel();
		JPanel infectionPanel = new JPanel();
		JLabel research = new JLabel();
		JLabel outbreak = new JLabel();
		JLabel infection = new JLabel();
		JLabel numResearch = new JLabel();
		JLabel numOutbreaks = new JLabel();
		JLabel numInfections = new JLabel();
		ImageIcon researchImg = new ImageIcon();
		ImageIcon outbreakImg = new ImageIcon();
		ImageIcon infectionImg = new ImageIcon();
		Image image = null;
		
		numResearch.setText(INITIAL_NUM_RESEARCH_STATIONS);
		numResearch.setForeground(CUSTOM_GRAY_1);
		image = this.setImage(RESEARCH_STATION_IMG);
		researchImg = new ImageIcon(image);
		research.setIcon(researchImg);
		researchPanel.add(research);
		researchPanel.add(numResearch);

		numOutbreaks.setText(INITIAL_NUM_OUTBREAKS);
		numOutbreaks.setForeground(CUSTOM_GRAY_1);
		image = this.setImage(OUTBREAK_IMG);
		outbreakImg = new ImageIcon(image);
		outbreak.setIcon(outbreakImg);
		outbreakPanel.add(outbreak);
		outbreakPanel.add(numOutbreaks);

		numInfections.setText(INFECTION_RATE_TEXT);
		numInfections.setForeground(CUSTOM_GRAY_1);
		image = this.setImage(BIOHAZARD_IMG);
		infectionImg = new ImageIcon(image);
		infection.setIcon(infectionImg);
		infectionPanel.add(infection);
		infectionPanel.add(numInfections);

		this.setLayout(infectionLayout);
		this.add(researchPanel);
		this.add(outbreakPanel);
		this.add(infectionPanel);
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
