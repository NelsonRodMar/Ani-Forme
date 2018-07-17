package fr.eni.clinique.ihm;

import javax.swing.SwingUtilities;

import fr.eni.clinique.ihm.login.EcranLogin;

public class DemoApp {

	public static void main(String[] args){
		DemoApp demoApp = new DemoApp();
		
		SwingUtilities.invokeLater(new Runnable(){
			@Override
			public void run(){
				EcranLogin ecranLogin = new EcranLogin("Login");
				ecranLogin.setVisible(true);
			}
		});
	}
}