package cat.institutmarianao.shipments.services;

import java.util.List;

import cat.institutmarianao.shipments.model.Action;
import cat.institutmarianao.shipments.model.Shipment;
import cat.institutmarianao.shipments.model.forms.ShipmentsFilter;

public interface ShipmentService {
	List<Shipment> filterShipments(ShipmentsFilter filter);

	Shipment add(Shipment shipment);

	Action tracking(Action action);
}