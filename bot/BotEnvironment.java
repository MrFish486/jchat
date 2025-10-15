import java.net.*;

public class BotEnvironment implements Runnable {
	public InetAddress address;
	public int port;
	public String username;
	public Bot bot;
	public BotEnvironment (InetAddress address, int port, String username, Bot bot) throws Exception {
		this.address = address;
		this.port = port;
		this.username = username;
		this.bot = bot;
		BotEnvironment.get(this.address, this.port, "r;" + this.username); // Assert that account exists
	}
	public void run () {
		System.out.println("Bot started.");
		while (true) {
			try {
				String messages = BotEnvironment.get(this.address, this.port, "g;");
				if (this.bot.shouldSendMessage(messages)) {
					BotEnvironment.get(this.address, this.port, this.username + ";" + this.bot.messageContent(messages));
				}
				Thread.sleep(this.bot.executionInterval);
			} catch (Exception e) {
				System.err.println("An error occurred.");
				e.printStackTrace();
				System.out.println("Restarting...");
			}
		}
	}
	public static String get (InetAddress address, int port, String request) throws Exception {
		byte[] rcv = new byte[8192];
		DatagramSocket socket = new DatagramSocket();
		DatagramPacket packet = new DatagramPacket(request.getBytes(), request.getBytes().length, address, port);
		socket.connect(address, port);
		socket.send(packet);
		DatagramPacket rcvpacket = new DatagramPacket(rcv, rcv.length);
		socket.receive(rcvpacket);
		return new String(rcvpacket.getData(), 0, rcvpacket.getLength());
	}

}
