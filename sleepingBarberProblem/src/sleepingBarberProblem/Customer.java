package sleepingBarberProblem;

import java.util.Date;

class Customer implements Runnable
{
    String name;
    Date inTime;
 
    Bshop shop;
 
    public Customer(Bshop shop)
    {
        this.shop = shop;
    }
 
    public String getName() {
        return name;
    }
 
    public Date getInTime() {
        return inTime;
    }
 
    public void setName(String name) {
        this.name = name;
    }
 
    public void setInTime(Date inTime) {
        this.inTime = inTime;
    }
 
    public void run()
    {
        goForHairCut();
    }
    private synchronized void goForHairCut()
    {
        shop.add(this);
    }
}