package menus;

import java.util.Locale;
import java.util.ResourceBundle;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class LanguageMenu {
	
	String language = "English";
	String location = "USA";
	Locale locale = Locale.getDefault();
	ResourceBundle resourceBundle;
	
	public void selectLocale() {
		JPanel panel = new JPanel();
		panel.add(new JLabel(constants.Game.LOCALE_INFO));
		JComboBox localeBox = new JComboBox();
		localeBox.addItem("English");
		localeBox.addItem("French");
		panel.add(localeBox);
		
		String[] languageLocaleList = new String[2];
		int choiceSelected = JOptionPane.showConfirmDialog(null, 
				panel, "", JOptionPane.OK_CANCEL_OPTION,
				JOptionPane.QUESTION_MESSAGE);
		
		switch (choiceSelected) {
			case JOptionPane.OK_OPTION:
				this.location = (String) localeBox.getSelectedItem();
				break;
		}
		
		resourceBundle = ResourceBundle.getBundle("resources/resources", locale.getDefault());
		updateLocale();
	}
	
	public void updateLocale() {
		if(location.equals("French")){
			this.locale = locale.FRANCE;
		}
		updateResources();
	}
	
	public void updateResources() {
		resourceBundle = ResourceBundle.getBundle("resources/resources", locale);
		constants.Game.updateConstants(resourceBundle);
	}
}