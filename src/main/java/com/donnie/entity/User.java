package com.donnie.entity;

import lombok.Data;


@Data
public class User {

    private Long id;
    private String name;
    private String email;
    private String sex;
    private String age;
    private String status;
    private String description;
    private String mobile;

}
