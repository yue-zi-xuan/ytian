package com.gcsj.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcsj.pojo.Awards;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface AwardsMapper extends BaseMapper<Awards> {
    List<Awards> getAwardsLike(String awardsName);
}
