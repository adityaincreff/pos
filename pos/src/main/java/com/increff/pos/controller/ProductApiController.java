package com.increff.pos.controller;

import com.increff.pos.dto.ProductDto;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/product")

public class ProductApiController {

	@Autowired
	private ProductDto productDto;

	@ApiOperation(value = "Adds a product")
	@RequestMapping(method = RequestMethod.POST)
	public ProductPojo  add(@RequestBody ProductForm productForm) throws ApiException {
		ProductPojo pojo=productDto.add(productForm);
		return pojo;
	}

	@ApiOperation(value = "Updates a product")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public ProductPojo update(@PathVariable int id, @RequestBody ProductForm productForm) throws ApiException {
		ProductPojo pojo =productDto.update(id,productForm);
		return pojo;
	}

	@ApiOperation(value = "Selects a product")
	@RequestMapping(path = "/{id}", method = RequestMethod.GET)
	public ProductData getById(@PathVariable int id) throws ApiException {
		return productDto.getById(id);
	}

	@ApiOperation(value = "Shows all products")
	@RequestMapping(method = RequestMethod.GET)
	public List<ProductData> getAll() {
		return productDto.getAll();
	}

}
