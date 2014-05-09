import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;


public class MainMemoryManagerTest 
{
	MainMemoryManager mmm;
	//int[] mm;
	
	@Before
	public void setUp() throws Exception 
	{
		//mm = null;
		mmm = new MainMemoryManager(Strategy.FIRST_FIT);
	}

	@Test
	public void test() 
	{
		int[] mm = {-3,0,0,0,-3,2,0,0,2,0,1,0,1};
		mmm.mm_init(mm.length);
		mmm.mm_release(5);
		int[] expected = {8,0,0,0,0,0,0,0,0,8,1,0,1};
		assertArrayEquals(expected, mm);
	}

}
