package com.hsitx.java8.samples.lambda;

public class Lambda2 {

	
	/*
	 * exactly one abstract method !!
	 * default mehots is not abstract method
	 */
	@FunctionalInterface
	interface Converter<F, T> {
		T convert(F from);
	}
	
	static class Something {
		String startsWith(String s) {
			return String.valueOf(s.charAt(0));
		}
	}
	
	static class Person {
		private String firstName;
		private String lastName;
		
		Person() {}

		public Person(String firstName, String lastName) {
			super();
			this.firstName = firstName;
			this.lastName = lastName;
		}
		
		public String getFirstName() {
			return firstName;
		}

		public String getLastName() {
			return lastName;
		}

		@Override
		public String toString() {
			return "Person [firstName=" + firstName + ", lastName=" + lastName + "]";
		}
	}
	
	static class Man extends Person {
		private int power;

		public Man(String firstName, String lastName) {
			this(firstName, lastName, 1);
		}
		
		public Man(String firstName, String lastName, int power) {
			super(firstName, lastName);
			this.power = power;
		}

		@Override
		public String toString() {
			return "Man [power=" + power + ", toString()=" + super.toString() + "]";
		}

	}
	
	@FunctionalInterface
	interface PersonFactory<P extends Person> {
		P create(String firstName, String lastName);
	}
	
	public static void main(String[] args) {
//		Converter<String, Integer> converter = (from) -> Integer.valueOf(from);
		Converter<String, Integer> converter = Integer::valueOf;
		Integer converted = converter.convert("123");
		System.out.println(converted);
		
		
		Something something = new Something();
		Converter<String, String> converter2 = something::startsWith;
		String converted2 = converter2.convert("Java");
		System.out.println(converted2);    // "J"
		
		PersonFactory<Person> personFactory = Person::new;
		Person person = personFactory.create("Peter", "Parker");
		System.out.println(person.toString());
		
		PersonFactory<Man> manFactory = Man::new;
		Man man = manFactory.create("Peter", "Parket");
		System.out.println(man.toString());
	}
}
