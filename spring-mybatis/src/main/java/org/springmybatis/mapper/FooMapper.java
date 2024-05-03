package org.springmybatis.mapper;

import org.apache.ibatis.annotations.Mapper;

import java.time.LocalDateTime;

@Mapper
public interface FooMapper {

    /**
     * 获取当前时间
     * @return
     */
    LocalDateTime now();
}
