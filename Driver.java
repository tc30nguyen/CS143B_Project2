import java.util.LinkedList;
import java.io.File;

public class Driver 
{
	double mem_utilization;
	File output;
	int mem_size;
	int a; //average of gaus fnc
	int d; //standard deviation of gaus fnc
	int sim_step; //how many requests/releases to run
	int total_search_time;
	LinkedList<Integer> allocatedBlocks;
	Strategy strategy;
	
	public Driver(int mem_size, int a, int d, int sim_step, Strategy s)
	{
		mem_utilization = 0.0;
		output = new File("output");
		this.mem_size = mem_size;
		this.a = a;
		this.d = d;
		this.sim_step = sim_step;
		total_search_time = 0;
		allocatedBlocks = new LinkedList<>();
		strategy = s;
	}
}