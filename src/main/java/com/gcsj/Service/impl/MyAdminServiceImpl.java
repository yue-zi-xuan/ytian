package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.MyAdminService;
import com.gcsj.mapper.MyAdminMapper;
import com.gcsj.pojo.MyAdmin;
import org.springframework.stereotype.Service;


@Service
public class MyAdminServiceImpl extends ServiceImpl<MyAdminMapper,MyAdmin> implements MyAdminService {
}
