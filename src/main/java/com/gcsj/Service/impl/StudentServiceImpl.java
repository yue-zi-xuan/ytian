package com.gcsj.Service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gcsj.Service.StudentService;
import com.gcsj.mapper.StudentMapper;
import com.gcsj.pojo.student;
import org.springframework.stereotype.Service;

@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, student> implements StudentService {
}
