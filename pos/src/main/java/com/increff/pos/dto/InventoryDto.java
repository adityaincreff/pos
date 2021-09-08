package com.increff.pos.dto;

import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.InventoryService;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class InventoryDto {
	@Autowired
	private InventoryService inventoryService;
	public InventoryPojo add(InventoryForm inventoryForm) throws ApiException {
		if (StringUtil.isEmpty(inventoryForm.getBarcode())) {
			throw new ApiException("Barcode cannot be empty");
		}
		inventoryForm = normalizeCheck(inventoryForm);
		InventoryPojo inventoryPojo = convert(inventoryForm);
		inventoryService.add(inventoryPojo, inventoryForm.getBarcode());
		return inventoryPojo;
	}

	public void update(int id, int quantity) throws ApiException {
		 inventoryService.update(id, quantity);


	}

	public InventoryData getById(int id) throws ApiException {
		return inventoryService.getById(id);

	}

	public List<InventoryData> getAll() {

		return inventoryService.getAll();
	}

	private InventoryForm normalizeCheck(InventoryForm inventoryForm) throws ApiException {
		if (StringUtil.negative(inventoryForm.getQuantity()) == true) {
			throw new ApiException("Quantity cannot be negative");

		}

		if (StringUtil.isEmpty(inventoryForm.getBarcode())) {
			throw new ApiException("Barcode cannot be empty");
		}
		inventoryForm.setBarcode(StringUtil.toLowerCaseTrim(inventoryForm.getBarcode()));
		return inventoryForm;
	}

	private InventoryPojo convert(InventoryForm inventoryForm) {
		InventoryPojo pojo=new InventoryPojo();
		pojo.setQuantity(inventoryForm.getQuantity());
		return pojo;
	}
}