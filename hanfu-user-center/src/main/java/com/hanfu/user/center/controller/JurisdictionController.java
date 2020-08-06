package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.user.center.Jurisdiction.dao.*;
import com.hanfu.user.center.Jurisdiction.model.*;
import com.hanfu.user.center.dao.AccountMapper;
import com.hanfu.user.center.dao.AccountModelMapper;
import com.hanfu.user.center.dao.AccountRolesMapper;
import com.hanfu.user.center.dao.AccountTypeModelMapper;
import com.hanfu.user.center.dao.HfModuleMapper;
import com.hanfu.user.center.dao.HfStoneMapper;
import com.hanfu.user.center.dao.HfUserMapper;
import com.hanfu.user.center.dao.RoleModelMapper;
import com.hanfu.user.center.dao.UserRoleMapper;
import com.hanfu.user.center.manual.model.AccountRolesType;
import com.hanfu.user.center.manual.model.RoleCode.RoleCodeEnum;
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
import com.hanfu.user.center.model.HfStone;
import com.hanfu.user.center.model.HfStoneExample;
import com.hanfu.user.center.model.HfUser;
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

import org.apache.curator.shaded.com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.configurationprocessor.json.JSONException;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
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
	@Autowired
	private HfUserMapper hfUserMapper;
	@Autowired
	private HfStoneMapper hfStoneMapper;
	@Autowired
	private RestTemplate restTemplate;
	private static final String REST_URL_PREFIX = "https://www.tjsichuang.cn:1443/api/product/";

	@ApiOperation(value = "", notes = "")
	@RequestMapping(value = "/addDepartment", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "DepartmentName", value = "部门名称", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户", required = false, type = "Integer") })
	@Transactional
	public ResponseEntity<JSONObject> addDepartment(String DepartmentName,HttpServletRequest request, Integer userId) throws JSONException {
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
		if (request.getServletContext().getAttribute("getServletContextType").equals("stone")) {
			if (request.getServletContext().getAttribute("getServletContext") != null) {
				department.setMerchantId((Integer) request.getServletContext().getAttribute("getServletContext"));
				department.setDepartmentType("stone");
			}
		}
		department.setLastModifier(String.valueOf(userId));
		department.setDepartmentName(DepartmentName);
		departmentMapper.insertSelective(department);
		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	@ApiOperation(value = "", notes = "")
	@RequestMapping(value = "/getDepartment", method = RequestMethod.POST)
	@ApiImplicitParams({
			@ApiImplicitParam(paramType = "query", name = "DepartmentName", value = "部门名称", required = false, type = "String"),
			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户", required = false, type = "Integer") })
	@Transactional
	public ResponseEntity<JSONObject> getDepartment(HttpServletRequest request) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		DepartmentExample example = new DepartmentExample();
		if (request.getServletContext().getAttribute("getServletContextType").equals("boss")) {
			System.out.println("request.getServletContext().getAttribute得到全局数据："
					+ request.getServletContext().getAttribute("getServletContext"));
			if (request.getServletContext().getAttribute("getServletContext") != null) {
				example.createCriteria().andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
				example.createCriteria().andDepartmentTypeEqualTo("boss");
			}
		}
		if (request.getServletContext().getAttribute("getServletContextType").equals("stone")) {
			if (request.getServletContext().getAttribute("getServletContext") != null) {
				example.createCriteria().andMerchantIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
				example.createCriteria().andDepartmentTypeEqualTo("stone");
			}
		}
		List<Department> list = departmentMapper.selectByExample(example);
		return builder.body(ResponseUtils.getResponseBody(list));
	}
	
//	@ApiOperation(value = "", notes = "")
//	@RequestMapping(value = "/addUserDepartment", method = RequestMethod.POST)
//	@ApiImplicitParams({
//			@ApiImplicitParam(paramType = "query", name = "DepartmentName", value = "部门名称", required = false, type = "String"),
//			@ApiImplicitParam(paramType = "query", name = "userId", value = "用户", required = false, type = "Integer") })
//	@Transactional
//	public ResponseEntity<JSONObject> addUserDepartment(Integer id, Integer userId) throws JSONException {
//		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//		DepartmentPersonnelExample example = new DepartmentPersonnelExample();
//		example.createCriteria().andDepartmentIdEqualTo(id).and
//		return builder.body(ResponseUtils.getResponseBody(0));
//	}

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
				accountExample.clear();
				List<String> str = new ArrayList<String>();
				str.add("stone");
				str.add("warehouse");
				accountExample.createCriteria().andAccountTypeIn(str).andAccountRoleEqualTo("Super Admin").andIsDeletedEqualTo(0);
				List<Account> a = accountMapper.selectByExample(accountExample);
				account.addAll(a);
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
			HfUser hfUser = hfUserMapper.selectByPrimaryKey(account1.getUserId());
			account1.setLastModifier(hfUser.getNickName());
			if (redisTemplate.opsForValue().get(String.valueOf(account1.getId()) + "token") != null) {
				System.out.println(account1.getAccountCode() + "在线，id:" + account1.getId());
				account1.setIsDeleted(2);
			}
		});
		return builder.body(ResponseUtils.getResponseBody(account));
	}

	@ApiOperation(value = "添加角色", notes = "添加角色")
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> addRole(HttpServletRequest request, String roleName, Integer userId,
			String roleCode, Integer accountId)
			throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		Roles roles = new Roles();
        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                roles.setRoleCode("boss");
                roles.setMachId((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
            	roles.setRoleCode("stone");
                roles.setMachId((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }

//		roles.setRoleType("boss");
//		roles.setMachId(1);
		
        roles.setRoleType(roleCode);
		roles.setRoleName(roleName);
		roles.setCreateDate(LocalDateTime.now());
		roles.setModifyDate(LocalDateTime.now());
		roles.setIsDeleted(0);
		roles.setLastModifier(String.valueOf(userId));
		rolesMapper.insert(roles);
		AccountRoles accountRoles = new AccountRoles();
		accountRoles.setAccountId(accountId);
		accountRoles.setRolesId(roles.getId());
		accountRoles.setCreateTime(LocalDateTime.now());
		accountRoles.setModifyTime(LocalDateTime.now());
		accountRoles.setIsDeleted((short) 0);
		accountRolesMapper.insert(accountRoles);
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
                rolesExample.createCriteria().andRoleCodeEqualTo("boss").andIsDeletedEqualTo(0).andMachIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                rolesExample.createCriteria().andRoleTypeEqualTo("stone").andIsDeletedEqualTo(0).andMachIdEqualTo((Integer) request.getServletContext().getAttribute("getServletContext"));
            }
        } else {
            rolesExample.createCriteria().andIsDeletedEqualTo(0);
        }
		List<Roles> roles = rolesMapper.selectByExample(rolesExample);

		return builder.body(ResponseUtils.getResponseBody(roles));
	}
	
	@RequestMapping(value = "/selectRoleCode", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectRoleCode(HttpServletRequest request) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
                List<String> str = RoleCodeEnum.getRoleCode("boss");
                return builder.body(ResponseUtils.getResponseBody(str));
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
            	List<String> str = new ArrayList<String>();
            	str.add("stone");
                return builder.body(ResponseUtils.getResponseBody(str));
            }
        } 
        return builder.body(ResponseUtils.getResponseBody(null));
	}
	
	@ApiOperation(value = "获取列表（店铺或仓库）", notes = "获取列表（店铺或仓库）")
	@RequestMapping(value = "/getListWaOrStore", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> getListWaOrStore(HttpServletRequest request,String type) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("boss")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
            	if(RoleCodeEnum.STONE.getRoleCodeType().equals(type)) {
            		HfStoneExample example = new HfStoneExample();
                	example.createCriteria().andBossIdEqualTo((Integer)request.getServletContext().getAttribute("getServletContext"));
                    List<HfStone> str = hfStoneMapper.selectByExample(example);
                    return builder.body(ResponseUtils.getResponseBody(str));
            	}
            	if(RoleCodeEnum.WAREHOUSE.getRoleCodeType().equals(type)) {
            		JSONObject jsonObject = restTemplate.getForObject(REST_URL_PREFIX+"/wareHouse/listWareHouse", JSONObject.class, (Integer)request.getServletContext().getAttribute("getServletContext"));
            		System.out.println(jsonObject.toString());
            	}
            }
        }else if (request.getServletContext().getAttribute("getServletContextType")!=null&&request.getServletContext().getAttribute("getServletContextType").equals("stone")){
            System.out.println("request.getServletContext().getAttribute得到全局数据："+request.getServletContext().getAttribute("getServletContext"));
            if (request.getServletContext().getAttribute("getServletContext")!=null){
            	List<String> str = new ArrayList<String>();
            	str.add("stone");
                return builder.body(ResponseUtils.getResponseBody(str));
            }
        } 
        return builder.body(ResponseUtils.getResponseBody(null));
	}
	
	
	
	
	@ApiOperation(value = "查询当前账号角色", notes = "查询当前账号角色")
	@RequestMapping(value = "/selectAccountRole", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> selectAccountRole(Integer id, String type) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		
		AccountRolesExample example = new AccountRolesExample();
		example.createCriteria().andAccountIdEqualTo(id).andIsDeletedEqualTo((short) 0);
		List<AccountRoles> list = accountRolesMapper.selectByExample(example);
		List<Roles> result = null;
		List<AccountRolesType> results = new ArrayList<AccountRolesType>();
		if(!CollectionUtils.isEmpty(list)) {
			List<Integer> roleId = list.stream().map(AccountRoles::getRolesId).collect(Collectors.toList());
			RolesExample rolesExample = new RolesExample();
			rolesExample.createCriteria().andIdIn(roleId);
			result = rolesMapper.selectByExample(rolesExample);
			if(!CollectionUtils.isEmpty(result)) {
				Map<String, List<Roles>> map = result.stream().collect(Collectors.groupingBy(Roles::getRoleType));
				Set<Entry<String, List<Roles>>> entry = map.entrySet();
				for (Entry<String, List<Roles>> item : entry) {
					AccountRolesType rolesType = new AccountRolesType();
					rolesType.setRoleName(item.getKey());
					rolesType.setRoles(item.getValue());
					results.add(rolesType);
				}
			}
		}
		if(type != null) {
			return builder.body(ResponseUtils.getResponseBody(result));
		}
		return builder.body(ResponseUtils.getResponseBody(results));
	}

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
	@RequestMapping(value = "/roleAddJurisdiction", method = RequestMethod.POST)
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
			}else {
				RoleJurisdiction roleJurisdiction = list.get(0);
				if(roleJurisdiction.getIsDeleted() == (short) 1) {
					roleJurisdiction.setIsDeleted((short) 0);
					roleJurisdictionMapper.updateByPrimaryKey(roleJurisdiction);
				}
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	@ApiOperation(value = "角色删除权限", notes = "角色删除权限")
	@RequestMapping(value = "/roleDeleteJurisdiction", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> roleDeleteJurisdiction(Integer roleId, Integer[] JurisdictionIds)
			throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		for (Integer jurId : JurisdictionIds) {
			RoleJurisdictionExample example = new RoleJurisdictionExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andJurisdictionIdEqualTo(jurId);
			List<RoleJurisdiction> list = roleJurisdictionMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				RoleJurisdiction jurisdiction = list.get(0);
				jurisdiction.setIsDeleted((short) 1);
				roleJurisdictionMapper.updateByPrimaryKey(jurisdiction);
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
			}else {
				RoleModel model = list.get(0);
				if(model.getIsDeleted() == (byte) 1) {
					model.setIsDeleted((byte) 0);
					roleModelMapper.updateByPrimaryKey(model);
				}
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	@ApiOperation(value = "角色删除模块", notes = "角色删除模块")
	@RequestMapping(value = "/roleDeleteModel", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> roleDeleteModel(Integer roleId, Integer[] modelId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		for (Integer item : modelId) {
			RoleModelExample example = new RoleModelExample();
			example.createCriteria().andRoleIdEqualTo(roleId).andModelIdEqualTo(item);
			List<RoleModel> list = roleModelMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				RoleModel model = list.get(0);
				model.setIsDeleted((byte) 1);
				roleModelMapper.updateByPrimaryKey(model);
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}
	
	@ApiOperation(value = "查询当前账号拥有的模块和权限", notes = "查询当前用户拥有的模块")
	@RequestMapping(value = "/findAdminHasModelAndJus", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findAdminHasModelAndJus(Integer id) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		List<Map> result = new ArrayList<Map>();
		Integer[] a = {0};
		AccountRolesExample example = new AccountRolesExample();
		example.createCriteria().andAccountIdEqualTo(id);
		List<AccountRoles> roles = accountRolesMapper.selectByExample(example);
		List<Integer> roleId = roles.stream().map(AccountRoles::getRolesId).collect(Collectors.toList());
		RoleModelExample example2 = new RoleModelExample();
		example2.createCriteria().andRoleIdIn(roleId);
		List<RoleModel> models = roleModelMapper.selectByExample(example2);
		List<Integer> modelId = models.size()==0 ?Lists.newArrayList(a):models.stream().map(RoleModel::getModelId).collect(Collectors.toList());
		HfModuleExample example3 = new HfModuleExample();
		example3.createCriteria().andIdIn(modelId);
		List<HfModule> list = hfModuleMapper.selectByExample(example3);
		RoleJurisdictionExample example4 = new RoleJurisdictionExample();
		example4.createCriteria().andRoleIdIn(roleId);
		List<RoleJurisdiction> jurisdictions = roleJurisdictionMapper.selectByExample(example4);
		List<Integer> jurisdictionId = jurisdictions.size()==0 ? Lists.newArrayList(a):jurisdictions.stream()
				.map(RoleJurisdiction::getJurisdictionId).collect(Collectors.toList());
		JurisdictionExample example5 = new JurisdictionExample();
		List<Jurisdiction> list2 = new ArrayList<Jurisdiction>();
		for (int i = 0; i < list.size(); i++) {
			Map<Object, Object> map = new HashMap<Object, Object>();
			map.clear();
			HfModule hfModule = list.get(i);
			example5.clear();
			example5.createCriteria().andModelIdEqualTo(hfModule.getId()).andIdIn(jurisdictionId);
			list2 = jurisdictionMapper.selectByExample(example5);
			map.put("jurisdictionName", hfModule.getHfModel());
			map.put("id", hfModule.getId());
			map.put("list", list2);
			result.add(map);
		}
		return builder.body(ResponseUtils.getResponseBody(result));
	}
	
	@ApiOperation(value = "查询当前账号拥有的模块", notes = "查询当前用户拥有的模块")
	@RequestMapping(value = "/findAdminHasModel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findAdminHasModel(Integer id,Integer rId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		AccountRolesExample example = new AccountRolesExample();
		example.createCriteria().andAccountIdEqualTo(id);
		List<AccountRoles> roles = accountRolesMapper.selectByExample(example);
		List<Integer> roleId = roles.stream().map(AccountRoles::getRolesId).collect(Collectors.toList());
		RoleModelExample example2 = new RoleModelExample();
		example2.createCriteria().andRoleIdIn(roleId);
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
			if(!CollectionUtils.isEmpty(modelId)) {
				example3.createCriteria().andIdIn(modelId);
				List<HfModule> result = hfModuleMapper.selectByExample(example3);
				return builder.body(ResponseUtils.getResponseBody(result));
			}else {
				return builder.body(ResponseUtils.getResponseBody(null));
			}
			
		}
		
	}
	
	@ApiOperation(value = "查询当前账号在某模块拥有的权限", notes = "查询当前账号在某模块拥有的权限")
	@RequestMapping(value = "/findAdminHasJusInModel", method = RequestMethod.GET)
	public ResponseEntity<JSONObject> findAdminHasJusInModel(Integer id,Integer modelId,Integer rId) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		AccountRolesExample example = new AccountRolesExample();
		example.createCriteria().andAccountIdEqualTo(id);
		List<AccountRoles> roles = accountRolesMapper.selectByExample(example);
		List<Integer> roleId = roles.stream().map(AccountRoles::getRolesId).collect(Collectors.toList());
		RoleJurisdictionExample example2 = new RoleJurisdictionExample();
		example2.createCriteria().andRoleIdIn(roleId);
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
			}else {
				AccountRoles role = list.get(0);
				if(role.getIsDeleted() == (short) 1) {
					role.setIsDeleted((short) 0);
					accountRolesMapper.updateByPrimaryKey(role);
				}
			}
		}
		return builder.body(ResponseUtils.getResponseBody(0));
	}

	@ApiOperation(value = "删除用户角色", notes = "删除用户角色")
	@RequestMapping(value = "/deleteUserRole", method = RequestMethod.POST)
	public ResponseEntity<JSONObject> deleteUserRole(Integer roleId[], Integer id) throws JSONException {
		ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
		for (Integer item : roleId) {
			AccountRolesExample example = new AccountRolesExample();
			example.createCriteria().andRolesIdEqualTo(item).andAccountIdEqualTo(id);
			List<AccountRoles> list = accountRolesMapper.selectByExample(example);
			if (!CollectionUtils.isEmpty(list)) {
				AccountRoles role = list.get(0);
				role.setIsDeleted((short) 1);
				accountRolesMapper.updateByPrimaryKey(role);
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
    		roles.setRoleCode("sass");
    		roles.setRoleType("boss");
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
