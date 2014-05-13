public class Initializer {

	public static void main(String[] args) 
	{
		Driver driver = new Driver(10000);
		driver.runSim(20, 2000, Strategy.FIRST_FIT);
		driver.runSim(3000, 2000, Strategy.FIRST_FIT);
		driver.runSim(1000, 1000, Strategy.FIRST_FIT);
		driver.runSim(1000, 3000, Strategy.FIRST_FIT);
		
		driver.runSim(20, 2000, Strategy.BEST_FIT);
		driver.runSim(3000, 2000, Strategy.BEST_FIT);
		driver.runSim(1000, 1000, Strategy.BEST_FIT);
		driver.runSim(1000, 3000, Strategy.BEST_FIT);
		
		driver.outputResults();
	}

}
