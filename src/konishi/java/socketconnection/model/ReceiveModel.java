package konishi.java.socketconnection.model;

import konishi.java.socketconnection.json.MapCoordinates;

public class ReceiveModel {
	
	volatile public static MapCoordinates map;
	volatile public static boolean isUpdatedListToDraw = false;
//	public static boolean isUpdatedListToTransfer = false;
	
}
