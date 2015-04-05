package konishi.java.socketconnection.main;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;

import konishi.java.socketconnection.base.TransmitBase;
import konishi.java.socketconnection.json.MapCoordinates;
import konishi.java.socketconnection.model.ReceiveModel;
import konishi.java.socketconnection.model.StoreData;

public class TransmitClient extends TransmitBase implements Runnable {
	
	private InetSocketAddress socketAddress = null;
	private MapCoordinates map;
	
	PrintWriter out;
		
	public TransmitClient() throws Exception {
		super();
		
		socketAddress = new InetSocketAddress(StoreData.HOST_NAME, StoreData.PORT);
		setup();
	}
	
	public void setup() throws IOException {
		if(!socket.isConnected()) {
			socket.connect(socketAddress, 100);
		}
		
		ois = new ObjectInputStream(socket.getInputStream());
		
		new Thread(this).start();
	}
	
	@Override
	public void run() {
		try {
			while ((map = (MapCoordinates)ois.readObject()) != null) {
				ReceiveModel.map = map;
				ReceiveModel.isUpdatedListToDraw = true;
			}
		} catch (Exception e) {}
	}
	
}
