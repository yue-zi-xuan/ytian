package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.GPAService;
import com.gcsj.mapper.GAPMapper;
import com.gcsj.pojo.GPA;
import org.springframework.stereotype.Service;

@Service
public class GPAServiceImpl extends ServiceImpl<GAPMapper, GPA> implements GPAService {
}
