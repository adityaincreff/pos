package com.increff.pos.controller;

import com.increff.pos.dto.OrderDto;
import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.model.OrderHistoryData;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/order")
public class OrderApiController {
	@Autowired
	private OrderDto orderDto;

	@ApiOperation(value = "Shows all orders")
	@RequestMapping(method = RequestMethod.GET)
	public List<OrderHistoryData> getAll() {
		return orderDto.getAll();
	}

	@ApiOperation(value = "Shows details about an order")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public List<OrderData> getByOrderId(@PathVariable int id) throws ApiException {
		return orderDto.getByOrderId(id);

	}

	@ApiOperation(value = "Creates an order")
	@RequestMapping(method = RequestMethod.POST)
	public void add(@RequestBody List<OrderForm> orderForms) throws ApiException {
		orderDto.add(orderForms);
	}
}
