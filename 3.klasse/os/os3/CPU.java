
public class CPU {
	
	Queue queue;
	Statistics statistics;
	Process active;
	Gui graphcs;
	long max;
	
	public CPU (Queue queue, long max, Statistics statistics, Gui gui) {
		this.queue = queue;
		this.statistics = statistics;
		this.graphcs = gui;
		this.max = max;
    }
	
	public void insertProcessInQueue(Process p) {
		queue.insert(p);
	}
	
	public boolean isIdle() {
		return this.active == null;
	}
	
	public Process getActive() {
		Process p = this.active;
		this.active = null;
		return p;
	}
	
	public long getMax() {
		return max;
	}
	
	public Process start() {
		if (!this.queue.isEmpty()) {
			Process p = (Process) this.queue.removeNext();
			this.active = p;
			graphcs.setCpuActive(p);
			return p;
		}
		else {
			this.active = null;
			graphcs.setCpuActive(null);
			return null;
		}
	}
	
	public void updateTime (long timePassed) {
		this.statistics.cpuQueueLengthTime += this.queue.getQueueLength() * timePassed; 
		if (this.queue.getQueueLength() > this.statistics.largestOccuringCPUQueueLength)
			this.statistics.largestOccuringCPUQueueLength = this.queue.getQueueLength();
	}
	
}
