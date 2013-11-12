import java.awt.*;
import java.util.*;

/**
 * This class contains data associated with processes,
 * and methods for manipulating this data as well as
 * methods for displaying a process in the GUI.
 *
 * You will probably want to add more methods to this class.
 */
public class Process implements Constants
{
	/** The ID of the next process to be created */
	private static long nextProcessId = 1;
	/** The font used by all processes */
	private static Font font = new Font("Arial", Font.PLAIN, 10);
	/** The ID of this process */
	private long processId;
	/** The color of this process */
	private Color color;
	/** The amount of memory needed by this process */
    private long memoryNeeded;
	/** The amount of cpu time still needed by this process */
    private long cpuTimeNeeded;
	/** The average time between the need for I/O operations for this process */
    private long avgIoInterval;
	/** The time left until the next time this process needs I/O */
    private long timeToNextIoOperation = 0;

	/** The time that this process has spent waiting in the memory queue */
	private long timeSpentWaitingForMemory = 0;
	/** The time that this process has spent waiting in the CPU queue */
	private long timeSpentInReadyQueue = 0;
	/** The time that this process has spent processing */
    private long timeSpentInCpu = 0;
	/** The time that this process has spent waiting in the I/O queue */
    private long timeSpentWaitingForIo = 0;
	/** The time that this process has spent performing I/O */
	private long timeSpentInIo = 0;

	/** The number of times that this process has been placed in the CPU queue */
	private long nofTimesInReadyQueue = 0;
	/** The number of times that this process has been placed in the I/O queue */
	private long nofTimesInIoQueue = 0;

	/** The global time of the last event involving this process */
	private long timeOfLastEvent;

	/** Time when process starts up **/
	private long startUpTime;

	/** The time this process ended */
	private long endTime;

	/**
	 * Creates a new process with given parameters. Other parameters are randomly
	 * determined.
	 * @param memorySize	The size of the memory unit.
	 * @param creationTime	The global time when this process is created.
	 */
	public Process(long memorySize, long creationTime) {
		Random rng = new Random();
		// Memory need varies from 100 kB to 25% of memory size
		memoryNeeded = 100 + (long) (rng.nextDouble() * (memorySize*0.25 - 100));
		// CPU time needed varies from 100 to 10000 milliseconds
		cpuTimeNeeded = 100 + (long)(rng.nextDouble()*9900);
		// Average interval between I/O requests varies from 1% to 25% of CPU time needed
		avgIoInterval = (1 + (long)(rng.nextDouble()*25))*cpuTimeNeeded/100;
		// The first and latest event involving this process is its creation
		timeOfLastEvent = creationTime;
		// Assign a process ID
		processId = nextProcessId++;
		// Assign a pseudo-random color used by the GUI
		int red = 64+(int)((processId*101)%128);
		int green = 64+(int)((processId*47)%128);
		int blue = 64+(int)((processId*53)%128);
		color = new Color(red, green, blue);
	}

	/**
	 * Draws this process as a colored box with a process ID inside.
	 * @param g	The graphics context.
	 * @param x	The leftmost x-coordinate of the box.
	 * @param y	The topmost y-coordinate of the box.
	 * @param w	The width of the box.
	 * @param h	The height of the box.
	 */
	public void draw(Graphics g, int x, int y, int w, int h) {
		g.setColor(color);
		g.fillRect(x, y, w, h);
		g.setColor(Color.black);
		g.drawRect(x, y, w, h);
		g.setFont(font);
		FontMetrics fm = g.getFontMetrics(font);
		g.drawString(""+processId, x+w/2-fm.stringWidth(""+processId)/2, y+h/2+fm.getHeight()/2);
	}

	/**
	 * This method is called when the process leaves the memory queue (and
	 * enters the cpu queue).
     * @param clock The time when the process leaves the memory queue.
     */
    public synchronized void leftMemoryQueue(long clock) {
		  timeSpentWaitingForMemory += clock - timeOfLastEvent;
		  timeOfLastEvent = clock;
    }

    /**
	 * Returns the amount of memory needed by this process.
     * @return	The amount of memory needed by this process.
     */
	public long getMemoryNeeded() {
		return memoryNeeded;
	}

