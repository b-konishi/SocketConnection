package konishi.java.socketconnection.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.json.MapCoordinates;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

/**
 * TransmitBaseをラップするクラスです。
 * @version 1.0.0
 * @author konishi
 *
 */
public class TransmitServer extends TransmitBase {
	private ServerSocket server = null;
	
	public TransmitServer() throws Exception {
		super();
		server = new ServerSocket(StoreData.PORT);
		stackTrace("PORT: " + server.getLocalPort());
		
		new Thread(new Runnable() {
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
		}).start();		
	}
	
	public void setup() throws Exception {
		
		socket = server.accept();
		
		new MultiThreadManager(socket).start();
		
		stackTrace();
	}
	
	
	@Override
	public void closeTransport() throws IOException {
		super.closeTransport();
		
		server.close();
		stackTrace();
	}
}

class MultiThreadManager extends Thread {
	Socket socket;
	MapCoordinates map;
	MapCoordinates map_tmp;
	
	PrintWriter out;
	ObjectInputStream ois;
	
	public MultiThreadManager(Socket _socket) throws Exception {
		socket = _socket;
		
		System.out.println(socket.getRemoteSocketAddress() + "が接続されました");
		
		out = new PrintWriter(socket.getOutputStream(), true);
		ois = new ObjectInputStream(socket.getInputStream());		
	}
	
	@Override
	public void run() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(socket.getRemoteSocketAddress());
				try {
					while ((map_tmp = (MapCoordinates)ois.readObject()) != null) {
						ReceiveModel.map = map_tmp;
						ReceiveModel.isUpdatedListToDraw = true;
					}
				} catch (ClassNotFoundException | IOException e) {}
			}
		}).start();
		
		while (true) {
			try {
				Thread.sleep(100);
			} catch (InterruptedException e1) {}
			if (ReceiveModel.map != null && map != ReceiveModel.map) {
				map = ReceiveModel.map;
				System.out.println("if文の中！");
				out.print(map);
			}
			// map = ReceiveModel.getMap();
//			stackTrace();
		}
	}
	
}
