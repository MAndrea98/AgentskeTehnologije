package rest;

import javax.ejb.LocalBean;
import javax.ejb.Remote;
import javax.ejb.Stateless;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import dao.CollectorAgentDAO;
import dao.MasterAgentDAO;
import dao.PredictorAgentDAO;
import model.AID;
import model.CollectorAgent;
import model.MasterAgent;
import model.PredictorAgent;

@Stateless
@LocalBean
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@Remote(TenisRest.class)
@Path("/agents")
public class TenisRestBean implements TenisRest {

	@PUT
	@Path("/running/{type}/{name}")
	@Override
	public String runAgent(@PathParam("type") String type, @PathParam("name") String name) {
		// TODO Auto-generated method stub
		if (type.equals("MASTER")) {
			MasterAgent masterAgent = MasterAgentDAO.getInstance().findByName(name);
			if (masterAgent != null && !MasterAgentDAO.getInstance().getStartedMasterAgents().contains(masterAgent)) {
				MasterAgentDAO.getInstance().getStartedMasterAgents().add(masterAgent);
				return "Success";
			}
			else if (MasterAgentDAO.getInstance().getStartedMasterAgents().contains(masterAgent)) {
				return "Already running";
			}
			else 
				return "Error";
		}
		else if (type.equals("COLLECTOR")) {
			CollectorAgent collectorAgent = CollectorAgentDAO.getInstance().findByName(name);
			if (collectorAgent != null && !CollectorAgentDAO.getInstance().getStartedCollectorAgents().contains(collectorAgent)) {
				CollectorAgentDAO.getInstance().getStartedCollectorAgents().add(collectorAgent);
				return "Success";
			}
			else if (CollectorAgentDAO.getInstance().getStartedCollectorAgents().contains(collectorAgent)) {
				return "Already running";
			}
			else 
				return "Error";
		}
		else if (type.equals("PREDICTOR")) {
			PredictorAgent predictorAgent = PredictorAgentDAO.getInstance().findByName(name);
			if (predictorAgent != null && !PredictorAgentDAO.getInstance().getStartedPredictorAgents().contains(predictorAgent)) {
				PredictorAgentDAO.getInstance().getStartedPredictorAgents().add(predictorAgent);
				return "Success";
			}
			else if (PredictorAgentDAO.getInstance().getStartedPredictorAgents().contains(predictorAgent)) {
				return "Already running";
			}
			else 
				return "Error";
		}
		return "Error";
	}

	@DELETE
	@Path("/running")
	@Override
	public String stopAgent(AID aid) {
		// TODO Auto-generated method stub
		if (aid.getType().getName().equals("Master")) {
			MasterAgent masterAgent = MasterAgentDAO.getInstance().findByName(aid.getName());
			if (masterAgent != null) {
				boolean stopped = MasterAgentDAO.getInstance().getStartedMasterAgents().remove(masterAgent);
				if (stopped)
					return "Stopped";
				else 
					return "Already stopped";
			}
			else {
				return "Error";
			}
		}
		else if (aid.getType().getName().equals("Predikator")) {
			PredictorAgent predictorAgent = PredictorAgentDAO.getInstance().findByName(aid.getName());
			if (predictorAgent != null) {
				boolean stopped = PredictorAgentDAO.getInstance().getStartedPredictorAgents().remove(predictorAgent);
				if (stopped)
					return "Stopped";
				else 
					return "Already stopped";
			}
			else {
				return "Error";
			}
		}
		else if (aid.getType().getName().equals("Sakupljac")) {
			System.out.println("####");
			CollectorAgent collectorAgent = CollectorAgentDAO.getInstance().findByName(aid.getName());
			System.out.println(collectorAgent.getId().getName());
			if (collectorAgent != null) {
				boolean stopped = CollectorAgentDAO.getInstance().getStartedCollectorAgents().remove(collectorAgent);
				if (stopped)
					return "Stopped";
				else 
					return "Already stopped";
			}
			
		}
		return "Error";
	}

	@GET
	@Path("/test")
	@Override
	public String test() {
		System.out.println(MasterAgentDAO.getInstance().getAllMasterAgents().size() + " " + 
				   MasterAgentDAO.getInstance().getStartedMasterAgents().size() + " # " + 
				   CollectorAgentDAO.getInstance().getAllCollectorAgents().size() + " " +
				   CollectorAgentDAO.getInstance().getStartedCollectorAgents().size() + " # " +
				   PredictorAgentDAO.getInstance().getAllPredictorAgents().size() + " " + 
				   PredictorAgentDAO.getInstance().getStartedPredictorAgents().size());
		return "test";
	}

}