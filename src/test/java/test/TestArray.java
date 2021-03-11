package test;

import com.hazelcast.internal.management.request.ForceStartNodeRequest;

public class TestArray {
	
	StringBuffer buffer=new StringBuffer();
	String string;
	TestArray testArray =new TestArray();
	public void test(Object b[]) {
		if(b.getClass().isArray()) {
		for(int i=0;i<b.length;i++) {
			if(b[i].getClass().isArray()) {
				//testArray.test(b[i]);
			}else {
				buffer.append(b[i]);
			}
		}
		string=buffer.toString();
		//return string;
		}
	}
	public static void main(String[] args) {
		Object [][] aObjects= {{1},{2},{3,4}};
		
		String string="123456";
		System.out.println(string.substring(string.length()-3,string.length()));
		
	}
	
	
}
