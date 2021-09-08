package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.InventoryData;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class InventoryService {
	@Autowired
	private InventoryDao inventoryDao;
	@Autowired
	private ProductDao productDao;


@Transactional(readOnly = true)
	public List<InventoryData> getAll() {


		List<ProductPojo> productPojoList = productDao.getAll();
		List<InventoryPojo> inventoryPojoList = inventoryDao.getAll();
		List<InventoryData> list = convert(productPojoList, inventoryPojoList);

		return list;
	}

	@Transactional
	public void update(int id, int quantity) throws ApiException {
		if (quantity < 0) {

			throw new ApiException("Quantity cannot be negative");
		}
		inventoryDao.setInventory(id, quantity);
	}

	public void add(InventoryPojo inventoryPojo, String barcode) throws ApiException {
		ProductPojo p = productDao.select(barcode);
		if (p == null) {
			throw new ApiException("No such product exists");
		}
		InventoryPojo inv = inventoryDao.select(p.getProduct_id());
		if (inv != null) {
			if (inventoryPojo.getQuantity() + inv.getQuantity() < 0) {
				throw new ApiException("Quantity cannot be negative");
			}
			inventoryDao.update(p.getProduct_id(), inventoryPojo.getQuantity());
			return;
		}
		if (inventoryPojo.getQuantity() < 0) {
			throw new ApiException("Quantity cannot be negative");
		}
		inventoryPojo.setProduct_id(p.getProduct_id());
		inventoryDao.add(inventoryPojo);
	}

	@Transactional(readOnly = true)
	public InventoryData getById(String barcode) throws ApiException {
	  int id=barcodetoId(barcode);
	    InventoryPojo i= inventoryDao.select(id);
		ProductPojo p= productDao.select(id);

		if(p==null)
		{
			throw new ApiException("No such products exists");
		}
		if(i==null)
		{
			throw new ApiException("No inventory for such product exists");
		}
		return convert(p,i);
	}
	@Transactional(readOnly = true)
	public InventoryData getById(int id) throws ApiException {
		InventoryPojo i = inventoryDao.select(id);
		ProductPojo p = productDao.select(id);
		if(p==null){
			throw new ApiException("No such product exists");
		}
		if(i==null){
			throw new ApiException("No inventory for product exists");
		}
		return convert(p, i);
	}
	private int barcodetoId(String barcode) {
	        return productDao.select(barcode).getProduct_id();
	    }

	private List<InventoryData> convert(List<ProductPojo> productPojoList, List<InventoryPojo> inventoryPojoList) {
        List<InventoryData> inventoryDataList = new ArrayList<InventoryData>();
        HashMap<Integer, ProductPojo> hm = new HashMap<Integer, ProductPojo>();
        for (ProductPojo p : productPojoList) {
            hm.put(p.getProduct_id(),p);
        }
        for (InventoryPojo i : inventoryPojoList) {
            ProductPojo p = hm.get(i.getProduct_id());
            inventoryDataList.add(convert(p, i));
        }
        return inventoryDataList;
    }

    private InventoryData convert(ProductPojo p, InventoryPojo i) {
        InventoryData data = new InventoryData();
        data.setProduct_id(i.getProduct_id());
        data.setBarcode(p.getBarcode());
        data.setName(p.getName());
        data.setQuantity(i.getQuantity());
        return data;
    }
}

