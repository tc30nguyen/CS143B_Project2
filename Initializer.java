
public class Initializer {

	public static void main(String[] args) 
	{
		MainMemoryManager mmm = new MainMemoryManager(Strategy.FIRST_FIT);
		mmm.mm_init(10);
		mmm.printMM();
		mmm.mm_request(2);
		mmm.printMM();
		mmm.mm_request(1);
		mmm.printMM();
		mmm.mm_request(1);
		mmm.printMM();
		mmm.mm_request(3);
		
		mmm.mm_release(4);
		mmm.printMM();
		mmm.mm_release(0);
		mmm.printMM();
		
		mmm.mm_request(3);
		mmm.printMM();
		mmm.mm_release(7); //fix bug: second block should be free
		mmm.printMM();
		
	}

}
