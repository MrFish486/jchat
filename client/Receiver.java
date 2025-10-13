import java.net.*;

public class Receiver implements Runnable {
	public InetAddress target;
	public int port;
	public String output;
	public int rcvDelay = 200;
	public Receiver (String output, InetAddress target, int port, int rcvDelay) {
		this.output = output;
		if (rcvDelay > 0) this.rcvDelay = rcvDelay;
		this.target = target;
		this.port = port;
	}
	public void run () {
		while (true) {
			try {
				this.output = Receiver.get(this.target, this.port, "g;");
				System.out.println(this.output);
				System.out.println("hello");
				Thread.sleep(this.rcvDelay);
			} catch (Exception e) {
				System.err.println("uh-oh!");
				e.printStackTrace();
				break;
			}
			System.out.println(this.output);
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
