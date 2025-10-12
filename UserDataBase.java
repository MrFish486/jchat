import java.util.ArrayList;
import java.net.*;

public class UserDataBase {
	public ArrayList <User> users;
	public UserDataBase () {
		this.users = new ArrayList <User> ();
	}
	public boolean addUser (User user) {
		if (user.username.contains(";") || user.username.length() < 2) return false;
		for (int i = 0; i < this.users.size(); i ++) {
			if (user.username.equals(this.users.get(i).username)) {
				return false;
			}
		}
		this.users.add(user);
		return true;
	}
	public boolean verifyUser (String username, InetAddress ip) {
		for (int i = 0; i < this.users.size(); i ++) {
			if (username.equals(this.users.get(i).username) && ip.toString().equals(this.users.get(i).ip.toString())) {
				return true;
			}
		}
		return false;
	}
}
