//Multi-threaded updating of users 

package editGUI;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.Session;

import data.User;
import data.Website;
import utility.StringConstants;

public class ConcurrentEdit extends Thread{
	private Website website;
	private ArrayList <User> users = new ArrayList<User>();
	private Lock lock = new ReentrantLock();
	//
	private Map <String, Session> sessionMap = new HashMap<String, Session>(); 
	//Constructor
	public ConcurrentEdit(Website myWebsite) {
		website = myWebsite;
		sessionMap = new HashMap<String, Session>();
	}
	//adds a user to the map
	public void addUser(User newUser, Session mySession) {
		lock.lock();
		sessionMap.put(newUser.getUsername(), mySession);
		users.add(newUser);
		lock.unlock();
	}
	public void update(String html) {
		synchronized(website){
			updateWebsite(html);
			updateUsers(html);
		}
	}
	public void updateWebsite(String html) {
		//write string to html of mainpage
		String path = website.getMainPage();
		
		System.out.println(new File(path));

		try {

			FileWriter fw = new FileWriter(path);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(html);
			bw.close();
			fw.close();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	//Nested class 
	private class UpdateThread extends Thread {
		private String html;
		private Session session;
		
		UpdateThread(String myHtml, Session mySession) {
			html = myHtml;
			session = mySession;
			this.start();
		}
		
		public void run () {
			try {
				session.getBasicRemote().sendText(html);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	} //end nested class
	
	//Multi-threading
	public void updateUsers(String html) {
		for (User u: users) {
			Session mySession = sessionMap.get(u.getUsername());
			new UpdateThread(html, mySession);
		}
	}
	public static void main (String [] args) {
		Website mysite = new Website("", StringConstants.LOCALREPO, "alan", "site1");
		ConcurrentEdit myCE = new ConcurrentEdit(mysite);
		myCE.update("hello darkness my old friend");
	}
}
