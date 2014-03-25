import java.util.Random;


public class IO {
	private Statistics stats;
	private Gui gui;
	
	private Queue ioQueue;
	private Process activeProc = null;
	private long avgIOTime;

	public IO(Statistics statistics, Gui gui, Queue IOQueue, long avgIOTime) {
		this.stats = statistics;
		this.gui = gui;
		this.ioQueue = IOQueue;
		this.avgIOTime = avgIOTime;
	}
	
	public Process begin() {
		if (this.ioQueue.isEmpty())
			return null;
		this.activeProc = (Process) this.ioQueue.removeNext();
		this.gui.setIoActive(this.activeProc);
		return this.activeProc;
	}
	
	public boolean addProcess(Process process) {
		this.ioQueue.insert(process);
		if (this.activeProc == null) {
			this.begin();
			return true;
		}
		return false;
	}

	public Process getProcess() {
		Process proc = this.activeProc;
		this.gui.setIoActive((this.activeProc = null));
		return proc;
	}

	public long getIOTime() {
		Random rng = new Random();
		return (long) (rng.nextDouble() * rng.nextDouble() * avgIOTime * 2);
	}
	
	public void updateQueueTime(long time) {
		long l = this.ioQueue.getQueueLength();
		this.stats.ioQueueLengthTime += l * time;
		if (l > stats.largestOccuringIOQueueLength) {
			stats.largestOccuringIOQueueLength = l;
		}
	}
}
