package data;

import java.io.File;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.util.Vector;

import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;
import org.mongodb.morphia.annotations.Reference;

import utility.PasswordHasher;

@Entity("users")

public class User {
	@Id
	private ObjectId id;
	@Indexed
	private String username;
	private String passwordHash;
	private String passwordSalt;
	private String email;
	private String fName;
	private String lName;
	@Reference
	private Vector<Website> websites;
	String path;
	
	public User() {
		websites = new Vector<Website>();
	}
	
	public User(String username, String path) {
		this.username = username;
		this.path = path+"/"+username;
		File f = new File(path);
		f.mkdir();
		websites = new Vector<Website>();
	}
	
	public User(String fName, String lName, String email, String username, String password) {
		this.fName = fName;
		this.lName = lName;
		this.email = email;
		this.username = username;
		
		setPasswordHash(password);
		websites = new Vector<Website>();
	}
	
	public String GetFullName() {
		return fName + " " + lName;
	}
	
	public void AddWebsite(Website website) {
		this.websites.add(website);
	}
	
	public void RemoveWebsite(Website website) {
		this.websites.remove(website);
	}

	public ObjectId getId() {
		return id;
	}

	public void setId(ObjectId id) {
		this.id = id;
	}
	
	public boolean CheckValidPassword(String password) {
		String hashedPassword = PasswordHasher.GetSHA1Password(password, this.passwordSalt.getBytes());
		return this.passwordHash.equals(hashedPassword);
	}

	public void setPasswordHash(String password) {
		try {
			this.passwordSalt = new String(PasswordHasher.GetSalt());
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		this.passwordHash = PasswordHasher.GetSHA1Password(password, this.passwordSalt.getBytes());
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getfName() {
		return fName;
	}

	public void setfName(String fName) {
		this.fName = fName;
	}

	public String getlName() {
		return lName;
	}

	public void setlName(String lName) {
		this.lName = lName;
	}

	public Vector<Website> getWebsites() {
		return websites;
	}
	
	public boolean isWebsite(String name) {
		for(int i = 0; i < websites.size(); i++) {
			if (name.toLowerCase().equals(websites.get(i).getName().toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}

	public void setWebsites(Vector<Website> websites) {
		this.websites = websites;
	}
	//share specific website with a specific user
	public void shareWebsite(String websiteName, String userName) {
		for (Website ws: websites) {
			if (ws.getName().equals(websiteName)) {
				ws.addSharedUser(userName);
				return;
			}
		}
	}
	public String getUsername() {
		return username;
	}
	//delete website from vector given websiteName
	public void deleteWebsite(String websiteName) {
		for (int i=0; i<websites.size(); i++) {
			if (websites.get(i).getName().toLowerCase().equals(websiteName.toLowerCase())) {
				System.out.println(websites.get(i).getName() + " removed");
				websites.remove(i);
				return;
			}
		}
	}
	
	public void publishWebsite(String websiteName) {
		for (Website ws: websites) {
			if (ws.getName().equals(websiteName)) {
				ws.setIsPublished(true);
				System.out.println("WEBSITE: " + ws.getName() + " has been published!");
				return;
			}
		}
	}
}
