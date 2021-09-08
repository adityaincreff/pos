package com.increff.pos.dao;

import com.increff.pos.pojo.BrandDetailPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository


public class BrandDao extends AbstractDao {

    private static String SELECTByBrandAndCategory ="select p from BrandDetailPojo p where p.brand=:brand AND p.category=:category";
    private static String SELECT_BY_ID = "select p from BrandDetailPojo p where p.brand_id = :id";
    private static String SELECT_ALL = "select p from BrandDetailPojo p order by p.brand_id";
    @Transactional
    public BrandDetailPojo selectByBrandAndCategory(String brand,String category) {
        TypedQuery<BrandDetailPojo> query = getQuery(SELECTByBrandAndCategory, BrandDetailPojo.class);
        query.setParameter("brand", brand).setParameter("category", category);
        return getSingle(query);
    }
    @Transactional
    public void insert(BrandDetailPojo pojo) {
        em().persist(pojo);
    }
    @Transactional
    public void update(BrandDetailPojo pojo) {
        em().merge(pojo);
    }

    @Transactional
    public BrandDetailPojo select(int id) {
        TypedQuery<BrandDetailPojo> query = getQuery(SELECT_BY_ID, BrandDetailPojo.class);
        query.setParameter("id", id);
        return getSingle(query);

    }
    @Transactional
    public List<BrandDetailPojo> selectAll() {
        TypedQuery<BrandDetailPojo> query = getQuery(SELECT_ALL, BrandDetailPojo.class);
        return query.getResultList();
    }
}
