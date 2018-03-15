package email;

import data.Database;
import data.User;
import utility.StringConstants;

public class DeleteAccountAction extends Action {
	public final String name = StringConstants.DELETEACCOUNTACTION;
	private User user;
	private Database db;
	
	public DeleteAccountAction(User user, Database db) {
		this.user = user;
		this.db = db;
	}
	@Override
	public void execute() {
		db.DeleteUser(user.getUsername());
	}
}
