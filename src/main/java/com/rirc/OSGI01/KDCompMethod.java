package com.rirc.OSGI01;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;
import java.util.function.Consumer;

public interface KDCompMethod {
	final AtomicLong siTime= new AtomicLong();
	final AtomicBoolean noUserBeak= new AtomicBoolean();

	default void ping(/*ConcurrentMap<CompletableFuture<Void>,String> mCF2Mess*/) {
		siTime.set(System.currentTimeMillis());
	}
	
	default void setNoUserBeak() {
		noUserBeak.set(true);
	}
	
	default void userBeak() throws InterruptedException {
		if (isUserBeak()) throw new InterruptedException("Завершение процесса");
	}
	
	default boolean isUserBeak() {
		if (noUserBeak.get()) return false;
		if (Thread.interrupted()) return true;
		long d= System.currentTimeMillis()-siTime.get();
		return d>10*1000;
	}
	
	default void work(Process proc, Consumer<String> cSt) throws IOException, InterruptedException {
		try (BufferedReader out= new BufferedReader(new InputStreamReader(proc.getInputStream()))) {
			String st;
			while ((st= out.readLine())!=null) {
				cSt.accept(st);
				if (isUserBeak()) {
					proc.destroyForcibly();
					throw new InterruptedException("Завершение процесса");
				}
			}
		}

		while (!proc.waitFor(5, TimeUnit.SECONDS)) {
			if (isUserBeak()) {
				proc.destroyForcibly();
				throw new InterruptedException("Завершение процесса");
			}
		}
		
		int exitValue= proc.exitValue();
		if (exitValue!=0) {
			String err= "";
			try (BufferedReader out= new BufferedReader(new InputStreamReader(proc.getErrorStream()))) {
				String st;
				while ((st= out.readLine())!=null) {
					cSt.accept(st);
					userBeak();
					err+= st;
					err+= '\n';
				}
			}
			throw new InterruptedException(err);
		}
	}
}
