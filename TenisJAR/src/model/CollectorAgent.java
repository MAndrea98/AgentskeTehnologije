package model;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

@Stateful
@LocalBean
public class CollectorAgent extends Agent implements AgentInterface {

	@Override
	public void handleMessage(ACLMessage message) {
		// TODO Auto-generated method stub
		
	}

}
