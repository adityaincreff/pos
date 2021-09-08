package com.increff.pos.dao;

import com.increff.pos.pojo.ProductPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository

@Transactional
public class ProductDao extends AbstractDao {
	private static String SELECT_All = "select p from ProductPojo p";
	private static String SELECT_ID = "select p from ProductPojo p where barcode=:barcode";
	private static String UPDATE_ID = "update ProductPojo set brand_id=:brand_id,name=:name, mrp=:mrp, barcode=:barcode where id=:id";

	public List<ProductPojo> getAll() {
		TypedQuery<ProductPojo> query = getQuery(SELECT_All, ProductPojo.class);
		return query.getResultList();
	}


	public ProductPojo select(String barcode) {
		TypedQuery<ProductPojo> query = getQuery(SELECT_ID, ProductPojo.class);
		query.setParameter("barcode", barcode);
		return getSingle(query);

	}


	public void add(ProductPojo product) {
		em().persist(product);

	}


	public void update(int id, ProductPojo p) {
		em().createQuery(UPDATE_ID).setParameter("id", id).setParameter("brand_id", p.getBrand_id())
				.setParameter("name", p.getName()).setParameter("mrp", p.getMrp())
				.setParameter("barcode", p.getBarcode()).executeUpdate();

	}


	public ProductPojo select(int id) {
		TypedQuery<ProductPojo> query = getQuery("select p from ProductPojo p where product_id=:product_id", ProductPojo.class);
		query.setParameter("product_id", id);
		return getSingle(query);

	}
}
