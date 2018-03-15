package email;

import data.Database;
import data.User;
import utility.StringConstants;

public class ChangePasswordAction extends Action {
	public final String name = StringConstants.CHANGEPASSWORDACTION;
	private User user;
	private Database db;
	private String newPassword;
	
	public ChangePasswordAction(User user, Database db, String password) {
		this.user = user;
		this.db = db;
		this.newPassword = password;
	}
	
	@Override
	public void execute() {
		user.setPasswordHash(newPassword);
		db.UpdateUser(user);
	}
}
