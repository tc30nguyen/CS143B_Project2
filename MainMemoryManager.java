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

	int mm_request(int n)
	{
		//request block of n consecutive words
		//return index of first usable word or error if insufficient mem
	}

	int mm_release()
	{
		//releases previously requested block back to mm
	}
}
