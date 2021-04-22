package com.rirc.OSGI01;

import java.util.Arrays;

public class KBCmpBinArr { // ByteBuffer
	private final byte[] bin;
	
	public KBCmpBinArr(byte[] _bin) {
		bin= _bin;
	}

	public byte[] getBin() {
		return bin;
	}
	
	@Override
	public int hashCode() {
		return Arrays.hashCode(bin);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj==null) return false;
		return Arrays.equals(bin, ((KBCmpBinArr)obj).bin);
	}
}
