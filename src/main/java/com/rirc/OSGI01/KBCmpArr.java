package com.rirc.OSGI01;

import java.util.Arrays;

public class KBCmpArr<T> implements Comparable<KBCmpArr<T>> {
	private final T[] bin;
	
	public KBCmpArr(T[] _bin) {
		bin= _bin;
	}

	public T[] getArr() {
		return bin;
	}
	
	public T get(int ind) {
		return bin[ind];
	}

	@Override
	public int hashCode() {
		return Arrays.hashCode(bin);
	}

	@Override
	public boolean equals(Object obj) {
		if (obj==null) return false;
		
		@SuppressWarnings("unchecked")
		KBCmpArr<T> b2= (KBCmpArr<T>)obj;

		return Arrays.equals(bin, b2.bin);
	}
	
	@Override
	public String toString() {
		return Arrays.toString(bin);
	}

	@Override
	public int compareTo(KBCmpArr<T> o) {
		T[] a1= bin;
		T[] a2= o.bin;

		int n= Math.min(a1.length, a2.length);
		
		for (int i= 0; i< n; i++) {
			T v1= a1[i];
			@SuppressWarnings("unchecked")
			Comparable<T> c1= (Comparable<T>)v1;
			int r= c1.compareTo(a2[i]);
			if (r!=0) return r;
		}

		return a1.length-a2.length;
	}
}
