package com.rirc.OSGI01;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class KDExecutors {
	private static final ExecutorService executor= Executors.newCachedThreadPool();

	public static Executor getStdExecutor() {
		return executor;
	}
}
