package utility;

public class StringConstants {
	// Service Names
	public static final String SERVICENAME = "MyPage";
	
	//Database URL
	public static final String DATAURL = "mongodb://rdsun0301:pass123@cluster0-shard-00-00-jtlja.mongodb.net:27017,cluster0-shard-00-01-jtlja.mongodb.net:27017,cluster0-shard-00-02-jtlja.mongodb.net:27017/test?ssl=true&replicaSet=Cluster0-shard-0&authSource=admin&retryWrites=true";
	
	// Actions
	public static final String GENERICACTION = "GenericAction";
	public static final String SIGNUPACTION = "UserSignUp";
	public static final String CHANGEFIRSTNAMEACTION = "ChangeFirstName";
	public static final String CHANGELASTNAMEACTION = "ChangeLastName";
	public static final String CHANGEEMAILACTION = "ChangeEmail";
	public static final String CHANGEPASSWORDACTION = "ChangePassword";
	public static final String DELETEACCOUNTACTION = "DeleteAccount";
	
	// Data Objects
	public static final String USER = "user";
	public static final String WEBSITE = "website";
	public static final String CURRENTUSER = "currentuser";
	
	// Query Fields
	public static final String USERNAME = "username";
	public static final String OWNER = "owner";
	public static final String WEBSITENAME = "name";
	
	// Request Parameters
	public static final String ACTIONID = "actionId";
	public static final String ACTIONNAME = "actionName";
	public static final String CONTENTS = "contents";
	public static final String NEWWEBPAGE = "newWebpage";
	
	// Directory
	public static final String LOCALREPO = "/localRepo";
	public static final String TEMPLATE = "html/Template.html";
	public static final String GUESTPAGE = "html/GuestPage.html";
	
	//Github
	public static final String GITURL = "https://github.com/mypagecs201/mypagecs201.github.io.git";
	public static final String GITUSERNAME = "mypagecs201";
	public static final String GITPASSWORD = "usctrojan1";
}
