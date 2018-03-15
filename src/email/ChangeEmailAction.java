package email;

import data.Database;
import data.User;
import utility.StringConstants;

public class ChangeEmailAction extends Action {
	public final String name = StringConstants.DELETEACCOUNTACTION;
	private User user;
	private Database db;
	private String newEmail;
	
	public ChangeEmailAction(User user, Database db, String email) {
		this.user = user;
		this.db = db;
		this.newEmail = email;
	}
	
	@Override
	public void execute() {
		user.setEmail(newEmail);
		db.UpdateUser(user);
	}
}
