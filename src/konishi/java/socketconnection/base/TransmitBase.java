package konishi.java.socketconnection.base;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import konishi.java.socketconnection.type.JsonType;

import com.google.gson.Gson;
import com.google.gson.stream.JsonWriter;

/**
 * 通信・ファイルの根底となる制御クラスです。
 * @version 1.0.0
 * @author konishi
 * @see konishi.java.socketconnection.main.Server
 * @see konishi.java.socketconnection.main.Client
 *
 */
abstract public class TransmitBase extends TotalBase {
	protected Socket socket = null;
	
	protected ObjectOutputStream oos = null;
	
	public TransmitBase() {
		socket = new Socket();
	}

	/**
	 * 通信疎通確認を行います。
	 * @return ソケット接続確認
	 */
	public boolean isConnected() {
		return socket.isConnected();
	}
	
//	public boolean mapEqual(MapCoordinates map1, MapCoordinates map2) {
//		try {
//			if (map1 == null && map2 != null)
//				return false;
//			
//			if (map1.itemID == map2.itemID && map1.itemX == map2.itemX && map1.itemY == map2.itemY)
//				return true;
//			else
//				return false;
//		} catch (NullPointerException e) {
////			errorStackTrace(e);
//			return true;
//		}
//	}
	
	/**
	 * ファイルの中身を空にします。
	 * @param filePath 空にしたいFileのパス
	 * @throws Exception エラー
	 */
	public void clearFile(String filePath) throws Exception {
		FileWriter fWriter = new FileWriter(filePath);
		fWriter.close();
	}
	
	/**
	 * JSON形式でファイル出力します。
	 * @param filePath ファイルのパス
	 * @param list 中身のリスト<br>JSON形式である必要がある
	 * @throws Exception エラー
	 */
	public void writeFile(String filePath, ArrayList<JsonType> list) throws Exception {
		if (list == null)	return;
		
		FileWriter fWriter = new FileWriter(filePath, true);
		BufferedWriter bWriter = new BufferedWriter(fWriter);
		
		try (JsonWriter writer = new JsonWriter(bWriter)) {
			writer.setIndent(" ");
			
			stackTrace();
			Gson gson = new Gson();
			gson.toJson(list, ArrayList.class, writer);
		}
	}
	
	/**
	 * JSON形式のファイルを読み込みます。
	 * @param filePath ファイルのパス
	 * @return 読み込んだリスト
	 * @throws Exception エラー
	 */
//	public ArrayList<JsonType> readFile(String filePath) throws Exception {
//		stackTrace();
//		ArrayList<JsonType> list = readObject();
//		if (list == null)	return null;
//		writeFile(filePath, list);
//		return list;
//	}
	
	/**
	 * オブジェクトを書き込みます。
	 * 引数にはObject型を基底クラスとするインスタンスでなくてはなりません。
	 * @param obj 基底クラスをObject型とするインスタンス
	 * @throws IOException 入出力エラー
	 */
//	public <T> void writeObject(T obj) throws IOException {
//		if (obj == null)	return;
//		
//		if (obj instanceof Object) {
//			oos.writeObject(obj);
//			oos.flush();
////			stackTrace();
//		}
//	}
	
	/**
	 * オブジェクトを読み込みます。
	 * @return 基底クラスをObject型とするインスタンス
	 * @throws Exception 通信エラー
	 */
//	@SuppressWarnings("unchecked")
//	public <T> T readObject() throws Exception {
//		stackTrace();
//		return (T)ois.readObject();
//	}
	
	/**
	 * ソケット・ストリームのクローズを行います。
	 * @throws IOException 接続エラー
	 */
	public void closeTransport() throws IOException {
		if (socket.isConnected()) {
			socket.close();
			stackTrace();
		}

//		ois.close();
		oos.close();
		System.out.println("CLOSE");
	}
}
