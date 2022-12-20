package main;



import java.awt.EventQueue;

import gui.FormDangNhap;
/**
 * 
 * @author nmthu
 *
 */
public class ApplicationMain {
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					FormDangNhap frame = new FormDangNhap();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}
}
