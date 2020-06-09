package com.hanfu.user.center.manual.model;

import java.util.ArrayList;
import java.util.List;

public class RoleCode {
	
	public enum RoleCodeEnum{
		BOSS("boss",1),
		STONE("stone",2),
		WAREHOUSE("warehouse",2);
		private String roleCodeType;
		private Integer codeLevel;
		private RoleCodeEnum(String roleCodeType, Integer codeLevel) {
			this.roleCodeType = roleCodeType;
			this.codeLevel = codeLevel;
		}
		public String getRoleCodeType() {
			return roleCodeType;
		}
		public Integer getCodeLevel() {
			return codeLevel;
		}
		public static List<String> getRoleCode(String type){
			Integer level = getCodeLevel(type);
			List<String> str = new ArrayList<String>();
			for (RoleCodeEnum item: RoleCodeEnum.values()) {
				if(item.getCodeLevel() >= level) {
					str.add(item.getRoleCodeType());
				}
			}
			return str;
		}
		public static Integer getCodeLevel(String type) {
			for (RoleCodeEnum item: RoleCodeEnum.values()) {
				if(item.getRoleCodeType().equals(type)) {
					return item.getCodeLevel();
				}
			}
			return null;
		}
	}
}
