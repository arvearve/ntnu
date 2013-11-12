import java.io.*;

public class Simulator implements Constants
{
	/** The queue of events to come */
    private EventQueue eventQueue;
	/** Reference to the memory unit */
    private Memory memory;
	/** Reference to the GUI interface */
	private Gui gui;
	/** Reference to the statistics collector */
	private Statistics statistics;
	/** The global clock */
    private long clock;
	/** The length of the simulation */
	private long simulationLength;
	/** The average length between process arrivals */
	private long avgArrivalInterval;
	private CPU cpu;
	private IO io;

	/**
	 * Constructs a scheduling simulator with the given parameters.
	 * @param memoryQueue			The memory queue to be used.
	 * @param cpuQueue				The CPU queue to be used.
	 * @param ioQueue				The I/O queue to be used.
	 * @param memorySize			The size of the memory.
	 * @param maxCpuTime			The maximum time quant used by the RR algorithm.
	 * @param avgIoTime				The average length of an I/O operation.
	 * @param simulationLength		The length of the simulation.
	 * @param avgArrivalInterval	The average time between process arrivals.
	 * @param gui					Reference to the GUI interface.
	 */
	public Simulator(Queue memoryQueue, Queue cpuQueue, Queue ioQueue, long memorySize,
			long maxCpuTime, long avgIoTime, long simulationLength, long avgArrivalInterval, Gui gui) {
		this.simulationLength = simulationLength;
		this.avgArrivalInterval = avgArrivalInterval;
		this.gui = gui;
		statistics = new Statistics();
		eventQueue = new EventQueue();
		memory = new Memory(memoryQueue, memorySize, statistics);
		clock = 0;
		cpu = new CPU(cpuQueue, maxCpuTime, statistics, gui);
		io = new IO(statistics, gui, ioQueue, avgIoTime);
    }

    /**
	 * Starts the simulation. Contains the main loop, processing events.
	 * This method is called when the "Start simulation" button in the
	 * GUI is clicked.
	 */
	public void simulate() {
		System.out.print("Simulating...");
		eventQueue.insertEvent(new Event(NEW_PROCESS, 0));
		while (clock < simulationLength && !eventQueue.isEmpty()) {
			Event event = eventQueue.getNextEvent();
			long timeDifference = event.getTime()-clock;
			clock = event.getTime();
			memory.timePassed(timeDifference);
			gui.timePassed(timeDifference);
			io.updateQueueTime(timeDifference);
			cpu.updateTime(timeDifference);
			if (clock < simulationLength) {
				processEvent(event);
			}
		}
		System.out.println("..done.");
		statistics.printReport(simulationLength);
	}

	/**
	 * Processes an event by inspecting its type and delegating
	 * the work to the appropriate method.
	 * @param event	The event to be processed.
	 */
	private void processEvent(Event event) {
		switch (event.getType()) {
			case NEW_PROCESS:
				createProcess();
				break;
			case SWITCH_PROCESS:
				switchProcess();
				break;
			case END_PROCESS:
				endProcess();
				break;
			case IO_REQUEST:
				processIoRequest();
				break;
			case END_IO:
				endIoOperation();
				break;
		}
	}

	/**
	 * Simulates a process arrival/creation.
	 */
	private void createProcess() {
		Process newProcess = new Process(memory.getMemorySize(), clock);
		memory.insertProcess(newProcess);
		flushMemoryQueue();
		long nextArrivalTime = clock + 1 + (long)(2*Math.random()*avgArrivalInterval);
		eventQueue.insertEvent(new Event(NEW_PROCESS, nextArrivalTime));
		statistics.nofCreatedProcesses++;
    }

	/**
	 * Transfers processes from the memory queue to the ready queue as long as there is enough
	 * memory for the processes.
	 */
	private void flushMemoryQueue() {
		Process p = memory.checkMemory(clock);
		while(p != null) {
			this.cpu.insertProcessInQueue(p);
			if (this.cpu.isIdle()) {
				this.switchProcess();
			}
			flushMemoryQueue();
			p = memory.checkMemory(clock);
		}
	}

