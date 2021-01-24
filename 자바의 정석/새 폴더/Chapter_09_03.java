public class Chapter_09_03 {
	public static void main(String[] args) {
		String fullPath = "c:\\jdk1.8\\work\\PathSeparateTest.java";
		String path = "";
		String fileName = "";
		StringBuilder sb = new StringBuilder(fullPath);
		path = sb.substring(0,sb.indexOf("work"));
		fileName = sb.substring(sb.indexOf("Path"));
		System.out.println("fullPath:"+fullPath);
		System.out.println("path:"+path);
		System.out.println("fileName:"+fileName);
	}
}







