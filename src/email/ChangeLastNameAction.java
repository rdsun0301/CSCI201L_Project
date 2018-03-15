package email;

import data.Database;
import data.User;
import utility.StringConstants;

public class ChangeLastNameAction extends Action {
	public final String name = StringConstants.DELETEACCOUNTACTION;
	private User user;
	private Database db;
	private String newLName;
	
	public ChangeLastNameAction(User user, Database db, String lName) {
		this.user = user;
		this.db = db;
		this.newLName = lName;
	}
	
	@Override
	public void execute() {
		user.setlName(newLName);
		db.UpdateUser(user);
	}
}
