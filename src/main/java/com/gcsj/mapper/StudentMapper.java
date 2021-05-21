package com.gcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcsj.pojo.student;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface StudentMapper extends BaseMapper<student> {
}
