import java.net.*;

public class User {
	public PermissionSet permissions;
	public String username;
	public INetAddress ip;
	public User (PermissionSet permissions, String username, INetAddress ip) {
		this.permissions = permissions;
		this.username = username;
		this.ip = ip;
	}
	public String handle () {
		return String.format("(%s):%s", this.ip.toString(), this.username);
	}
}
