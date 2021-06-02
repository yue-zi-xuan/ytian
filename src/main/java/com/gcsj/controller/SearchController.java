package com.gcsj.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.gcsj.Service.CompetitionNewsService;
import com.gcsj.Service.NewsService;
import com.gcsj.Service.NoticeService;
import com.gcsj.pojo.*;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@Api(tags = "搜索")
public class SearchController {
    @Autowired
    private NoticeService noticeService;
    @Autowired
    private NewsService newsService;
    @Autowired
    private CompetitionNewsService competitionNewsService;

    @PostMapping("/search/all")
    public SearchResult SearchAll(@RequestParam("title")String title)
    {
        SearchResult<News, notice, CompetitionNews> searchResult = new SearchResult<>();
        List<News> news = new ArrayList<>();
        news = newsService.list(new QueryWrapper<News>().like("title",title).orderByDesc("time"));
        List<notice> notices = new ArrayList<>();
        notices = noticeService.list(new QueryWrapper<notice>().like("title",title).orderByDesc("time"));
        List<CompetitionNews> competitionNews = new ArrayList<>();
        competitionNews = competitionNewsService.list(new QueryWrapper<CompetitionNews>().like("title",title).orderByDesc("time"));
        final ListResult<News> result1 = new ListResult<>();
        result1.setName("News").setList(news);
        searchResult.setListResult1(result1);

        final ListResult<notice> result2 = new ListResult<>();
        result2.setName("Notices").setList(notices);
        searchResult.setListResult2(result2);

        final ListResult<CompetitionNews> result3 = new ListResult<>();
        result3.setName("CompetitionNews").setList(competitionNews);

        searchResult.setListResult3(result3);

        return searchResult;
    }

}
