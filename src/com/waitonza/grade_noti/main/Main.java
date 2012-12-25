/**
 * 
 */
package com.waitonza.grade_noti.main;

import java.util.Scanner;

import com.waitonza.grade_noti.parser.KUGradeParser;

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
		System.out.println("KU Grade Notification By WaiTonZa ");
		System.out.println("Note : It won't show grade. See grade with yourself.");
		System.out.print("Username : ");
		String username = scanner.nextLine();
		System.out.print("Password : ");
		String password = scanner.nextLine();
		KUGradeParser parser = new KUGradeParser(username, password);
	}

}
