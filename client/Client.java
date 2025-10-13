import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Client {
	public InetAddress address;
	public int port = 0;
	public String messages = "";
	public static void main (String args[]) throws Exception {
		Client client = new Client();
		client.promptConfig();
		Thread receiver = new Thread(new Receiver(client.messages, client.address, client.port, 200));
		receiver.start();
	}
	public Client () throws Exception {
		this.address = InetAddress.getByName("0.0.0.0");
	}
	public void promptConfig () throws Exception {
		try {
			File handle = new File(System.getProperty("user.home") + "/.jchat");
			Scanner read  = new Scanner(handle);
			String line;
			while (read.hasNextLine()) {
				line = read.nextLine();
				try {
					if (line.split(" ").length != 2) {
						System.out.println("An error occurred while parsing the configuration file. The file was located and read successfully, but it can not be recognized by this program. (a)");
						System.exit(2);
						return;
					}
					if (line.split(" ")[0].equals("ip")) {
						this.address = InetAddress.getByName(line.split(" ")[1]);
					} else if (line.split(" ")[0].equals("port")) {
						this.port = Integer.parseInt(line.split(" ")[1]);
					} else {
						System.out.println("An error occurred while parsing the configuration file. The file was located and read successfully, but it can not be recognized by this program. (b)");
						System.exit(3);
					}
				} catch (Exception eee) {
					System.err.println("An error occurred while parsing the configuration file. The file was located and read successfully, but it can not be recognized by this program. (c)");
					System.exit(4);
				}
			}
			read.close();
		} catch (FileNotFoundException ee) {
			PrintWriter handle = new PrintWriter(System.getProperty("user.home") + "/.jchat", "UTF-8");
			BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
			System.err.println("The configuration file is missing. This is likely because this program is being run for the first time.");
			System.out.print("Please input the ip address of the server: ");
			handle.println("ip " + input.readLine());
			System.out.print("Please input the port that the server is running on: ");
			handle.println("port " + input.readLine());
			handle.close();
			System.out.println("The configuration file has been successfully written. This program will now stop.");
			System.exit(1);
		}
	}
}
