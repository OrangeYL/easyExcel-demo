package com.orange.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.orange.easyexcel.annotation.CustomMerge;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.util.Date;

@Data
@EqualsAndHashCode(callSuper = false)
public class OrderData {

    @ExcelProperty(value = "订单ID")
    @ColumnWidth(10)
    @CustomMerge(needMerge = true,isPK = true)
    private String id;

    @ExcelProperty(value = "订单编码")
    @ColumnWidth(20)
    @CustomMerge(needMerge = true)
    private String orderSn;

    @ExcelProperty(value = "创建时间")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd")
    @CustomMerge(needMerge = true)
    private Date createTime;

    @ExcelProperty(value = "收获地址")
    @ColumnWidth(20)
    @CustomMerge(needMerge = true)
    private String receiverAddress;

    @ExcelProperty(value = {"商品信息", "商品编码"})
    @ColumnWidth(20)
    private String productSn;

    @ExcelProperty(value = {"商品信息", "商品名称"})
    @ColumnWidth(20)
    private String name;

    @ExcelProperty(value = {"商品信息", "商品标题"})
    @ColumnWidth(30)
    private String subTitle;

    @ExcelProperty(value = {"商品信息", "品牌名称"})
    @ColumnWidth(20)
    private String brandName;

    @ExcelProperty(value = {"商品信息", "商品价格"})
    @ColumnWidth(20)
    private BigDecimal price;

    @ExcelProperty(value = {"商品信息", "商品数量"})
    @ColumnWidth(20)
    private Integer count;


}
