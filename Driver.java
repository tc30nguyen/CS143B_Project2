import java.util.LinkedList;
import java.io.File;
import java.util.Random;

public class Driver 
{
	private File output;
	private int currentlyAllocated;
	private int mem_size;
	private final int sim_step = 10000; //how many requests/releases to run
	private LinkedList<MemBlock> allocatedBlocks;
	private MainMemoryManager mmm;
	private Strategy strategy;

	private class MemBlock
	{
		private int index;
		private int size;

		public MemBlock(int index, int size)
		{
			this.index = index;
			this.size = size;
		}

		public int getIndex()
		{
			return index;
		}

		public int getSize()
		{
			return size;
		}
	}
	
	public Driver(int mem_size)
	{
		output = new File("output");
		currentlyAllocated = 0;
		this.mem_size = mem_size;
		allocatedBlocks = new LinkedList<>();
		mmm = null;
		strategy = null;
	}
	
	public void runSim(int avg, int stdDev, Strategy s)
	{
		double avg_mem_utilization = 0.0;
		double avg_search_count = 0.0;
		double mem_utilization = 0.0;
		strategy = s;
		
		mmm = new MainMemoryManager(s);
		mmm.mm_init(mem_size);
		
		//for sim_step, run request/release
		//get avg mem utilization & search time
		for(int i = 0; i < sim_step; i++)
		{
			int index = -1;
			do
			{
				int requestSize = gauss(avg, stdDev);
				index = mmm.mm_request(requestSize);
				if(index != -1)
				{
					allocatedBlocks.add(new MemBlock(index, requestSize));
					currentlyAllocated += requestSize;
				}
			}
			while(index != -1);

			//record mem utilization
			mem_utilization = ((double) currentlyAllocated) / ((double) mem_size);
			avg_mem_utilization += mem_utilization;

			//select block p tfrom allocatedBlocks to be released
			Random rand = new Random();
			if(!allocatedBlocks.isEmpty())
			{
				int r = allocatedBlocks.size() > 1 ? rand.nextInt(allocatedBlocks.size() - 1) : 0;
				MemBlock p = allocatedBlocks.get(r);
				//mmm.printMM();
				mmm.mm_release(p.getIndex());
				allocatedBlocks.remove(p);
				currentlyAllocated -= p.getSize();
			}
		}

		avg_search_count = mmm.getSearchCount() / sim_step;
		avg_mem_utilization /= sim_step;

		System.out.println("Average search count: " + avg_search_count);
		System.out.println("Average memory utilization" + avg_mem_utilization);
	}
	
	//returns a random int r with gaussian distribution where r > 0 and < max size
	private int gauss(int avg, int stdDev)
	{
		Random rand = new Random();
		int r =  0;
		while(r <= 0 || r > mem_size - 2)
			r = (int) rand.nextGaussian() * stdDev + avg;
		return r;
	}
}