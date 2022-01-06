package com.orange.easyexcel.change;


import com.alibaba.excel.converters.Converter;
import com.alibaba.excel.converters.ReadConverterContext;
import com.alibaba.excel.converters.WriteConverterContext;
import com.alibaba.excel.enums.CellDataTypeEnum;
import com.alibaba.excel.metadata.data.WriteCellData;
import com.alibaba.excel.util.StringUtils;

/**
 * Excel性别转换器
 */
public class GenderConverter implements Converter<Integer> {
    @Override
    public Class<?> supportJavaTypeKey() {
        //对象属性类型
        return Integer.class;
    }

    @Override
    public CellDataTypeEnum supportExcelTypeKey() {
        //CellData属性类型
        return CellDataTypeEnum.STRING;
    }

    @Override
    public Integer convertToJavaData(ReadConverterContext<?> context) throws Exception {
        //CellData转对象属性
        String stringValue = context.getReadCellData().getStringValue();
        if(StringUtils.isEmpty(stringValue)){
            return null;
        }
        if("男".equals(stringValue)){
            return 1;
        }else if("女".equals(stringValue)){
            return 0;
        }else{
            return null;
        }

    }

    @Override
    public WriteCellData<?> convertToExcelData(WriteConverterContext<Integer> context) throws Exception {
        //对象属性转CellDate
        Integer value = context.getValue();
        if(value==null){
            return new WriteCellData<>("");
        }
        if(value==1){
            return new WriteCellData<>("男");
        }else if(value==0){
            return new WriteCellData<>("女");
        }else{
            return new WriteCellData<>("");
        }
    }
}
