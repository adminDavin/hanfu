package com.hanfu.dichan.center.manual.model;

public class RatationType {
	
	public enum RatationTypeEnum{
		HOME_PAGE("homePage"),
		OUR("our");
		private String ratationType;

		private RatationTypeEnum(String ratationType) {
			this.ratationType = ratationType;
		}

		public String getRatationType() {
			return ratationType;
		}
		
		public String getRatationTypeEnum(String type) {
			for (RatationTypeEnum item : RatationTypeEnum.values()) {
				if(item.equals(type)) {
					return item.getRatationType();
				}
			}
			return null;
		}
	}	

}
