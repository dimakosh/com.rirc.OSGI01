package com.rirc.OSGI01;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class ListToParts<T> implements Iterable<Iterable<T>> {
	private final Collection<T> arr;
	private final int zMax;

	public ListToParts(int _zMax) {
		arr= new ArrayList<T>();
		zMax= _zMax;
	}
	
    public boolean add(T e) {
    	return arr.add(e);
    }

    boolean addAll(Collection<? extends T> c) {
    	return arr.addAll(c);
    }

	@Override
	public Iterator<Iterable<T>> iterator() {
		Iterator<T> list= arr.iterator();
		return new Iterator<Iterable<T>>() {
			@Override
			public boolean hasNext() {
				return list.hasNext();
			}
			@Override
			public Iterable<T> next() {
				List<T> l= new ArrayList<T>(zMax);
				for (int i= 0; i< zMax; i++) {
					if (list.hasNext()) l.add(list.next());
					else break;
				}
				return l;
			}
		};
	}
}
