package com.hanfu.product.center.manual.model;

import java.time.LocalDateTime;

import org.springframework.util.StringUtils;

import com.hanfu.product.center.dao.ProductMapper;
import com.hanfu.product.center.manual.dao.HfMemberDao;
import com.hanfu.product.center.model.Product;

public class LastModifier {
	
	public static void setLastModifier(Integer userId,Integer productId,ProductMapper productMapper,HfMemberDao hfMemberDao) {
		if(userId != null) {
			String str = hfMemberDao.selectNameByUserId(userId);
			Product product = productMapper.selectByPrimaryKey(productId);
			String[] s = str.split(",");
			if(!StringUtils.isEmpty(s[0])) {
				product.setLastModifier(s[0]);
			}
			product.setModifyTime(LocalDateTime.now());
			productMapper.updateByPrimaryKey(product);
		}
	}
}
