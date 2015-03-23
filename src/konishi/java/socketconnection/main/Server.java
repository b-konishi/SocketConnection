package konishi.java.socketconnection.main;

import konishi.java.socketconnection.view.ServerView;

/**
 * サーバー側の制御を行います。
 * @version 1.0.0
 * @author konishi
 * <br>
 * クライアントサイドは{@link Client}を参照
 * @see Client
 */
public class Server {
	
	public static void main(String[] args) throws Exception {
		new Server();
	}
	
	/**
	 * 変数の初期化・通信準備を行います。
	 */
	public Server() {
		new ServerView().initialize();
	}
}


