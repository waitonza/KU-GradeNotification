/**
 * 
 */
package parser;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

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
	private String mode = "";
	
	private String yourname;
	private String sessionId;
	private Date last_update_date;
	private List<CourseNode> old_courseList; 
	private List<CourseNode> current_courseList; 
	
	private Response res = null;
	private Document doc = null;
	private boolean isOnline;
	
	public KUGradeParser() {
		
	}
	
	public KUGradeParser(String username, String password, String mode) {
		this.username = username;
		this.password = password;
		this.mode = mode;
		this.old_courseList = new ArrayList<CourseNode>();
		this.current_courseList = new ArrayList<CourseNode>();
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
			this.isOnline = false;
		} else {
			System.out.println("Login successful.");
			System.out.println("**** Note : You can keep open application away for notify!! ****");
			System.out.println("**** Note : This application will update infomation in every 30 second!! ****");
			System.out.println("------------------------------------------------------------------------------------");
			System.out.println("Last grade info!");
			System.out.println("------------------------------------------------------------------------------------");
			parse();
			showGrade();
			this.isOnline = true;
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
			System.out.println("Cannot refresh, Try again!");
		}
		
		if (doc.getElementsByClass("searchName").get(0).text().toString().equals("ชื่อผู้ใช้:")) {
			System.out.println("Session time out, Try to reconnect");
			login();
		}
	}
	
	public void parse() {
		refresh();
		Element result_table = doc.getElementsByTag("table").get(6);
		this.yourname = doc.getElementsByClass("mainMenu").get(0).text();
		Elements result_data = result_table.getElementsByTag("tr");
		old_courseList = new ArrayList<CourseNode>(current_courseList);
		current_courseList.clear();
		for	(int i = 1; i < result_data.size() ; i++) {
			Element element_node = result_data.get(i);
			CourseNode node = new CourseNode();
			node.setId(element_node.getElementsByTag("td").get(1).getElementsByTag("font").get(0).text());
			node.setName(element_node.getElementsByTag("td").get(2).getElementsByTag("font").get(1).text());
			node.setSec(element_node.getElementsByTag("td").get(3).getElementsByTag("font").get(0).text());
			node.setGrade(element_node.getElementsByTag("td").get(4).getElementsByTag("font").get(1).text());
			node.setStatus(element_node.getElementsByTag("td").get(5).getElementsByTag("font").get(0).text());
			current_courseList.add(node);
		}
		last_update_date = new Date();
		System.out.println("@[ " + last_update_date +" ]" + " Update new data");
		if(checkNotify()) {
			isOnline = false;
			if(this.mode.equals("s")) {
				showGrade();
			} else {
				System.out.println("Please visit https://grade-std.ku.ac.th/GSTU_login.php to view grade!!");
			}
			playSound("http://www.burninglotus.com/dogtoys/sounds/dtbeeper.wav");
			playSound("http://www.burninglotus.com/dogtoys/sounds/dtbeeper.wav");
		}
	}
	
	private boolean checkNotify() {
		if(old_courseList.size() == 0) return false;
		for(int i = 0; i < current_courseList.size(); i++) {
			if(!current_courseList.get(i).equals(old_courseList.get(i))) {
				// Notify New Grade Come out
				System.out.println("@[ " + last_update_date +" ]" + " New Grade Come out on " + old_courseList.get(i).getName());
				return true;
			}
		}
		return false;
	}
	
	private void showGrade() {
		System.out.println();
		System.out.println("Your ID/Name : " + this.yourname);
		System.out.println("Last update time result : " + this.last_update_date);
		System.out.println("------------------------------------------------------------------------------------");
		for (CourseNode node : current_courseList) {
			int namelen = node.getName().length();
			int indent_width = 60 - namelen;
			String print_format = "%s %s %"+ indent_width +"s\n";
			System.out.printf(print_format,node.getId(),node.getName(),node.getGrade());
		}
		System.out.println("------------------------------------------------------------------------------------");
	}
	
	 public void run() {
		 while( this.isOnline ) {
			 try {
	             parse();       		// method that updates time
				 Thread.sleep(30*1000);  // sleep 30 second by default
	         } catch ( InterruptedException e ) { /*ignore it */ }
	     }
	 }
	 
	 public static void playSound(final String url) {
		 try {
			 Clip clip = AudioSystem.getClip();
		     InputStream is = new URL(url).openStream();
		     BufferedInputStream bis = new BufferedInputStream( is );
		     AudioInputStream inputStream = AudioSystem.getAudioInputStream(bis);
	    	 clip.open(inputStream);
			 clip.start();
		     Thread.sleep(3*1000);
		 } catch (Exception e) {
		     System.err.println(e.getMessage());
		 }     
	 }
	
}
