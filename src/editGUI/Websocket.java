package editGUI;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import data.Database;
import data.User;
import data.Website;

@ServerEndpoint("/editGUI")
public class Websocket {
	final static private Map<String, Session> sessions = new HashMap<String, Session>();
	final static private Map<String, ConcurrentEdit> concEdits = new HashMap<String, ConcurrentEdit>();
	final static private Map<Website, ConcurrentEdit> concEditsWeb = new HashMap<Website, ConcurrentEdit>();
	final static private Map<String, String> users = new HashMap<String, String>();
	final static private Map<String, Website> webNameToWebObject = new HashMap<String, Website>();
	final private static Lock lock = new ReentrantLock();
	
	@OnOpen
	public void onOpen(Session session) {
		lock.lock();
		session.setMaxTextMessageBufferSize(1*1024*1024);
		session.setMaxIdleTimeout(300000);
		sessions.put(session.getId(), session);
		lock.unlock();
	}
	
	@OnMessage
	public void onMessage(String message, Session session) {
		//lock.lock();
		String username = users.get(session.getId()); // Check if user has connected before
		if(username != null) { // User is sending an update message
			concEdits.get(username).update(message);
		} else { // User requires to be initialized
			Database db = new Database(new MongoClient(new MongoClientURI("mongodb://admin:mypage123@yousifd.vms.uscnsl.net/test")), "test");
			String websiteName = message.split(":")[0];
			username = message.split(":")[1];
			users.put(session.getId(), username);
			Website website = webNameToWebObject.get(websiteName);
			if(website == null) {
				website = db.ReadFromWebsitesCollection(websiteName);
			}
			
			User user = db.ReadFromUsersCollection(username);
			
			ConcurrentEdit concEdit = concEditsWeb.get(website);
			if(concEdit != null) { // Website is already mapped, so just add user
				concEdit.addUser(user, session);
				concEdits.put(username, concEdit);
				users.put(session.getId(), username);
			} else { // Website isn't mapped yet
				concEdit = new ConcurrentEdit(website);
				concEdit.addUser(user, session);
				users.put(session.getId(), username);
				concEdits.put(username, concEdit);
				concEditsWeb.put(website, concEdit);
				webNameToWebObject.put(websiteName, website);
			}
			//lock.unlock();
			//return website.ReadMainPageContents();
		}
		//lock.unlock();
		//return message;
	}
	
	@OnClose
	public void onClose(Session session) {
		lock.lock();
		sessions.remove(session.getId());
		String username = users.get(session.getId());
		if(username != null) {
			concEdits.remove(username);
		}
		users.remove(session.getId());
		lock.unlock();
	}
	
	@OnError
	public void onError(Throwable error) {
		error.printStackTrace();
	}
}
