package konishi.java.socketconnection.model;

import java.util.ArrayList;


public class ReceiveModel {
	
	
	volatile public static boolean isUpdated = false;
	
	volatile public static String data;
	volatile public static int clientValue = 0;
	
	volatile public static ArrayList<int[]> mapItem = new ArrayList<>();
}
