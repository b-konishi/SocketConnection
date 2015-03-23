package konishi.java.socketconnection.main;

import konishi.java.socketconnection.view.ClientView;

/**
 * クライアント側の制御を行います。
 * @version 1.0.0
 * @author konishi
 * <br>
 * サーバーサイドは{@link Server}を参照
 * @see Server
 *
 */
public class Client {

	public static void main(String[] args) {
		new Client(1);
	}
	
	/**
	 * 変数の初期化・通信準備・ウィンドウ生成を行います。
	 * @param machine ロボット号機番号
	 */
	public Client(int machine) {
		new ClientView().initialize();
	}
}