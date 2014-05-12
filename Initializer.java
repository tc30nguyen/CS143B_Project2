public class Initializer {

	public static void main(String[] args) 
	{
		Driver driver = new Driver(20);
		driver.runSim(1, 3, Strategy.FIRST_FIT);
		driver.runSim(5, 3, Strategy.FIRST_FIT);
		driver.runSim(3, 5, Strategy.FIRST_FIT);
		driver.runSim(3, 8, Strategy.FIRST_FIT);
		
		driver.runSim(1, 3, Strategy.BEST_FIT);
		driver.runSim(5, 3, Strategy.BEST_FIT);
		driver.runSim(3, 5, Strategy.BEST_FIT);
		driver.runSim(3, 8, Strategy.BEST_FIT);
		
		driver.outputResults();
	}

}
