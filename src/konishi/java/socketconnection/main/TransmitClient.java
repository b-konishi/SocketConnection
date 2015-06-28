package konishi.java.socketconnection.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

/**
 * クライアント側の通信関連の処理を行います。
 * 主に受信したデータはモデルクラスに転送されます。
 * @author konishi
 * @see ReceiveModel
 *
 */
public class TransmitClient extends TransmitBase implements Runnable {
	
	private InetSocketAddress socketAddress = null;
	
	private PrintWriter out = null;
	private BufferedReader in = null;
	private ObjectOutputStream oos = null;
		
	public TransmitClient() throws Exception {
		super();

		socketAddress = new InetSocketAddress(StoreData.HOST_NAME, StoreData.PORT);
		setup();
	}
	
	public void setup() throws IOException {
		stackTrace(StoreData.PORT);
		if(!socket.isConnected()) {
			socket.connect(socketAddress, 100);
		}
		stackTrace(socket.getLocalSocketAddress());
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(oos, true);
		
		new Thread(this).start();
		
		
		Thread sendImageThread = new Thread(new Runnable() {
			@Override
			public void run() {
				while (!ReceiveModel.emergencyStopSignal) {
					if (ReceiveModel.isSendedImage) {
						write("IMAGE:"+ReceiveModel.myMachineNumber);
						try {
							oos.writeObject(ReceiveModel.image);
							oos.flush();
							stackTrace("REACH sendImage");
						} catch (IOException e) {
							errorStackTrace(e);
						}
						ReceiveModel.isSendedImage = false;
					}
					if(ReceiveModel.isSendedGrid) {
						write("GRID:"+ReceiveModel.myMachineNumber);
						try {
							oos.writeObject(ReceiveModel.gridData);
							stackTrace(ReceiveModel.gridData);
						} catch (IOException e) {
							errorStackTrace(e);
						}
						ReceiveModel.isSendedGrid = false;
					}
				}
			}
		});
		sendImageThread.start();
	}
	
	@Override
	public String getPort() {
		return "" + socket.getLocalPort();
	}
	
	@Override
	public void run() {
		String data;
		StoreData.CLIENT_PORT = getPort();
		
		try {
			while ((data = in.readLine()) != null) {
				if (data.equals("SHUTDOWN"))
					System.exit(0);
				if (data.equals("ERROR")) {
					ReceiveModel.emergencyStopSignal = true;
					break;
				}
				else {
					// isUpdateがtrueならば処理を待機
					while (ReceiveModel.isUpdated) {}
					stackTrace(data);
					ReceiveModel.data = data;
					ReceiveModel.isUpdated = true;
				}
			}
		} catch (Exception e) {
			errorStackTrace(e);
		}
	}
	
	public void write(String data) {
		out.println(data);
	}
	
}
