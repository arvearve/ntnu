/**
 * Created with IntelliJ IDEA.
 * User: Arve
 * Date: 21.09.13
 * Time: 20:42
 * To change this template use File | Settings | File Templates.
 */
import java.util.Random;

public class Door extends Thread {
    private ServingArea servingArea;
    private int counter = 1;
    private Random r =  new Random();

    public Door(ServingArea sa){ this.servingArea = sa; }

    public void run(){
        while(SushiBar.isOpen){
           Customer c = new Customer(counter++, servingArea);
           c.start();
            try{
                sleep(r.nextInt(SushiBar.doorWait)); // Random interval, up to 400ms
            } catch(InterruptedException e){
                SushiBar.write("InterruptedException" + e.getStackTrace());
            }
        }
        SushiBar.write("door closed");
        try{
            servingArea.printStats();
        }catch (InterruptedException e){e.printStackTrace();}
    }
}