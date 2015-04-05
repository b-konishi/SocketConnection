package konishi.java.socketconnection.controller;

import java.util.StringTokenizer;


public class Test {
	
	public static void main(String[] args) {
//		String a = "abc";
//		String b = "cde";
//		String c = "abc";
//		
//		while (a == c) {
//			System.out.println("aとcは一緒！");
//			while (a == b) {
//				System.out.print("aもbもcも一緒！");
//			}
//		}
		//カンマ区切り文字列
        String str = "AAA,111,BBB,222";

        
        String[] string = new String[4];

        StringTokenizer st = new StringTokenizer(str , ",");
        
        System.out.println(string.length);

        //分割した文字を画面出力する
        while (st.hasMoreTokens()) {
        	System.out.println(st.countTokens());
            string[string.length - st.countTokens()] = st.nextToken();
        }
        for (int i = 0; i < string.length; i++) {
        	System.out.println(string[i]);
        }
        		
	}
}


class A {
	int a, b;
	public A(int a, int b) {
		this.a = a;
		this.b = b;
	}
}
