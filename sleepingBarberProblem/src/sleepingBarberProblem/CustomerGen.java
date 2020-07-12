package sleepingBarberProblem;

import java.io.IOException;
import java.util.Date;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class CustomerGenerator implements Runnable 
{
	Bshop shop;
	int numOfCust;

	public CustomerGenerator(Bshop shop)
	{
		this.shop = shop;
	}

	public void run()
	{
		System.out.println("Enter the number of Barbers avilable to cut hair: ");
		Scanner sc = new Scanner(System.in);
//		BufferedReader bf = new BufferedReader(new InputStreamReader(System.in));
		int custCount = 0;
		custCount = sc.nextInt();

		ExecutorService openUp = Executors.newFixedThreadPool(custCount);
		Barber[]  employees = new Barber[custCount];

		System.out.println("Enter the number of Customers to enter the shop: ");
		numOfCust = sc.nextInt();

		System.out.println("Its time to open the barber shop");
		for(int i = 0; i < custCount; i++) {
			employees[i]= new Barber(shop,custCount);
			openUp.execute(employees[i]);
		}

		while(true)
		{
			Customer customer = new Customer(shop);
			customer.setInTime(new Date());
			Thread thcustomer = new Thread(customer);
			customer.setName("Customer Thread "+thcustomer.getId());
			thcustomer.start();

			Random rng = new Random();
			int mean=3,std=4;
			double list[] = new double[numOfCust+1];
			for(int i =0; i<numOfCust;i++) {
				list[i] = Math.round((mean + std * rng.nextGaussian()) * 10.0 / 100.0);
//				System.out.println("Customer Time : " + list[i]);
				try
				{
					TimeUnit.SECONDS.sleep((long)(list[i]));
				}
				catch(InterruptedException iex)
				{
					iex.printStackTrace();
				}

			}			

		}
	}

}
