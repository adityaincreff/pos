package com.increff.pos.dao;

import com.increff.pos.pojo.OrderItemPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
@Repository
public class OrderItemDao extends AbstractDao {
    private static String SELECT_ALL = "select p from OrderItemPojo p";
    private static String SELECT_BY_ID = "Select p from OrderItemPojo p where orderpojoid = :id";
    @Transactional
    public void add(OrderItemPojo orderItemPojo) {
        em().persist(orderItemPojo);
    }

    public List<OrderItemPojo> select(int id) {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_BY_ID, OrderItemPojo.class);
        query.setParameter("id", id);
        return query.getResultList();
    }

    @Transactional
    public List<OrderItemPojo> selectAll() {
        TypedQuery<OrderItemPojo> query = getQuery(SELECT_ALL, OrderItemPojo.class);
        return query.getResultList();
    }

}
