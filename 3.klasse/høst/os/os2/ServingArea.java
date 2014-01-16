import java.util.ArrayList;

/**
 * Created with IntelliJ IDEA.
 * User: arve
 * Date: 9/26/13
 * Time: 22:55
 * To change this template use File | Settings | File Templates.
 */
public class ServingArea{
    private int takeawayOrders = 0;
    private int eatHereOrders = 0;
    private int capacity;
    ArrayList<Customer> sittingCustomers= new ArrayList<Customer>();

    public ServingArea(int capacity){
        this.capacity = capacity;
    }

    public synchronized void enter(Customer c) throws InterruptedException{
        while(sittingCustomers.size() == capacity){
            SushiBar.write(Thread.currentThread().getName()
                    +": Customer " + c.getCustomerId()
                    + " is waiting for a free seat");

            wait();
        }
        sittingCustomers.add(c);
        SushiBar.write(Thread.currentThread().getName()
                +": Customer " + c.getCustomerId()
                + " has a seat now.");
    }

    public synchronized void leave(Customer c) throws InterruptedException{
        sittingCustomers.remove(c);
        SushiBar.write(Thread.currentThread().getName()
                +": Customer " + c.getCustomerId()
                + " has left the shop.");
        notify();
    }

    // Stats

    public synchronized void printStats() throws InterruptedException{
        while(sittingCustomers.size() > 0 || SushiBar.isOpen){
            wait();
        }
        SushiBar.write(Thread.currentThread().getName()
                + "***** NO MORE CUSTOMERS - THE SHOP IS CLOSED NOW. *****");

        SushiBar.write(Thread.currentThread().getName()
                + "*** Statistics ***");
        SushiBar.write(Thread.currentThread().getName()
                + " Total Orders: " + (takeawayOrders + eatHereOrders));
        SushiBar.write(Thread.currentThread().getName()
                + " Takeaway orders: " + takeawayOrders);
        SushiBar.write(Thread.currentThread().getName()
                + " 'Eat Here' orders: " + eatHereOrders);
    }

    public synchronized void orderTakeaway(int items) throws InterruptedException{
        takeawayOrders += items;
    }

    public synchronized void orderHere(int items) throws InterruptedException{
        eatHereOrders += items;
    }


}
