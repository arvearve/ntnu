/**
 * Created with IntelliJ IDEA.
 * User: Arve
 * Date: 21.09.13
 * Time: 20:39
 * To change this template use File | Settings | File Templates.
 */

import java.util.Random;


public class Customer extends Thread {
    private int id;
    private int orderCount;
    private int takeawayCount;
    private int eatHereCount;
    private ServingArea servingArea;

    public Customer(int newId, ServingArea servingArea){
        this.id = newId;
        this.servingArea = servingArea;
        Random rng = new Random();
        this.orderCount = rng.nextInt(SushiBar.maxOrder -1 ) + 1; // 1-10
        this.takeawayCount = rng.nextInt(orderCount); // some items for takeaway
        this.eatHereCount = orderCount - takeawayCount;
    }

    public int getCustomerId(){
        return this.id;
    }

    public void run(){
        SushiBar.write(Thread.currentThread().getName()
                +": Customer " + getCustomerId()
                + " is appears at the shop");
        try{
            servingArea.enter(this);
            servingArea.orderTakeaway(takeawayCount);
            servingArea.orderHere(eatHereCount);
            eat(eatHereCount);
            servingArea.leave(this);
        }catch(InterruptedException e){ e.printStackTrace();}
    }


    private void eat(int foodItems) throws InterruptedException{
        SushiBar.write(Thread.currentThread().getName()
                +": Customer " + getCustomerId()
                + " started eating.");
        sleep(foodItems*SushiBar.customerWait);
        SushiBar.write(Thread.currentThread().getName()
                +": Customer " + getCustomerId()
                + " finished eating " + foodItems + " pieces of sushi.");


    }
}
