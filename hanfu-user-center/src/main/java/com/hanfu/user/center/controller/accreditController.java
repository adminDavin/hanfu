package com.hanfu.user.center.controller;

import com.alibaba.fastjson.JSONObject;
import com.hanfu.common.service.FileMangeService;
import com.hanfu.user.center.config.PermissionConstants;
import com.hanfu.user.center.dao.*;
import com.hanfu.user.center.manual.dao.AuthMapper;
import com.hanfu.user.center.manual.dao.UserAddressMapper;
import com.hanfu.user.center.manual.model.AuthorizationUserAddress;
import com.hanfu.user.center.model.*;
import com.hanfu.user.center.service.AuthorizationUserService;
import com.hanfu.user.center.service.RequiredPermission;
import com.hanfu.utils.response.handler.ResponseEntity;
import com.hanfu.utils.response.handler.ResponseUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * 皓月千里
 *
 * @param
 * @return
 */
@RestController
@Api
@RequestMapping("/accredit")
@CrossOrigin
public class accreditController {
    @Autowired
    private AuthorizationMapper authorizationMapper;
    @Autowired
    private FileMapper fileMapper;
    @Autowired
    private RoleMapper roleMapper;
    @Autowired
    private UserRoleMapper userRoleMapper;
    @Autowired
    private JurisdictionMapper jurisdictionMapper;
    @Autowired
    private RoleJurisdictionMapper roleJurisdictionMapper;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private AuthorizationUserService authorizationUserService;
    @Autowired
    private UserAddressMapper userAddressMapper;
    @Autowired
    private AuthMapper authMapper;
    @RequiredPermission(PermissionConstants.ADMIN_PRODUCT_LIST)
    @RequestMapping(path = "/insertTest", method = RequestMethod.GET)
    @ApiOperation(value = "insertTest", notes = "insertTest")
    public ResponseEntity<JSONObject> insertTest(Authorization authorization, HttpServletResponse response, HttpServletRequest request) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        authorizationMapper.insert(authorization);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(path = "/selectJurisdiction", method = RequestMethod.GET)
    @ApiOperation(value = "权限查询", notes = "权限查询")
    public ResponseEntity<JSONObject> selectJurisdiction() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(jurisdictionMapper.selectAll()));
    }
    @RequestMapping(path = "/deleteRoleJurisdiction", method = RequestMethod.GET)
    @ApiOperation(value = "角色权限删除", notes = "角色权限删除")
    public ResponseEntity<JSONObject> deleteRoleJurisdiction(Integer RoleId,Integer JurisdictionId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example = new Example(RoleJurisdiction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",RoleId).andEqualTo("jurisdictionId",JurisdictionId);
        roleJurisdictionMapper.deleteByExample(example);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }

    @RequestMapping(path = "/insertRoleJurisdiction", method = RequestMethod.GET)
    @ApiOperation(value = "角色添加权限", notes = "角色添加权限")
    public ResponseEntity<JSONObject> insertRoleJurisdiction(Integer RoleId,Integer JurisdictionId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example = new Example(RoleJurisdiction.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("roleId",RoleId).andEqualTo("jurisdictionId",JurisdictionId);
        if (roleJurisdictionMapper.selectByExample(example).size()!=0){
            return builder.body(ResponseUtils.getResponseBody("此角色已有该权限"));
        }
        RoleJurisdiction rolejurisdiction = new RoleJurisdiction();
        rolejurisdiction.setIsDeleted((short) 0);
        rolejurisdiction.setModifyTime(LocalDateTime.now());
        rolejurisdiction.setCreateTime(LocalDateTime.now());
        rolejurisdiction.setRoleId(RoleId);
        rolejurisdiction.setJurisdictionId(JurisdictionId);
        roleJurisdictionMapper.insert(rolejurisdiction);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(path = "/selectAccreditRole", method = RequestMethod.GET)
    @ApiOperation(value = "员工的角色查询", notes = "员工的角色查询")
    public ResponseEntity<JSONObject> selectAccreditRole(Integer UserId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",UserId);
        List<UserRole> userRole= userRoleMapper.selectByExample(example);
        List<Role> roleList = new ArrayList();
        if (userRole.size()==0){
            return builder.body(ResponseUtils.getResponseBody("没有对应的角色"));
        }
        for (int i=0;userRole.size()>i;i++){
            int roleId=userRole.get(i).getRoleId();
            Role role=roleMapper.selectByPrimaryKey(roleId);
            roleList.add(role);
        }
        return builder.body(ResponseUtils.getResponseBody(roleList));
    }
    @RequiredPermission(PermissionConstants.ADMIN_PRODUCT_LIST)
    @RequestMapping(path = "/selectAccredit", method = RequestMethod.GET)
    @ApiOperation(value = "员工查询", notes = "员工查询")
    public ResponseEntity<JSONObject> selectAccredit() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(authorizationUserService.selectAuthorizationUser()));
    }
    @RequestMapping(path = "/deleteAccreditRole", method = RequestMethod.GET)
    @ApiOperation(value = "员工的角色删除", notes = "员工的角色删除")
    public ResponseEntity<JSONObject> deleteAccreditRole(Integer UserId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example1 = new Example(UserRole.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId",UserId);
        if (userRoleMapper.selectByExample(example1).size()==0){
            return builder.body(ResponseUtils.getResponseBody("此用户没有角色"));
        }
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",UserId);
        userRoleMapper.deleteByExample(example);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(path = "/updateAccreditRole", method = RequestMethod.GET)
    @ApiOperation(value = "员工的角色修改", notes = "员工的角色修改")
    public ResponseEntity<JSONObject> updateAccreditRole(Integer Userid,Integer roleId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example1 = new Example(UserRole.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId",Userid);
        if (userRoleMapper.selectByExample(example1).size()==0){
            return builder.body(ResponseUtils.getResponseBody("用户没有角色"));
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(Userid);
        userRole.setRoleId(roleId);
        userRole.setIsDeleted((short) 0);
        userRole.setModifyTime(LocalDateTime.now());
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",Userid);
        userRoleMapper.updateByExampleSelective(userRole,example);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(path = "/insertAccreditRole", method = RequestMethod.GET)
    @ApiOperation(value = "员工的角色添加", notes = "员工的角色添加")
    public ResponseEntity<JSONObject> insertAccreditRole(Integer UserId,Integer roleId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example = new Example(UserRole.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",UserId);
        if (userRoleMapper.selectByExample(example).size()!=0){
            return builder.body(ResponseUtils.getResponseBody("改用户已有角色"));
        }
        UserRole userRole = new UserRole();
        userRole.setUserId(UserId);
        userRole.setRoleId(roleId);
        userRole.setIsDeleted((short) 0);
        userRole.setCreateTime(LocalDateTime.now());
        userRole.setModifyTime(LocalDateTime.now());
        userRoleMapper.insert(userRole);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(path = "/selectRole", method = RequestMethod.GET)
    @ApiOperation(value = "角色查询", notes = "角色查询")
    public ResponseEntity<JSONObject> selectRole() throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        return builder.body(ResponseUtils.getResponseBody(roleMapper.selectAll()));
    }
    @RequestMapping(path = "/deleteRole", method = RequestMethod.GET)
    @ApiOperation(value = "角色删除", notes = "员工角色删除")
    public ResponseEntity<JSONObject> deleteRole(Integer roleId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        roleMapper.deleteByPrimaryKey(roleId);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(path = "/updateRole", method = RequestMethod.GET)
    @ApiOperation(value = "角色修改", notes = "角色修改")
    public ResponseEntity<JSONObject> updateRole(Role role) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Role role1 = new Role();
        role1.setId(role.getId());
        role1.setRoleName(role.getRoleName());
        role1.setModifyTime(LocalDateTime.now());
        roleMapper.updateByPrimaryKeySelective(role1);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }
    @RequestMapping(path = "/insertRole", method = RequestMethod.GET)
    @ApiOperation(value = "角色添加", notes = "员工角色添加")
    public ResponseEntity<JSONObject> insertRole(String RoleName,String creator) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Role role = new Role();
        role.setRoleName(RoleName);
        role.setCreateTime(LocalDateTime.now());
        role.setModifyTime(LocalDateTime.now());
        role.setIsDeleted((short) 0);
        role.setCreator(creator);
        roleMapper.insert(role);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }


    @RequestMapping(path = "/deleteAccredit", method = RequestMethod.GET)
    @ApiOperation(value = "员工删除", notes = "员工删除")
    public ResponseEntity<JSONObject> deleteAccredit(int UserId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        Example example = new Example(Authorization.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("userId",UserId);
        authorizationMapper.deleteByExample(example);
        Example example1 = new Example(UserRole.class);
        Example.Criteria criteria1 = example1.createCriteria();
        criteria1.andEqualTo("userId",UserId);
        userRoleMapper.deleteByExample(example1);
        userMapper.deleteByPrimaryKey(UserId);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }

    @RequestMapping(value = "/deleteBatchCancel", method = RequestMethod.GET)
    @ApiOperation(value = "批量删除", notes = "批量删除")
    public ResponseEntity<JSONObject> deleteBatchCancel(@RequestParam("UserId") List UserId) throws Exception {
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        System.out.println(UserId);
        if (UserId.size()== 0) {
            builder.body(ResponseUtils.getResponseBody("请选择"));
        }
        for (int i = 0; i < UserId.size(); i++) {
            int UserID = Integer.parseInt(UserId.get(i).toString());
            System.out.println(UserID);
            Example example = new Example(Authorization.class);
            Example.Criteria criteria = example.createCriteria();
            criteria.andEqualTo("userId",UserID);
            List<Authorization> authorization = authorizationMapper.selectByExample(example);
            System.out.println(authorization+"authorization");
            authorizationMapper.deleteByExample(example);
            Example example1 = new Example(UserRole.class);
            Example.Criteria criteria1 = example1.createCriteria();
            criteria1.andEqualTo("userId",UserID);
            userRoleMapper.deleteByExample(example1);
            userMapper.deleteByPrimaryKey(UserID);
        }
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }


    @RequestMapping(path = "/insertAccredit", method = RequestMethod.POST)
    @ApiOperation(value = "新增人员", notes = "新增人员")
    public ResponseEntity<JSONObject> insertAccredit(AuthorizationUserAddress authorizationUserAddress, MultipartFile fileInfo) throws Exception {
//        System.out.println(file.getBytes());
        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
        String uuid = UUID.randomUUID().toString(); //转化为String对象
        System.out.println(uuid); //打印UUID
        uuid = uuid.replace("-", "");//因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
        System.out.println(uuid);
        Integer fileId = null;
        FileMangeService fileMangeService = new FileMangeService();
        String arr[];
        arr = fileMangeService.uploadFile(fileInfo.getBytes(),"-1");
        Example example = new Example(FileDesc.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("fileName",authorizationUserAddress.getId());
        List<FileDesc> list = fileMapper.selectByExample(example);
        System.out.println(list+"1231111111");
        FileDesc fileDesc = new FileDesc();
        fileDesc.setFileName(fileInfo.getName());
        fileDesc.setGroupName(arr[0]);
        fileDesc.setRemoteFilename(arr[1]);
        fileDesc.setUserId(authorizationUserAddress.getId());
        fileDesc.setCreateTime(LocalDateTime.now());
        fileDesc.setModifyTime(LocalDateTime.now());
        fileDesc.setIsDeleted((short) 0);
        fileMapper.insert(fileDesc);
        fileId = fileDesc.getId();
        System.out.println(fileDesc.getId()+"-------"+"1234567890");

//        else {
//            FileDesc fileDesc = list.get(0);
//            fileMangeService.deleteFile(fileDesc.getGroupName(),fileDesc.getRemoteFilename() );
//            fileDesc.setGroupName(arr[0]);
//            fileDesc.setRemoteFilename(arr[1]);
//            fileDesc.setModifyTime(LocalDateTime.now());
//            fileMapper.updateByPrimaryKey(fileDesc);
//            fileId = fileDesc.getId();
//        }
        HfUser hfUser = new HfUser();
        hfUser.setRealName(authorizationUserAddress.getRealName());
        hfUser.setNickName(authorizationUserAddress.getNickName());
        hfUser.setEmail(authorizationUserAddress.getEmail());
        hfUser.setFileId(fileId);
        hfUser.setPhone(authorizationUserAddress.getPhone());
        hfUser.setModifyDate(LocalDateTime.now());
        hfUser.setCreateDate(LocalDateTime.now());
        userMapper.insert(hfUser);
        System.out.println(hfUser.getId()+"77777777777777777777777");
        Authorization authorization = new Authorization();
        authorization.setCreateDate(LocalDateTime.now());
        authorization.setModifyDate(LocalDateTime.now());
        authorization.setUserId(hfUser.getId());
        authorization.setEmployeeCode(authorizationUserAddress.getEmployeeCode());
        authorization.setEmployeeDepartment(authorizationUserAddress.getEmployeeDepartment());
        authorization.setIdDeleted((byte) 0);
        authorization.setState((byte) 0);
        authorization.setEmployeeCode(authorizationUserAddress.getEmployeeCode());
        authorizationMapper.insert(authorization);
        HfUserAddress hfUserAddress = new HfUserAddress();
        hfUserAddress.setUserId(hfUser.getId());
        hfUserAddress.setCreateTime(LocalDateTime.now());
        hfUserAddress.setModifyTime(LocalDateTime.now());
        hfUserAddress.setHfAddressDetail(authorizationUserAddress.getHfAddressDetail());
        hfUserAddress.setContact(authorizationUserAddress.getConty());
        hfUserAddress.setIsDeleted((short) 0);
        hfUserAddress.setPhoneNumber(authorizationUserAddress.getPhone());
        hfUserAddress.setHfCity(authorizationUserAddress.getHfCity());
        hfUserAddress.setHfProvince(authorizationUserAddress.getHfProvince());
        userAddressMapper.insert(hfUserAddress);
        HfAuth hfAuth = new HfAuth();
        hfAuth.setAuthKey(authorizationUserAddress.getPhone());
        hfAuth.setUserId(hfUser.getId());
        hfAuth.setAuthType("2");
        hfAuth.setCreateDate(LocalDateTime.now());
        hfAuth.setModifyDate(LocalDateTime.now());
        hfAuth.setIdDeleted((byte) 0);
        authMapper.insert(hfAuth);
        return builder.body(ResponseUtils.getResponseBody("成功"));
    }

//    @RequestMapping(path = "/updateAccredit", method = RequestMethod.POST)
//    @ApiOperation(value = "修改人员", notes = "修改人员")
//    public ResponseEntity<JSONObject> updateAccredit(Authorization authorization, MultipartFile file) throws Exception {
//        System.out.println(file.getBytes());
//        ResponseEntity.BodyBuilder builder = ResponseUtils.getBodyBuilder();
//        String uuid = UUID.randomUUID().toString(); //转化为String对象
//        System.out.println(uuid); //打印UUID
//        uuid = uuid.replace("-", "");//因为UUID本身为32位只是生成时多了“-”，所以将它们去点就可
//        System.out.println(uuid);
//        FileMangeService fileMangeService = new FileMangeService();
//        String arr[];
//        arr = fileMangeService.uploadFile(file.getBytes(), uuid);
//        FileDesc fileDesc = new FileDesc();
//        fileDesc.setFileName(file.getName());
//        fileDesc.setGroupName(arr[0]);
//        fileDesc.setRemoteFilename(arr[1]);
//        fileDesc.setCreateTime(LocalDateTime.now());
//        fileDesc.setModifyTime(LocalDateTime.now());
//        fileDesc.setIsDeleted((short) 0);
//        fileMapper.insert(fileDesc);
//        int fileId=fileDesc.getId();
//        authorization.setId(authorization.getId());
//        authorization.setFileId(fileId);
//        authorization.setModifyDate(LocalDateTime.now());
//        authorizationMapper.updateByPrimaryKeySelective(authorization);
//        return builder.body(ResponseUtils.getResponseBody("成功"));
//    }
//    @RequestMapping(path = "/select", method = RequestMethod.GET)
//    @ApiOperation(value = "图片查询", notes = "图片查询")
//    public void select(int id, HttpServletResponse response) throws Exception {
//        Authorization authorization =authorizationMapper.selectByPrimaryKey(id);
//        System.out.println(authorization.getFileId());
//        FileDesc fileDesc = fileMapper.selectByPrimaryKey(authorization.getFileId());
//        System.out.println(fileDesc);
//        FileMangeService fileManageService = new FileMangeService();
//        byte[] file = fileManageService.downloadFile(fileDesc.getGroupName(), fileDesc.getRemoteFilename());
//        if (file != null) {
//            BufferedImage readImg = ImageIO.read(new ByteArrayInputStream(file));
//            ImageIO.write(readImg, "png", response.getOutputStream());
//        }
//    }
}