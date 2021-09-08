package com.increff.pos.service;

import com.increff.pos.dao.InventoryDao;
import com.increff.pos.dao.OrderHistoryDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.OrderHistoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
@Service
@Transactional

public class OrderService {
	@Autowired
	private OrderHistoryDao orderHistoryDao;
	@Autowired
	private OrderItemDao orderItemDao;
	@Autowired
	private ProductDao productDao;
	@Autowired
	private InventoryDao inventoryDao;



	public List<OrderHistoryPojo> getAll() {
		return orderHistoryDao.getall();

	}

	public List<OrderData> getByOrderId(int id)  throws ApiException{
		List<OrderItemPojo> items = orderItemDao.select(id);
		if(items == null )
		{
			throw new ApiException("Order does not exists");
		}
		return convert(items);
	}

	private List<OrderData> convert(List<OrderItemPojo> items) {
		List<OrderData> ans = new ArrayList<>();
		for(OrderItemPojo i : items)
		{
			OrderData orderData= new OrderData();
			orderData.setMrp(i.getSellingPrice());
			orderData.setName(productDao.select(i.getProductPojoId()).getName());
			orderData.setQuantity(i.getQuantity());
			ans.add(orderData);
		}
		return  ans;


	}
	@Transactional(rollbackFor = ApiException.class)
	public void add(List<OrderForm> orderForms) throws ApiException {
		List<OrderItemPojo> orderItemPojoList = check(orderForms);
		OrderHistoryPojo orderHistoryPojo = convert();
		int id=orderHistoryDao.add(orderHistoryPojo);
		for(OrderItemPojo orderItemPojo : orderItemPojoList)
		{
			orderItemPojo.setOrderPojoId(id);
			orderItemDao.add(orderItemPojo);
		}
		
	}

	private OrderHistoryPojo convert() {
		OrderHistoryPojo history = new OrderHistoryPojo();
		LocalDateTime now = LocalDateTime.now();
		history.setDate(now);
		return history;
	}

	private List<OrderItemPojo> check(List<OrderForm> orderForms) throws ApiException {
		List<OrderItemPojo> ans = new ArrayList<OrderItemPojo>();
		for(OrderForm form  : orderForms)
		{
			OrderItemPojo q= new OrderItemPojo();
			ProductPojo product= productDao.select(form.getBarcode());
			if(product == null)
			{
				throw new ApiException("No such product exists");
			}
			q.setProductPojoId(product.getProduct_id());
			InventoryPojo i = inventoryDao.select(product.getProduct_id());
					q.setQuantity(form.getQuantity());
			if(i== null)
			{
				throw new ApiException(("No inventory exists for this product"));
			}
			if (form.getQuantity() > i.getQuantity())
			{
				throw new ApiException(("Selected quantity cannot be greater than inventory"));
			}
			q.setOrderPojoId(1);
			q.setSellingPrice(product.getMrp());
			inventoryDao.updateinventory(i.getProduct_id(),form.getQuantity());
			ans.add(q);
		}
		return ans;
	}

}
