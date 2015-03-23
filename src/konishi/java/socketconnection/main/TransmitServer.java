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
	private Thread thread = null;
	private boolean connecting = false;
	private boolean clientConnect = false;
	
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
	
	/**
	 * 接続中フラグを立て、スレッドによりクライアントとの通信を開始します。
	 */
	public boolean setupServer() {
		if (connecting)	return true;
		connecting = true;
		thread.start();
		
		return connecting;
	}
	
	/**
	 * クライアントの接続状況を設定します。
	 * @param _connect 接続フラグ
	 */
	public void setConnect(boolean _connect) {
		clientConnect = _connect;
	}
	
	/**
	 * クライアントの接続状況を取得します。
	 * @return 接続フラグ
	 */
	public boolean getConnect() {
		return clientConnect;
	}
	
	@Override
	public void closeTransport() throws IOException {
		super.closeTransport();
		
		server.close();
		System.out.println("server.close()");
	}
}
