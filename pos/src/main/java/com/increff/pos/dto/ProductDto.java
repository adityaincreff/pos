package com.increff.pos.dto;
import com.increff.pos.model.ProductData;
import com.increff.pos.model.ProductForm;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.ProductService;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ProductDto {
	@Autowired
	private ProductService productService;
	@Autowired
	private BrandService brandService;

	public ProductPojo add(ProductForm productForm) throws ApiException {
		check(productForm);
		productForm = normalize(productForm);
		ProductPojo p = convert(productForm);
		productService.add(p);
		return p;
	}

	public List<ProductData> getAll() {

		return productService.getAll();
	}

	public ProductPojo update(int id, ProductForm productForm) throws ApiException {
		check(productForm);
		productForm = normalize(productForm);
		ProductPojo p = convert(productForm);
		productService.update(id, p);
		return p;

	}

	public ProductData getById(int id) throws ApiException {
		return productService.getById(id);

	}

	private void check(ProductForm productForm) throws ApiException {
		if (StringUtil.isEmpty(productForm.getBarcode()) || StringUtil.isEmpty(productForm.getBrand())
				|| StringUtil.isEmpty(productForm.getCategory()) || StringUtil.isEmpty(productForm.getName())) {
			throw new ApiException("One or more fields are empty");
		}
		if (StringUtil.negative(productForm.getMrp())) {
			throw new ApiException("MRP cannot be negative");
		}

	}

	private ProductForm normalize(ProductForm productForm) {
		productForm.setBarcode(StringUtil.toLowerCaseTrim(productForm.getBarcode()));
		productForm.setBrand(StringUtil.toLowerCaseTrim(productForm.getBrand()));
		productForm.setCategory(StringUtil.toLowerCaseTrim(productForm.getCategory()));
		productForm.setName(StringUtil.toLowerCaseTrim(productForm.getName()));
		return productForm;
	}

	private ProductPojo convert(ProductForm productForm) throws ApiException {


		int Brand_id = brandService.getByBrandAndCategory(productForm.getBrand(),productForm.getCategory()).getBrand_id();
		ProductPojo productPojo=new ProductPojo();
		productPojo.setBrand_id(Brand_id);
		productPojo.setName(productForm.getName());
		productPojo.setBarcode(productForm.getBarcode());
		productPojo.setMrp(productForm.getMrp());


		return productPojo;
	}

}
