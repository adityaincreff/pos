package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.pojo.BrandDetailPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class BrandService {

    @Autowired
    BrandDao brandDao;

    @Transactional(rollbackFor = ApiException.class)
    public void add(BrandDetailPojo brandDetailPojo) throws ApiException {
        if (brandDao.selectByBrandAndCategory(brandDetailPojo.getBrand(), brandDetailPojo.getCategory()) != null) {
            throw new ApiException("Brand and Category Combination exists");
        }
        brandDao.insert(brandDetailPojo);
    }


    @Transactional
    public void update(int id, BrandDetailPojo pojo) throws ApiException {
        if(brandDao.selectByBrandAndCategory(pojo.getBrand(), pojo.getCategory())!= null)
        {
            throw new ApiException("Brand and Category combination already exists");
        }
        BrandDetailPojo existing = getCheck(id);
        existing.setCategory(pojo.getCategory());
        existing.setBrand(pojo.getBrand());
        brandDao.update(existing);

    }

    @Transactional
    public List<BrandDetailPojo> getAll() {
        List<BrandDetailPojo> list = brandDao.selectAll();

        return list;
    }
    @Transactional
    public BrandDetailPojo get(int id) {

        return brandDao.select(id);
    }
    @Transactional
    public BrandDetailPojo getCheck(int id) throws ApiException {
        BrandDetailPojo p = brandDao.select(id);
        if (p == null) {
            throw new ApiException("Brand does not exists for the ID " + id);
        }
        return p;

    }


    public BrandDetailPojo getByBrandAndCategory (String brand, String category) throws ApiException {
        BrandDetailPojo pojo= brandDao.selectByBrandAndCategory(brand,category);
        if(pojo==null)
        {
            throw new ApiException("Brand and Category combination does not exists.");
        }
        return pojo;
    }
}