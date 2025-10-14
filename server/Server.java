import java.net.*;
import java.io.IOException;
import java.util.regex.*;
import java.util.Arrays;
import java.util.Date;
import java.text.SimpleDateFormat;

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
						String.join(";", Arrays.copyOfRange(request.split(";"), 1, request.split(";").length))
						//request.split(";")[1]
					));
				} else {
					reply = "!";
				}
				
			} else if (b.matcher(request).matches()) {
				if (this.userTable.addUser(new User(new PermissionSet(true), request.split(";")[1], rcv.getAddress()))) {
					reply = "d";
					this.messageTable.sendServerMessage("A new user has registered. (" + this.userTable.users.get(this.userTable.getUser(request.split(";")[1], rcv.getAddress())).handle() + ")");
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
	public static void main (String[] args) throws Exception {
		int port = 4096;
		if (args.length > 0) {
			port = Integer.parseInt(args[0]);
			System.out.println("Using port " + port + ".");
		} else {
			System.out.println("No port provided. Defaulting to port 4096.");
		}
		Server server = new Server(port, 10);
		Runner runner = new Runner(server);
		new Thread(runner).start();
	}
	public String time () {
		return new SimpleDateFormat("MM/dd HH:mm:ss").format(new Date());
	}
}
