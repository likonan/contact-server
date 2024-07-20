package com.swpu.userserver.user.dto;

import lombok.Data;

@Data
public class QueryPageUser {
    //分页字段
    private Long pageNumber;
    private Long pageSize;
    //条件查询字段
    private String username;
}
