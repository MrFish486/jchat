import java.net.*;
import java.io.IOException;
import java.util.regex.*;

public class Server {
	private int port;
	private int messageHistoryLimit;
	private UserDataBase userTable;
	private MessageDataBase messageTable;
	public Server (int port) {
		this.port = port;
		this.userTable = new UserDataBase();
		this.messageTable = new MessageDataBase();
	}
	public void listen () {
		byte[] RcvData = new byte[1024];
		DatagramPacket  rcv = new DatagramPacket(rcvdata, rcvdata.length);
		DatagramSocket  socket = new DatagramSocket(port);
		String request, reply;
		Pattern a = Pattern.compile("User;");
		while (true) {
			socket.receive(rcv);
			request = new String(rcv.getData(), 0, rcv.getLength());
			
			DatagramPacket outGoingPacket = new DatagramPacket(reply.getBytes(), reply.getBytes().length, rcv.getAddress(), rcv.getPort());
			socket.send(outGoingPacket);
		}
	}
	public static void main (String[] args) {
		
	}
}
