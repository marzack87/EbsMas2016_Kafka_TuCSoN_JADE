package examples.playground;

import jade.core.Profile;
import jade.core.ProfileImpl;
import jade.core.Runtime;
import jade.wrapper.AgentContainer;
import jade.wrapper.StaleProxyException;

public class Playground {

	public static void main(String[] args) {
		
		// This is the important method. This launches the jade platform.
		Runtime rt = Runtime.instance();
		
		Profile profile = new ProfileImpl();
		
		// With the Profile you can set some options for the container
		profile.setParameter(Profile.PLATFORM_ID, "Playground");
		profile.setParameter(Profile.CONTAINER_NAME, "K2");
		profile.setParameter(Profile.SERVICES, "it.unibo.tucson.jade.service.TucsonService");
		
		
		// Create the Main Container
		AgentContainer mainContainer = rt.createMainContainer(profile);
		
		PlaygroundGUI playgroundGUI = new PlaygroundGUI();
		playgroundGUI.setVisible(true);
		
		try {
			
			wait(10);
			
			int players_per_team = 2;
			
			KeeperAgent keeper = new KeeperAgent(playgroundGUI, players_per_team);
			mainContainer.acceptNewAgent("keeper", keeper).start();
			
			wait(5);
			
			String[] names = {"michael", "larry", "magic", "shaq", "kobe", "lebron"};
			
			for (int i = 0; i < (players_per_team*2 + 1); i++) {
				wait(1);
				mainContainer.acceptNewAgent(names[i], new PlayerAgent(playgroundGUI)).start();
			}
			
		} catch(StaleProxyException e) {
			e.printStackTrace();
		}

	}
	
	private static void wait(int seconds){
		try {
		    Thread.sleep(seconds * 1000);
		} catch(InterruptedException ex) {
		    Thread.currentThread().interrupt();
		}
	}

}
