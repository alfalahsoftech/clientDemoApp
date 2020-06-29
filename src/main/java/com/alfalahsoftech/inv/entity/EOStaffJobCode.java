package com.alfalahsoftech.inv.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;


@Entity
@SequenceGenerator(sequenceName="EOStaffJobCode_SEQ", name="EOStaffJobCode_SEQ", allocationSize=1 )
public class EOStaffJobCode extends AFMainEntity {
	@Id
	@GeneratedValue(strategy=GenerationType.SEQUENCE,generator="EOStaffJobCode_SEQ")
	private Long primaryKey;
	
	@OneToOne
	@JoinColumn(name="lkJobCode")
	private LKJobCode jobCode;

	@OneToOne
	@JoinColumn(name="eoStaff")
	private EOStaff eoStaff;
	
	@Override
	public Long primaryKey() {
		return this.primaryKey;
	} 

}
