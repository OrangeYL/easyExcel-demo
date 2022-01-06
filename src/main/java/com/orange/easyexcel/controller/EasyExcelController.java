package com.orange.easyexcel.controller;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.excel.EasyExcel;
import com.alibaba.excel.support.ExcelTypeEnum;
import com.orange.easyexcel.common.CommonResult;
import com.orange.easyexcel.entity.Member;
import com.orange.easyexcel.entity.Order;
import com.orange.easyexcel.entity.OrderData;
import com.orange.easyexcel.entity.Product;
import com.orange.easyexcel.strategy.CustomMergeStrategy;
import com.orange.easyexcel.util.LocalJsonUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.SneakyThrows;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

@Controller
@Api(tags = "EasyExcelController",description = "EasyExcel导入导出测试")
@RequestMapping("/easyExcel")
public class EasyExcelController {
    @SneakyThrows(IOException.class)
    @ApiOperation("导出会员列表")
    @RequestMapping(value = "/export",method = RequestMethod.GET)
    public void exportList(HttpServletResponse response){
        setExcelRespProp(response, "会员列表");
        List<Member> memberList = LocalJsonUtil.getListFromJson("json/members.json", Member.class);
        EasyExcel.write(response.getOutputStream()).head(Member.class).excelType(ExcelTypeEnum.XLSX)
                .sheet("会员列表").doWrite(memberList);
    }

    /**
     * @RequestPart: 使用@RequestPart注解修饰文件上传参数，否则在Swagger中就没法显示上传按钮了
     * @param file
     * @return
     */
    @SneakyThrows
    @ApiOperation("从Excel导入会员列表")
    @RequestMapping(value = "/import", method = RequestMethod.POST)
    @ResponseBody
    public CommonResult importList(@RequestPart("file") MultipartFile file) {
        List<Member> memberList = EasyExcel.read(file.getInputStream())
                .head(Member.class)
                .sheet()
                .doReadSync();
        return CommonResult.success(memberList);
    }

    //设置excel下载响应头属性
    public void setExcelRespProp(HttpServletResponse response,String rawFileName) throws UnsupportedEncodingException {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
        response.setCharacterEncoding("utf-8");
        String fileName = URLEncoder.encode(rawFileName, "UTF-8").replaceAll("\\+", "%20");
        response.setHeader("Content-disposition", "attachment;filename*=utf-8''" + fileName + ".xlsx");
    }

    /**
     * 将原来嵌套的Order对象列表转换为OrderData对象列表
     * @param orderList
     * @return
     */
    public List<OrderData> convert(List<Order> orderList){
        ArrayList<OrderData> result = new ArrayList<>();
        for(Order order:orderList){
            List<Product> productList = order.getProductList();
            for(Product product:productList){
                OrderData orderData = new OrderData();
                BeanUtil.copyProperties(product,orderData);
                BeanUtil.copyProperties(order,orderData);
                result.add(orderData);
            }
        }
        return result;
    }

    @SneakyThrows
    @ApiOperation(value = "导出订单列表Excel")
    @RequestMapping(value = "/exportOrderList", method = RequestMethod.GET)
    public void exportOrderList(HttpServletResponse response) {
        List<Order> orderList = getOrderList();
        List<OrderData> orderDataList = convert(orderList);
        setExcelRespProp(response, "订单列表");
        EasyExcel.write(response.getOutputStream())
                .head(OrderData.class)
                .registerWriteHandler(new CustomMergeStrategy(OrderData.class))
                .excelType(ExcelTypeEnum.XLSX)
                .sheet("订单列表")
                .doWrite(orderDataList);
    }

    private List<Order> getOrderList(){
        List<Order> orderList = LocalJsonUtil.getListFromJson("json/orders.json", Order.class);
        List<Product> productList = LocalJsonUtil.getListFromJson("json/products.json", Product.class);
        List<Member> memberList = LocalJsonUtil.getListFromJson("json/members.json", Member.class);
        ArrayList<Order> orders = new ArrayList<>();
        for(int i=0;i<orderList.size();i++){
            Order order = orderList.get(i);
            order.setMember(memberList.get(i));
            order.setProductList(productList);
            orders.add(order);
        }
        return orders;
    }

}
