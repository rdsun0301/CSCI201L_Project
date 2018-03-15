package email;

import data.Database;
import data.User;
import utility.StringConstants;

public class ChangeFirstNameAction extends Action {
	public final String name = StringConstants.CHANGEFIRSTNAMEACTION;
	private User user;
	private Database db;
	private String fName;
	
	public ChangeFirstNameAction(User user, Database db, String firstName) {
		this.user = user;
		this.db = db;
		this.fName = firstName;
	}
	
	@Override
	public void execute() {
		user.setfName(fName);
		db.UpdateUser(user);
	}
}
