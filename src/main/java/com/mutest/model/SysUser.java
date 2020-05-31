package com.mutest.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.util.Date;

@Data
public class SysUser extends BaseEntity<Long> {

    private static final long serialVersionUID = -6525908145032868837L;

    private String username;
    private String password;
    private String nickname;
    private String department;
    private String role;
    private String headImgUrl;
    private String phone;
    private String telephone;
    private String email;
    private String description;
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthday;
    private Integer sex;
    private Integer status;
    private String createTime;
    private String modifyTime;

    public interface Status {
        int DISABLED = 0;
        int VALID = 1;
        int LOCKED = 2;
    }
}
