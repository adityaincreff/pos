package com.increff.pos.service;

import com.increff.pos.dao.BrandDao;
import com.increff.pos.dao.OrderHistoryDao;
import com.increff.pos.dao.OrderItemDao;
import com.increff.pos.dao.ProductDao;
import com.increff.pos.model.FilterForm;
import com.increff.pos.model.SalesData;
import com.increff.pos.pojo.BrandDetailPojo;
import com.increff.pos.pojo.OrderHistoryPojo;
import com.increff.pos.pojo.OrderItemPojo;
import com.increff.pos.pojo.ProductPojo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ReportService {
    @Autowired
    private OrderItemDao orderDao;
    @Autowired
    private ProductDao productDao;
    @Autowired
    private BrandDao brandDao;
    @Autowired
    private OrderHistoryDao historyDao;

    public List<SalesData> generateSalesReport(FilterForm form){
        List<OrderItemPojo> item = orderDao.selectAll();
        List<OrderItemPojo> items = filtering(item,form);
        List<SalesData> s = convert(items);
        List<SalesData> g = grouping(s);
        return g;
    }
    public List<OrderItemPojo> filtering(List<OrderItemPojo> list, FilterForm filter){
        List<OrderItemPojo> list2 = new ArrayList<OrderItemPojo>();
        for(OrderItemPojo r:list) {
            list2.add(r);
            BrandDetailPojo find = brandDao.select(productDao.select(r.getProductPojoId()).getBrand_id());
            if (filter.getCategory()!= null && !filter.getCategory().equals(find.getCategory()) && filter.getCategory()!="") {
                list2.remove(r);
                continue;
            }
            else if(filter.getBrand()!=null && !filter.getBrand().equals(find.getBrand()) && filter.getBrand()!=""){
                list2.remove(r);
                continue;
            }
            OrderHistoryPojo his = historyDao.select(r.getOrderPojoId());
            if(filter.getStart().isAfter(his.getDate()) || filter.getEnd().isBefore(his.getDate())){
                list2.remove(r);
            }
        }

        return list2;
    }
    public List<SalesData> grouping(List<SalesData> s){
        HashMap<String, SalesData> hm = new HashMap<String,SalesData>();
        for(SalesData r:s){
            if(hm.containsKey(r.getCategory()) == false){
                hm.put(r.getCategory(),r);
            }
            else {
                SalesData temp = hm.get(r.getCategory());
                temp.setQuantity(temp.getQuantity() + r.getQuantity());
                temp.setRevenue(temp.getRevenue() + r.getRevenue());
                hm.put(r.getCategory(), temp);
            }
        }
        List<SalesData> afterGrouping = new ArrayList<SalesData>();
        for (Map.Entry<String,SalesData> e : hm.entrySet()){
            afterGrouping.add(e.getValue());
        }
        return afterGrouping;
    }
    protected List<SalesData> convert(List<OrderItemPojo> item){
        List<SalesData> s = new ArrayList<SalesData>();
        for(OrderItemPojo i:item){
            SalesData n = new SalesData();
            n.setQuantity(i.getQuantity());
            ProductPojo p = productDao.select(i.getProductPojoId());
            BrandDetailPojo b = brandDao.select(p.getBrand_id());
            n.setCategory(b.getCategory());
            n.setRevenue(i.getQuantity()*i.getSellingPrice());
            s.add(n);
        }
        return s;
    }

}
