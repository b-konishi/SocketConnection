package konishi.java.socketconnection.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import konishi.java.socketconnection.base.TransmitBase;
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
	String data_tmp, data;
	
	int myNumber;
	
	PrintWriter out;
	BufferedReader in;
	
	public MultiThreadManager(Socket _socket) throws Exception {
		socket = _socket;
		ReceiveModel.clientValue++;
		
		System.out.println(ReceiveModel.clientValue + "台目: " + socket.getRemoteSocketAddress() + "が接続されました");
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
	}
	
	@Override
	public void run() {
		
		new Thread(new Runnable() {
			@Override
			public void run() {
				System.out.println(socket.getRemoteSocketAddress());
				try {
					while ((data_tmp = in.readLine()) != null) {
						ReceiveModel.data = data_tmp;
						ReceiveModel.isUpdated = true;
					}
				} catch (Exception e) {}
			}
		}).start();
		
		while (true) {
			if (ReceiveModel.data != null && data != ReceiveModel.data) {
				data = ReceiveModel.data;
				System.out.println("if文の中！");
				out.println(data);
			}
		}
	}
	
}
