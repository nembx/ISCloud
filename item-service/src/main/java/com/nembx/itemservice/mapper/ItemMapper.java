package com.nembx.itemservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nembx.itemservice.domain.Item;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author Lian
 */
@Mapper
public interface ItemMapper extends BaseMapper<Item> {
}
