package com.redhat.ea.fair.service;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.redhat.ea.fair.Risk;
import com.redhat.ea.fair.RiskType;

@Path("/risk")
public class RiskService {

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@Path("/calculate")
	public RiskAnalysis calculateRisk(RiskEventRequest req) {

		Risk rp = new Risk(req.getPrimaryLossTitle(), RiskType.Primary);
		rp.setLossFrequency(req.getLefPrimary().getLow(), req.getLefPrimary()
				.getHigh(), req.getLefPrimary().getLikely());
		rp.setLossMagnitude(req.getLemPrimary().getLow(), req.getLemPrimary()
				.getHigh(), req.getLemPrimary().getLikely());
		try {
			rp.runSimulation(req.getIterations());
		} catch (IllegalStateException ise) {

		}

		Risk rs = null;
		if (req.getLefSecondary() != null && req.getLemSecondary() != null && req.getLefSecondary().getLow() > 0.0) {
			rs = new Risk(req.getSecondaryLossTitle(), RiskType.Secondary);
			rs.setLossFrequency(req.getLefSecondary().getLow(), req
					.getLefSecondary().getHigh(), req.getLefSecondary()
					.getLikely());
			rs.setLossMagnitude(req.getLemSecondary().getLow(), req
					.getLemSecondary().getHigh(), req.getLemSecondary()
					.getLikely());
			try {
				rs.runSimulation(req.getIterations());
			} catch (IllegalStateException ise) {

			}
		}

		RiskAnalysis ra = new RiskAnalysis(req.getTitle(),
				req.getDescription(), rp, rs, "TBD");

		return ra;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/exampleRisk")
	public Risk showExampleRisk() {
		Risk r = new Risk("Example Primary Risk", RiskType.Primary);
		r.setLossFrequency(0.0001d, 1.0d, 0.5d);
		r.setLossMagnitude(10, 100, 50);
		r.runSimulation(100);
		return r;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@Path("/exampleAnalysis")
	public RiskAnalysis showExampleAnalysis() {
		Risk r = new Risk("Example Primary Risk", RiskType.Primary);
		r.setLossFrequency(.05d, .43d, .14d);
		r.setLossMagnitude(70000, 780000, 440000);
		r.runSimulation(3000);

		Risk r2 = new Risk("Example Secondary Risk", RiskType.Secondary);
		r2.setLossFrequency(.02d, .17d, .05d);
		r2.setLossMagnitude(250000, 17500000, 1100000);
		r2.runSimulation(3000);

		RiskAnalysis ra = new RiskAnalysis("Sample Risk Analysis",
				"Detailed description goes here.", r, r2, "TBD");
		return ra;
	}

}
