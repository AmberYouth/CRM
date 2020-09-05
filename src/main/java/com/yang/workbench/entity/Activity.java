package com.yang.workbench.entity;

public class Activity {
    private  String id ; // //主键
    private  String owner; // 所有者 外键 关联tbl_user
    private  String name;// 市场活动名称
    private  String startDate;//开始年月日
    private  String endDate;//结束日期 年月日
    private  String cost;//成本
    private  String description;//描述
    private  String createTime;//创建时间 年月日时分秒
    private  String createBy;//创建人
    private  String editTime;//修改时间  年月日时分秒
    private  String editBy;//修改人
    //
    public void setId(String id) {
        this.id = id;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public void setCost(String cost) {
        this.cost = cost;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setCreateBy(String createBy) {
        this.createBy = createBy;
    }

    public void setEditTime(String editTime) {
        this.editTime = editTime;
    }

    public void setEditBy(String editBy) {
        this.editBy = editBy;
    }

    @Override
    public String toString() {
        return "Activity{" +
                "id='" + id + '\'' +
                ", owner='" + owner + '\'' +
                ", name='" + name + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", cost='" + cost + '\'' +
                ", description='" + description + '\'' +
                ", createTime='" + createTime + '\'' +
                ", createBy='" + createBy + '\'' +
                ", editTime='" + editTime + '\'' +
                ", editBy='" + editBy + '\'' +
                '}';
    }

    public String getId() {
        return id;
    }

    public String getOwner() {
        return owner;
    }

    public String getName() {
        return name;
    }

    public String getStartDate() {
        return startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public String getCost() {
        return cost;
    }

    public String getDescription() {
        return description;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getCreateBy() {
        return createBy;
    }

    public String getEditTime() {
        return editTime;
    }

    public String getEditBy() {
        return editBy;
    }
}
