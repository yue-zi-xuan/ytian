package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.NoticeService;
import com.gcsj.mapper.NoticeMapper;
import com.gcsj.pojo.notice;
import org.springframework.stereotype.Service;


@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeMapper, notice> implements NoticeService {
}
