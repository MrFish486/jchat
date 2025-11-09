public abstract class Bot {
	public int executionInterval = 200;
	public abstract boolean shouldSendMessage (String messages);
	public abstract String messageContent (String messages); // This is only called if bot.shouldSendMessage is true.
}
