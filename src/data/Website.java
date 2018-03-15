package data;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Vector;

import org.apache.commons.io.FileUtils;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;
import org.mongodb.morphia.annotations.Indexed;

@Entity("website")
public class Website {
	
	@Id
	private ObjectId id;

	@Indexed
	private String owner;
	@Indexed
	private String name;
	private String mainPage;
	private String cssFile;
	private String jsFile;
	private String directory;
	private boolean isPublished;
	
	private Vector<String> sharedUsers;
	
	public Website() {
		sharedUsers = new Vector<String>();
	}
	
	public Website(String templatePath, String directoryName, String owner, String websiteName) {
		sharedUsers = new Vector<String>();
		this.name = websiteName;		
		this.owner = owner;
		
		String userDirectory = directoryName+"/"+owner;
		directory = directoryName+"/"+owner+"/"+websiteName;
		mainPage = directory+"/index.html";
		cssFile = directory+"/index.css";
		jsFile = directory+"/index.js";
		
		File f = new File(userDirectory);
		f.mkdir();
		f = new File(directory);
		f.mkdir();
		//Put template in index.html
		File template = new File(templatePath);
		File dest = new File(mainPage);
		try {
			FileUtils.copyFile(template, dest);
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			//f = new File(mainPage);
			//f.createNewFile();
			f = new File(cssFile);
			f.createNewFile();
			f = new File(jsFile);
			f.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public ObjectId getId() {
		return id;
	}
	public void setId(ObjectId id) {
		this.id = id;
	}
	public String getOwner() {
		return owner;
	}
	public void setOwner(String owner) {
		this.owner = owner;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMainPage() {
		return mainPage;
	}

	public void setMainPage(String mainPage) {
		this.mainPage = mainPage;
	}

	public String getCssFile() {
		return cssFile;
	}

	public void setCssFile(String cssFile) {
		this.cssFile = cssFile;
	}

	public String getJsFile() {
		return jsFile;
	}

	public void setJsFile(String jsFile) {
		this.jsFile = jsFile;
	}

	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public Vector<String> getSharedUsers() {
		return sharedUsers;
	}
	public void setSharedUsers(Vector<String> sharedUsers) {
		this.sharedUsers = sharedUsers;
	}
	public void addSharedUser(String user) {
		sharedUsers.add(user);
	}
	public void setIsPublished(boolean status) {
		isPublished = status;
	}
	public boolean getIsPublished() {
		return isPublished;
	}
	
	public boolean isSharedUser(String s) {
		for(int i = 0; i < sharedUsers.size(); i++) {
			if (sharedUsers.get(i).equals(s)) {
				return true;
			}
		}
		return false;
	}
	
	public String ReadMainPageContents() {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(mainPage));
			//System.out.println(Files.readAllBytes(Paths.get(mainPage)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Encoded: " + encoded);
		return new String(encoded, StandardCharsets.UTF_8);
	}
	
	public String ReadAnyPageContents(String pageName) {
		byte[] encoded = null;
		try {
			encoded = Files.readAllBytes(Paths.get(directory+"\\"+pageName+".html"));
			//System.out.println(Files.readAllBytes(Paths.get(mainPage)));
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Encoded: " + encoded);
		return new String(encoded, StandardCharsets.UTF_8);
	}
	
}
