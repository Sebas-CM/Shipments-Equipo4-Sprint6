package cat.institutmarianao.shipments.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import cat.institutmarianao.shipments.model.Company;
import cat.institutmarianao.shipments.services.CompanyService;

@Service
@PropertySource("classpath:application.properties")
public class CompanyServiceImpl implements CompanyService {

	@Value("${webService.host}")
	private String webServiceHost;

	@Value("${webService.port}")
	private String webServicePort;

	@Autowired
	private RestTemplate restTemplate;

	@Override
	public List<Company> getAllCompanies() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Company getById(Long id) {
		// TODO Auto-generated method stub
		return null;
	}
}
