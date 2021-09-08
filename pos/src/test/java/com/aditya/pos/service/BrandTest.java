package com.aditya.pos.service;

import com.aditya.pos.config.AbstractUnit;
import com.increff.pos.pojo.BrandDetailPojo;
import com.increff.pos.service.ApiException;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class BrandTest extends AbstractUnit {
    //    Test for add(), get by id and getAll() in service layer
    @Test
    public void testAdd() throws ApiException {

        BrandDetailPojo p = getBrandPojo();
        List<BrandDetailPojo> brand_list_before = brandService.getAll();
        brandService.add(p);
        List<BrandDetailPojo> brand_list_after = brandService.getAll();
        BrandDetailPojo pojo= brandService.getByBrandAndCategory(p.getBrand(),p.getCategory());
        // Number of brands should increase
        assertEquals(brand_list_before.size() + 1, brand_list_after.size());
        assertEquals(p.getBrand(), pojo.getBrand());
        assertEquals(p.getCategory(), pojo.getCategory());
    }

    @Test
    public void testQueries() throws ApiException{
        BrandDetailPojo p = getBrandPojo();
        brandService.add(p);
        BrandDetailPojo pojo= brandService.get(p.getBrand_id());

        assertEquals(p.getBrand(), pojo.getBrand());
        assertEquals(p.getCategory(), pojo.getCategory());
    }

    @Test()
    public void testAddDuplicate() throws ApiException {

        BrandDetailPojo p = getBrandPojo();
        brandService.add(p);

        try {
            brandService.add(p);
            fail("Api Exception did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Brand and Category Combination exists");
        }

    }

    /* Testing adding of invalid brand pojo. Exception should be thrown */


    @Test
    public void testCheck() throws ApiException {
        BrandDetailPojo p = getBrandPojo();

        try {
            brandService.getByBrandAndCategory(p.getBrand(),p.getCategory());
            fail("Api Exception did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Brand and Category combination does not exists.");
        }
    }

    private BrandDetailPojo getBrandPojo() {
        BrandDetailPojo p = new BrandDetailPojo();
        p.setBrand("Parle");
        p.setCategory("Biscuits");
        return p;
    }
}
