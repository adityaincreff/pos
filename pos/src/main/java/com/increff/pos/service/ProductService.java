package com.increff.pos.service;

import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.ProductData;
import com.increff.pos.pojo.BrandDetailPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service

public class ProductService {
	@Autowired
	private BrandService brandService;
	@Autowired
	private ProductDao productDao;

	public void add(ProductPojo productPojo) throws ApiException {

		getCheck(productPojo.getProduct_id());
		getByBarcode(productPojo.getBarcode());
		productDao.add(productPojo);

	}
	private void getCheck(int id) throws ApiException {
		if(productDao.select(id)!= null )
		{
			throw new ApiException("Product already exists");

		}

	}
	public List<ProductData> getAll() {

		 return innerJoin(productDao.getAll(), brandService.getAll());	}

	public void update(int id, ProductPojo p) throws ApiException {
		getCheck(p.getProduct_id());
		productDao.update(id, p);

	}

	public ProductData getById(int id) throws ApiException {

		ProductPojo productPojo = productDao.select(id);
		if (productPojo == null) {
			throw new ApiException("No such productPojo exists");

		}

		BrandDetailPojo detailPojo = brandService.getCheck(productPojo.getBrand_id());
		return convertToProductData(productPojo, detailPojo);
	}

	 public void getByBarcode(String barcode) throws ApiException {
		ProductPojo productPojo = productDao.select(barcode);
		if (productPojo != null) {
			throw new ApiException("Barcode already exists");
		}

	}

	private ProductData convertToProductData(ProductPojo productPojo, BrandDetailPojo brandDetail) {
		
		ProductData productdata=new ProductData();
		productdata.setBarcode(productPojo.getBarcode());
		productdata.setBrand(brandDetail.getBrand());
		productdata.setCategory(brandDetail.getCategory());
		productdata.setMrp(productPojo.getMrp());
		productdata.setName(productPojo.getName());
		productdata.setProduct_id(productPojo.getProduct_id());
		return productdata;
	}
	private List<ProductData> innerJoin(List<ProductPojo> productPojoList, List<BrandDetailPojo> brandDetailPojoList) {
		ArrayList<ProductData> productData=new ArrayList<ProductData>();
		HashMap<Integer, BrandDetailPojo> hm = new HashMap<Integer, BrandDetailPojo>();
		for(BrandDetailPojo bdetail:brandDetailPojoList)
		{
			hm.put(bdetail.getBrand_id(), bdetail);
		}
		for(ProductPojo productPojo : productPojoList)
		{
			BrandDetailPojo bdetail=hm.get(productPojo.getBrand_id());
			productData.add(convertToProductData(productPojo,bdetail));

		}
		return productData;
	}

}
