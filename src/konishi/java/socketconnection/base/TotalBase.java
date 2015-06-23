package konishi.java.socketconnection.base;

import java.util.ArrayList;
import java.util.StringTokenizer;

import konishi.java.socketconnection.model.StoreData;


public class TotalBase {
	

	/**
	 * 文字列をkeyで分割し、リストに保存します。
	 * @param str 対象となる文字列
	 * @param key 分割するキーとなる文字列
	 * @return ArrayList型の文字列リスト
	 */
	public ArrayList<String> stringSeparator(String str, String key) {
		
		ArrayList<String> data = new ArrayList<>();
		
        StringTokenizer st = new StringTokenizer(str , key);

        while (st.hasMoreTokens()) {
        	data.add(st.nextToken());
        }
        return data;
	}
	
	public String getOS() {
		if (System.getProperty("file.separator").equals("\\"))
			return StoreData.WINDOWS;
		else
			return StoreData.UNIX;
	}
	
	/**
	 * String型のリストをInteger型のリストに変換します。
	 * @param list 文字列リスト
	 * @return 数値リスト
	 */
	public ArrayList<Integer> parseIntList(ArrayList<String> list) {
		ArrayList<Integer> data = new ArrayList<>();
		
		for (int i = 0; i < list.size(); i++) {
			data.add(Integer.parseInt(list.get(i)));
		}
		return data;
	}
	
	
	/**
	 * 呼び出し元スレッドの表示
	 */
	public void stackTrace() {
		try {
			System.out.println("Pass: " + Thread.currentThread().getStackTrace()[2]);
		}catch(Exception e) {
			System.out.println("Please give me acception for \"System.out.println()\". ");
		}
	}
	
	/**
	 * stackTraceのオーバーロード
	 * メッセージを記述できる
	 * @param msg 表示メッセージ
	 */
	public <T> void stackTrace(T msg) {
		try {
			System.out.println("Pass: " + Thread.currentThread().getStackTrace()[2]);
			System.out.println("Message: " + msg);
		}catch(Exception e) {
			System.out.println("Please give me acception for \"System.out.println()\". ");
		}
	}
	
	/**
	 * エラー発生場所の表示とStackTraceをおこないます。
	 * @param e 例外インスタンス
	 */
	public void errorStackTrace(Exception e) {
		try {
			System.out.println("Error: " + Thread.currentThread().getStackTrace()[2]);
			e.printStackTrace();
		}catch(Exception ex) {
			System.out.println("Please give me acception for \"System.out.println()\". ");
		}
	}
	
	/**
	 * errorStackTraceのオーバーロード
	 * メッセージを記述できる
	 * @param e 例外インスタンス
	 * @param msg 表示メッセージ
	 */
	public <T> void errorStackTrace(Exception e, T msg) {
		try {
			System.out.println("Error: " + Thread.currentThread().getStackTrace()[2]);
			System.out.println("ErrorMessage: " + msg);
			e.printStackTrace();
		}catch(Exception ex) {
			System.out.println("Please give me acception for \"System.out.println()\". ");
		}
	}
}
