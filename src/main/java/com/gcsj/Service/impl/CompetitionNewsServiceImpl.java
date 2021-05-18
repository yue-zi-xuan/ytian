package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.CompetitionNewsService;
import com.gcsj.mapper.CompetitionNewsMapper;
import com.gcsj.pojo.CompetitionNews;
import org.springframework.stereotype.Service;

@Service
public class CompetitionNewsServiceImpl extends ServiceImpl<CompetitionNewsMapper, CompetitionNews> implements CompetitionNewsService {
}
