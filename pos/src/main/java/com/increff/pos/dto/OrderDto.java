package com.increff.pos.dto;

import com.increff.pos.model.OrderData;
import com.increff.pos.model.OrderForm;
import com.increff.pos.model.OrderHistoryData;
import com.increff.pos.pojo.OrderHistoryPojo;
import com.increff.pos.service.ApiException;
import com.increff.pos.service.OrderService;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OrderDto {
    @Autowired
    OrderService orderservice;

    public List<OrderHistoryData> getAll() {
        return convert(orderservice.getAll());

    }

    public List<OrderData> getByOrderId(int id) throws ApiException {

        return orderservice.getByOrderId(id);
    }

    public void add(List<OrderForm> orderForms) throws ApiException {
        orderForms=normalize(orderForms);
        for (OrderForm form : orderForms) {
            if (StringUtil.isEmpty(form.getBarcode())) {
                throw new ApiException("Barcode cannot be empty");
            }
            if (StringUtil.negative(form.getQuantity())) {
                throw new ApiException("Quantity cannot be negative");
            }


        }
        orderservice.add(orderForms);

    }
    private List<OrderForm> normalize(List<OrderForm> orderForms)
    {
        for(OrderForm form :orderForms)
        {
            form.setBarcode(StringUtil.toLowerCaseTrim(form.getBarcode()));
        }
        return orderForms;
    }

    private List<OrderHistoryData> convert(List<OrderHistoryPojo> list) {
        List<OrderHistoryData> orderHistoryData = new ArrayList<OrderHistoryData>();
        for (OrderHistoryPojo orderHistoryPojo : list) {
            OrderHistoryData obj = new OrderHistoryData();
            obj.setOrder_id(orderHistoryPojo.getOrder_id());
            obj.setDate(orderHistoryPojo.getDate());
            orderHistoryData.add(obj);

        }

        return orderHistoryData;

    }
}
