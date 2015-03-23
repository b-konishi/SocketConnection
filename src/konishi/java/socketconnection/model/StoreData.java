package konishi.java.socketconnection.model;

/**
 * 定数を定義するモデルクラスです。
 * @version 1.0.0
 * @author konishi
 *
 */
public class StoreData {
	public static final int PORT = 8000;
	/**
	 * ファイルのディレクトリを指定します。<br>
	 * 各PCによって変更してください。
	 */
	private static final String BASE_DIR = "/home/konishi/workspace_java/";
	
	// public static final String HOST_NAME = "192.168.17.107";
	/**
	 * 接続PCのホスト名<br>
	 * 各PCによって変更してください。
	 */
	public static final String HOST_NAME = "localhost";
	
	public static final String SRC_DIR = BASE_DIR + "SocketConnection/src/";
	public static final String IMAGE_DIR = BASE_DIR + "SocketConnection/image/";
	public static final String FILE_NAME = SRC_DIR + "text.json";
	public static final String SERVER_MAP_FILE = SRC_DIR + "server_map.json";
	public static final String CLIENT_MAP_FILE = SRC_DIR + "client_map.json";
}
