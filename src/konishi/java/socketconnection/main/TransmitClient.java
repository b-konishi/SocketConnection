package konishi.java.socketconnection.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.model.StoreData;

public class TransmitClient extends TransmitBase {
	
	private InetSocketAddress socketAddress = null;
	
	public TransmitClient() throws Exception {
		super();
		
		socketAddress = new InetSocketAddress(StoreData.HOST_NAME, StoreData.PORT);
	}
	
	public void setup() throws IOException {
		if(!socket.isConnected()) {
			socket.connect(socketAddress, 100);
		}
		
		oos = new ObjectOutputStream(socket.getOutputStream());
		ois = new ObjectInputStream(socket.getInputStream());
	}
	
}
