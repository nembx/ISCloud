package com.nembx.orderservice.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nembx.orderservice.domain.City;
import com.nembx.orderservice.mapper.CityMapper;
import com.nembx.orderservice.service.CityService;
import org.springframework.stereotype.Service;

/**
 * @author Lian
 */
@Service
public class CityServiceImpl extends ServiceImpl<CityMapper, City> implements CityService {
}
