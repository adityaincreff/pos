package com.increff.pos.dto;

import com.increff.pos.model.BrandData;
import com.increff.pos.model.BrandForm;
import com.increff.pos.pojo.BrandDetailPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BrandDto {
	@Autowired
	private BrandService brandService;

	public List<BrandData> getAll() {
		return convert(brandService.getAll());
	}
	public BrandData getByBrand(int id) throws ApiException {
		return convert(brandService.getCheck(id));
	}

	public BrandDetailPojo add(BrandForm brandForm) throws ApiException {
		brandForm = normalize(brandForm);
		BrandDetailPojo brandDetailPojo = convert(brandForm);

		brandService.add(brandDetailPojo);
		return brandDetailPojo;


	}
	public BrandDetailPojo update(int id, BrandForm brandform) throws ApiException {
		brandform = normalize(brandform);
		BrandDetailPojo pojo = convert(brandform);
		brandService.update(id,pojo);
		return pojo;

	}

	private BrandDetailPojo convert(BrandForm brandForm) {
		BrandDetailPojo pojo = new BrandDetailPojo();
		pojo.setBrand(brandForm.getBrand());
		pojo.setCategory(brandForm.getCategory());
		return pojo;
	}

	private BrandForm normalize(BrandForm brandForm) throws ApiException {
		if(StringUtil.isEmpty(brandForm.getBrand()) || StringUtil.isEmpty(brandForm.getCategory()))
		{
			throw new ApiException("One or more fields are empty");
		}
		brandForm.setBrand(StringUtil.toLowerCaseTrim(brandForm.getBrand()));
		brandForm.setCategory(StringUtil.toLowerCaseTrim(brandForm.getCategory()));
		return brandForm;

	}

	private List<BrandData> convert(List<BrandDetailPojo> list) {
	List<BrandData> dataList = new ArrayList<BrandData>();
	for(BrandDetailPojo pojo : list)
	{
		BrandData brandData = convert(pojo);
		dataList.add(brandData);
	}
	return dataList;
	}

	private BrandData convert(BrandDetailPojo pojo) {
		BrandData brandData = new BrandData();
		brandData.setBrand_id(pojo.getBrand_id());
		brandData.setBrand(pojo.getBrand());
		brandData.setCategory(pojo.getCategory());
		return brandData;
	}



}