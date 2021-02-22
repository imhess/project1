package testpack;

import java.util.Arrays;

public class LinkedBagTest {
	public static void main(String[] args) {
		
		System.out.println("Creating...");
		BagInterface<String> aBag = new LinkedBag<>();
		BagInterface<String> bBag = new ResizableArrayBag<>();
		aBag.add("A");
		aBag.add("B");
		aBag.add("B");
		aBag.add("B");
		bBag.add("A");
		bBag.add("B");
		bBag.add("B");
		bBag.add("D");
		bBag.add("E");
		System.out.println("Creating a union...");
		System.out.println(Arrays.toString(aBag.union(bBag).toArray()));
		System.out.println("Creating an intersection...");
		System.out.println(Arrays.toString(aBag.intersection(bBag).toArray()));
		System.out.println("Creating a difference...");
		System.out.println(Arrays.toString(aBag.difference(bBag).toArray()));
	}
}
