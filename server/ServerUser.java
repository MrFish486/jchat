import java.net.*;

public class ServerUser extends User {
	public ServerUser () throws Exception {
		super(new PermissionSet(true), "[server]", InetAddress.getByName("localhost"));
	}
	public String handle () {
		return this.username;
	}
}
