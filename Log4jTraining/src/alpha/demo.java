package alpha;
import org.apache.logging.log4j.*;
import org.testng.annotations.Test;

public class demo {
	
	//Init logger:
	private static Logger log = LogManager.getLogger(demo.class.getName());
	
	@Test
	public void testing() {
		log.debug("Debuging");
		log.info("Info");
		log.error("Error");
		log.fatal("Fatal");
	}

}
