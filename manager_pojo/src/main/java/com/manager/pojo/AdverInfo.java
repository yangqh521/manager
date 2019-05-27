package com.manager.pojo;

import lombok.Data;

import java.util.Date;

@Data
public class AdverInfo {

    private Integer adverId;
    private String adverName;
    private Integer regionId;
    private String regionName;
    private Integer agentId;
    private String agentName;
    private String operateName;
    private Integer catId;
    private Integer subCatId;
    private String siteName;
    private String siteUrl;
    private Integer userId;
    private Integer budget;
    private Integer balance;
    private Integer cost;
    private Integer status;
    private Integer icpTypeId;
    private Integer isDelete;
    private Date createTime;
    private Date lastModifyTime;
}
