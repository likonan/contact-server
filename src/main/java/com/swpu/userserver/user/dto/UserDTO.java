package com.swpu.userserver.user.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Integer id;
    private String username;
    private String password;
    private String sex;
    private Integer age;
    private String address;
    private String imgUrl;
}
