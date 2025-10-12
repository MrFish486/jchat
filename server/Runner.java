public class Runner implements Runnable {
	private final Server executor;
	public Runner (Server executor) {
		this.executor = executor;
	}
	public void run () {
		while (true) {
			System.out.println("Environment started.");
			try {
				this.executor.listen();
			} catch (Exception e) {
				System.err.println("The server encountered an error. Restarting.");
				e.printStackTrace();
			}
		}
	}
}
