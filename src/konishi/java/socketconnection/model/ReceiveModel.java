package konishi.java.socketconnection.model;

import java.util.ArrayList;


public class ReceiveModel {
	
	/**
	 * サーバーが停止した際のストップシグナルです。
	 */
	volatile public static boolean emergencyStopSignal = false;
	
	/**
	 * サーバー側からのシャットダウンシグナルです。。
	 */
	volatile public static boolean shutdownSignal = false;
	
	/**
	 * 描画の必要性があるかどうかを判定するための変数です。
	 */
	volatile public static boolean isUpdated = false;
	
	/**
	 * クライアント接続台数を示す値です。
	 */
	volatile public static int clientValue = 0;
	
	/**
	 * クライアントのアドレスをリスト形式にて保存します。
	 */
	volatile public static ArrayList<String> clientAdress = new ArrayList<>();

	/**
	 * 描画データ用文字列変数です。
	 */
	volatile public static String data;
	
	/**
	 * 描画送信データ用文字列変数です。
	 */
	volatile public static String sendData;
	
	/**
	 * マップ上のアイテムの座標を管理するリストです。
	 */
	volatile public static ArrayList<int[]> mapItem = new ArrayList<>();
}
