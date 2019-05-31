package com.system.mapper;

import java.util.List;

import com.system.po.Selectedcourse;

public interface SelectedMapper {
	
	//根据两个id查询  Selectedcourse中有courseid、studentid
	public List<Selectedcourse> checkSelectedcourse(Selectedcourse selectedcourse);
}
