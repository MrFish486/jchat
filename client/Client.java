import java.net.*;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Client {
	public InetAddress address;
	public int port = 0;
	public String username;
	public static void main (String args[]) throws Exception {
		Client client = new Client();
		if (args.length > 0 && args[0].equals("register")) {
			client.promptConfigNoLogin();
			if (args.length != 2) {
				System.err.println("Incorrect argument count. Expected 'register <username>'.");
				System.exit(6);
			}
			String reply = Receiver.get(client.address, client.port, "r;" + args[1]);
			if (reply.equals("d")) {
				System.out.println("The account was successfully created. This program will now stop.");
				System.exit(7);
			} else if (reply.equals("!")) {
				System.err.println("Someone on your network has already registered an account with that name. Try a different one.");
				System.exit(8);
			} else {
				System.err.println("An unknown error occurred. Your account was likely not registered.");
				System.exit(9);
			}
		} else {
			client.promptConfig();
			Receiver receiver = new Receiver("", client.address, client.port, 200, true);
			Thread receiverThread = new Thread(receiver);
			receiverThread.start();
			Scanner stdinReader = new Scanner(System.in);
			while (true) {
				Receiver.get(client.address, client.port, client.username + ";" + stdinReader.nextLine());
			}
		}
	}
	public Client () throws Exception {
		this.address = InetAddress.getByName("0.0.0.0");
	}
	public static void square (int h, int w, char b) {
		System.out.println(String.valueOf(b).repeat(w));
		for (int y = 1; y < h - 1; y ++) {
			System.out.println(b + " ".repeat(w - 2) + b);
		}
		System.out.println(String.valueOf(b).repeat(w));
	}
	public static void squareAt (int row, int h, int w, char b) {
		System.out.println("\033[" + row + "H");
		System.out.println(String.valueOf(b).repeat(w));
		for (int y = 1; y < h - 1; y ++) {
			System.out.println(b + " ".repeat(w - 2) + b);
		}
		System.out.println(String.valueOf(b).repeat(w));
	}
	public static void clear () {  
		System.out.print("\033[H\033[2J\033[3J");
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
					} else if (line.split(" ")[0].equals("username")) {
						this.username = line.split(" ")[1];
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
			System.out.print("Please input your username: ");
			handle.println("username " + input.readLine());
			handle.close();
			System.out.println("The configuration file has been successfully written. This program will now stop.");
			System.exit(1);
		}
	}
	public void promptConfigNoLogin () throws Exception {
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
