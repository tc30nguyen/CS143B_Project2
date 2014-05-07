public class MainMemoryManager 
{
	int[] mainMemory;
	Strategy strategy;

	public MainMemoryManager(Strategy s)
	{
		mainMemory = null;
		strategy = s;
	}

	public int mm_init(int mem_size)
	{
		mainMemory = new int[mem_size];
		return mem_size;
	}

	public int mm_request(int n)
	{
		//request block of n consecutive words
		//return index of first usable word or error if insufficient mem
	}

	public int mm_release()
	{
		//releases previously requested block back to mm
	}

	private firstFit(int n)
	{
		boolean placed = false;
		for(int i = 0; i < mainMemory.length && !placed; i++)
		{
			if(mainMemory[i] * -1 >= n)
			{
				int difference = mainMemory[i] + n + 2; //calculate remaining space
				int rightSide = 1 + i + n;
				mainMemory[i] = n; //new block size left
				mainMemory[rightSide] = n; //new block size right

				if(difference == 2) //if n perfectly fits the hole, do nothing
				{ }
				else if (difference == 1 || difference == 0) 
				{
					mainMemory[rightSide + 1] = 1; //sets size 1 hole to 1, denoting that it cannot hold anything due to lack of space for both tags and data
					if (difference == 0) 
						mainMemory[rightSide + 2] = 1; //sets size 1 hole to 1, denoting that it cannot hold anything due to lack of space for both tags and data
				}
				else //retag the original hole with its decreased size
				{
					mainMemory[rightSide + 1] = difference;
					mainMemory[rightSide + 2 - difference] = difference;
				}

				placed = true;
			}
		}
	}
}
