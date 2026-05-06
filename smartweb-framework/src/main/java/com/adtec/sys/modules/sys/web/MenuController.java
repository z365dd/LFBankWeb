package com.adtec.sys.modules.sys.web;

import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.SpringContextHolder;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.share.dataset.DatasetService;
import com.adtec.framework.interfaces.share.IDataset;
import com.adtec.sys.common.utils.excel.ImportExcel;
import com.adtec.sys.common.web.BaseController;
import com.adtec.sys.modules.sys.entity.Menu;
import com.adtec.sys.modules.sys.entity.User;
import com.adtec.sys.modules.sys.service.OfficeService;
import com.adtec.sys.modules.sys.service.SystemService;
import com.adtec.sys.modules.sys.utils.UserUtils;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 菜单Controller
 *
 * @version 2013-3-23
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/menu")
public class MenuController extends BaseController {

    private final SystemService systemService;
    private final OfficeService officeService;

    @Autowired
    public MenuController(SystemService systemService, OfficeService officeService) {
        this.systemService = systemService;
        this.officeService = officeService;
    }

    @ModelAttribute("menu")
    public Menu get(@RequestParam(required = false) String id) {
        if (StringUtil.isNotBlank(id)) {
            return systemService.getMenu(id);
        } else {
            return new Menu();
        }
    }

    // @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = {"list", ""})
    public String list(Model model) {
        List<Menu> list = Lists.newArrayList();
        List<Menu> sourcelist = systemService.findAllMenu();
        Menu.sortList(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("list", list);
        return "modules/sys/menuList";
    }

    @RequestMapping(value = {"listAuth"})
    public String listAuth(Model model) {
        List<Menu> list = Lists.newArrayList();
        List<Menu> sourcelist = systemService.findAllMenu();
        Menu.sortListAuth(list, sourcelist, Menu.getRootId(), true);
        model.addAttribute("listAuth", list);
        return "modules/sys/menuListAuth";
    }

    // @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = "formAuth")
    public String formAuth(Menu menu, Model model) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        menu.setParent(systemService.getMenu(menu.getParent().getId()));
        // 获取排序号，最末节点排序号+30
        if (StringUtil.isBlank(menu.getId())) {
            List<Menu> list = Lists.newArrayList();
            List<Menu> sourcelist = systemService.findAllMenu();
            Menu.sortList(list, sourcelist, menu.getParentId(), false);
            if (list.size() > 0) {
                menu.setSort(list.get(list.size() - 1).getSort() + 30);
            }
        }
        model.addAttribute("menuAuth", menu);
        return "modules/sys/menuFormAuth";
    }

