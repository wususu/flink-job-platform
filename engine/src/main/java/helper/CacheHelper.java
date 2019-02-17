package helper;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public abstract class CacheHelper<K, V> {

	private ConcurrentHashMap<K, V> cache;
	private static ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

	{
		prepare();
		cache = getNew();
		init();
	}
	
	public Map<K, V> getAll() {
		return cache;
	}
	
	protected void init() {
		scheduledExecutorService.scheduleWithFixedDelay(new Flasher(), 0, 5, TimeUnit.MINUTES);
	}
	
	public abstract void prepare();
	
	public abstract ConcurrentHashMap<K, V> getNew();
	
	private class Flasher implements Runnable {

		public void run() {
			cache = getNew();
		}
		
	}
	
}
