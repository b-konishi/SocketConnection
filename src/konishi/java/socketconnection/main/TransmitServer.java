package konishi.java.socketconnection.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.controller.ServerController;
import konishi.java.socketconnection.controller.ThreadManager;
import konishi.java.socketconnection.model.StoreData;

/**
 * TransmitBaseをラップするクラスです。
 * @version 1.0.0
 * @author konishi
 *
 */
public class TransmitServer extends TransmitBase implements Runnable {
	private ServerSocket server = null;
	private ServerController serverCtrl = null;
	
	public TransmitServer() throws Exception {
		super();
		server = new ServerSocket(StoreData.PORT);
		serverCtrl = new ServerController();
		
		new Thread(this).start();
	}
	
	/**
	 * スレッドとして、様々な通信処理を行います。
	 */
	@Override
	public void run() {
		try{
			while (true) {
				System.out.println("Accept from Client...");
				setup();
				System.out.println("Accepted!!");
			}
		} catch (Exception e) {
			errorStackTrace(e);
		}
	}
	
	public void setup() throws Exception {
		
		socket = server.accept();
		
		new Thread(serverCtrl).start();
		ThreadManager manager = new ThreadManager(serverCtrl);
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void closeTransport() throws IOException {
		super.closeTransport();
		
		server.close();
		stackTrace();
	}
}
