package konishi.java.socketconnection.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.model.StoreData;

/**
 * TransmitBaseをラップするクラスです。
 * @version 1.0.0
 * @author konishi
 *
 */
public class TransmitServer extends TransmitBase implements Runnable {
	private ServerSocket server = null;
	
	public TransmitServer() throws Exception {
		super();
		server = new ServerSocket(StoreData.PORT);
		
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
			e.printStackTrace();
		}
	}
	
	public void setup() throws IOException {
		
		socket = server.accept();
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
	@Override
	public void closeTransport() throws IOException {
		super.closeTransport();
		
		server.close();
		System.out.println("server.close()");
	}
}
