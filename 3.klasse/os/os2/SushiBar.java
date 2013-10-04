import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;


public class SushiBar {
    //Creating the log file
    private static File log;
    private static String path = "./";

    //SushiBar settings:
    	//These parameters have to be changed to check that the program works in all situations.
	private static int capacity =10; //capacity of Sushi Bar
	private static int duration = 3; // Simulation time
	public static int maxOrder = 10; // Maximum number of orders for each customer
	public static int customerWait= 3000; // coefficient of eating time for customers
	public static int doorWait = 100; // coefficient of waiting time for door for creating next customer
    public static boolean isOpen=true;

    public static void main(String[] args) {
        log= new File(path + "log.txt");
        Clock clock = new Clock(duration);
        ServingArea servingArea = new ServingArea(capacity);
        Door door = new Door(servingArea);
        door.start();
	}

	
	//Writes actions in the log file and console
	public static void write(String str){
		try {
			FileWriter fw = new FileWriter(log.getAbsoluteFile(), true);
			BufferedWriter bw = new BufferedWriter(fw);
			bw.write(Clock.getTime() + ", " + str +"\n");
			bw.close();
			System.out.println(Clock.getTime() + ", " + str);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
