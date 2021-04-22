package com.rirc.OSGI01;

import java.nio.ByteBuffer;
import java.util.UUID;

public class KDUUID {

	static public UUID fromString(String s) {
		if (KDStr.isNullOrEmpty(s)) return null;
		return UUID.fromString(s);
	}

	static public UUID fromStringNEx(String s) {
		if (KDStr.isNullOrEmpty(s)) return null;
		try {
			return UUID.fromString(s);
		} catch (Exception ex) {
			return null;
		}
	}
	
	static public byte[] getByteAsId(UUID uuid)	{
		if (uuid==null) return null;
		byte[] ba= new byte[16];
		ByteBuffer bb = ByteBuffer.wrap(ba);
	    bb.putLong(uuid.getMostSignificantBits());
	    bb.putLong(uuid.getLeastSignificantBits());
	    return ba;
	}

	static public UUID getIdAsByte(byte[] b) {
		if (b==null) return null;
	    ByteBuffer bb = ByteBuffer.wrap(b);
		return new UUID(bb.getLong(), bb.getLong());
	}

	static public byte[] getByteAsId(String s) {
		if (KDStr.isNullOrEmpty(s)) return null;
		return getByteAsId(UUID.fromString(s));
	}
}
