package com.aditya.pos.service;

import com.aditya.pos.config.AbstractUnit;
import com.increff.pos.model.InventoryData;
import com.increff.pos.pojo.InventoryPojo;
import com.increff.pos.pojo.ProductPojo;
import com.increff.pos.service.ApiException;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class InventoryServiceTest extends AbstractUnit {
    @Before
    public void Initialize() throws ApiException {
        createTestSchema();
    }

    @Test
    public void testAdd() throws ApiException {

        InventoryPojo i = getInventoryPojo(products.get(2));
        List<InventoryData> inv_list_before = inventoryService.getAll();
        inventoryService.add(i, "abc3");
        List<InventoryData> inv_list_after = inventoryService.getAll();
        assertEquals(inv_list_before.size() + 1, inv_list_after.size());
        assertEquals(i.getProduct_id(), inventoryService.getById(i.getProduct_id()).getProduct_id());
        assertEquals(i.getQuantity(), inventoryService.getById(i.getProduct_id()).getQuantity());

    }


    /* Testing adding of an invalid pojo. Should throw exception */
    @Test()
    public void testAddWrong() throws ApiException {

        InventoryPojo i = getWrongInventoryPojo(products.get(0));

        try {
            inventoryService.add(i, "abc3");
            fail("ApiException did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "Quantity cannot be negative");
        }

    }


    /* Testing checkifexists with an non-existent id. Should throw exception */
    @Test()
    public void testNoProduct() throws ApiException {
        int id = 5;
        try {
            inventoryService.getById(id);
            fail("ApiException did not occur");
        } catch (ApiException e) {
            assertEquals(e.getMessage(), "No such product exists");
        }
    }

    /* Testing get by id of a non-existent pojo. Should throw exception */
    @Test(expected = ApiException.class)
    public void testNoInventoryForProduct() throws ApiException {
        inventoryService.getById("abc3");
    }

    @Test()
    public void testToGetAll() {
        List<InventoryData> inventory = new ArrayList<InventoryData>();
        inventory = inventoryService.getAll();
        assertEquals(inventory.size(), 2);
    }


    private InventoryPojo getInventoryPojo(ProductPojo p) {
        InventoryPojo i = new InventoryPojo();
        i.setProduct_id(p.getProduct_id());
        i.setQuantity(20);
        return i;
    }

    private InventoryPojo getWrongInventoryPojo(ProductPojo p) {
        InventoryPojo i = new InventoryPojo();
        i.setProduct_id(p.getProduct_id());
        i.setQuantity(-5);
        return i;
    }
}
