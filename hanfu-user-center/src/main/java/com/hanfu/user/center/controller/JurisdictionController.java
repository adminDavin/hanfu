package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.Jurisdiction.dao.*;
import com.hanfu.user.center.Jurisdiction.model.*;
import com.hanfu.user.center.dao.AccountMapper;
import com.hanfu.user.center.dao.AccountModelMapper;
import com.hanfu.user.center.dao.AccountRolesMapper;
import com.hanfu.user.center.dao.AccountTypeModelMapper;
import com.hanfu.user.center.dao.HfModuleMapper;
import com.hanfu.user.center.dao.RoleModelMapper;
import com.hanfu.user.center.dao.UserRoleMapper;
import com.hanfu.user.center.model.Account;
import com.hanfu.user.center.model.AccountExample;
import com.hanfu.user.center.model.AccountModel;
import com.hanfu.user.center.model.AccountModelExample;
import com.hanfu.user.center.model.AccountRoles;
import com.hanfu.user.center.model.AccountRolesExample;
import com.hanfu.user.center.model.AccountTypeModel;
import com.hanfu.user.center.model.HfCoupon;
import com.hanfu.user.center.model.HfModule;
import com.hanfu.user.center.model.HfModuleExample;
import com.hanfu.user.center.model.Role;
import com.hanfu.user.center.model.RoleModel;
import com.hanfu.user.center.model.RoleModelExample;
import com.hanfu.user.center.model.UserRole;
import com.hanfu.user.center.model.UserRoleExample;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@Api
@RequestMapping("/jurisdiction")
@CrossOrigin
public class JurisdictionController {
	@Autowired
	private DepartmentPersonnelMapper departmentPersonnelMapper;
	@Autowired
	private DepartmentMapper departmentMapper;
	@Autowired
	private AccountMapper accountMapper;
	@Autowired
	private RolesMapper rolesMapper;
	@Autowired
	private JurisdictionMapper jurisdictionMapper;
	@Autowired
	private RoleJurisdictionMapper roleJurisdictionMapper;
	@Resource
	private RedisTemplate<String, Object> redisTemplate;
	@Autowired
	private UserRoleMapper userRoleMapper;
	@Autowired
	private RoleModelMapper roleModelMapper;
	@Autowired
	private AccountRolesMapper accountRolesMapper;
	@Autowired
	private AccountModelMapper accountModelMapper;
	@Autowired
	private AccountTypeModelMapper accountTypeModelMapper;
	@Autowired
	private HfModuleMapper hfModuleMapper;

