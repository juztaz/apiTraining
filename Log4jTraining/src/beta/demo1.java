package beta;
import org.apache.logging.log4j.*;
import org.testng.annotations.Test;

public class demo1 {
	
	//Init logger:
	private static Logger log = LogManager.getLogger(demo1.class.getName());
	
	@Test
	public void testing() {
		log.debug("Debuging");
		log.info("Info");
		log.error("Error");
		log.fatal("Fatal");
	}

}
