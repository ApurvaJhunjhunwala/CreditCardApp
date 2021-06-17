package com.creditcard.controller;

import java.util.Random;

public class Test {
	 public static void main(String[] args) {
		  System.out.println(generateRandom(52));
	    }

	    public static long generateRandom(int prefix) {
	        Random rand = new Random();

	        long x = (long)(rand.nextDouble()*100000000000000L);

	        String s = String.valueOf(prefix) + String.format("%014d", x);
	        return Long.valueOf(s);
	    }
}
