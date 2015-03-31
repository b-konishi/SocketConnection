package konishi.java.socketconnection.base;

public class TotalBase {
	
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
	 * エラー発生場所の表示とStackTraceをおこないます。
	 * @param e 例外インスタンス
	 */
	public void errorStackTrace(Exception e) {
		try {
			System.out.print("Error: " + Thread.currentThread().getStackTrace()[2]);
			e.printStackTrace();
		}catch(Exception ex) {
			System.out.println("Please give me acception for \"System.out.println()\". ");
		}
	}
}
