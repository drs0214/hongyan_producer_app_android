package com.aerozhonghuan.hongyan.producer.modules.common.entity;

/**
 * 获取用户信息返回bean
 */
public class UserBean {
    private String name;
    private String phone;
    //角色类型 （0:创建者，1:管理员，2:司机，3:普通用户）
    private int role;
    private int teamRole;
    //身份证
    private String identityCard;
    //驾照类型
    private String drivingLicense;
    //驾照类型显示用
    private String drivingLicenseString;
    //
    private String carNo;
    private String carVin;
    private String carId;
    //当前车队
    private String teamName;
    private String teamId;

    public String getName() {
        return (name == null ? "" : name);
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return (phone == null ? "" : phone);
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public int getRole() {
        return role;
    }

    public void setRole(int role) {
        this.role = role;
    }

    public int getTeamRole() {
        return teamRole;
    }

    public void setTeamRole(int teamRole) {
        this.teamRole = teamRole;
    }

    public String getIdentityCard() {
        return identityCard;
    }

    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }

    public String getDrivingLicense() {
        return drivingLicense;
    }

    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }

    public String getDrivingLicenseString() {
        return drivingLicenseString;
    }

    public void setDrivingLicenseString(String drivingLicenseString) {
        this.drivingLicenseString = drivingLicenseString;
    }

    public String getCarNo() {
        return (carNo == null ? "" : carNo);
    }

    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }

    public String getCarVin() {
        return carVin;
    }

    public void setCarVin(String carVin) {
        this.carVin = carVin;
    }

    public String getCarId() {
        return carId == null ? "" : carId;
    }

    public void setCarId(String carId) {
        this.carId = carId;
    }

    public String getTeamName() {
        return (teamName == null ? "" : teamName);
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getTeamId() {
        return (teamId == null ? "" : teamId);
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
}
