// Will be implemented when Internationalization is completed.
//
//package menus;
//
//import java.awt.Color;
//import java.util.ArrayList;
//import java.util.Locale;
//import java.util.ResourceBundle;
//
//import javax.swing.JComboBox;
//import javax.swing.JLabel;
//import javax.swing.JOptionPane;
//import javax.swing.JPanel;
//
//public class LanguageMenu {
//	
//	String language = "English";
//	String location = "USA";
//	Locale locale = Locale.getDefault();
//	ResourceBundle resourceBundle;
//	
//	public LanguageMenu(){
//		
//	}
//	
//	public void selectLanguageAndLocale() {
//		JPanel panel = new JPanel();
//		panel.add(new JLabel(GraphicsConstants.LOCALE_INFO));
//		JComboBox languageBox = new JComboBox();
//		languageBox.addItem("English");
//		languageBox.addItem("French");
//		panel.add(languageBox);
//		JComboBox localeBox = new JComboBox();
//		localeBox.addItem("USA");
//		localeBox.addItem("France");
//		panel.add(localeBox);
//		
//		String[] languageLocaleList = new String[2];
//		int choiceSelected = JOptionPane.showConfirmDialog(null, 
//				panel, "", JOptionPane.OK_CANCEL_OPTION,
//				JOptionPane.QUESTION_MESSAGE);
//		switch (choiceSelected) {
//		case JOptionPane.OK_OPTION:
//			this.language = (String) languageBox.getSelectedItem();
//			this.location = (String) localeBox.getSelectedItem();
//			break;
//		}
//		System.out.println(locale.getDefault());
//		resourceBundle = ResourceBundle.getBundle("resources/resources", locale.getDefault());
//		updateLocale();
//	}
//	
//	public void updateLocale() {
//		if(location.equals("France")){
//			this.locale = locale.FRANCE;
//		}
//		updateResources();
//	}
//	
//	public void updateResources() {
//		resourceBundle = ResourceBundle.getBundle("resources/resources", locale);
//		System.err.println("Locale: " + locale.toString());
//		GraphicsConstants.updateGraphicsConstants(resourceBundle);
//	}
//
//}
