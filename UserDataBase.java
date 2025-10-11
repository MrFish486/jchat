import java.util.ArrayList;

public class UserDataBase {
	public ArrayList <User> users;
	public UserDataBase () {
		this.users = new ArrayList <User> ();
	}
	public boolean addUser (User user) {
		for (int i = 0; i < this.users.size(); i ++) {
			if (user.username.equals(this.users.get(i).username)) {
				return false;
			}
		}
		this.users.add(user);
		return true;
	}
	public boolean verifyUser (String username, INetAddress ip) {
		for (int i = 0; i < this.users.size(); i ++) {
			if (user.username.equals(this.users.get(i).username) && user.ip.toString().equals(this.users.get(i).ip.toString())) {
				return true;
			}
		}
		return false;
	}
}
