package cards;

import static constants.Constants.OFFSET_10;
import static constants.Constants.CARD_SIZE;
import static constants.Constants.CUSTOM_GRAY_1;
import static constants.Constants.PLAYER_DISCARD_PILE;
import static constants.Constants.INITIAL_PLAYER_DECK_SIZE;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

import game.GameModel;

public class CardView extends JPanel {
	FlowLayout layout = new FlowLayout(FlowLayout.CENTER, OFFSET_10, 0);
	boolean cardCount;
	boolean discardFirst;
	
	public CardView(boolean cardCount, boolean discardFirst) {
		this.layout = new FlowLayout();
		this.cardCount = cardCount;
		this.discardFirst = discardFirst;
	}
	
	public void drawPanel() {
		JLabel discardPile = new JLabel();
		JLabel deckPile = new JLabel();
		
		discardPile.setText(PLAYER_DISCARD_PILE);
		discardPile.setHorizontalAlignment(JLabel.CENTER);
		discardPile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		discardPile.setOpaque(true);
		discardPile.setPreferredSize(CARD_SIZE);
		
		deckPile.setBorder(BorderFactory.createLineBorder(Color.BLACK));
		deckPile.setOpaque(true);
		deckPile.setPreferredSize(CARD_SIZE);
		
		this.setLayout(this.layout);
		
		addDeckOrder(deckPile, discardPile);
		
		if (this.cardCount) {
			JLabel deckCount = new JLabel();

			deckCount.setText(INITIAL_PLAYER_DECK_SIZE + "");
			deckCount.setForeground(CUSTOM_GRAY_1);
			
			this.add(deckCount);
		}
	}
	
	private void addDeckOrder(JLabel deckPile, JLabel discardPile) {
		if (this.discardFirst) {
			this.add(discardPile);
			this.add(deckPile);
		} else {
			this.add(deckPile);
			this.add(discardPile);
		}
	}
}
