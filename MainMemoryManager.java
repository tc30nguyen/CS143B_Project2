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
		if(mem_size > 2)
		{
			int initialHole = (mem_size - 2) * -1;
			mainMemory[0] = initialHole;
			mainMemory[mem_size - 1] = initialHole;
		}
		
		return mem_size;
	}

	//returns -1 for no fit
	public int mm_request(int n)
	{
		//request block of n consecutive words
		//return index of first usable word or error if insufficient mem
		return firstFit(n);
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
				while(mainMemory[currentIndex] == 0)
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
						while(mainMemory[currentIndex] == 0)
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
				int move = mainMemory[i] > 0 ? mainMemory[i] : mainMemory[i] * -1;
				i += move + 1;
			}
		}
		return -1;
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
