/**
 * 
 */

import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author Gupta
 */
public class DiskBenchMark {

	/**
	 * @param args
	 * @throws InterruptedException
	 * @throws NumberFormatException
	 */
	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		DiskBenchMark diskBenchMark = new DiskBenchMark();

		diskBenchMark.bechmarkFileQuerry(Integer.parseInt(args[0]), Integer.parseInt(args[1]),
		        Integer.parseInt(args[2]));

		System.out.println("BenchMarkComplete");
	}

	private void bechmarkFileQuerry(int numThread, int times, int timeout) throws InterruptedException {
		ExecutorService executorService = Executors.newFixedThreadPool(numThread);
		for (int i = 0; i < times; i++) {
			executorService.submit(new FileCheckTask(String.valueOf(i)));
		}

		executorService.shutdown();
		executorService.awaitTermination(1, TimeUnit.MINUTES);

	}

	public static class FileCheckTask implements Runnable {
		private String fileName;

		public FileCheckTask(String fileName) {
			this.fileName = fileName;
		}

		@Override
		public void run() {
			long startTime = System.currentTimeMillis();
			new File("/mnt/d/workspace/" + fileName + ".txt").isFile();
			long endTime = System.currentTimeMillis();
			System.out.println("Latency: " + (endTime - startTime));
		}
	}
}
