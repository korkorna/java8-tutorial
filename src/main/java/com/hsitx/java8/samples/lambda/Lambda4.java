package com.hsitx.java8.samples.lambda;

import java.util.Comparator;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import com.hsitx.java8.samples.lambda.Lambda2.Person;

public class Lambda4 {
	/*
	 *	Built-in Functional Interface test 
	 */
	public static void main(String[] args) {
		// Predicates
		//
		Predicate<String> predicate = (s) -> s.length() > 0;
		
		System.out.println(predicate.test("foo"));;
		System.out.println(predicate.negate().test("foo"));
		
		Predicate<Boolean> nonNull = Objects::nonNull;
		Predicate<Boolean> isNull  = Objects::isNull;
		
		System.out.println(nonNull.test(null));
		System.out.println(nonNull.negate().test(null));
		
		Predicate<String> isEmpty = String::isEmpty;
		Predicate<String> isNotEmpty = isEmpty.negate();
		
		System.out.println(isEmpty.test(""));
		System.out.println(isNotEmpty.and(predicate).test("2"));
		
		// Functions
		// 하나의 인자와 생산된 결과만 허용함.
		// 그리고 기본 매소드를 체인으로 이용할 수 있다.(compose, andThen)
		Function<String, Integer> toInteger = Integer::valueOf;
		Function<String, String> backToString = toInteger.andThen(String::valueOf);
		
		System.out.println(toInteger.apply("123"));
		System.out.println(backToString.apply("123"));
		
		//Suppliers
		// Functions와 달리 인자를 허가하지 않는다.
		Supplier<Person> personSupplier = Lambda2.Person::new;
		System.out.println(personSupplier.get());
		
		// Consumers
		Consumer<Person> greeter = (p) -> System.out.println("Hello, " + p.getFirstName());
		greeter.accept(new Person("Luke", "Skywalker"));
		
		// Comparators
		Comparator<Person> comparator = (p1, p2) -> p1.getFirstName().compareTo(p2.getFirstName());
		
		Person p1 = new Person("John", "Doe");
		Person p2 = new Person("Alice","Wonderland");
		
		System.out.println(comparator.compare(p1, p2));				// > 0
		System.out.println(comparator.reversed().compare(p1, p2));	// < 0
		
		// Optionals
		// Optionals는 함수인터페이스는 아니며, 대신 NullPointerExecption을 방지한다.
		// Null이거나 inon-null이 아닌 값에 대한 간단한 컨테이너이다.
		// Null을 리턴하는 대신 Optionals를 리턴한다.
		Optional<String> optional = Optional.of("bam");
		
		System.out.println(optional.isPresent());			// true
		System.out.println(optional.get());					// "bam"
		System.out.println(optional.orElse("fallback"));	// "bam"
		
		optional.ifPresent((s) -> System.out.println(s.charAt(0)));
		
		
		
	}
}
