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
 * サーバー側の通信処理をまとめたクラス。
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
	public String getPort() {
		return "" + server.getLocalPort();
	}
	
	@Override
	public void closeTransport() throws IOException {
		super.closeTransport();
		
		ReceiveModel.clientValue = 0;
		ReceiveModel.clientAdress.clear();
		
		ReceiveModel.emergencyStopSignal = false;
		
		server.close();
		stackTrace();
	}
	
	/**
	 * Clientごとのスレッドを管理する内部クラスです。
	 * @author konishi
	 *
	 */
	class MultiThreadManager extends Thread {
		Socket socket;
		String data_tmp, data;
		
		/**
		 * 自分が何番目のクライアントかを記録します。<br>
		 * 0から始まることに注意してください。
		 */
		int clientOrder;
		
		PrintWriter out;
		BufferedReader in;
		
		public MultiThreadManager(Socket _socket) throws Exception {
			socket = _socket;
			clientOrder = ReceiveModel.clientValue++;
			
			ReceiveModel.clientAdress.add(stringSeparator(socket.getRemoteSocketAddress().toString(), ":").get(1));
			
			stackTrace(ReceiveModel.clientValue + "台目: " + socket.getRemoteSocketAddress() + "が接続されました");
			
			in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			out = new PrintWriter(socket.getOutputStream(), true);
		}

		@Override
		public void run() {
			
			Thread readThread = new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						while ((data_tmp = in.readLine()) != null) {
							if (data_tmp.equals("CLOSE")) {
								stackTrace();
								closeTransport();
							} else {
								ReceiveModel.data = data_tmp;
								ReceiveModel.isUpdated = true;
							}
						}
					} catch (Exception e) {}
					stackTrace("スレッド終了");
				}
			});
			readThread.start();
			
			while (true) {
				
				if (ReceiveModel.shutdownSignal) {
					out.println("SHUTDOWN");
					System.exit(0);
				}
				if (ReceiveModel.emergencyStopSignal) {
					System.out.println("ERROR");
					out.println("ERROR");
					break;
				}
				if (!ReceiveModel.emergencyStopSignal && out.checkError()) {
					// スレッド終了処理
					stackTrace();
					ReceiveModel.clientAdress.remove(clientOrder);
					ReceiveModel.clientValue--;
					break;
				}
				stackTrace(ReceiveModel.data + " " + ReceiveModel.sendData);
				if ((ReceiveModel.sendData != null && data == null) || (ReceiveModel.sendData != null && !data.equals(ReceiveModel.sendData))) {
					if (data == null)
					stackTrace();
					data = ReceiveModel.sendData;
					stackTrace();
					out.println(data);
				}
			}
			stackTrace("スレッド終了");
		}
		
	}
	
}


