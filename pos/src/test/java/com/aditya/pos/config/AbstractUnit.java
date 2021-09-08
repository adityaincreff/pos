package com.aditya.pos.config;

import com.increff.pos.pojo.BrandDetailPojo;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.BrandService;
import com.increff.pos.service.InventoryService;
import com.increff.pos.service.ProductService;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.AnnotationConfigWebContextLoader;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = QaConfig.class, loader = AnnotationConfigWebContextLoader.class)
@WebAppConfiguration("src/test/webapp")
@Transactional
public abstract class AbstractUnit {
    @Autowired
    protected BrandService brandService;
    @Autowired
    protected ProductService productService;
    @Autowired
    protected InventoryService inventoryService;
    protected List<String> barcodes;

    protected List<BrandDetailPojo> brands;
    protected List<ProductPojo> products;
    protected List<InventoryPojo> inventories;


    protected void createTestSchema() throws ApiException {
        barcodes = new ArrayList<String>();
        brands = new ArrayList<BrandDetailPojo>();
        products = new ArrayList<ProductPojo>();
        inventories = new ArrayList<InventoryPojo>();

        for (int i = 0; i < 2; i++) {
            BrandDetailPojo brand = new BrandDetailPojo();
            brand.setBrand("brand");
            brand.setCategory("category" + i);
            brandService.add(brand);
            brands.add(brand);

            ProductPojo product = new ProductPojo();
            product.setBrand_id(brand.getBrand_id());
            product.setName("product" + i);
            product.setBarcode("abc" + i);
            product.setMrp(50);
            productService.add(product);
            products.add(product);
            barcodes.add(product.getBarcode());

            InventoryPojo inventory = new InventoryPojo();
            inventory.setProduct_id(product.getProduct_id());
            inventory.setQuantity(20);
            inventoryService.add(inventory, product.getBarcode());
            inventories.add(inventory);
        }

        BrandDetailPojo brand = new BrandDetailPojo();
        brand.setBrand("brand");
        brand.setCategory("category3");
        brandService.add(brand);
        brands.add(brand);

        ProductPojo product = new ProductPojo();
        product.setBrand_id(brand.getBrand_id());
        product.setName("product3");
        product.setBarcode("abc3");
        product.setMrp(50);
        productService.add(product);
        products.add(product);
        barcodes.add(product.getBarcode());
    }
}
