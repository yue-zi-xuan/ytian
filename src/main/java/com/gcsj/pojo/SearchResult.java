package com.gcsj.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchResult <T,S,Q>{
    public ListResult listResult1;
    public ListResult listResult2;
    public ListResult listResult3;
}
