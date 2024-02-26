package cat.institutmarianao.shipments.services.impl;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import cat.institutmarianao.shipments.model.Action;
import cat.institutmarianao.shipments.model.Shipment;
import cat.institutmarianao.shipments.model.forms.ShipmentsFilter;
import cat.institutmarianao.shipments.services.ShipmentService;

@Service
@PropertySource("classpath:application.properties")
public class ShipmentServiceImpl implements ShipmentService {

	@Value("${webService.host}")
	private String webServiceHost;

	@Value("${webService.port}")
	private String webServicePort;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Shipment> filterShipments(ShipmentsFilter filter) {
		final String baseUri = webServiceHost + ":" + webServicePort + "/shipments/find/all";

		UriComponentsBuilder uriTemplate = UriComponentsBuilder.fromHttpUrl(baseUri);

		uriTemplate.queryParam("status", filter.getStatus());
		uriTemplate.queryParam("category", filter.getCategory());

		if (filter.getCourierAssigned() != null) {
			uriTemplate.queryParam("courierAssigned", filter.getCourierAssigned());
		}
		if (filter.getReceptionist() != null) {
			uriTemplate.queryParam("receptionist", filter.getReceptionist());
		}
		ResponseEntity<Shipment[]> response = restTemplate.getForEntity(uriTemplate.encode().toUriString(),
				Shipment[].class);
		return Arrays.asList(response.getBody());
	}

	@Override
	public Shipment add(Shipment shipment) {
		final String uri = webServiceHost + ":" + webServicePort + "/shipments/save";

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Shipment> request = new HttpEntity<>(shipment, headers);

		return restTemplate.postForObject(uri, request, Shipment.class);
	}

	@Override
	public Action tracking(Action action) {
		final String uri = webServiceHost + ":" + webServicePort + "/shipments/save/action";
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		HttpEntity<Action> request = new HttpEntity<>(action, headers);
		return restTemplate.postForObject(uri, request, Action.class);
	}
}
