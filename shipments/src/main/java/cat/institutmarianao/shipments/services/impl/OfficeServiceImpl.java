package cat.institutmarianao.shipments.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cat.institutmarianao.shipments.model.Office;
import cat.institutmarianao.shipments.services.OfficeService;

@Service
@PropertySource("classpath:application.properties")
public class OfficeServiceImpl implements OfficeService {

	@Value("${webService.host}")
	private String webServiceHost;

	@Value("${webService.port}")
	private String webServicePort;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public Office getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Office> getAllOffices() {
		// TODO Auto-generated method stub
		return null;
	}
}
