package konishi.java.socketconnection.controller;

import konishi.java.socketconnection.base.TotalBase;

public class ThreadManager extends TotalBase implements Runnable {
	
	ServerController server;

	public ThreadManager(ServerController ctrl) throws Exception {
		server = ctrl;
		
		System.out.println("ThreadManager");
		serverThread();
	}
	
	public void serverThread() throws Exception {
		while (true) {
			server.wait();
			new Thread().start();
			notifyAll();
		}
	}
	
	@Override
	public void run() {
		try {
			Thread.sleep(500);
		} catch (Exception e) {
			errorStackTrace(e);
		}
	}
	
}
