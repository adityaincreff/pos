package com.increff.pos.dao;

import com.increff.pos.pojo.OrderHistoryPojo;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.TypedQuery;
import java.util.List;
@Repository

public class OrderHistoryDao extends AbstractDao {
    private static String SELECT_BY_ID = "select p from OrderHistoryPojo p where order_id=:id";
    private static String SELECT_ALL = "select p from OrderHistoryPojo p";

    @Transactional
    public  int add(OrderHistoryPojo orderHistoryPojo) {

        em().persist(orderHistoryPojo);
        return orderHistoryPojo.getOrder_id();
    }

    public  List<OrderHistoryPojo> getall() {
        TypedQuery<OrderHistoryPojo> query = getQuery(SELECT_ALL, OrderHistoryPojo.class);
        return query.getResultList();
    }

    public OrderHistoryPojo select(int id) {
        TypedQuery<OrderHistoryPojo> query = getQuery(SELECT_BY_ID, OrderHistoryPojo.class);
        query.setParameter("id", id);
        return getSingle(query);

    }
}