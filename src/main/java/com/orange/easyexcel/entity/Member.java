package com.orange.easyexcel.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import com.alibaba.excel.annotation.format.DateTimeFormat;
import com.alibaba.excel.annotation.write.style.ColumnWidth;
import com.orange.easyexcel.change.GenderConverter;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 创建一个Member类，封装会员信息，体验easyExcel的导出
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Member {

    /**
     * @ExcelProperty： 核心注解，value属性设置表头名称，converter属性可用来设置类型转换器
     * @ColumnWidth： 用于设置表格列的长度
     * @DateTimeFormat： 用于设置日期转换格式
     * @ExcelIgnore： 忽略该属性，不导出
     */

    @ExcelProperty("ID")
    @ColumnWidth(10)
    private Long id;

    @ExcelProperty("用户名")
    @ColumnWidth(20)
    private String userName;

    @ExcelIgnore
    private String password;

    @ExcelProperty("昵称")
    @ColumnWidth(20)
    private String nickName;

    @ExcelProperty("出生日期")
    @ColumnWidth(20)
    @DateTimeFormat("yyyy-MM-dd")
    private Date birthday;

    @ExcelProperty("手机号")
    @ColumnWidth(20)
    private String phone;

    @ExcelIgnore
    private String icon;

    /**
     * 在EasyExcel中，如果你想实现枚举类型到字符串的转换（比如gender属性中，1->男，0->女），需要自定义转换器
     * 该自定义转换器为GenderConverter
     */
    @ExcelProperty(value = "性别",converter = GenderConverter.class)
    @ColumnWidth(10)
    private Integer gender;

}
