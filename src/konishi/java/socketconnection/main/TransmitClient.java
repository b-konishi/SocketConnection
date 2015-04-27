package konishi.java.socketconnection.main;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

public class TransmitClient extends TransmitBase implements Runnable {
	
	private InetSocketAddress socketAddress = null;
	
	private PrintWriter out = null;
	private BufferedReader in = null;
		
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
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		new Thread(this).start();
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
