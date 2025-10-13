import java.net.*;

public class Receiver implements Runnable {
	public InetAddress target;
	public int port;
	public String previousOutput = "";
	public String output;
	public int rcvDelay = 200;
	public boolean shouldRender;
	public Receiver (String output, InetAddress target, int port, int rcvDelay, boolean shouldRender) {
		this.output = output;
		if (rcvDelay > 0) this.rcvDelay = rcvDelay;
		this.target = target;
		this.port = port;
		this.shouldRender = shouldRender;
	}
	public void run () {
		while (true) {
			try {
				this.output = Receiver.get(this.target, this.port, "g;");
				Thread.sleep(this.rcvDelay);
				this.render();
			} catch (Exception e) {
				System.err.println("uh-oh!");
				e.printStackTrace();
				break;
			}
		}
	}
	public void render () throws Exception {
		if (shouldRender) {
			if (!this.previousOutput.equals(this.output)) {
				int maxWidth = 0;
				for (int i = 0; i < this.output.split("\n").length; i ++) {
					if (this.output.split("\n")[i].length() > maxWidth) maxWidth = this.output.split("\n")[i].length();
				}
				Client.clear();
				Client.square(this.output.split("\n").length + 2, maxWidth + 3, '#');
				for (int i = 0; i < this.output.split("\n").length; i ++) {
					System.out.print("\033[" + (i + 2) + ";2H" + this.output.split("\n")[i]);
				}
				Client.squareAt(this.output.split("\n").length + 2, 3, this.calculateMaxWidth() + 3, '#');
				System.out.print("\033[" + (this.output.split("\n").length + 4) + ";2H");
				Thread.sleep(100);
				this.previousOutput = this.output;
			}
		}
	}
	public int calculateMaxWidth () throws Exception {
		this.output = Receiver.get(this.target, this.port, "g;");
		int maxWidth = 0;
		for (int i = 0; i < this.output.split("\n").length; i ++) {
			if (this.output.split("\n")[i].length() > maxWidth) {
				maxWidth = this.output.split("\n")[i].length();
			}
		}
		return maxWidth;
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
