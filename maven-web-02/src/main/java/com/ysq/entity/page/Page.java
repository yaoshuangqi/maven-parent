package com.ysq.entity.page;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
* @Description: 
* @Author: quanroon.yaosq
* @Date: 2020/9/14 14:12
* @Param: 
* @Return: 
*/
@Data
public class Page {

    @JsonIgnore
    private PageParams pageParams;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer pageNo;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private Integer pageSize;

    public void openPage(){
        this.pageParams = new PageParams();
        this.pageParams.openPage();
        this.pageParams.setPageNo(this.pageNo);
        this.pageParams.setPageSize(this.pageSize);
    }

    public void closePage(){
        if(this.pageParams != null){
            this.pageParams.closePage();
            this.pageParams = null;
        }
    }

    public void setPageParams(PageParams pageParams){
        this.pageParams = pageParams;
    }

    public void setPageParams(Page page){
        this.pageParams = page.getPageParams();
    }
    public PageParams page(List list){
        return this.pageParams.page(list);
    }

}
