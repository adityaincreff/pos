package com.increff.pos.dto;

import com.increff.pos.model.FilterForm;
import com.increff.pos.model.SalesData;
import com.increff.pos.service.ReportService;
import com.increff.pos.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class ReportDto {
    @Autowired
    ReportService reportService;
    public List<SalesData> generateSalesReport(FilterForm filterForm) {
        filterForm.setBrand(StringUtil.toLowerCaseTrim(filterForm.getBrand()));
        filterForm.setCategory(StringUtil.toLowerCaseTrim(filterForm.getCategory()));
        return reportService.generateSalesReport(filterForm);
    }
}
