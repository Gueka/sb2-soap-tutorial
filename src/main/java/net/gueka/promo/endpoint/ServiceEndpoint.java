package net.gueka.promo.endpoint;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import lombok.extern.log4j.Log4j2;
import net.gueka.promo.schema.Data;
import net.gueka.promo.schema.ObjectFactory;
import net.gueka.promo.schema.PromotionRequest;
import net.gueka.promo.schema.PromotionResponse;

@Log4j2
@Endpoint
public class ServiceEndpoint {

	public static final String NAMESPACE_URI = "http://www.gueka.net/promo/schema";
	private static final String SUBMIT_SM_REQ = "promotionRequest";

	@PayloadRoot(namespace = NAMESPACE_URI, localPart = SUBMIT_SM_REQ)
	@ResponsePayload
	public PromotionResponse submitSM(@RequestPayload PromotionRequest request) {
		log.info("A message just arrive with userId: " + request.getData().getUserId());
		return generateResponse(getOffers(request.getData()));
	}
	
	private PromotionResponse generateResponse(List<String> offers) {
		PromotionResponse response = new ObjectFactory().createPromotionResponse();
		response.getOffers().addAll(offers);
		return response;
	}

	private List<String> getOffers(Data data) {
		List<String> offers = new ArrayList<>();
		Calendar newUserDate = new GregorianCalendar();
		newUserDate.add(Calendar.MONTH, -6);
		Calendar initDate = data.getInitDate().toGregorianCalendar();

		if(newUserDate.compareTo(initDate) > 0){
			// TODO: get month rest with discount
			offers.add("Has 20% discount for X months");
		}
		
		// TODO: check birthday discount
		offers.add("Has 10% birthday discount");
		
		return offers;
	}

}