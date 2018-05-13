package data;

import java.util.HashMap;
import java.util.Map;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.Morphia;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import utility.StringConstants;

public class Database {
	private final Morphia morphia;
	private final Datastore datastore;
	private Map<String, User> users;
	private Map<String, Website> websites;
	
	public static void main(String [] args) {
		Database db = new Database(new MongoClient(new MongoClientURI(StringConstants.DATAURL)), "test");

		User user = new User("Richard", "Sun", "sunr@usc.edu", "sunr", "qwe");
		db.WriteToUsersCollection(user);
	}
	
	public Database(MongoClient mc, String db) {
		morphia = new Morphia();
		morphia.mapPackage("data");
		datastore = morphia.createDatastore(mc, db);
		users = new HashMap<String, User>();
		websites = new HashMap<String, Website>();
	}
	
	public void WriteToUsersCollection(User user) {
		this.datastore.save(user);
		users.put(user.getUsername(), user);
	}
	
	public void WriteToWebsitesCollection(Website website) {
		this.datastore.save(website);
		websites.put(website.getName(), website);
	}
	
	public User ReadFromUsersCollection(String username) {
		User user = datastore.createQuery(User.class).field(StringConstants.USERNAME).equal(username).get();
		if(user != null) {
			users.put(user.getUsername(), user);
		}
		return user;
	}
	
	public Website ReadFromWebsitesCollection(String websiteName) {
		Website website = datastore.createQuery(Website.class).field(StringConstants.WEBSITENAME).equal(websiteName).get();
		if(website != null) {
			websites.put(website.getName(), website);
		}
		return website;
	}
	
	public User ReadFromUsersCollection(User user) {
		return datastore.get(user);
	}
	
	public Website ReadFromWebsitesCollection(Website website) {
		return datastore.get(website);
	}
	
	public void UpdateUser(User user) {
		//don't use merge
		datastore.save(user);
		users.put(user.getUsername(), user);
	}
	
	public void UpdateWebsite(Website website) {
		//don't use merge
		datastore.save(website);
		websites.put(website.getName(), website);
	}
	
	public void DeleteUser(User user) {
		this.datastore.delete(user);
		users.remove(user);
	}
	
	public void DeleteWebsite(Website website) {
		this.datastore.delete(website);
		websites.remove(website);
	}
	
	public void DeleteUser(String username) {
		DeleteUser(ReadFromUsersCollection(username));
	}
	
	public void DeleteWebsite(String websiteName) {
		DeleteWebsite(ReadFromWebsitesCollection(websiteName));
	}
	
	public Datastore GetDatastore() {
		return this.datastore;
	}
}
