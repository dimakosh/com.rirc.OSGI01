package com.rirc.OSGI01;

import java.util.Arrays;

public class KDCmpInt {
	private final int[] arr;

	public KDCmpInt(int ... k) {
		arr= k;
	}
	
	public int get(int i) {
		return arr[i];
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(arr);
	}
	
	@Override
	public boolean equals(Object obj) {
		KDCmpInt nk= (KDCmpInt)obj;
		return Arrays.equals(arr, nk.arr);
	}

	@Override
	public String toString() {
		return Arrays.toString(arr);
	}
}