    /**
	 * Updates the statistics collected by the given Statistic object, adding
	 * data collected by this process. This method is called when the process
	 * leaves the system.
     * @param statistics	The Statistics object to be updated.
     */
	public void updateStatistics(Statistics statistics) {
		statistics.totalTimeSpentWaitingForMemory += this.timeSpentWaitingForMemory;
		statistics.nofCompletedProcesses++;
		statistics.totalTimeInReadyQueue += this.timeSpentInReadyQueue;
		statistics.totalCPUTimeSpentProcessing += this.timeSpentInCpu;
        statistics.totalTimeWaitingForIO += this.timeSpentWaitingForIo;
        statistics.totalOTime += this.timeSpentInIo;
        statistics.totalNofTimesInReadyQueue += this.nofTimesInReadyQueue;
        statistics.totalNofTimesInIOQueue += this.nofTimesInIoQueue;
	}

	
	public void TotalRunningTime(Statistics stats) {
		stats.totalSystemTime += this.endTime - this.startUpTime;
	}
	
	/**
	 * 
	 * @return The time (in ms) until this process will require I/O next.
	 */
	public synchronized long timeUntilIO() {
		if (this.timeToNextIoOperation == 0) {
			Random rng = new Random();
			this.timeToNextIoOperation = (long) (rng.nextDouble() * avgIoInterval * 1.9 + 0.1 * avgIoInterval);
			return this.timeToNextIoOperation;
		} else {
			return this.timeToNextIoOperation;
		}
	}
	/**
	 * @return How much CPU Time this process requires (in ms)
	 */
	public long getCPUTimeNeeded() {
		return this.cpuTimeNeeded;
	}
	
	/**
	 * This function is called when the process leaves the CPU.
	 * @param clock The time when this process leaves the CPU.
	 */
	public synchronized void leaveCPU(long clock) {
		this.timeSpentInCpu += clock - this.timeOfLastEvent;
		this.cpuTimeNeeded -= clock - this.timeOfLastEvent;
		this.timeToNextIoOperation -= clock - this.timeOfLastEvent;
		this.endTime = clock;
		this.timeOfLastEvent = clock;
		notifyAll();
	}
	/**
	 * Call this method when the process enters the CPU
	 * @param clock The current time (in ms) (since the simulation began)
	 */
	public synchronized void enterCPU(long clock) {
		this.timeSpentInReadyQueue += clock - timeOfLastEvent;
		this.timeOfLastEvent = clock;
		notifyAll();
	}
	/**
	 * Call this method when the process enters the CPU queue.
	 * @param clock The current time (in ms) (since the simulation began)
	 */
	public synchronized void enterCPUQueue(long clock) {
		this.nofTimesInReadyQueue++;
		this.timeOfLastEvent = clock;
		notifyAll();
	}
	/**
	 * This method is called when the process gets to grab some IO, /yeah/ 
	 * @param clock The current time (in ms) (since the simulation began)
	 */
	public synchronized void enterIO(long clock) {
		this.timeSpentWaitingForIo += clock - timeOfLastEvent;
		this.timeOfLastEvent = clock;
		notifyAll();
	}
	/**
	 * This method is called when the process is torn away from the I/O
	 * @param clock The current time (in ms) (since the simulation began)
	 */
	public synchronized void leavesIO(long clock) {
		this.timeSpentInIo += clock - timeOfLastEvent;
		this.timeToNextIoOperation = this.timeUntilIO();
		this.timeOfLastEvent = clock;
		notifyAll();
	}
	/**
	 * This method is called when the process enters the IO queue
	 * @param clock The current time (in ms) (since the simulation began)
	 */
	public synchronized void enterIOQueue(long clock) {
		this.nofTimesInIoQueue++;
		this.timeSpentInReadyQueue += clock - timeOfLastEvent;
		this.timeOfLastEvent = clock;
		notifyAll();
	}
	/**
	 * this method saves us two lines of code in a lot of methods so yeah
	 * @param clock The current time (in ms) (since the simulation began)
	 */
	private synchronized void updateTimeOfLastEvent(long clock) {
		this.timeOfLastEvent = clock;
		notifyAll();
	}
}