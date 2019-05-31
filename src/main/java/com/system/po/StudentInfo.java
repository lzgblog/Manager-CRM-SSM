package com.system.po;

import java.util.Date;

public class StudentInfo {


	//所属院系名
    private String collegeName;
    
    //学生信息
    private Integer userid;

    private String username;

    private String sex;

    private Date birthyear;

    private Date grade;

    private Integer collegeid;

	public String getCollegeName() {
		return collegeName;
	}

	public void setCollegeName(String collegeName) {
		this.collegeName = collegeName;
	}

	public Integer getUserid() {
		return userid;
	}

	public void setUserid(Integer userid) {
		this.userid = userid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public Date getBirthyear() {
		return birthyear;
	}

	public void setBirthyear(Date birthyear) {
		this.birthyear = birthyear;
	}

	public Date getGrade() {
		return grade;
	}

	public void setGrade(Date grade) {
		this.grade = grade;
	}

	public Integer getCollegeid() {
		return collegeid;
	}

	public void setCollegeid(Integer collegeid) {
		this.collegeid = collegeid;
	}

	@Override
	public String toString() {
		return "StudentInfo [collegeName=" + collegeName + ", userid=" + userid + ", username=" + username + ", sex="
				+ sex + ", birthyear=" + birthyear + ", grade=" + grade + ", collegeid=" + collegeid + "]";
	}
    
}
