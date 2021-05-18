package com.gcsj.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.gcsj.pojo.CompetitionNews;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CompetitionNewsMapper extends BaseMapper<CompetitionNews> {
     List<CompetitionNews> getCompetitionNewsLike(String title);
}
