/**
 * 
 */
package main;

import java.io.Console;
import java.util.Scanner;

import parser.KUGradeParser;


/**
 * KU Grade Notification
 * Main Class
 * @author waitonza
 */
public class Main {

	/**
	 * Method Main
	 * @param args
	 */
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Scanner scanner = new Scanner(System.in);
		Console console = System.console();
		System.out.println("KU Grade Notification By WaiTonZa [Version : Beta1] ");
		System.out.println("Visit this source code and user manual at https://github.com/waitonza/KU-GradeNotification");
		System.out.print("Select mode [S -> Show grade and notify, N -> notify only] : ");
		String mode = scanner.nextLine().toLowerCase();
		if (mode.equals("n")) {
			System.out.println("Note : It won't show grade. See grade with yourself.");
		}
		System.out.print("Username : ");
		String username = scanner.nextLine();
		System.out.print("Password : ");
		String password = scanner.nextLine();
		/*
		//For console only
		char passwordArray[] = console.readPassword("Password (It will be hidden) : ");
		String password = new String(passwordArray);
		*/
		KUGradeParser parser = new KUGradeParser(username, password, mode);
		parser.run();
	}

}
