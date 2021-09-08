package com.increff.pos.controller;

import com.increff.pos.dto.ReportDto;
import com.increff.pos.model.FilterForm;
import com.increff.pos.model.SalesData;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Api
@RestController
public class ReportApiController {
    @Autowired
    private ReportDto reportDto;

    @ApiOperation(value = "Sales Report")
    @RequestMapping(value = "/api/report/sales", method = RequestMethod.POST)
    public List<SalesData> generateSalesReport(@RequestBody FilterForm form) {
        return reportDto.generateSalesReport(form);
    }
}
