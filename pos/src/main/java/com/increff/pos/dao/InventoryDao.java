package com.increff.pos.dao;

import com.increff.pos.pojo.InventoryPojo;
import org.springframework.stereotype.Repository;

import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional


public class InventoryDao extends AbstractDao {
private static String SELECT_ALL="select p from InventoryPojo p";
private static String SELECT_ID="select p from InventoryPojo p where product_id=:id";
	private static String UPDATE_BY_ID = "update InventoryPojo set quantity = quantity - :quantity where product_id=:id";
private static String UPDATE_INV="update InventoryPojo set quantity=quantity + :quantity where product_id=:id ";
private static String SET_INVENTORY="update InventoryPojo set quantity = :quantity where product_id=:id";

public List<InventoryPojo> getAll() {
	
	TypedQuery<InventoryPojo> query = getQuery(SELECT_ALL, InventoryPojo.class);
	return query.getResultList();
}

	public void setInventory(int id, int quantity) {
		em().createQuery(SET_INVENTORY).
		setParameter("id",id).
		setParameter("quantity",quantity).
		executeUpdate();
		
	}

	public InventoryPojo select(int Id) {
		 TypedQuery<InventoryPojo> query = getQuery(SELECT_ID, InventoryPojo.class);
		 query.setParameter("id", Id); 
		 return getSingle(query); 
		
	}

	public void update(int id, int quantity) {
		em().createQuery(UPDATE_INV).
		setParameter("quantity",quantity).
		setParameter("id",id).
		executeUpdate();
		
	}

	public void add(InventoryPojo x) {
		em().persist(x);
	
		
	}
	public void updateinventory(int id,int quantity){
		em().createQuery(UPDATE_BY_ID).
				setParameter("quantity",quantity).
				setParameter("id",id).
				executeUpdate();
	}

}
