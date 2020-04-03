package com.hanfu.product.center.manual.model;

public class BalanceType {

	public static enum BalanceTypeEnum {
		DISCOUNT_COUPON("discountCoupon"), RECHARGE_AMOUNT("rechargeAmount"), INTEGRAL("integral");

		private String balanceType;

		private BalanceTypeEnum(String balanceType) {
			this.balanceType = balanceType;
		}

		public String getBalanceType() {
			return balanceType;
		}

		public static String getBalanceType(String balanceType) {
			for (BalanceTypeEnum item : BalanceTypeEnum.values()) {
				if (item.getBalanceType().equals(balanceType)) {
					return item.getBalanceType();
				}
			}
			return "discountCoupon";
		}
	}
}
