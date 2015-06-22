package konishi.java.socketconnection.model;

import java.io.File;
import java.util.ArrayList;

import javafx.scene.image.ImageView;
import javafx.scene.paint.Paint;


public class ReceiveModel {
	
	volatile public static int selectedClientDialog;
	volatile public static String addressPortDialog;
	volatile public static boolean errorDialog = false;
	
	/**
	 * サーバーが停止した際のストップシグナルです。
	 */
	volatile public static boolean emergencyStopSignal = false;
	
	/**
	 * サーバー側からのシャットダウンシグナルです。。
	 */
	volatile public static boolean shutdownSignal = false;
	
	/**
	 * 描画の必要性があるかどうかを判定するためのフラグです。
	 */
	volatile public static boolean isUpdated = false;
	
	/**
	 * 画像送信ボタンが押されたかどうかを判定するためのフラグです。
	 */
	volatile public static boolean isSendedImage = false;
	
	/**
	 * 画像ファイルを格納します。
	 */
	volatile public static File image;
	
	/**
	 * 受信した画像のロボット番号を格納します。
	 */
	volatile public static String newImageMachineNumber;
	
	/**
	 * これまで画像を受信したロボット番号を格納します。
	 */
	volatile public static ArrayList<String> imageMachineNumber = new ArrayList<>();
	
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
//	volatile public static String sendData;
	
	/**
	 * iconの画像を格納する配列です。
	 */
	volatile public static ImageView imageViews[] = new ImageView[5];
	
	/**
	 * マップ上のアイテムの座標を管理するリストです。
	 */
	volatile public static ArrayList<int[]> mapItem = new ArrayList<>();
	
	volatile public static Paint defaultGridColor;
	
	volatile public static String gridData;
	
	volatile public static boolean isSendedGrid = false;
}
