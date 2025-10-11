import java.util.ArrayList;

public class MessageDataBase {
	public ArrayList <Message> messages;
	public int historyLimit;
	public MessageDataBase (int historyLimit) {
		this.messages = new ArrayList <Message> ();
	}
	public void clean (int historyLimit) {
		if (this.messages.size() <= limit) return;
		while (this.messages.size() > limit) {
			this.messages.remove(0);
		}
	}
	public boolean send (Message message) {
		if (message.author.permissions.canPostMessages) {
			this.messages.add(message);
			this.clean(this.historyLimit);
			return true;
		}
		return false;
	}
}
