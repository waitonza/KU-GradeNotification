/**
 * 
 */
package com.waitonza.grade_noti.parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;

/**
 * @author waitonza
 *
 */
public class KUGradeParser {
	
	private String username;
	private String password;
	
	public KUGradeParser() {
		
	}
	
	public KUGradeParser(String username, String password) {
		this.username = username;
		this.password = password;
		login();
	}
	
	private void login() {
		String link = "https://grade-std.ku.ac.th/GSTU_login.php";
		
		Response res = null;
		Document doc = null;
		try {
			res = Jsoup.connect(link)
					.method(Method.POST)
					.data("UserName", this.username, "Password", this.password, "zone", "0")
					.timeout(1000*600).execute();
			doc = res.parse();
		} catch (IOException e) {
			System.out.print("Cannot login, Try again!");
		}
		System.out.print("Login successful.");
		System.out.print(doc.toString());
	}
	
	
}
