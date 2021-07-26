package crust.explorer.mapper;

import org.springframework.stereotype.Component;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
@org.apache.ibatis.annotations.Mapper
@Component
public @interface Mapper {
}