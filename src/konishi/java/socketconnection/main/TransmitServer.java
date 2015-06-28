package konishi.java.socketconnection.main;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;
import konishi.java.socketconnection.serialize.BufferedImageSerializer;

/**
 * サーバー側の通信処理をまとめたクラスです。
 * 主に受信したデータはモデルクラスに格納されます。
 * @author konishi
 * @see ReceiveModel
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
		private Socket socket;
		private String data_str, data;
		private int data_tmp, data_tmp2;
		private StringBuilder sb = new StringBuilder();
		
		/**
		 * 自分が何番目のクライアントかを記録します。<br>
		 * 0から始まることに注意してください。
		 */
		int clientOrder;
		
		PrintWriter out;
		InputStreamReader in;
		ObjectInputStream ois;
		
		int tmp;
		ArrayList<String> messageData = new ArrayList<>();
		
		public MultiThreadManager(Socket _socket) throws Exception {
			socket = _socket;
			clientOrder = ReceiveModel.clientValue++;
			
			ReceiveModel.clientAdress.add(stringSeparator(socket.getRemoteSocketAddress().toString(), ":").get(1));
			
			stackTrace(ReceiveModel.clientValue + "台目: " + socket.getRemoteSocketAddress() + "が接続されました");
			
			ois = new ObjectInputStream(socket.getInputStream());
			in = new InputStreamReader(ois, StandardCharsets.UTF_8);
			out = new PrintWriter(socket.getOutputStream(), true);
		}
		
		/**
		 * OSを意識したデータ読み込みメソッドです。
		 * Windows, Unixの改行文字に対応しています。
		 * @param os OS種類
		 * @return 評価値(-1:Windowsの改行コード, -2:Windowsであり改行コードでない, -1:Unixの改行コード, -3:改行コードでない)
		 * @throws Exception 読み込みエラー
		 */
		public int nextLineProcedure(String os) throws Exception {
			switch (os) {
			case StoreData.WINDOWS:
				if ((data_tmp = in.read()) == '\r')
					return (((data_tmp2 = in.read()) == '\n') ? -1 : -2);
				break;

			case StoreData.UNIX:
				if ((data_tmp = in.read()) == '\n')
					return -1;
				break;
			}
			return -3;
		}

		@Override
		public void run() {
			Thread readThread = new Thread(new Runnable() {
				@Override
				public void run() {
					while (!ReceiveModel.emergencyStopSignal) {
						try {
							while ((tmp = nextLineProcedure(getOS())) != -1 && data_tmp != -1) {
								if (tmp == -3) {
									sb.append((char)data_tmp);
								} else {
									sb.append((char)data_tmp);
									sb.append((char)data_tmp2);
								}
							}
							data_str = new String(sb);
							sb.setLength(0);
							if (data_str.equals("CLOSE")) {
								stackTrace();
								closeTransport();
							} else if (data_str.startsWith("IMAGE")) {
								ReceiveModel.image = (BufferedImageSerializer) ois.readObject();
								stackTrace("image");
								ReceiveModel.myMachineNumber = data_str.substring(6);
								ReceiveModel.isSendedImage = true;
								data_str = "";
							} else if (data_str.startsWith("GRID")) {
								ReceiveModel.myMachineNumber = data_str.substring(5);
								ReceiveModel.gridData = (String)ois.readObject();
								ReceiveModel.isSendedGrid = true;
							} else if (data_str.startsWith("MESSAGE")) {
								messageData = stringSeparator(data_str, ":");
								ReceiveModel.myMachineNumber = messageData.get(1);
								switch (messageData.get(2)) {
								case "WEIGHT":
									ReceiveModel.weightData = messageData.get(3);
									ReceiveModel.weightFlag = true;
									break;
								case "COLOR":
									ReceiveModel.colorData = messageData.get(3);
									ReceiveModel.colorFlag = true;
									break;
								case "FREQUENCY":
									ReceiveModel.frequencyData = messageData.get(3);
									ReceiveModel.frequencyFlag = true;
									break;
								case "MESSAGE":
									ReceiveModel.messageData = messageData.get(3);
									ReceiveModel.messageFlag = true;
									break;
								}
							} else if (!data_str.equals("")) {
								StringBuilder sb = new StringBuilder();
								sb.append(data_str);
								sb.insert(0, "_");
								ReceiveModel.data = new String(sb);
								stackTrace(data);
								ReceiveModel.isUpdated = true;
							} else {
							}

						} catch (Exception e) {
							e.printStackTrace();
						}
					}
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
//				stackTrace(ReceiveModel.data);
				if (data != null && ReceiveModel.data != null) {
					if (!ReceiveModel.data.equals(data)) {
						data = ReceiveModel.data;
						stackTrace(data);
						out.println(data);
					}
				} else if (data == null && ReceiveModel.data != null) {
					data = ReceiveModel.data;
					stackTrace(data);
					out.println(data);
				}
			}
			stackTrace("スレッド終了");
		}
		
	}
	
}


