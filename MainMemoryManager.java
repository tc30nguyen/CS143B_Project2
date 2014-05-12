public class MainMemoryManager 
{
	private int searchCount;
	private int[] mainMemory;
	private Strategy strategy;

	public MainMemoryManager(Strategy s)
	{
		searchCount = 0;
		mainMemory = null;
		strategy = s;
	}

	public int mm_init(int mem_size)
	{
		mainMemory = new int[mem_size];
		if(mem_size > 2)
		{
			int initialHole = (mem_size - 2) * -1;
			mainMemory[0] = initialHole;
			mainMemory[mem_size - 1] = initialHole;
		}
		
		return mem_size;
	}

	//returns -1 for no fit
	public int mm_request(int n) //throws Exception
	{
		//request block of n consecutive words
		//return index of first usable word or error if insufficient mem
		if(n <= 0)
			return -1; //throw invalid input
		if(strategy == Strategy.FIRST_FIT)
			return firstFit(n);
		else if(strategy == Strategy.BEST_FIT)
			return bestFit(n);
		else
			return -1;//throw UnhandledStrategyException();
	}		

	public int mm_release(int index)
	{
		int counter = mainMemory[index];
		int currentIndex = index - 1;

		//combine left
		if(currentIndex >= 0 && mainMemory[currentIndex] <= 0)
		{
			mainMemory[index] = 0;

			//combine with empty tags
			if(mainMemory[currentIndex] == 0)
			{
				while(currentIndex >= 0 && mainMemory[currentIndex] == 0)
				{
					currentIndex--;
					counter++;
				}
				currentIndex++;
			}

			//combine with hole
			else
			{
				counter++;
				int toMove = mainMemory[currentIndex] * -1 + 1;
				mainMemory[currentIndex] = 0;
				currentIndex -= toMove;
				counter += toMove;
			}
			
			index = currentIndex;
			mainMemory[index] = counter * -1;
			currentIndex += counter + 1; //move currentIndex to right side of block
			
		}
		else
		{
			currentIndex = index + mainMemory[index] + 1; //move currentIndex to right side of block
			mainMemory[index] *= -1;
		}

		//combine right
		if(currentIndex == mainMemory.length - 1)
			mainMemory[currentIndex] = counter * -1;
		else
		{
			currentIndex++;
			if(mainMemory[currentIndex] <= 0)
			{
				mainMemory[currentIndex - 1] = 0;

				//combine with empty tags
				if(mainMemory[currentIndex] == 0)
					{
						while(currentIndex < mainMemory.length && mainMemory[currentIndex] == 0)
						{
							currentIndex++;
							counter++;
						}
						currentIndex--;
					}
				
				//combine with hole
				else
				{
					counter++;
					int toMove = mainMemory[currentIndex] * -1 + 1;
					mainMemory[currentIndex] = 0;
					currentIndex += toMove;
					counter += toMove;
				}
				mainMemory[index] = counter * -1;
			}
			else
				currentIndex--;

			mainMemory[currentIndex] = counter * -1;
		}
		return index;
	}

	//iterates through memory array, giving the request the first block that fits. Returns -1 for no fit
	private int firstFit(int n)
	{
		for(int i = 0; i < mainMemory.length; i++)
		{
			searchCount++;
			if(mainMemory[i] * -1 >= n) //current hole can fit n
			{
				int difference = mainMemory[i] + n + 2; //calculate remaining space
				int rightSide = 1 + i + n;
				mainMemory[i] = n; //new block size left
				mainMemory[rightSide] = n; //new block size right

				if(difference == 2) //if n perfectly fits the hole, do nothing
				{ }
				else if(difference == 1 || difference == 0) 
				{
					mainMemory[rightSide + 1] = 0; //sets size 1 hole to 0, denoting that it cannot hold anything due to lack of space for both tags and data
					if (difference == 0) 
						mainMemory[rightSide + 2] = 0; //sets size 2 hole to 0, denoting that it cannot hold anything due to lack of space for both tags and data
				}
				else //retag the original hole with its decreased size
				{
					mainMemory[rightSide + 1] = difference;
					mainMemory[rightSide + 2 - difference] = difference;
				}

				return i;
			}
			else //move the iterator to the next memory block
			{
				int move = 0;
				if(mainMemory[i] > 0)
					move = mainMemory[i];
				else if(mainMemory[i] == 0)
					move--; //let i increment by 1
				else
					move = mainMemory[i] * -1;
				i += move + 1;
			}
		}
		return -1;
	}
	
	//iterates through memory array, giving the request the block that has the smallest difference in size. Returns -1 for no fit
	private int bestFit(int n)
	{
		int index = 0;
		int minDifference = Integer.MAX_VALUE;
		int minIndex = -1;
		
		while(index < mainMemory.length)
		{
			//if free, check difference and go to next block
			//else, go to next block
			//return minDifference at the end
			searchCount++;
			while(index < mainMemory.length && mainMemory[index] == 0)
			{
				index++;
				searchCount++;
			}
			if(index >= mainMemory.length)
				return -1;
			if(mainMemory[index] < 0)
			{
				int dif = mainMemory[index] * -1 - n;
				if(dif >= 0 && dif < minDifference)
				{
					minDifference = dif;
					minIndex = index;
				}
				index += mainMemory[index] * -1 + 2;
			}
			else
				index += mainMemory[index] + 2;
		}
		
		if(minIndex == -1 || minDifference < 0)
			return -1;
		
		int rightSide = minIndex +  n + 1;
		int newIndex = rightSide + 1;
		mainMemory[minIndex] = n;
		mainMemory[rightSide] = n;
		
		if(minDifference == 0) //if n perfectly fits in hole
		{ }
		else if(minDifference == 2 || minDifference == 1) //if remaining hole is not large enough to hold tags and memory
		{
			mainMemory[newIndex] = 0;
			if(minDifference == 2)
				mainMemory[newIndex + 1] = 0;
		}
		else
		{
			int newHole = (minDifference - 2) * -1;
			mainMemory[newIndex] = newHole;
			mainMemory[newIndex + (newHole * -1) + 1] = newHole;
		}
		
		return minIndex;
	}

	public int getSearchCount()
	{
		return searchCount;
	}

	public void resetSearchCount()
	{
		searchCount = 0;
	}

	//TESTING ONLY
	public void setMM(int[] mm)
	{
		mainMemory = mm;
	}
	
	public void printMM()
	{
		System.out.print("[");
		for(int i = 0; i < mainMemory.length; i++)
		{
			System.out.print(mainMemory[i]);
			if(i == (mainMemory.length - 1))
				System.out.print("]\n");
			else
				System.out.print(", ");
		}
	}
}
