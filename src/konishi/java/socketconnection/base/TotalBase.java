package konishi.java.socketconnection.base;

abstract public class TotalBase {
	public <T> void testPrint(T msg) {
		try {
			System.out.println(msg);
		}catch(Exception e) {
			System.out.println("Please give me acception for \"System.out.println()\". ");
		}
	}
}
