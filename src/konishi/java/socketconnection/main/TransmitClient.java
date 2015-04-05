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
	
	String data;
	
	PrintWriter out;
	BufferedReader in;
		
	public TransmitClient() throws Exception {
		super();
		
		socketAddress = new InetSocketAddress(StoreData.HOST_NAME, StoreData.PORT);
		setup();
	}
	
	public void setup() throws IOException {
		if(!socket.isConnected()) {
			socket.connect(socketAddress, 100);
		}
		
		in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		out = new PrintWriter(socket.getOutputStream(), true);
		
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			while ((data = in.readLine()) != null) {
				ReceiveModel.data = data;
				ReceiveModel.isUpdatedListToDraw = true;
			}
		} catch (Exception e) {}
	}
	
	public void write(String data) {
		out.println(data);
	}
	
}
