package com.flyme.app.converter;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter
public class BooleanConverter implements AttributeConverter<Boolean, Integer>{

    @Override
    public Integer convertToDatabaseColumn(Boolean attribute) {
       if(Boolean.TRUE.equals(attribute)){
           return 1;
       }
       return -1;
    }

    @Override
    public Boolean convertToEntityAttribute(Integer dbData) {
       if(dbData == null){
           return Boolean.FALSE;
       }else{
           if(dbData == 1){
               return Boolean.TRUE;
           }else{
               return Boolean.FALSE;
           }
       }

    }

}