package com.itemsharing.itemservice.hystrix;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.itemsharing.itemservice.util.UserContextHolder;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariable;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestVariableLifecycle;
import com.netflix.hystrix.strategy.properties.HystrixProperty;

public class ThreadLocalAwareStrategy extends HystrixConcurrencyStrategy {

	private HystrixConcurrencyStrategy existingConcurrecyStrategy;
	
	public ThreadLocalAwareStrategy(HystrixConcurrencyStrategy existingConcurrecyStrategy) {
		this.existingConcurrecyStrategy = existingConcurrecyStrategy;
	}
	
	@Override
	public BlockingQueue<Runnable> getBlockingQueue(int maxQueueSize) {
		return existingConcurrecyStrategy != null ? existingConcurrecyStrategy.getBlockingQueue(maxQueueSize) : super.getBlockingQueue(maxQueueSize);
	}
	
	
	@Override
	public <T> HystrixRequestVariable<T> getRequestVariable(HystrixRequestVariableLifecycle<T> rv) {
		return existingConcurrecyStrategy != null ? existingConcurrecyStrategy.getRequestVariable(rv) : super.getRequestVariable(rv);
	}
	
	@Override
	public ThreadPoolExecutor getThreadPool(HystrixThreadPoolKey threadPoolKey, HystrixProperty<Integer> corePoolSize,
			HystrixProperty<Integer> maximumPoolSize, HystrixProperty<Integer> keepAliveTime, TimeUnit unit,
			BlockingQueue<Runnable> workQueue) {
		return existingConcurrecyStrategy != null ? existingConcurrecyStrategy.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue) : super.getThreadPool(threadPoolKey, corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}
	
	@Override
	public <T> Callable<T> wrapCallable(Callable<T> callable) {
		return existingConcurrecyStrategy != null ? existingConcurrecyStrategy.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext())) : super.wrapCallable(new DelegatingUserContextCallable<T>(callable, UserContextHolder.getContext()));
	}
}
