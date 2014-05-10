import java.util.LinkedList;
import java.io.File;
import java.util.Random;

public class Driver 
{
	double mem_utilization;
	File output;
	int mem_size;
	int a; //average of gaus fnc
	int d; //standard deviation of gaus fnc
	final int sim_step = 10000; //how many requests/releases to run
	int total_search_time;
	LinkedList<Integer> allocatedBlocks;
	Strategy strategy;
	
	public Driver(int mem_size, int a, int d, int sim_step, Strategy s)
	{
		mem_utilization = 0.0;
		output = new File("output");
		this.mem_size = mem_size;
		a = 0;
		d = 0;
		total_search_time = 0;
		allocatedBlocks = new LinkedList<>();
		strategy = null;
	}
	
	public void runSim(int a, int d, Strategy s)
	{
		this.a = a;
		this.d = d;
		strategy = s;
		
		//for sim_step, run request/release
		//get avg mem utilization & search time
		for(int i = 0; i < sim_step; i++)
		{
			int index = -1;
			do
			{
				index = mm_request(getRand); //request must count # holes examined for avg search
				if(index != -1)
					allocatedBlocks.add(index);
			}
			while(index != -1)

			//record mem utilization: add block sizes, divide by total mem size. computer average for all iterations
			//select block p tfrom allocatedBlocks to be released
			//mm_release(p):
		}
	}
	
	private int getRand(int avg, int stdDev)
	{
		return 0;
	}
}