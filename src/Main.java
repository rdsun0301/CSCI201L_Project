import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;

import data.Database;
import data.User;
import email.EmailScheme;
import email.SignUpAction;
import utility.StringConstants;
import webHosting.Webhost;

public class Main {
	public static void main(String [] args) {
		//Clone the github repo
		Webhost myPage = new Webhost (StringConstants.GITURL, StringConstants.LOCALREPO, 
				StringConstants.GITUSERNAME, StringConstants.GITPASSWORD);
		myPage.cloneRepo();
		// Test Password
//		User userP = new User("TESTING");
//		userP.setPasswordHash("test");
//		System.out.println("Is Valid Password: " + userP.CheckValidPassword("abc"));
//		
//		// Test Database
//		Database db = new Database(new MongoClient(new MongoClientURI("mongodb://admin:mypage123@yousifd.vms.uscnsl.net/test")), "test");
//		
//		final User user = new User("testYousif");
//		user.setfName("Test");
//		
//		db.WriteToUsersCollection(user);
//		
//		user.setlName("TESTT");
//		db.UpdateUser(user);
//		
//		System.out.println(db.ReadFromUsersCollection("testYousif").getUsername());
//		
//		final User user1 = new User("NewTest");
//		db.WriteToUsersCollection(user1);
//		System.out.println(db.ReadFromUsersCollection("NewTest").getUsername());
//		
//		db.DeleteUser(user1);
//		if(db.ReadFromUsersCollection("NewTest") == null) {
//			System.out.println("User was removed");
//		} else {
//			System.out.println("User was not Removed");
//		}
//		
//		// Test Email Scheme
//		EmailScheme email = new EmailScheme();
//		SignUpAction sua = new SignUpAction(user1, db);
//		//email.SendMessage("1234", sua, sua.name);
//		email.GetAction("1234").execute();
	}
}
