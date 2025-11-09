import java.net.*;
import java.util.Random;

public class Example {
	public static void main (String args[]) throws Exception {
		BotEnvironment env = new BotEnvironment(
			InetAddress.getByName("localhost" /* replace with server address */),
			8001, /* replace with server port */
			"BasicBot", /* replace with bot name */
			new BasicBot () /* replace with bot instance */
		);
		Thread executionThread = new Thread(env);
		executionThread.start();
	}
}
class BasicBot extends Bot {
	Random random;
	public BasicBot () {
		this.random = new Random();
	}
	public boolean shouldSendMessage (String messages) {
		return /*messages.split("\n")[messages.split("\n").length - 1].contains("coinflip");*/ true;
	}
	public String messageContent (String messages) {
		return this.random.nextBoolean() ? "Heads" : "Tails";
	}
}
