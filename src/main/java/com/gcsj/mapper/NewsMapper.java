package com.gcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcsj.pojo.News;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface NewsMapper extends BaseMapper<News> {
    List<News> getNewsLike(String title);
}
