package email;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;

import utility.StringConstants;

public class EmailScheme {
	private static final String emailAccount = "mypagecs201@gmail.com";
	private static final String emailPassword = "usctrojan";
	private static Map<String, Action> IdToAction;
	
	static {
		IdToAction = new HashMap<String, Action>();
	}
	
	public static void main(String [] args) {
		EmailScheme email = new EmailScheme();
		//.SendMessage("de1bna7e", new ChangeFirstNameAction(), StringConstants.CHANGEFIRSTNAMEACTION, "yad999@gmail.com");
	}
	
	public EmailScheme() {
		
	}
	
	public static Action GetAction(String actionId) {
		return IdToAction.get(actionId);
	}
	
	public void SendMessage(String actionId, Action action, String actionName, String emailString) {
		IdToAction.put(actionId, action);
		
		HtmlEmail email = new HtmlEmail();
		email.setHostName("smtp.gmail.com");
		email.setAuthentication(emailAccount, emailPassword);
		email.setDebug(true);
		email.setSmtpPort(587);
		email.setStartTLSEnabled(true);
		
		try {
			email.addTo(emailString);
			email.setFrom(emailAccount, StringConstants.SERVICENAME);
			email.setSubject(GetEmailSubject(actionName));
			String msg = FormatMessage(actionId, actionName);
			email.setHtmlMsg("<html><body><a href='" + msg + ">Sign Up</a></body></html>");
			email.setMsg(msg);
			email.send();
		} catch (EmailException e) {
			e.printStackTrace();
		}
	}
	
	private String GetEmailSubject(String name) {
		if(name.equals(StringConstants.SIGNUPACTION)) {
			return StringConstants.SERVICENAME + ": Sign Up Confirmation";
		} else if(name.equals(StringConstants.CHANGEFIRSTNAMEACTION)) {
			return StringConstants.SERVICENAME + ": Changing First Name Confirmation";
		} else if(name.equals(StringConstants.CHANGELASTNAMEACTION)) {
			return StringConstants.SERVICENAME + ": Changing Last Name Confirmation";
		} else if(name.equals(StringConstants.CHANGEEMAILACTION)) {
			return StringConstants.SERVICENAME + ": Changing Email Confirmation";
		} else if(name.equals(StringConstants.CHANGEPASSWORDACTION)) {
			return StringConstants.SERVICENAME + ": Changing Password Confirmation";
		} else if(name.equals(StringConstants.DELETEACCOUNTACTION)) {
			return StringConstants.SERVICENAME + ": Deleting Account Confirmation";
		}
		
		return StringConstants.SERVICENAME;
	}
	
	private String FormatMessage(String actionId, String actionName) {
		return "http://localhost:8080/MyPage/EmailLinkManager?" + 
			StringConstants.ACTIONID + "=" + actionId + "&" 
			+ StringConstants.ACTIONNAME + "=" + actionName;
	}
}
