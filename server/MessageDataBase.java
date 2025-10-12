import java.util.ArrayList;

public class MessageDataBase {
	public ArrayList <Message> messages;
	public int historyLimit;
	public MessageDataBase (int historyLimit) {
		this.messages = new ArrayList <Message> ();
		this.historyLimit = historyLimit;
	}
	public void clean (int thresh) {
		if (this.messages.size() <= thresh) return;
		while (this.messages.size() > thresh) {
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
	public String prettyPrint () {
		int largestHandleLength = 0;
		for (int i = 0; i < this.messages.size(); i ++) {
			if (this.messages.get(i).author.handle().length() > largestHandleLength) largestHandleLength = this.messages.get(i).author.handle().length();
		}
		String output = "";
		for (int i = 0; i < this.messages.size(); i ++) {
			output += String.format("%" + largestHandleLength + "s | %s", this.messages.get(i).author.handle(), this.messages.get(i).content);
			if (i != this.messages.size()) output += "\n";
		}
		return output;
	}
}
