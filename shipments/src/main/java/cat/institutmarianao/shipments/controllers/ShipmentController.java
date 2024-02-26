package cat.institutmarianao.shipments.controllers;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import cat.institutmarianao.shipments.model.Assignment;
import cat.institutmarianao.shipments.model.Delivery;
import cat.institutmarianao.shipments.model.Shipment;
import cat.institutmarianao.shipments.model.Shipment.Status;
import cat.institutmarianao.shipments.model.User;
import cat.institutmarianao.shipments.model.forms.ShipmentsFilter;
import cat.institutmarianao.shipments.services.ShipmentService;
import cat.institutmarianao.shipments.services.UserService;

@Controller
@SessionAttributes({ "user" })
@RequestMapping(value = "/shipments")
public class ShipmentController {

	@Autowired
	private UserService userService;

	@Autowired
	private ShipmentService shipmentService;

	@ModelAttribute("user")
	public User setupUser() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String username = authentication.getName();
		return (User) userService.loadUserByUsername(username);
	}

	@GetMapping("/new")
	public ModelAndView newShipment() {
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		ModelAndView newShipmentView = new ModelAndView("shipment");
		Shipment shipment = new Shipment();
		String username = authentication.getName();
		shipment.setReceptionist(username);
		newShipmentView.getModelMap().addAttribute("shipmentForm", shipment);
		return newShipmentView;
	}

	@PostMapping("/new")
	public String submitNewShipment(@Validated Shipment shipment, BindingResult result, ModelMap modelMap) {
		if (result.hasErrors()) {
			return "shipment";
		}
		shipmentService.add(shipment);
		return "redirect:/shipments/list/PENDING";
	}

	@GetMapping("/list/{shipment-status}")
	public ModelAndView allShipmentsList(@ModelAttribute("user") User user,
			@PathVariable("shipment-status") Status shipmentStatus) {
		ModelAndView shipmentView = new ModelAndView("shipments");
		ShipmentsFilter shipmentsFilter = new ShipmentsFilter();
		shipmentsFilter.setStatus(shipmentStatus);
		if (user.getRole().toString().equals(User.COURIER)) {
			shipmentsFilter.setCourierAssigned(user.getUsername());
		}
		if (user.getRole().toString().equals(User.RECEPTIONIST)) {
			shipmentsFilter.setReceptionist(user.getUsername());
		}
		List<Shipment> filteredShipments = shipmentService.filterShipments(shipmentsFilter);
		shipmentView.getModelMap().addAttribute("shipments", filteredShipments);
		shipmentView.getModelMap().addAttribute("shipmentStatus", shipmentStatus);
		return shipmentView;
	}

	@PostMapping("/assign")
	public String assignShipment(@Validated Assignment assignment) {
		assignment.setDate(new Date());
		shipmentService.tracking(assignment);
		return "redirect:/shipments/list/PENDING";
	}

	@PostMapping("/deliver")
	public String deliverShipment(@Validated Delivery delivery) {
		delivery.setDate(new Date());
		shipmentService.tracking(delivery);
		return "redirect:/shipments/list/IN_PROCESS";
	}
}
