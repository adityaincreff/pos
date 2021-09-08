package com.increff.pos.controller;

import com.increff.pos.dto.BrandDto;
import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandDetailPojo;
import com.increff.pos.service.ApiException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api
@RestController
@RequestMapping(path = "/api/brand")

public class BrandApiController {

	@Autowired
	private BrandDto brandDto;

	@ApiOperation(value="Adds a brand")
	@RequestMapping(method=RequestMethod.POST)
	public BrandDetailPojo add(@RequestBody BrandForm brandForm) throws ApiException
	{
		  BrandDetailPojo pojo=brandDto.add(brandForm);
		  return pojo;

	}

	@ApiOperation(value = "Updates a brand by ID")
	@RequestMapping(path = "/{id}", method = RequestMethod.PUT)
	public BrandDetailPojo update(@PathVariable int id,@RequestBody BrandForm brandForm) throws ApiException
	{
		  BrandDetailPojo pojo =brandDto.update(id, brandForm);
		  return pojo;
	}

	@ApiOperation(value="Selects a brand by ID")
	@RequestMapping(path="/{id}", method=RequestMethod.GET)
	public BrandData getByBrand(@PathVariable int id) throws ApiException {
		return brandDto.getByBrand(id);

	}

	@ApiOperation(value="Gets list of all brands")
	@RequestMapping(method=RequestMethod.GET)
	public List<BrandData> getAll()
	{
		return brandDto.getAll();

	}

}
