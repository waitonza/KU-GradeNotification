/**
 * 
 */
package parser;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.Connection.Method;
import org.jsoup.Connection.Response;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * @author waitonza
 *
 */
public class KUGradeParser {
	
	private String username;
	private String password;
	private String sessionId;
	private Response res = null;
	private Document doc = null;
	
	
	public KUGradeParser() {
		
	}
	
	public KUGradeParser(String username, String password) {
		this.username = username;
		this.password = password;
		login();
	}
	
	private void login() {
		String link = "https://grade-std.ku.ac.th/GSTU_login.php";
		
		try {
			res = Jsoup.connect(link)
					.method(Method.POST)
					.data("UserName", this.username, "Password", this.password, "zone", "0")
					.timeout(1000*60).execute();
			doc = res.parse();
			doc.outputSettings().charset("TIS-620");
			sessionId = res.cookie("PHPSESSID");
		} catch (IOException e) {
			System.out.print("Cannot login, Try again!");
		}
		
		if (doc.getElementsByClass("searchName").get(0).text().toString().equals("ชื่อผู้ใช้:")) {
			System.out.println("Wrong username or password, Try again.");
		} else {
			System.out.println("Login successful.");
			System.out.println(sessionId);
		}
		
	}
	
	private void refresh() {
		String link = "https://grade-std.ku.ac.th/GSTU_course.php";
		
		try {
			doc = Jsoup.connect(link)
			.cookie("PHPSESSID", sessionId)
			.get();
			doc.outputSettings().charset("TIS-620");
		} catch (IOException e) {
			System.out.print("Cannot refresh, Try again!");
		}
		
		if (doc.getElementsByClass("searchName").get(0).text().toString().equals("ชื่อผู้ใช้:")) {
			System.out.println("Session time out, Try to reconnect");
			login();
		}
	}
	
	public void parse() {
		refresh();
		Element result_table = doc.getElementsByTag("table").get(6);
		Elements result_data = result_table.getElementsByTag("tr");
		for	(int i = 1; i < result_data.size() ; i++) {
			Element element_node = result_data.get(i);
			CourseNode node = new CourseNode();
			node.setId(element_node.getElementsByTag("td").get(1).getElementsByTag("font").get(0).text());
			node.setName(element_node.getElementsByTag("td").get(2).getElementsByTag("font").get(1).text());
			node.setSec(element_node.getElementsByTag("td").get(3).getElementsByTag("font").get(0).text());
			node.setGrade(element_node.getElementsByTag("td").get(4).getElementsByTag("font").get(1).text());
			node.setStatus(element_node.getElementsByTag("td").get(5).getElementsByTag("font").get(0).text());
			System.out.println(node);
		}
	}
	
	
}
