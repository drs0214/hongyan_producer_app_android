package com.aerozhonghuan.hongyan.producer.dao.beans;//package com.aerozhonghuan.driverapp.modules.account.beans;


import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Property;

/**
 * 用户详细信息
 * 备注： UserInfo 是授权时的基本信息，UserBean记录了用户的详情信息
 * Created by dell on 2017/6/26.
 */
@Entity
public class UserBean {
    @Id
    private Long id;
    @Property(nameInDb = "name")
    private String name;
    @Property(nameInDb = "phone")
    private String phone;
    //角色类型 （1:普通用户、2:司机、3:管理员、4:车主）
    @Property(nameInDb = "role")
    private int role;
    //身份证
    @Property(nameInDb = "identityCard")
    private String identityCard;
    //驾照类型
    @Property(nameInDb = "drivingLicense")
    private String drivingLicense;
    //驾照类型显示用
    @Property(nameInDb = "drivingLicenseString")
    private String drivingLicenseString;
    @Property(nameInDb = "carNo")
    private String carNo;
    @Property(nameInDb = "teamName")
    private String teamName;
    @Property(nameInDb = "teamId")
    private String teamId;
    @Generated(hash = 1937009350)
    public UserBean(Long id, String name, String phone, int role, String identityCard, String drivingLicense,
                    String drivingLicenseString, String carNo, String teamName, String teamId) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.role = role;
        this.identityCard = identityCard;
        this.drivingLicense = drivingLicense;
        this.drivingLicenseString = drivingLicenseString;
        this.carNo = carNo;
        this.teamName = teamName;
        this.teamId = teamId;
    }
    @Generated(hash = 1203313951)
    public UserBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getPhone() {
        return this.phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public int getRole() {
        return this.role;
    }
    public void setRole(int role) {
        this.role = role;
    }
    public String getIdentityCard() {
        return this.identityCard;
    }
    public void setIdentityCard(String identityCard) {
        this.identityCard = identityCard;
    }
    public String getDrivingLicense() {
        return this.drivingLicense;
    }
    public void setDrivingLicense(String drivingLicense) {
        this.drivingLicense = drivingLicense;
    }
    public String getDrivingLicenseString() {
        return this.drivingLicenseString;
    }
    public void setDrivingLicenseString(String drivingLicenseString) {
        this.drivingLicenseString = drivingLicenseString;
    }
    public String getCarNo() {
        return this.carNo;
    }
    public void setCarNo(String carNo) {
        this.carNo = carNo;
    }
    public String getTeamName() {
        return this.teamName;
    }
    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
    public String getTeamId() {
        return this.teamId;
    }
    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }
   
}
