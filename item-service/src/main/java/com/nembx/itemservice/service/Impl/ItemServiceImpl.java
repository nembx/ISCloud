package com.nembx.itemservice.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nembx.itemservice.domain.Item;
import com.nembx.itemservice.mapper.ItemMapper;
import com.nembx.itemservice.service.ItemService;
import org.springframework.stereotype.Service;

/**
 * @author Lian
 */
@Service
public class ItemServiceImpl extends ServiceImpl<ItemMapper, Item> implements ItemService {
}
