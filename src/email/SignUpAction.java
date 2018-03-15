package email;

import data.Database;
import data.User;
import utility.StringConstants;

public class SignUpAction extends Action {
	public final String name = StringConstants.SIGNUPACTION;
	private User user;
	private Database db;
	
	public SignUpAction(User user, Database db) {
		this.user = user;
		this.db = db;
	}

	@Override
	public void execute() {
		db.WriteToUsersCollection(this.user);
	}

}
