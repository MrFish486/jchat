import java.net.*;
import java.io.IOException;
import java.util.regex.*;
import java.util.Arrays;

public class Server {
	private int port;
	private int messageHistoryLimit;
	private UserDataBase userTable;
	private MessageDataBase messageTable;
	public Server (int port, int messageHistoryLimit) {
		this.port = port;
		this.messageHistoryLimit = messageHistoryLimit;
		this.userTable = new UserDataBase();
		this.messageTable = new MessageDataBase(messageHistoryLimit);
	}
	public void listen () throws Exception {
		byte[] RcvData = new byte[1024];
		DatagramPacket rcv = new DatagramPacket(RcvData, RcvData.length);
		DatagramSocket socket = new DatagramSocket(port);
		String request, reply = "";
		Pattern a = Pattern.compile("[a-zA-Z0-9]{3,20};.{0,1000}");
		Pattern b = Pattern.compile("r;[a-zA-Z0-9]{3,20}");
		while (true) {
			socket.receive(rcv);
			request = new String(rcv.getData(), 0, rcv.getLength());
			if (a.matcher(request).matches()) {
				if (this.userTable.verifyUser(request.split(";")[0], rcv.getAddress())) {
					reply = "d";
					this.messageTable.send(new Message(
						this.userTable.users.get(this.userTable.getUser(request.split(";")[0], rcv.getAddress())),
						//String.join(";", Arrays.copyOfRange(request.split(";"), 1, request.split(";").length - 1))
						request.split(";")[1]
					));
				} else {
					reply = "!";
				}
				
			} else if (b.matcher(request).matches()) {
				if (this.userTable.addUser(new User(new PermissionSet(true), request.split(";")[1], rcv.getAddress()))) {
					reply = "d";
				} else {
					reply = "!";
				}
			} else if (request.equals("g;")) {
				reply = this.messageTable.prettyPrint();
			} else reply = "?";
			DatagramPacket outGoingPacket = new DatagramPacket(reply.getBytes(), reply.getBytes().length, rcv.getAddress(), rcv.getPort());
			try {
				socket.send(outGoingPacket);
			} catch (Exception e) {
				System.err.println("Failed to send reply to " + rcv.getAddress().toString() + ":" + String.valueOf(rcv.getPort()));
			}
		}
	}
	public static void main (String[] args) {
		Server server = new Server(4096, 10);
		Runner runner = new Runner(server);
		new Thread(runner).start();
	}
}
