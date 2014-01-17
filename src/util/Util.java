package util;

import processing.core.PApplet;

import java.util.Arrays;

public class Util {
public Util() {}

public static float[] splitNumbers(String theText) {
	String[] temp = PApplet.splitTokens(theText, " ,.?!:;[]-\"");
	float[] slist = new float[temp.length];
	for (int i = 0; i < temp.length; i++) { slist[i] = Float.valueOf(temp[i]); }
	return slist;
}

public static String[] splitLetters(String theText) {
	String[] nlist = PApplet.splitTokens(theText, " ,.?!:;[]-\"");
	System.out.println(Arrays.toString(nlist));
	return nlist;
}
}