	@ApiOperation(value = "", notes = "")
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "DepartmentName", value = "部门名称", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "accountId", value = "账号", required = false, type = "Integer"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户", required = false, type = "Integer") })
	@Transactional
	public ResponseEntity<JSONObject> addDepartment(String DepartmentName, Integer accountId,
			HttpServletRequest request, Integer userId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Department department = new Department();
		department.setCreateDate(LocalDateTime.now());
		department.setModifyDate(LocalDateTime.now());
		department.setIsDeleted(0);
		if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
			System.out.println("request.getServletContext().getAttribute得到全局数据："
					+ request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext") != null) {
				department.setMerchantId((Integer) request.getServletContext().getAttribute("getServletContext"));
				department.setDepartmentType("boss");
			}
		}
		department.setAccountId(accountId);
		department.setLastModifier(String.valueOf(userId));
		department.setDepartmentName(DepartmentName);
		departmentMapper.insertSelective(department);
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "账号查询", notes = "账号查询")
	@RequestMapping(value = "/selectAccount", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectAccount(HttpServletRequest request) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		AccountExample accountExample = new AccountExample();
		List<Account> account = new ArrayList<>();
		if (request.getServletContext().getAttribute("getServletContextType") != null
				&& request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
			System.out.println("request.getServletContext().getAttribute得到全局数据："
					+ request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext") != null) {
				accountExample.createCriteria().andAccountTypeEqualTo("boss")
						.andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"))
						.andIsDeletedEqualTo(0);
				account = accountMapper.selectByExample(accountExample);
			}
		} else if (request.getServletContext().getAttribute("getServletContextType") != null
				&& request.getServletContext().getAttribute("getServletContextType").equals("stone")) {
			System.out.println("request.getServletContext().getAttribute得到全局数据："
					+ request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext") != null) {
				accountExample.createCriteria().andAccountTypeEqualTo("stone")
						.andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"))
						.andIsDeletedEqualTo(0);
				account = accountMapper.selectByExample(accountExample);
			}
		} else {
			accountExample.createCriteria().andIsDeletedEqualTo(0);
			account = accountMapper.selectByExample(accountExample);
		}
		account.forEach(account1 -> {
			if (redisTemplate.opsForValue().get(String.valueOf(account1.getId()) + "token") != null) {
				System.out.println(account1.getAccountCode() + "在线，id:" + account1.getId());
				account1.setIsDeleted(2);
			}
		});
		return builder.body(ResponseUtils.getResponseBody(account));
	}

	@ApiOperation(value = "添加角色", notes = "添加角色")
	@RequestMapping(value = "/addRole", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> addRole(HttpServletRequest request, String roleName, Integer userId,
			String roleCode)
			throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Roles roles = new Roles();
        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                roles.setRoleType("boss");
                roles.setMachId((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                roles.setRoleType("stone");
                roles.setMachId((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }

//		roles.setRoleType("boss");
//		roles.setMachId(1);
		
		roles.setRoleCode(roleCode);
		roles.setRoleName(roleName);
		roles.setCreateDate(LocalDateTime.now());
		roles.setModifyDate(LocalDateTime.now());
		roles.setIsDeleted(0);
		roles.setLastModifier(String.valueOf(userId));
		rolesMapper.insert(roles);
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "角色查询", notes = "角色查询")
	@RequestMapping(value = "/selectRole", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectRole(HttpServletRequest request) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		RolesExample rolesExample = new RolesExample();

        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                rolesExample.createCriteria().andRoleTypeEqualTo("boss").andIsDeletedEqualTo(0).andMachIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
            	List<String> s = new ArrayList<String>();
            	s.add("stone");
            	s.add("boss");
                rolesExample.createCriteria().andRoleTypeIn(s).andIsDeletedEqualTo(0).andRoleCodeEqualTo("stone").andMachIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        } else {
            rolesExample.createCriteria().andIsDeletedEqualTo(0);
        }
		List<Roles> roles = rolesMapper.selectByExample(rolesExample);

		return builder.body(ResponseUtils.getResponseBody(roles));
	}
	
//	@ApiOperation(value = "查询当前商户角色", notes = "查询当前商户角色")
//	@RequestMapping(value = "/selectMachRole", method = RequestMethod.GET)
//	public ResponseEntity<JSONObject> selectMachRole(Integer mathId) throws JSONException {
//		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//		RolesExample example = new RolesExample();
//		example.createCriteria().andMachIdEqualTo(mathId);
//		List<Roles> list = rolesMapper.selectByExample(example);
//		return builder.body(ResponseUtils.getResponseBody(list));
//	}

	@ApiOperation(value = "权限查询", notes = "权限查询")
	@RequestMapping(value = "/selectJurisdiction", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectJurisdiction() throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		JurisdictionExample jurisdictionExample = new JurisdictionExample();
		jurisdictionExample.createCriteria().andIsDeletedEqualTo((short) 0);
		return builder.body(ResponseUtils.getResponseBody(jurisdictionMapper.selectByExample(jurisdictionExample)));
	}

	@ApiOperation(value = "下线", notes = "下线")
	@RequestMapping(value = "/deleteAccount", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> delete(Integer id, String type) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        Account account= accountMapper.selectByPrimaryKey(id);
		if (null != id && null != type) {
			redisTemplate.delete(String.valueOf(id) + "token");
			System.out.println(redisTemplate.opsForValue().get(String.valueOf(id) + "token"));
//			redisTemplate.delete(String.valueOf(account.getUserId()) + type + String.valueOf(BSid)+ "token");
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "角色添加权限", notes = "角色添加权限")
	@RequestMapping(value = "/roleAddJurisdiction", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> roleAddJurisdiction(Integer roleId, Integer[] JurisdictionIds)
			throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		for (Integer jurId : JurisdictionIds) {
			RoleJurisdictionExample example = new RoleJurisdictionExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andJurisdictionIdEqualTo(jurId);
			List<RoleJurisdiction> list = roleJurisdictionMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {
				RoleJurisdiction roleJurisdiction = new RoleJurisdiction();
				roleJurisdiction.setCreateTime(LocalDateTime.now());
				roleJurisdiction.setModifyTime(LocalDateTime.now());
				roleJurisdiction.setIsDeleted((short) 0);
				roleJurisdiction.setRoleId(roleId);
				roleJurisdiction.setJurisdictionId(jurId);
				roleJurisdictionMapper.insertSelective(roleJurisdiction);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "角色添加模块", notes = "角色添加模块")
	@RequestMapping(value = "/roleAddModel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> roleAddModel(Integer roleId, Integer[] modelId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		for (Integer item : modelId) {
			RoleModelExample example = new RoleModelExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andModelIdEqualTo(item);
			List<RoleModel> list = roleModelMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {
				RoleModel model = new RoleModel();
				model.setRoleId(roleId);
				model.setModelId(item);
				model.setCreateTime(LocalDateTime.now());
				model.setModifyTime(LocalDateTime.now());
				model.setIsDeleted((byte) 0);
				roleModelMapper.insert(model);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	@ApiOperation(value = "查询当前账号拥有的模块", notes = "查询当前用户拥有的模块")
	@RequestMapping(value = "/findAdminHasModel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findAdminHasModel(Integer id,Integer rId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		AccountRolesExample example = new AccountRolesExample();
		example.createCriteria().andAccountIdEqualTo(id).andIsDeletedEqualTo((short) 0);
		List<AccountRoles> roles = accountRolesMapper.selectByExample(example);
		List<Integer> roleId = roles.stream().map(AccountRoles::getRolesId).collect(Collectors.toList());
		RoleModelExample example2 = new RoleModelExample();
		example2.createCriteria().andRoleIdIn(roleId).andIsDeletedEqualTo((byte) 0);
		List<RoleModel> models = roleModelMapper.selectByExample(example2);
		List<Integer> modelId = models.stream().map(RoleModel::getModelId).collect(Collectors.toList());
		HfModuleExample example3 = new HfModuleExample();
		example3.createCriteria().andIdIn(modelId);
		if(rId == null) {
			List<HfModule> result = hfModuleMapper.selectByExample(example3);
			return builder.body(ResponseUtils.getResponseBody(result));
		}else {
			example2.clear();
			example2.createCriteria().andRoleIdEqualTo(rId).andIsDeletedEqualTo((byte) 0);
			models = roleModelMapper.selectByExample(example2);
			modelId = models.stream().map(RoleModel::getModelId).collect(Collectors.toList());
			example3.clear();
			example3.createCriteria().andIdIn(modelId);
			List<HfModule> result = hfModuleMapper.selectByExample(example3);
			return builder.body(ResponseUtils.getResponseBody(result));
			
		}
		
	}
	
	@ApiOperation(value = "查询当前账号在某模块拥有的权限", notes = "查询当前账号在某模块拥有的权限")
	@RequestMapping(value = "/findAdminHasJusInModel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findAdminHasJusInModel(Integer id,Integer modelId,Integer rId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		AccountRolesExample example = new AccountRolesExample();
		example.createCriteria().andAccountIdEqualTo(id).andIsDeletedEqualTo((short) 0);
		List<AccountRoles> roles = accountRolesMapper.selectByExample(example);
		List<Integer> roleId = roles.stream().map(AccountRoles::getRolesId).collect(Collectors.toList());
		RoleJurisdictionExample example2 = new RoleJurisdictionExample();
		example2.createCriteria().andRoleIdIn(roleId).andIsDeletedEqualTo((short) 0);
		List<RoleJurisdiction> jurisdictions = roleJurisdictionMapper.selectByExample(example2);
		List<Integer> jurisdictionId = jurisdictions.stream().map(RoleJurisdiction::getJurisdictionId).collect(Collectors.toList());
		JurisdictionExample example3 = new JurisdictionExample();
		example3.createCriteria().andIdIn(jurisdictionId).andModelIdEqualTo(modelId);
		if(rId == null) {
			List<Jurisdiction> result = jurisdictionMapper.selectByExample(example3);
			return builder.body(ResponseUtils.getResponseBody(result));
		}else {
			example2.clear();
			example2.createCriteria().andRoleIdEqualTo(rId).andIsDeletedEqualTo((short) 0);
			jurisdictions = roleJurisdictionMapper.selectByExample(example2);
			jurisdictionId = jurisdictions.stream().map(RoleJurisdiction::getJurisdictionId).collect(Collectors.toList());
			example3.clear();
			example3.createCriteria().andIdIn(jurisdictionId).andModelIdEqualTo(modelId);
			List<Jurisdiction> result = jurisdictionMapper.selectByExample(example3);
			return builder.body(ResponseUtils.getResponseBody(result));
		}
		
	}

	@ApiOperation(value = "给用户绑定角色", notes = "给用户绑定角色")
	@RequestMapping(value = "/addUserRole", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addUserRole(Integer roleId[], Integer id) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		for (Integer item : roleId) {
			AccountRolesExample example = new AccountRolesExample();
			example.createCriteria().andRolesIdEqualTo(item).andAccountIdEqualTo(id);
			List<AccountRoles> list = accountRolesMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {
				AccountRoles role = new AccountRoles();
				role.setAccountId(id);
				role.setRolesId(item);
				role.setCreateTime(LocalDateTime.now());
				role.setModifyTime(LocalDateTime.now());
				role.setIsDeleted((short) 0);
				accountRolesMapper.insert(role);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "删除用户角色", notes = "删除用户角色")
	@RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteUserRole(Integer roleId[], Integer userId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		for (Integer item : roleId) {
			UserRoleExample example = new UserRoleExample();
			example.createCriteria().andUserIdEqualTo(userId).andRoleIdEqualTo(item);
			List<UserRole> list = userRoleMapper.selectByExample(example);
			if (CollectionUtils.isEmpty(list)) {

			} else {
				userRoleMapper.deleteByExample(example);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "我们添加平台账号", notes = "我们添加平台账号")
	@RequestMapping(value = "/addAdminByUs", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addAdminByUs(String accountCode, String accountType, Integer merchantId,
			String roleName,Integer[] modelId,Integer jurisdictonId[]) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		AccountExample accountExample = new AccountExample();
		accountExample.createCriteria().andAccountCodeEqualTo(accountCode).andAccountTypeEqualTo(accountType)
				.andMerchantIdEqualTo(merchantId);
		List<Account> accounts = accountMapper.selectByExample(accountExample);
		Account account = null;
		if (CollectionUtils.isEmpty(accounts)) {
			account = new Account();
			account.setAccountCode(accountCode);
			account.setAccountRole("Super Admin");
			account.setAccountType(accountType);
			account.setMerchantId(merchantId);
			account.setCreateDate(LocalDateTime.now());
			account.setModifyDate(LocalDateTime.now());
			account.setIsDeleted(0);
			accountMapper.insert(account);
		} else {
			return builder.body(ResponseUtils.getResponseBody("平台账户存在"));
		}

		RolesExample rolesExample = new RolesExample();
		rolesExample.createCriteria().andRoleCodeEqualTo("boss").andRoleTypeEqualTo("sass").andMachIdEqualTo(merchantId)
				.andRoleNameEqualTo(roleName);
		List<Roles> list = rolesMapper.selectByExample(rolesExample);
		Integer roleId = null;
    	if(!CollectionUtils.isEmpty(list)) {
    		roleId = list.get(0).getId();
    	}else {
    		Roles roles = new Roles();
    		roles.setRoleName(roleName);
    		roles.setRoleCode("boss");
    		roles.setRoleType("sass");
    		roles.setMachId(merchantId);
    		roles.setCreateDate(LocalDateTime.now());
    		roles.setModifyDate(LocalDateTime.now());
    		roles.setIsDeleted(0);
    		rolesMapper.insert(roles);
    		roleId = roles.getId();
    	}

		AccountRolesExample accountRolesExample = new AccountRolesExample();
		accountRolesExample.createCriteria().andRolesIdEqualTo(1).andAccountIdEqualTo(account.getId());
		List<AccountRoles> roles = accountRolesMapper.selectByExample(accountRolesExample);
		if (CollectionUtils.isEmpty(roles)) {
			AccountRoles accountRoles = new AccountRoles();
			accountRoles.setAccountId(account.getId());
			accountRoles.setRolesId(roleId);
			accountRoles.setCreateTime(LocalDateTime.now());
			accountRoles.setModifyTime(LocalDateTime.now());
			accountRoles.setIsDeleted((short) 0);
			accountRolesMapper.insert(accountRoles);
		} else {
			return builder.body(ResponseUtils.getResponseBody("账户角色存在"));
		}
		for (int i = 0; i < modelId.length; i++) {
//			AccountModel model = new AccountModel();
//			model.setAccountId(account.getId());
//			model.setModelId(modelId[i]);
//			model.setCreateDate(LocalDateTime.now());
//			model.setModifyDate(LocalDateTime.now());
//			model.setIdDeleted((byte) 0);
//			accountModelMapper.insert(model);
//			AccountTypeModel typeModel = new AccountTypeModel();
//			typeModel.setAccountType("boss");
//			typeModel.setMerchantId(merchantId);
//			typeModel.setModelId(modelId[i]);
//			typeModel.setCreateDate(LocalDateTime.now());
//			typeModel.setModifyDate(LocalDateTime.now());
//			typeModel.setIsDeleted((byte) 0);
//			accountTypeModelMapper.insert(typeModel);
			RoleModel roleModel = new RoleModel();
			roleModel.setRoleId(roleId);
			roleModel.setModelId(modelId[i]);
			roleModel.setCreateTime(LocalDateTime.now());
			roleModel.setModifyTime(LocalDateTime.now());
			roleModel.setIsDeleted((byte) 0);
			roleModelMapper.insert(roleModel);
		}
		for (int i = 0; i < jurisdictonId.length; i++) {
			RoleJurisdiction jurisdiction = new RoleJurisdiction();
			jurisdiction.setRoleId(roleId);
			jurisdiction.setJurisdictionId(jurisdictonId[i]);
			jurisdiction.setCreateTime(LocalDateTime.now());
			jurisdiction.setModifyTime(LocalDateTime.now());
			jurisdiction.setIsDeleted((short) 0);
			roleJurisdictionMapper.insert(jurisdiction);
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
}
