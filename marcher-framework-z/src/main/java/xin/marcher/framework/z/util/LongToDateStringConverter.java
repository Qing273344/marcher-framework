package xin.marcher.framework.z.util;

import ma.glasnost.orika.converter.BidirectionalConverter;
import ma.glasnost.orika.metadata.Type;

import java.lang.annotation.Annotation;

public class LongToDateStringConverter extends BidirectionalConverter<Long, String> {

    @Override
    public String convertTo(Long source, Type<String> destinationType) {
        Annotation[] annotations = destinationType.getRawType().getAnnotations();
        System.out.println(annotations);
        return null;
    }

    @Override
    public Long convertFrom(String source, Type<Long> destinationType) {
        return null;
    }


}
