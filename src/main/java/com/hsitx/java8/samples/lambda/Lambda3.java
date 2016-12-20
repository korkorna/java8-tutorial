package com.hsitx.java8.samples.lambda;

public class Lambda3 {

	static int outerStaticNum;
	int outerNum;
	
	void testScopes() {
		/*
		 * only local variable from the outer scope of lambda expressions.
		 * but instance fiedls and static variables we have both read and write 
		 */
		final int num = 1;
		Converter<Integer, String> stringConverter =
				(from) -> String.valueOf(from + num);
		String converted = stringConverter.convert(2);
		System.out.println(converted);
		
		int num2 = 1;
		Converter<Integer, String> stringConverter2 =
				(from) -> String.valueOf(from + num2);
		String converted2 = stringConverter2.convert(2);
		System.out.println(converted2);
//		num2 = 3;   //complie error .. because not final
		
		Converter<Integer, String> stringConverter3 = (from) -> {
			outerNum = 23;
			return String.valueOf(from + outerNum);
		};
		String converted3 = stringConverter3.convert(2);
		System.out.println(converted3);
		
		Converter<Integer, String> stringConverter4 = (from) -> {
            outerStaticNum = 72;
            return String.valueOf(from + outerStaticNum);
        };
        String converted4 = stringConverter4.convert(2);
		System.out.println(converted4);
		
		outerNum = 1;
		outerStaticNum = 1;
	}
	
	@FunctionalInterface
	interface Converter<F, T> {
		T convert(F from);
	}
	
	public static void main(String[] args) {
		new Lambda3().testScopes();
	} 
}