    // @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = "form")
    public String form(Menu menu, Model model) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        menu.setParent(systemService.getMenu(menu.getParent().getId()));
        // 获取排序号，最末节点排序号+30
        if (StringUtil.isBlank(menu.getId())) {
            List<Menu> list = Lists.newArrayList();
            List<Menu> sourcelist = systemService.findAllMenu();
            Menu.sortList(list, sourcelist, menu.getParentId(), false);
            if (list.size() > 0) {
                menu.setSort(list.get(list.size() - 1).getSort() + 30);
            }
        }
        model.addAttribute("menu", menu);
        return "modules/sys/menuForm";
    }

    // @RequiresPermissions("sys:menu:view")
    @RequestMapping(value = "detail")
    public String detail(Menu menu, Model model) {
        if (menu.getParent() == null || menu.getParent().getId() == null) {
            menu.setParent(new Menu(Menu.getRootId()));
        }
        menu.setParent(systemService.getMenu(menu.getParent().getId()));
        // 获取排序号，最末节点排序号+30
        if (StringUtil.isBlank(menu.getId())) {
            List<Menu> list = Lists.newArrayList();
            List<Menu> sourcelist = systemService.findAllMenu();
            Menu.sortList(list, sourcelist, menu.getParentId(), false);
            if (list.size() > 0) {
                menu.setSort(list.get(list.size() - 1).getSort() + 30);
            }
        }
        model.addAttribute("menu", menu);
        return "modules/sys/menuDetail";
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "save")
    public String save(Menu menu, Model model, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/menu/list";
        }
        if (!beanValidator(model, menu)) {
            return form(menu, model);
        }

        boolean isAdd = false;
        if (StringUtil.isBlank(menu.getId())) {
            isAdd = true;
        }
        try {
            systemService.saveMenu(menu);
        } catch (Exception e) {
            if (isAdd) {
                menu.setId(null);
            }
            addMessage(model, "保存失败，" + e.getMessage());
            return form(menu, model);
        }

        addMessage(redirectAttributes, "保存菜单'" + menu.getName() + "'成功");
        return "redirect:" + adminPath + "/sys/menu/list";
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "saveAuth")
    public String saveAuth(Menu menu, Model model, RedirectAttributes redirectAttributes) {
        if (!beanValidator(model, menu)) {
            return formAuth(menu, model);
        }

        boolean isAdd = false;
        if (StringUtil.isBlank(menu.getId())) {
            isAdd = true;
        }
        try {
            systemService.saveMenuAuth(menu);
        } catch (Exception e) {
            if (isAdd) {
                menu.setId(null);
            }
            addMessage(model, "保存失败，" + e.getMessage());
            return formAuth(menu, model);
        }

        addMessage(redirectAttributes, "保存按钮'" + menu.getName() + "'成功");
        return "redirect:" + adminPath + "/sys/menu/listAuth";
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "delete")
    public String delete(Menu menu, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/menu/list";
        }
        systemService.deleteMenu(menu);
        addMessage(redirectAttributes, "删除菜单成功");
        return "redirect:" + adminPath + "/sys/menu/list";
    }

    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "deleteAuth")
    public String deleteAuth(Menu menu, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/menu/list";
        }
        systemService.deleteMenu(menu);
        addMessage(redirectAttributes, "删除按钮成功");
        return "redirect:" + adminPath + "/sys/menu/listAuth";
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "tree")
    public String tree(String parentId, Model model) {
        Map<String, Menu> menuMap = getMenuMap();
        Menu menu = menuMap.get(parentId);
        model.addAttribute("menu", menu);
        return "modules/sys/menuTree";
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "treeselect")
    public String treeselect(String parentId, Model model) {
        model.addAttribute("parentId", parentId);
        return "modules/sys/menuTreeselect";
    }

    /**
     * 批量修改菜单排序
     */
    @RequiresPermissions("sys:menu:edit")
    @RequestMapping(value = "updateSort")
    public String updateSort(String[] ids, Integer[] sorts, RedirectAttributes redirectAttributes) {
        if (ParamUtil.isDemoMode()) {
            addMessage(redirectAttributes, "演示模式，不允许操作！");
            return "redirect:" + adminPath + "/sys/menu/list";
        }
        for (int i = 0; i < ids.length; i++) {
            Menu menu = new Menu(ids[i]);
            menu.setSort(sorts[i]);
            systemService.updateMenuSort(menu);
        }
        addMessage(redirectAttributes, "保存菜单排序成功!");
        return "redirect:" + adminPath + "/sys/menu/list";
    }

    /**
     * isShowHide是否显示隐藏菜单
     *
     * @param extId
     * @param isShowHide
     * @param response
     * @return
     */
    @RequiresPermissions("user")
    @ResponseBody
    @RequestMapping(value = "treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String extId, @RequestParam(required = false) String isShowHide, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<Menu> list = systemService.findAllMenu();
		for (Menu e : list) {
			// 不是菜单根目录，不是菜单的权限组，则跳过循环
			if (!"1".equals(e.getId())) {
				User user = UserUtils.getUser();
				if (!user.getCorporation().getId().equals(e.getLegaId())
						|| !user.getRent().getId().equals(e.getRentid())
						|| !user.getOffice().getId().equals(e.getBrchId())
						|| !user.getRoleIdList().containsAll(e.getRoleId())) {
					continue;
				}
			}

			if (StringUtil.isBlank(extId) || !extId.equals(e.getId()) && !e.getParentIdList().contains("," + extId + ",")) {
				if (isShowHide != null && isShowHide.equals("0") && e.getDpyFlg().equals("0") && e.getParentIdList().split("\\,").length == 5) {
					continue;
				}
				Map<String, Object> map = Maps.newHashMap();
				map.put("id", e.getId());
				map.put("pId", e.getParentId());
				map.put("name", e.getName());
				mapList.add(map);
			}
		}
        return mapList;
    }

    @RequiresPermissions("user")
    @RequestMapping(value = "mainMenu")
    public String getMainMenu(Model model) {
        List<Menu> menuList = UserUtils.getMenuList();
        Map<String, List<Menu>> menuSort = Maps.newHashMap();
        List<Menu> mainMenu = Lists.newArrayList();

        //找出所有需要的一级菜单
        for (Menu menu : menuList) {
            if (menu.getParentId().equals("1") && menu.getDpyFlg().equals("1")) {
                mainMenu.add(menu);
            }
        }
        menuSort.put("mainMenu", mainMenu);

        List<Menu> list = Lists.newArrayList();
        //找出所有二级菜单
        for (Menu menu : mainMenu) {
            String menuId = menu.getId();
            List<Menu> secondMenu = Lists.newArrayList();
            for (Menu value : menuList) {
                if (menuId.equals(value.getParentId()) && value.getDpyFlg().equals("1")) {
                    secondMenu.add(value);
                    list.add(value);
                }
            }
            menuSort.put(menu.getId(), secondMenu);
        }

        //找出所有三级菜单
        for (Menu menu : list) {
            String menuId = menu.getId();
            List<Menu> thirdMenu = Lists.newArrayList();
            for (Menu value : menuList) {
                if (menuId.equals(value.getParentId()) && value.getDpyFlg().equals("1")) {
                    thirdMenu.add(value);
                }
            }
            menuSort.put(menu.getId(), thirdMenu);
        }
        model.addAttribute("menu", menuSort);
        return "modules/sys/mainMenu";
    }

    /**
     * 获取所有显示的菜单并以list集合形式返回
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "getSearchMenu")
    public List<Map<String, Object>> getSearchMenu() {
        List<Map<String, Object>> maps = Lists.newArrayList();

        List<Menu> menuList = UserUtils.getMenuList();
        List<Menu> mainMenu = Lists.newArrayList();

        //找出所有需要的一级菜单
        for (Menu menu : menuList) {
            if ((menu.getParentId().equals("1") && menu.getDpyFlg().equals("1")) || menu.getId().equals("1")) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("id", menu.getId());
                map.put("name", menu.getName());
                map.put("pId", menu.getParentId());
                map.put("pName", menu.getParent() != null ? menu.getParent().getName() : "");
                maps.add(map);
                if (!menu.getId().equals("1")) {
                    mainMenu.add(menu);
                }

            }
        }

        List<Menu> list = Lists.newArrayList();
        //找出所有二级菜单
        for (Menu menu : mainMenu) {
            String menuId = menu.getId();
            for (Menu value : menuList) {
                if (menuId.equals(value.getParentId()) && value.getDpyFlg().equals("1")) {
                    Map<String, Object> map = Maps.newHashMap();
                    map.put("id", value.getId());
                    map.put("name", value.getName());
                    map.put("pId", value.getParentId());
                    map.put("pName", value.getParent() != null ? value.getParent().getName() : "");
                    maps.add(map);
                    list.add(value);
                }
            }
        }

        //找出所有三级菜单
		for (Menu menu : list) {
			String menuId = menu.getId();
			for (Menu value : menuList) {
				if (menuId.equals(value.getParentId()) && value.getDpyFlg().equals("1")) {
					Map<String, Object> map = Maps.newHashMap();
					map.put("id", value.getId());
					map.put("name", value.getName());
					map.put("pId", value.getParentId());
					map.put("pName", value.getParent() != null ? value.getParent().getName() : "");
					maps.add(map);
				}
			}
		}
        return maps;
    }

    @ResponseBody
    @RequestMapping(value = "getMenuPath")
    public String addQuickEntry(String menuId, String menuName, String pId, String pName) {
        SystemService systemService = SpringContextHolder.getBean("systemService");
        Menu pMenu = systemService.getMenu(pId);
        Menu gMenu = systemService.getMenu(pMenu.getParentId());
        String path = "";
        if (gMenu != null) {
            path = gMenu.getName() + "->" + pName + "->" + menuName;
        }

        return path;
    }


    //获取菜单
    public Map<String, Menu> getMenuMap() {
        List<Menu> menuList = UserUtils.getMenuList();
        Map<String, Menu> menuSort = Maps.newHashMap();
        List<Menu> firstMenu = Lists.newArrayList();

        //找出所有需要的一级菜单
        for (Menu menu : menuList) {
            if (menu.getParentId().equals("1") && menu.getDpyFlg().equals("1")) {
                menuSort.put(menu.getId(), menu);
                firstMenu.add(menu);
            }
        }

        List<Menu> list = Lists.newArrayList();
        //找出所有二级菜单
        for (int i = 0; i < firstMenu.size(); i++) {
            String menuId = firstMenu.get(i).getId();
            List<Menu> secondMenu = Lists.newArrayList();
            for (int j = 0; j < menuList.size(); j++) {
                if (menuId.equals(menuList.get(j).getParentId()) && menuList.get(j).getDpyFlg().equals("1")) {
                    secondMenu.add(menuList.get(j));
                    list.add(menuList.get(j));
                }
            }
            firstMenu.get(i).setChildren(secondMenu);
        }

        //找出所有三级菜单
        for (int i = 0; i < list.size(); i++) {
            String menuId = list.get(i).getId();
            List<Menu> thirdMenu = Lists.newArrayList();
            for (int j = 0; j < menuList.size(); j++) {
                if (menuId.equals(menuList.get(j).getParentId()) && menuList.get(j).getDpyFlg().equals("1")) {
                    thirdMenu.add(menuList.get(j));
                }
            }
            list.get(i).setChildren(thirdMenu);
        }

        return menuSort;
    }

    @RequestMapping(value = "importForm")
    public String importForm(Menu menu, Model model) {
        return "modules/sys/menuImport";
    }

    /**
     * 菜单模板下载
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
            String url = uploadFilePath + "/userfiles/ms/rent/template/menu_template.xlsx";
            String fileName = "菜单导入模板.xlsx";
            FileUtil.DownLoadFileByUri(url, fileName, response);
        } catch (Exception e) {
            throw new BaseException(SysErr.E_MESSAGE, "菜单导入模板下载失败！失败信息：" + e.getMessage());
        }
    }

    /**
     * 导入菜单数据
     *
     * @param file
     * @param redirectAttributes
     * @return
     */
    @RequestMapping(value = "import", method = RequestMethod.POST)
    public void importFile(HttpServletRequest request, HttpServletResponse response, MultipartFile file,
                           RedirectAttributes redirectAttributes) {
        IDataset responseData = DatasetService.getInstace().getDataset();
        try {
            StringBuilder failureMsg = new StringBuilder();
            int successNum = 0;
            int failureNum = 0;
            ImportExcel ei = new ImportExcel(file, 1, 0);
            List<Menu> list = ei.getDataList(Menu.class);
            for (Menu menu : list) {
                try {
                    systemService.saveMenu(menu);
                    successNum++;
                } catch (Exception e) {
                    failureMsg.append("菜单名：").append(menu.getName()).append("，失败信息：").append(e.getMessage());
                    failureNum++;
                }
            }
            if (failureNum > 0) {
                setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "成功" + successNum + "条，失败" + failureNum + "条。" + failureMsg);
            } else {
                setResponseDataset(request, response, responseData, SysErr.E_SUCCESS, "导入成功");
            }
        } catch (Exception e) {
            setResponseDataset(request, response, responseData, SysErr.E_MESSAGE, "导入失败！失败信息:" + e.getMessage());
        }
    }

}
