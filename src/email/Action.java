package email;

import utility.StringConstants;

public abstract class Action {
	public final String name = StringConstants.GENERICACTION;
	
	public abstract void execute();
}
