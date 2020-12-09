package narrative_analyser.starter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import narrative_analyser.starter.menu.MenuFactory;

public class Starter {

	private static final Logger LOG = LogManager.getLogger(Starter.class);

	public static void main(String[] args) {
		new Starter().run();
	}

	private void run() {
		LOG.info("**********************************************");
		LOG.info("App started");
		try {
			new Test().runTest();
			start();
			LOG.info("App closed");
			LOG.info("**********************************************");
		} catch (Exception e) {
			LOG.debug(e.getMessage());
		}
	}

	private void start() {
		Menu main = MenuFactory.getMainMenu();
		try {
			main.menuAction();
		} catch (ExitException e) {
			LOG.debug(e.getMessage());		
		}
	}

}
