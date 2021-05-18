package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.OperationLogService;
import com.gcsj.mapper.OperationLogMapper;
import com.gcsj.pojo.OperationLog;
import org.springframework.stereotype.Service;

@Service
public class OperationLogServiceImpl extends ServiceImpl<OperationLogMapper, OperationLog> implements OperationLogService {
}
