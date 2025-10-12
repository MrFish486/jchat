import java.net.*;
import java.io.IOException;
import java.util.regex.*;

public class Server {
	private int port;
	private int messageHistoryLimit;
	private UserDataBase userTable;
	private MessageDataBase messageTable;
	public Server (int port, int messageHistoryLimit) {
		this.port = port;
		this.messageHistoryLimit;
		this.userTable = new UserDataBase(messageHistoryLimit);
		this.messageTable = new MessageDataBase();
	}
	public void listen () {
		byte[] RcvData = new byte[1024];
		DatagramPacket  rcv = new DatagramPacket(rcvdata, rcvdata.length);
		DatagramSocket  socket = new DatagramSocket(port);
		String request, reply;
		Pattern a = Pattern.compile("[a-zA-Z0-9]{3,};.*");
		Pattern b = Pattern.compile("r;[a-zA-Z0-9]{3,}");
		Pattern c = Pattern.compile("g;");
		while (true) {
			socket.receive(rcv);
			request = new String(rcv.getData(), 0, rcv.getLength());
			if (a.matcher(request).matches()) {
				if (this.userTable.verifyUser(request.split(";")[0], rcv.getAddress())) {

				} else {
					reply = "!";
				}
				
			} else if (b.matcher(request).matches()) {
				if (this.userTable.addUser(new User(new PermissionSet(true), request.split(";")[1], rcv.getAddress()))) {
					reply = "d";
				} else {
					reply = "!";
				}
			}
			DatagramPacket outGoingPacket = new DatagramPacket(reply.getBytes(), reply.getBytes().length, rcv.getAddress(), rcv.getPort());
			socket.send(outGoingPacket);
		}
	}
	public static void main (String[] args) {
		
	}
}
