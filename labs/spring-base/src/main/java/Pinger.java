import java.util.Date;


public class Pinger {
	private int i = 0;
	
	public void ping() {
		System.out.printf("Ping #%d, at %s\n", ++i, new Date());

	}
}