package sleepingBarberProblem;

public class Barber implements Runnable
{
    Bshop shop;
    int numberOfBarbers;
    public Barber(Bshop shop,int numberOfBarbers)
    {
        this.shop = shop;
        this.numberOfBarbers = numberOfBarbers;
    }
    public void run()
    {
        try
        {
            Thread.sleep(2000);
        }
        catch(InterruptedException iex)
        {
            iex.printStackTrace();
        }
        System.out.println("Barber "+ Thread.currentThread().getId() +" is available");
        while(true)
        {
            shop.cutHair(numberOfBarbers);
        }
    }
}
