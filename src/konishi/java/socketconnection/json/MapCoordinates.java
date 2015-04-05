package konishi.java.socketconnection.json;

import java.io.Serializable;

import konishi.java.socketconnection.type.JsonType;

/**
 * Mapの座標を格納するJson専用クラスです。
 * 
 * @version 1.0.0
 * @author konishi
 */
public class MapCoordinates extends JsonType implements Serializable {
	
	/**
	 * ObjectOutputStream#readObjectを実行するため、シリアライズする。
	 */
	private static final long serialVersionUID = -8793051461476476029L;
	
	public int itemID;
	public double itemX;
	public double itemY;
	
	public MapCoordinates(int id, double x, double y) {
		itemID = id;
		itemX = x;
		itemY = y;
	}

}
