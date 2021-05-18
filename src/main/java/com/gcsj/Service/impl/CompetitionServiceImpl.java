package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.CompetitionService;
import com.gcsj.mapper.CompetitionMapper;
import com.gcsj.pojo.Competition;
import org.springframework.stereotype.Service;

@Service
public class CompetitionServiceImpl extends ServiceImpl<CompetitionMapper,Competition> implements CompetitionService {
}
