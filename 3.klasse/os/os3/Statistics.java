/**
 * This class contains a lot of public variables that can be updated
 * by other classes during a simulation, to collect information about
 * the run.
 */
public class Statistics
{
	/** The number of processes that have exited the system */
	public long nofCompletedProcesses = 0;
	/** The number of processes that have entered the system */
	public long nofCreatedProcesses = 0;
	/** The total time that all completed processes have spent waiting for memory */
	public long totalTimeSpentWaitingForMemory = 0;
	/** The time-weighted length of the memory queue, divide this number by the total time to get average queue length */
	public long memoryQueueLengthTime = 0;
	/** The largest memory queue length that has occured */
	public long memoryQueueLargestLength = 0;
	
	public long numberOFForcedProcessSwitches = 0;
	public long numberOFProcessedIOOperations = 0;
	public long totalCPUTimeSpentProcessing = 0;
	public long largestOccuringCPUQueueLength = 0;
	public long largestOccuringIOQueueLength = 0;
	
	public long cpuQueueLengthTime = 0;
	public long ioQueueLengthTime = 0;
	public long totalNofTimesInReadyQueue = 0;
	public long totalNofTimesInIOQueue = 0;
	public long totalSystemTime = 0;
	public long totalTimeInReadyQueue = 0;
	public long totalTimeWaitingForIO = 0;
	public long totalOTime = 0;
	
	
	/**
	 * Prints out a report summarizing all collected data about the simulation.
	 * @param simulationLength	The number of milliseconds that the simulation covered.
	 */
	public void printReport(long simulationLength) {
		System.out.println();
		System.out.println("Simulation statistics:");
		System.out.println();
		System.out.println("Number of completed processes:                                    " + nofCompletedProcesses);
		System.out.println("Number of created processes:                                      " + nofCreatedProcesses);
		System.out.println("Number of (forced) process switches:                              " + numberOFForcedProcessSwitches);
		System.out.println("Number of processed IO operations:                                " + numberOFProcessedIOOperations);
		System.out.println("Average throughput (processes per second):                        " + (float) nofCompletedProcesses / simulationLength * 100);
		
		System.out.println();
		
		System.out.println("Total CPU time spent processing:                                  " + totalCPUTimeSpentProcessing + " ms");
		System.out.println("Fraction of CPU time spent processing:                            " + (float) totalCPUTimeSpentProcessing / simulationLength * 100 + " %");
		System.out.println("Total CPU time spent waiting:                                     " + (simulationLength - totalCPUTimeSpentProcessing) + " ms");
		System.out.println("Fraction of CPU time spent waiting:                               " + (float) (simulationLength - totalCPUTimeSpentProcessing) / simulationLength * 100 + " %");
		
		System.out.println();
		
		System.out.println("Largest occuring memory queue length:                             " + memoryQueueLargestLength);
		System.out.println("Average memory queue length:                                      " + (float)memoryQueueLengthTime/simulationLength);
		System.out.println("Largest occuring cpu queue length:                                " + largestOccuringCPUQueueLength);
		System.out.println("Average cpu queue length:                                         " + (float) cpuQueueLengthTime /simulationLength);
		System.out.println("Largest occuring I/O queue length:                                " + largestOccuringIOQueueLength);
		System.out.println("Average I/O queue length:                                         " + (float) ioQueueLengthTime /simulationLength);
			
			if(nofCompletedProcesses > 0) {
			System.out.println("Average # of times a process has been placed in memory queue: " + 1);
			System.out.println("Average # of times a process has been placed in cpu queue:    " + (float) totalNofTimesInReadyQueue / nofCompletedProcesses);
			System.out.println("Average # of times a process has been placed in I/O queue:    " + (float) totalNofTimesInIOQueue / nofCompletedProcesses );
			
			System.out.println();
			
			System.out.println("Average time spent in system per process:                     " + (float) totalSystemTime / nofCompletedProcesses + " ms");
			System.out.println("Average time spent waiting for memory per process:            " + (float) totalTimeSpentWaitingForMemory/nofCompletedProcesses+" ms");
			System.out.println("Average time spent waiting for cpu per process:               " + (float) totalTimeInReadyQueue / nofCompletedProcesses + " ms");
			System.out.println("Average time spent processing per process:                    " + (float) totalCPUTimeSpentProcessing / nofCompletedProcesses + " ms");
			System.out.println("Average time spent waiting for I/O per process:               " + (float) totalTimeWaitingForIO / nofCompletedProcesses + " ms");
			System.out.println("Average time spent in I/O per process:                        " + (float) totalOTime / nofCompletedProcesses + " ms");
		}
	}
}
