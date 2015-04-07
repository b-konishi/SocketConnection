package konishi.java.socketconnection.model;

/**
 * PC固有の設定などを保存するモデルクラスです。
 * @version 1.0.0
 * @author konishi
 *
 */
public class StoreData {
	public static int PORT = 8000;
	/**
	 * ファイルのディレクトリを指定します。<br>
	 * 各PCによって変更してください。
	 */
	private static final String BASE_DIR = "/home/konishi/workspace_java/";
	
	// 吉原's PC Host-name
	// public static final String HOST_NAME = "192.168.17.107";
	
	/**
	 * ローカルを表す文字列です。
	 */
	public static final String LOCAL = "local";
	
	/**
	 * サーバー側から送信されてきたことを表す文字列です。
	 */
	public static final String REMOTE_SERVER = "server";
	
	/**
	 * クライアント側から送信されてきたことを表す文字列です。
	 */
	public static final String REMOTE_CLIENT = "client";
	
	
	
	/**
	 * サーバーのホスト名
	 */
	public static String HOST_NAME = "localhost";
		
	public static final int SERVER_MAP_SIZE = 600;
	public static final int CLIENT_MAP_SIZE = 300;
	
	public static final int MAP_MAGNIFICATION = SERVER_MAP_SIZE / CLIENT_MAP_SIZE;
	
	public static final int DEFAULT_MAP_ITEM_SIZE = 50;
	
	
	public static final String SRC_DIR = BASE_DIR + "SocketConnection/src/";
	public static final String IMAGE_DIR = BASE_DIR + "SocketConnection/image/";
	public static final String FILE_NAME = SRC_DIR + "text.json";
	public static final String SERVER_MAP_FILE = SRC_DIR + "server_map.json";
	public static final String CLIENT_MAP_FILE = SRC_DIR + "client_map.json";
}
