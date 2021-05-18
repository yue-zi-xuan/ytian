package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.superAdminService;
import com.gcsj.mapper.superAdminMapper;
import com.gcsj.pojo.superAdmin;
import org.springframework.stereotype.Service;

@Service
public class superAdminServiceImpl extends ServiceImpl<superAdminMapper, superAdmin> implements superAdminService {
}
