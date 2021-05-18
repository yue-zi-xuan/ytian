package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.NewsService;
import com.gcsj.mapper.NewsMapper;
import com.gcsj.pojo.News;
import org.springframework.stereotype.Service;

@Service
public class NewsServiceImpl extends ServiceImpl<NewsMapper, News> implements NewsService {
}
