package com.swpu.contactserver.contact.dto;

import lombok.Data;

@Data
public class ContactPageQueryDTO{
    //联系人姓名
    private String name;

    //页码
    private int page;

    //每页显示记录数
    private int pageSize;
}
