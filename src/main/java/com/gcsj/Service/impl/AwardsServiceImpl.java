package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.AwardsService;
import com.gcsj.mapper.AwardsMapper;
import com.gcsj.pojo.Awards;
import org.springframework.stereotype.Service;

@Service
public class AwardsServiceImpl extends ServiceImpl<AwardsMapper, Awards> implements AwardsService {
}