	/**
	 * Simulates a process switch.
	 */
	private void switchProcess() {
		Process currentProc = cpu.getActive();
		if (currentProc != null) {
			currentProc.leaveCPU(clock);
			this.statistics.numberOFForcedProcessSwitches++;
			this.cpu.insertProcessInQueue(currentProc);
			currentProc.enterCPUQueue(clock);
		}
		Process proc = this.cpu.start();
		if (proc != null) {
			proc.enterCPU(clock);
			if (proc.timeUntilIO() > this.cpu.getMax() && proc.getCPUTimeNeeded() > this.cpu.getMax()) {
				this.eventQueue.insertEvent(new Event(SWITCH_PROCESS, clock + this.cpu.getMax()));
			}
			else if (proc.timeUntilIO() > proc.getCPUTimeNeeded()) {
				this.eventQueue.insertEvent(new Event(END_PROCESS, clock + proc.getCPUTimeNeeded()));
			}
			else {
				this.eventQueue.insertEvent(new Event(IO_REQUEST, clock + proc.timeUntilIO()));
			}
			
		}
	}

	/**
	 * Ends the active process, and deallocates any resources allocated to it.
	 */
	private void endProcess() {
		Process proc = this.cpu.getActive();
		if (proc != null) {
			this.statistics.nofCompletedProcesses++;
			proc.leaveCPU(clock);
			proc.updateStatistics(this.statistics);
			proc.TotalRunningTime(this.statistics);
			this.memory.processCompleted(proc);
		}
	}

	/**
	 * Processes an event signifying that the active process needs to
	 * perform an I/O operation.
	 */
	private void processIoRequest() {
		this.statistics.numberOFProcessedIOOperations++;
		Process proc = this.cpu.getActive();
		if (proc != null) {
			proc.leaveCPU(clock);
			proc.enterIOQueue(clock);
			if (this.io.addProcess(proc)) {
				proc.enterIO(clock);
				this.eventQueue.insertEvent(new Event(Constants.END_IO, clock + io.getIOTime())); //this should work...
			}
			switchProcess();
		}
	}

	/**
	 * Processes an event signifying that the process currently doing I/O
	 * is done with its I/O operation.
	 */
	private void endIoOperation() {
		Process proc = this.io.getProcess();
		if (proc != null) {
			this.statistics.numberOFProcessedIOOperations++;
			proc.leavesIO(clock);
			this.cpu.insertProcessInQueue(proc);
			proc.enterCPUQueue(clock);
			
			if (this.cpu.isIdle()){
				switchProcess();
			}
			
			proc = this.io.begin();
			if (proc != null) {
				proc.enterIO(clock);
				this.eventQueue.insertEvent(new Event(Constants.END_IO, clock + io.getIOTime()));
			}
		}
	}
	

	/**
	 * Reads a number from the an input reader.
	 * @param reader	The input reader from which to read a number.
	 * @return			The number that was inputted.
	 */
	public static long readLong(BufferedReader reader) {
		try {
			return Long.parseLong(reader.readLine());
		} catch (IOException ioe) {
			return 100;
		} catch (NumberFormatException nfe) {
			return 0;
		}
	}

	/**
	 * The startup method. Reads relevant parameters from the standard input,
	 * and starts up the GUI. The GUI will then start the simulation when
	 * the user clicks the "Start simulation" button.
	 * @param args	Parameters from the command line, they are ignored.
	 */
	public static void main(String args[]) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
		System.out.println("Please input system parameters: ");

		System.out.print("Memory size (KB): ");
		long memorySize = readLong(reader);
		while(memorySize < 400) {
			System.out.println("Memory size must be at least 400 KB. Specify memory size (KB): ");
			memorySize = readLong(reader);
		}

		System.out.print("Maximum uninterrupted cpu time for a process (ms): ");
		long maxCpuTime = readLong(reader);

		System.out.print("Average I/O operation time (ms): ");
		long avgIoTime = readLong(reader);

		System.out.print("Simulation length (ms): ");
		long simulationLength = readLong(reader);
		while(simulationLength < 1) {
			System.out.println("Simulation length must be at least 1 ms. Specify simulation length (ms): ");
			simulationLength = readLong(reader);
		}

		System.out.print("Average time between process arrivals (ms): ");
		long avgArrivalInterval = readLong(reader);

		SimulationGui gui = new SimulationGui(memorySize, maxCpuTime, avgIoTime, simulationLength, avgArrivalInterval);
	}
}