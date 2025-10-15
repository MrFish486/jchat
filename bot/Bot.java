interface Bot {
	public int executionInterval = 200;
	public boolean shouldSendMessage (String messages);
	public String messageContent (String messages); // This is only called if bot.shouldSendMessage is true.
}
