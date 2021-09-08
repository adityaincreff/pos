package com.increff.pos.controller;

import com.increff.pos.dto.InventoryDto;
import com.increff.pos.model.InventoryData;
import com.increff.pos.model.InventoryForm;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/inventory")

public class InventoryApiController {
	@Autowired
	private InventoryDto inventoryDto;

	@ApiOperation(value="Adds into inventory")
	@RequestMapping(method=RequestMethod.POST)
	public InventoryPojo add(@RequestBody InventoryForm inventoryForm) throws ApiException
	{
		InventoryPojo pojo=inventoryDto.add(inventoryForm);
		return pojo;
	}

	@ApiOperation(value="Updates a  Product")
	@RequestMapping(path="/{id}",method=RequestMethod.PUT)
	public void update(@PathVariable int id,@RequestBody InventoryForm inventoryForm) throws ApiException
	{
		inventoryDto.update(id,inventoryForm.getQuantity());
		
	}
	
	@ApiOperation(value="Selects a product")
	@RequestMapping(path="/{id}",method=RequestMethod.GET)
	public InventoryData getById(@PathVariable int id) throws ApiException
	{
		return inventoryDto.getById(id);
	}
	
	@ApiOperation(value = "Shows all inventory") 
	@RequestMapping(method = RequestMethod.GET)
	public List<InventoryData> getAll()
	{

		return inventoryDto.getAll();
		
	}
	

	
}