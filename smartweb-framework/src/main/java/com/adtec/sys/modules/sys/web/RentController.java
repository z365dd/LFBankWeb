/**
 *
 */
package com.adtec.sys.modules.sys.web;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.adtec.comm.protocol.httpjson.HttpJsonFactory;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.ms.msagent.entity.TenanciesData;
import com.adtec.ms.msagent.util.MapKey;
import com.adtec.ms.msagent.util.ZkNode;
import com.adtec.sys.common.persistence.Page;
import com.adtec.sys.common.utils.excel.ImportExcel;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Dict;
import com.adtec.sys.modules.sys.entity.FileDO;
import com.adtec.sys.modules.sys.entity.Office;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.entity.UserTntDO;
import com.adtec.sys.modules.sys.service.FileService;
import com.adtec.sys.modules.sys.service.PermissionService;
import com.adtec.sys.modules.sys.service.RentService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.service.UserTntService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.adtec.sys.modules.sys.vo.PermissionDTO;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

/**
 * 租户Controller
 *
 * @author chenyl
 * @date 20170425
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/rent")
public class RentController extends BaseController {

	private final SystemService systemService;
	private final RentService rentService;
	private final PermissionService permissionService;
    @Autowired
    private FileService fileService;
    @Autowired
    private UserTntService userTntService;

	public RentController(SystemService systemService, RentService rentService, PermissionService permissionService) {
		this.systemService = systemService;
		this.rentService = rentService;
		this.permissionService = permissionService;
	}

	@ModelAttribute("rent")
	public Rent get(@RequestParam(required = false) String id) {
		if (StringUtil.isNotBlank(id)) {
			return systemService.getRent(id);
		} else {
			return new Rent();
		}
	}

	/* 获取当前用户下的所有租户列表 */
	// @RequiresPermissions("sys:rent:view")
	@RequestMapping(value = { "list", "" })
	public String list(Rent rent, Model model) {
		List<Rent> list = systemService.findAllRent();
		if(list !=null && list.size() > 0){
			
		}
		model.addAttribute("list", list);
		if(rent.getId() == null || StringUtil.isBlank(rent.getId())){
			User user = UserUtils.getUser();
			rent.setId(user.getRent().getId());
			model.addAttribute("rent", rent);
		}
		return "modules/sys/rentList";
	}

	/* 查看租户信息 */
	// @RequiresPermissions("sys:rent:view")
	@RequestMapping(value = "form")
	public String form(Rent rent, Model model) {
		rent.setParent(rentService.get(rent.getParent().getId()));
		if(DataUtil.isNullStr(rent.getId())){
			model.addAttribute("isUpdate", false);
		}else{
			model.addAttribute("isUpdate", true);
		}

		Object msMallRentService = null;
		try {
			msMallRentService = SpringContextHolder.getBean("msMallRentService");
		} catch (Exception e) {
			logger.warn("该版本中不包含资产库模块！");
		}
		if (null == msMallRentService) {
			model.addAttribute("hasMsMall", "NO");
		} else {
			model.addAttribute("hasMsMall", "YES");
            String imgId = rent.getBgImg();
            String descId = rent.getTntDesc();
            FileDO fileDO = new FileDO();
            // 获取租户背景图片的访问路径
            fileDO = fileService.get(imgId);
            if (null != fileDO) {
                model.addAttribute("imgUrl", fileDO.getPrevPath());
            }
            // 解析租户简介内容
            fileDO = fileService.get(descId);
            if (null != fileDO) {
                model.addAttribute("description", FileUtil.readTxtFileByPath(ParamUtil.getUploadFile() + fileDO.getSavePath()));
            }
		}
		String allowMultiLevelRent = ParamUtil.getConfig("allowMultiLevelRent");
		model.addAttribute("allowMultiLevelRent", allowMultiLevelRent);
		model.addAttribute("rent", rent);
		return "modules/sys/rentForm";
	}

	// @RequiresPermissions("sys:rent:view")
	@RequestMapping(value = "detail")
	public String detail(Rent rent, Model model) {
		User user = UserUtils.getUser();
		rent.setParent(rentService.get(rent.getParent().getId()));
		rent = rentService.get(rent);

		Object msMallRentService = null;
		try {
			msMallRentService = SpringContextHolder.getBean("msMallRentService");
		} catch (Exception e) {
			logger.warn("该版本中不包含资产库模块！");
		}
		if (null == msMallRentService) {
			model.addAttribute("hasMsMall", "NO");
		} else {
			model.addAttribute("hasMsMall", "YES");
			String imgId = rent.getBgImg();
			String descId = rent.getTntDesc();
			FileDO fileDO = new FileDO();
			// 获取租户背景图片的访问路径
			fileDO = fileService.get(imgId);
			if (null != fileDO) {
				model.addAttribute("imgUrl", fileDO.getPrevPath());
			}
			// 解析租户简介内容
			fileDO = fileService.get(descId);
			if (null != fileDO) {
				model.addAttribute("description", FileUtil.readTxtFileByPath(ParamUtil.getUploadFile() + fileDO.getSavePath()));
			}
		}

		model.addAttribute("rent", rent);
		return "modules/sys/rentDetail";
	}

	/* 编辑租户信息 */
	@RequiresPermissions("sys:rent:edit")
	@RequestMapping(value = "save")
	public String save(HttpServletRequest request, Rent rent, Model model, RedirectAttributes redirectAttributes) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		String description = reqDs.getString("description");
		rent.setTntDesc(description);
		if (ParamUtil.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/rent/?repage";
		}
		if (!beanValidator(model, rent)) {
			return form(rent, model);
		}
		if (!"true".equals(checkName(rent.getOldName(), rent.getName()))) {
			addMessage(model, "保存租户'" + rent.getName() + "'失败, 租户名已存在");
			return form(rent, model);
		}
		if (!"true".equals(checkEngName(rent.getOldEngName(), rent.getEngName()))) {
			addMessage(model, "保存租户'" + rent.getName() + "'失败, 英文名已存在");
			return form(rent, model);
		}
		
		//判断是否运行添加多层级租户，为了兼容历史数据，修改时可以保存
		String allowMultiLevelRent = ParamUtil.getConfig("allowMultiLevelRent");
		if("N".equals(allowMultiLevelRent) && !StringUtil.isBlank(rent.getParentId()) && StringUtil.isBlank(rent.getId())){
			Rent parent = rentService.get(rent.getParentId());
			if(!"head".equals(parent.getEngName())){
				model.addAttribute("isUpdate", false);
				addMessage(model, "保存租户'" + rent.getName() + "'失败, 非顶级租户下不允许添加下级租户");
				return form(rent, model);
			}
		}

		try {
			rentService.save(rent);
			rentService.syncStoreEnv(rent);
		} catch (Exception e) {
			logger.error("保存失败", e);
			addMessage(model, "保存失败，" + e.getMessage());
			return form(rent, model);
		}

		if (StringUtil.isBlank(rent.getId())) { // 新增租户的处理
			try {
				systemService.regTenant(rent);
			} catch (Exception e) {
				logger.error(e.getMessage());
				addMessage(model, "保存租户'" + rent.getName() + "'失败," + e.getMessage());
				return form(rent, model);
			}
		} else {
			try {
				systemService.uptTenant(rent);
			} catch (Exception e) {
				logger.error(e.getMessage());
				addMessage(model, "更新租户'" + rent.getName() + "'失败," + e.getMessage());
				return form(rent, model);
			}
		}

		addMessage(redirectAttributes, "保存租户'" + rent.getName() + "'成功");
		return "redirect:" + adminPath + "/sys/rent/?repage";
	}

	/* 删除租户信息 */
	@RequiresPermissions("sys:rent:edit")
	@RequestMapping(value = "delete")
	public String delete(Rent rent, RedirectAttributes redirectAttributes) {
		if (ParamUtil.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/rent/?repage";
		}
		List<User> list = systemService.findUserByTntId(rent);
		if(list != null && list.size() > 0){
			addMessage(redirectAttributes, "租户已被使用，不能删除！");
			return "redirect:" + adminPath + "/sys/rent/?repage";
		}
		// 非管理员的情况下不可以删除自己所有的租户信息
		if (!UserUtils.getUser().isManager()) {
			addMessage(redirectAttributes, "删除租户失败, 非管理员不能删除租户信息");
		} else {
			systemService.deleteRent(rent);
			// TODO 删除租户时将删除租户下的所有功能节点
			addMessage(redirectAttributes, "删除租户成功");
		}
		return "redirect:" + adminPath + "/sys/rent/?repage";
	}

	/**
	 * 租户分配 -- 根据部门编号获取用户列表
	 *
	 * @param brchId
	 * @param response
	 * @return
	 */
	// @RequiresPermissions("sys:rent:view")
	@ResponseBody
	@RequestMapping(value = "users")
	public List<Map<String, Object>> users(String brchId, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User user = new User();
		user.setOffice(new Office(brchId));
		Page<User> page = systemService.findUser(new Page<User>(1, -1), user);
		for (User e : page.getList()) {
			Map<String, Object> map = Maps.newHashMap();
			map.put("id", e.getId());
			map.put("pId", 0);
			map.put("name", e.getName());
			mapList.add(map);
		}
		return mapList;
	}

	/**
	 * 验证租户名是否有效
	 *
	 * @param oldName
	 * @param name
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkName")
	public String checkName(String oldName, String name) {
		if (name != null && name.equals(oldName)) {
			return "true";
		} else if (name != null && systemService.getRentByName(name) == null) {
			return "true";
		}
		return "false";
	}

	/**
	 * 验证租户英文名是否有效
	 *
	 * @param oldEngName
	 * @param engName
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "checkEngName")
	public String checkEngName(String oldEngName, String engName) {
		if (engName != null && engName.equals(oldEngName)) {
			return "true";
		}else if (engName != null){
			// 中文正则匹配表达式
			// String pattern = "[\u4e00-\u9fa5]+";
			// 数字、26个英文字母或者下划线
			String pattern = "^\\w+$";

			boolean isMatch =  Pattern.matches(pattern, engName);
			if (!isMatch) {
				return "false";
			} else if (systemService.getRentByEngName(engName) == null) {
				return "true";
			}
		}
		return "false";
	}

	/* 获取当前用户下的所有租户列表 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "selectData")
	public void getSelectJson(HttpServletRequest request, HttpServletResponse response) {
		logger.info("getSelectJson");
		List<Rent> list = systemService.findAllRent();
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", list);
		renderString(response, m);
	}
	
	/* 获取当前用户下的所有租户列表 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "allSelectRent")
	public void allSelectRent(HttpServletRequest request, HttpServletResponse response) {
		List<Dict> dl = new ArrayList<Dict>();
		List<Rent> list = systemService.findAllRent();
		for (Rent rent : list) {
			Dict d = new Dict();
			d.setLabel(rent.getName());
			d.setValue(rent.getId());
			dl.add(d);
		}
		Map<String, Object> m = new HashMap<String, Object>();
		m.put("retCode", "0000");
		m.put("list", dl);
		renderString(response, m);
	}

	/* 跳转至批量导入界面 */
	// @RequiresPermissions("sys:rent:view")
	@RequestMapping(value = "importForm")
	public String importForm(Rent rent, Model model) {
		return "modules/sys/rentImport";
	}

	/**
	 * 租户模板下载
	 *
	 * @param request
	 * @param response
	 * @param redirectAttributes
	 */
	@RequestMapping(value = "template")
	public void importTemplate(HttpServletRequest request, HttpServletResponse response,
			RedirectAttributes redirectAttributes) {
		try {
			String uploadFilePath = ParamUtil.getConfig("uploadFile");
			String url = uploadFilePath + "/userfiles/ms/rent/template/rent_template.xlsx";
			String fileName = "租户导入模板.xlsx";
			FileUtil.DownLoadFileByUri(url, fileName, response);
		} catch (Exception e) {
			throw new BaseException(SysErr.E_MESSAGE, "租户导入模板下载失败！失败信息：" + e.getMessage());
		}
	}

	@ResponseBody
	@RequestMapping(value = "import", method = RequestMethod.POST)
	public void importFile(HttpServletRequest request, HttpServletResponse response, MultipartFile file,
			RedirectAttributes redirectAttributes) {
		try {
			IDataset responseData = DatasetService.getInstace().getDataset();
			String errorNo = SysErr.E_SUCCESS;
			if (ParamUtil.isDemoMode()) {
				setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, "演示模式，不允许操作！");
				return;
			}
			//判断是否运行添加多层级租户
			String allowMultiLevelRent = ParamUtil.getConfig("allowMultiLevelRent");
			if ("N".equals(allowMultiLevelRent)) {
				Rent r = UserUtils.getUser().getRent();
				//配置为N时，非顶级租户不允许导入
				if(!"head".equals(r.getEngName())){
					setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, "导入失败, 非顶级租户下不允许添加下级租户！");
					return;
				}
			}
			if (null != file) {
				ImportExcel ei = new ImportExcel(file, 1, 0);
				List<Rent> list = ei.getDataList(Rent.class);

				logger.info("导入租户数量：" + list.size());
				Rent root = rentService.get("1");
				for (Rent temp : list) {
					logger.info("租户名称：" + temp.getName());
					logger.info("租户英文名：" + temp.getEngName());
					String pattern = "^\\w+$";
					boolean isMatch =  Pattern.matches(pattern, temp.getEngName());
					if (!isMatch) {
						throw new BaseException(SysErr.E_MESSAGE, "导入文件格式错误");
					}
					temp.setStat("Y");
					if (temp.getParent() == null) {
						temp.setParent(root);
					}
				}

				StringBuffer msg = new StringBuffer();
				// 发起租户批量注册
				String succIdx = systemService.regMultiTenant(list);
				String[] succIdxArr = null;
				boolean success = false;
				// 调用批量新增租户时返回值为空则表示全部成功，如返回有值则表示部分成功
				if (succIdx == null) {
					success = true;
				} else {
					success = false;
					succIdxArr = succIdx.split(",", -1);
				}
				int currIdx = 0;
				for (int i = 0; i < list.size(); i++) {
					Rent rent = list.get(i);
//					Office office = officeList.get(i);
					if (succIdxArr != null) {
						success = false;
						// 判断当前租户是否已注册成功，是则插入租户表记录，否则拼装错误信息返回至前端显示
						for (int j = currIdx; j < succIdxArr.length; j++) {
							if (succIdxArr != null && !"".equals(succIdxArr[j])) {
								if (i == Integer.parseInt(succIdxArr[j])) {
									success = true;
									currIdx = j + 1;
									break;
								}
							}
						}
					}

					if (success) {
						Rent r = UserUtils.getUser().getRent();
						rent.setParentIdList(r.getParentIdList() + r.getId() + ",");
						rent.setParent(r);
						rent.setStat(rent.getStat().substring(0,1));
						if ("false".equals(checkName("", rent.getName()))) {
							throw new BaseException(SysErr.E_MESSAGE, "租户名称"+rent.getName()+"重复");
						}
						if ("false".equals(checkEngName("", rent.getEngName()))) {
							throw new BaseException(SysErr.E_MESSAGE, "租户英文名"+rent.getEngName()+"重复");
						}
						systemService.saveRent(rent);
						System.out.println("保存租户'" + rent.getName() + "'成功");
					} else {
						msg.append(String.format("租户号[%s]新增失败;", rent.getEngName()));
					}
				}

				// 删除租户列表缓存
				UserUtils.removeCache(UserUtils.CACHE_RENT_LIST);
				setResponseDataset(request, response, responseData, errorNo, "导入成功");
			} else {
				setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, "导入文件不能为空");
			}
		} catch (Exception e) {
			IDataset responseData = DatasetService.getInstace().getDataset();
			setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, "导入租户失败！失败信息:" + e.getMessage());
		}
	}

	/**
	 * 获取租户JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "treeData")
	public List<Map<String, Object>> treeData(@RequestParam(required=false) String extId, @RequestParam(required=false) String type,
											  @RequestParam(required=false) Boolean isAll, HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		List<Rent> list = rentService.findAll();
		for (Rent e : list) {
			if ((StringUtil.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIdList().indexOf("," + extId + ",") == -1))
//					&& (type == null || (type != null && (type.equals("1") ? type.equals(e.getType()) : true)))
					&& "Y".equals(e.getStat())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIdList());
				map.put("name", e.getName());
//				if (type != null && ("3".equals(type) || "4".equals(type))) {
//					map.put("isParent", true);
//				}
				// 对于只获取租户的，则只需要总行节点对应的父节点为0
//				if (null != type && "4".equals(type) && "0".equals(e.getParentId())) {
//					mapList.add(map);
//					break;
//				} else {
					mapList.add(map);
//				}
			}
		}
		return mapList;
	}
	
	/**
	 * 获取租户JSON数据。
	 * @param extId 排除的ID
	 * @param type	类型
	 * @param response
	 * @return
	 */
	@RequiresPermissions("user")
	@ResponseBody
	@RequestMapping(value = "getUserTntData")
	public List<Map<String, Object>> getUserTntData(@RequestParam(required = false) String extId,
			@RequestParam(required = false) String type, @RequestParam(required = false) Boolean isAll,
			HttpServletResponse response) {
		List<Map<String, Object>> mapList = Lists.newArrayList();
		User u = UserUtils.getUser();
		List<Rent> list = Lists.newArrayList();
		if(u != null){
			if(u.isAdmin()){
				list = rentService.findAll();
			}else{
				UserTntDO userTntDO = new UserTntDO();
				userTntDO.setUserId(u.getId());
				List<UserTntDO> userTntList = userTntService.list(userTntDO);
				for (UserTntDO userTnt : userTntList) {
					Rent rent = systemService.getRent(userTnt.getTntId());
					if(rent != null){
						list.add(rent);
					}
				}
			}
		}
		for (Rent e : list) {
			if ((StringUtil.isBlank(extId) || (extId != null && !extId.equals(e.getId()) && e.getParentIdList().indexOf("," + extId + ",") == -1))
					&& "Y".equals(e.getStat())) {
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("pIds", e.getParentIdList());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
		return mapList;
	}	

	@RequestMapping("seePermissionToRent")
	public String seePermissionToRent(String id, Model model) {
		PermissionDTO permissionOwn = permissionService.assembleRentVoByAuthTp(id, "own");
		model.addAttribute("permissionOwn", permissionOwn);
		PermissionDTO permissionTransfer = permissionService.assembleRentVoByAuthTp(id, "transfer");
		model.addAttribute("permissionTransfer", permissionTransfer);
		PermissionDTO permissionUse = permissionService.assembleRentVoByAuthTp(id, "use");
		model.addAttribute("permissionUse", permissionUse);
		return "modules/sys/seePermissionToRent";
	}

	@RequestMapping("assignPermissionToRent")
	public String assignPermissionToRent(String id, String permissionType, Model model) {
		PermissionDTO permissionDTO = permissionService.assembleRentVo(id, permissionType);
		model.addAttribute("permissionDTO", permissionDTO);
		return "modules/sys/assignPermissionToRent";
	}

	@RequestMapping(value = "savePermissionToRent")
	public String savePermissionToRent(PermissionDTO permissionDTO, RedirectAttributes redirectAttributes) {
		if (ParamUtil.isDemoMode()) {
			addMessage(redirectAttributes, "演示模式，不允许操作！");
			return "redirect:" + adminPath + "/sys/rent/list";
		}
		int resultCount = permissionService.savePermissionToRent(permissionDTO);
		if (resultCount > 0) {
			addMessage(redirectAttributes, "分配权限组成功");
		} else {
			addMessage(redirectAttributes, "分配权限组失败");
		}
		User user = UserUtils.getUser();
		String id = user.getRent().getId();
		return "redirect:" + adminPath + "/sys/rent/list?id=" + id + "&parentIdList=";
	}

	/**
	 * 租户的节点信息查询
	 * @param request
	 * @param response
	 */
	@ResponseBody
	@RequestMapping(value="zkinfo")
	public void zkinfo(HttpServletRequest request, HttpServletResponse response) {
		IDataset reqDs = DatasetService.getInstace().getDataset(request);
		try {
			Object partService = SpringContextHolder.getBean("partService");
		} catch (Exception e) {
			logger.warn("该版本中不包含微服务模块！");
			throw new BaseException(SysErr.E_MESSAGE, "该版本中不包含微服务模块！");
		}

		try {
			HttpJsonFactory httpJsonFactory = HttpJsonFactory.getInstance();
			Map<String, Object> reqMap = Maps.newHashMap();
			StringBuffer zkpath = new StringBuffer();
			zkpath.append(ZkNode.TENANCIES).append("/").append(reqDs.getString("ename"));
			reqMap.put("zkpath", zkpath.toString());
			Map<String, Object> resMap = httpJsonFactory.sendRegHttpJson("/part/zkinfo", reqMap);
			if (!"SUCCESS".equals(resMap.get(MapKey.RETCODE))) {
				throw new BaseException(SysErr.E_MESSAGE, resMap.get(MapKey.MSG));
			}

			String jsonString = JSON.toJSONString(JSONObject.parseObject((String) resMap.get("jsonString")), SerializerFeature.PrettyFormat);

			TenanciesData tenanciesData = new TenanciesData();
			tenanciesData.setName(zkpath.toString());
			tenanciesData.setStatus(jsonString);
			IDataset resDs = DatasetService.getInstace().getDataset(tenanciesData, TenanciesData.class);
			setResponseDataset(request, response, resDs, SysErr.E_SUCCESS, "查询节点信息成功！");

		} catch (Exception e) {
			logger.error("查询节点信息异常！", e);
			throw new BaseException(SysErr.E_MESSAGE, "查询节点信息异常！");
		}
	}
	
	/**
	 * 切换租户
	 * 
	 * @param rentId
	 * @param model
	 * @return
	 */
	@RequiresPermissions("user")
	@RequestMapping(value = "switchRent")
	public String switchRent(String rentId, Model model) {
		User user = UserUtils.getUser();
		if (StringUtil.isNotBlank(rentId)) {
			Rent rent = rentService.get(rentId);
			if (rent != null) {
				user.setRent(rent);
				UserUtils.refreshUserCache(user);
				model.addAttribute("message", "切换租户成功");
			} else {
				model.addAttribute("message", "切换租户失败，该租户不存在");
			}
		}
		return "redirect:" + adminPath;
	}

}
