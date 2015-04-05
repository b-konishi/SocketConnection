package konishi.java.socketconnection.controller;


public class Test {
	
	public static void main(String[] args) {
		A a = new A(1, 2);
		A b = new A(10, 20);
		A c = new A(1, 2);
		
		while (a == c) {
			System.out.println("aとcは一緒！");
			while (a == b) {
				System.out.print("aもbもcも一緒！");
			}
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
