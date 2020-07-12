package sleepingBarberProblem;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;
 
public class BarberProblem {
 
    public static void main(String a[]) throws IOException
    {
        Bshop shop = new Bshop();
 
//        Barber barber = new Barber(shop);
        CustomerGenerator custGen = new CustomerGenerator(shop);

        Thread generateCust = new Thread(custGen);
        generateCust.start();
        
    }
}
 
class Bshop
{
    int NoOfchairs;
    List<Customer> listCustomer;
 
    public Bshop()
    {
    	Scanner myObj = new Scanner(System.in);
//        String NoOfchairs;
        
        System.out.println("Enter number of waiting chairs : "); 
        NoOfchairs = myObj.nextInt();   
           
        System.out.println("No chairs is: " +NoOfchairs); 
        listCustomer = new LinkedList<Customer>();
    }
 
    public void cutHair(int numberOfBarbers)
    {
        Customer customer;   
        System.out.println("Barber "+ Thread.currentThread().getId()+" waiting for lock.");
        synchronized (listCustomer)
        {
 
            if(listCustomer.size()==0)
            {
                System.out.println("Barber "+ Thread.currentThread().getId()+" is waiting for customer.");
                try
                {
                    listCustomer.wait();
                }
                catch(InterruptedException iex)
                {
                    iex.printStackTrace();
                }
            }
            System.out.println("Barber found a customer in the waiting room.");
            customer = (Customer)((LinkedList<?>)listCustomer).poll();
        }
        long duration=0;
        try
        {    
            System.out.println("Barber is currently cutting hair of Customer : "+customer.getName());
            duration = (long)(Math.random()*10);
            
//            int numberOfBarbers = 0;
			double[] list = new double[numberOfBarbers  + 1];
            double mean = 4, std = 2;
            Random rng = new Random();
            for (int i = 0; i < list.length; i++) {
                list[i] = Math.round((mean + std * rng.nextGaussian()) * 100.0 / 100.0);
//                System.out.println("Barber Time : " + list[i]);
            }
            long totalDuration = (long) list[(int) (Math.floor(Math.random() * numberOfBarbers) + 1)];
            Thread.sleep(totalDuration);
            
            TimeUnit.SECONDS.sleep(totalDuration);
        }
        catch(InterruptedException exception)
        {
            exception.printStackTrace();
        }
        System.out.println("Barber has completed cutting hair of Customer : "+customer.getName());
        System.out.println("Barber " + Thread.currentThread().getId() + " Completed cutting the hair in "+duration+ " seconds.");
    }
 
    public void add(Customer customer)
    {
    	
        System.out.println("New Customer : "+customer.getName()+ " Has just entered the shop at "+customer.getInTime());
 
        synchronized (listCustomer)
        {
            if(listCustomer.size() == NoOfchairs)
            {
                System.out.println("Looks like the barber is busy !! No chair available for customer "+customer.getName());
  //              System.out.println("Customer "+customer.getName()+"Exists...");
                return ;
            }
 
            ((LinkedList<Customer>)listCustomer).offer(customer);
            System.out.println("Customer : "+customer.getName()+ " wont have to go to the waiting room and has got the chair.");
             
            if(listCustomer.size()==1)
                listCustomer.notify();
        }
    }
}