package com.rirc.OSGI01;

import java.io.Serializable;

public class LSEnumValue implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public int index;

	public int Grp;
	public String NumBeg;
	public String NumEnd;
	public int HsOrStrByLSBeg;
	public int HsByLSEnd;
	public int PlatOnly;
	
	public String NPAdr;
	
	public String SQLQPrm() {
		return "?,?,?,?,?,?";
	}
	
	@Override
	public String toString() {
		return String.valueOf(Grp)+','+NumBeg+','+NumEnd+','+HsOrStrByLSBeg+','+HsByLSEnd+','+PlatOnly+','+NPAdr;
	}
}
