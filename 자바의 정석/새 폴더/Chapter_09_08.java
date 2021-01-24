package test;

public class Test {
	public static double round(double d,int n) {
		String d1 = d + "";
		if (d1.substring(d1.indexOf(".")).length() <= n) {
			return d;
		}
		Double d2 = new Double(d1.substring(d1.indexOf("."),d1.indexOf(".")+n+1))+(int)d;
		return d2;
	}


	public static void main(String[] args) {
		System.out.println(round(3.1415,1));
		System.out.println(round(3.1415,2));
		System.out.println(round(3.1415,3));
		System.out.println(round(3.1415,4));
		System.out.println(round(3.1415,5));

	}

}







