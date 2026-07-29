package com.yimo.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yimo.entity.Product;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}
