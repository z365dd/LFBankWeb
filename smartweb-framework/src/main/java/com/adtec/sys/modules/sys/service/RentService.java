package com.adtec.sys.modules.sys.service;

import java.sql.SQLException;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.adtec.framework.common.constant.Constants;
import com.adtec.framework.common.util.DataUtil;
import com.adtec.framework.common.util.DateUtil;
import com.adtec.framework.common.util.FileUtil;
import com.adtec.framework.common.util.ParamUtil;
import com.adtec.framework.common.util.StringUtil;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.utils.IdGen;
import com.adtec.sys.common.web.Servlets;
import com.adtec.sys.modules.sys.dao.MenuDao;
import com.adtec.sys.modules.sys.dao.PermissionDao;
import com.adtec.sys.modules.sys.dao.RentDao;
import com.adtec.sys.modules.sys.dao.SysPermissionWeightDao;
import com.adtec.sys.modules.sys.entity.FileDO;
import com.adtec.sys.modules.sys.entity.Rent;
import com.adtec.sys.modules.sys.utils.UserUtils;

@Service
public class RentService {

    private final static Logger logger = LoggerFactory.getLogger(RentService.class);

    private final PermissionDao permissionDao;
    private final RentDao rentDao;
    private final MenuDao menuDao;
    private final SysPermissionWeightDao sysPermissionWeightDao;
    @Autowired
    private FileService fileService;

    public RentService(PermissionDao permissionDao, RentDao rentDao, MenuDao menuDao, SysPermissionWeightDao sysPermissionWeightDao) {
        this.permissionDao = permissionDao;
        this.rentDao = rentDao;
        this.menuDao = menuDao;
        this.sysPermissionWeightDao = sysPermissionWeightDao;
    }

    public Rent get(String id) {
        return rentDao.get(id);
    }

    public Rent get(Rent rent) {
        return rentDao.get(rent);
    }
    
    public Rent getByEngName(Rent rent) {
    	return rentDao.getByEngName(rent);
    }

    public List<Rent> findAll() {
        return UserUtils.getRentList();
    }

    public void save(Rent rent) {
        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if (rent.getParent() == null || StringUtil.isBlank(rent.getParentId())
                || "0".equals(rent.getParentId())) {
            rent.setParent(null);
        } else {
            rent.setParent(rentDao.get(rent.getParentId()));
        }
        if (rent.getParent() == null) {
            Rent parentEntity;
            try {
                parentEntity = new Rent("0");
            } catch (Exception e) {
                throw new BaseException(SysErr.E_NO_MESSAGE, e, "保存租户异常");
            }
            rent.setParent(parentEntity);
            rent.getParent().setParentIdList(StringUtil.EMPTY);
        }
        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = rent.getParentIdList();
        // 设置新的父节点串
        rent.setParentIdList(rent.getParent().getParentIdList() + rent.getParent().getId() + ",");
        IDBSession session = DBSessionFactory.getSession();
        try {
            // 保存或更新实体
            if (StringUtil.isBlank(rent.getId())) {
            	saveFile(rent);
                rent.preInsert();
                rentDao.insert(rent);
                sysPermissionWeightDao.initMenuPermissionToRent(rent.getId());
            } else {
                Rent oldRent = rentDao.get(rent.getId());
                //删除背景图和描述文件
                if(oldRent !=null && !DataUtil.isNullStr(oldRent.getBgImg())){
                    delFile(oldRent.getBgImg());
                }
                if(oldRent !=null && !DataUtil.isNullStr(oldRent.getTntDesc())){
                    delFile(oldRent.getTntDesc());
                    FileUtil.deleteFile(oldRent.getId()+".txt");
                }
                //再插入背景图和描述文件
                saveFile(rent);
                rent.preUpdate();
                rentDao.update(rent);
                // 上级发生变化时，清空转授权和使用权
                if (!oldParentIds.equals(rent.getParentIdList())) {
                    permissionDao.deleteMenusByMenuIds("TNT_ID", rent.getId(), "transfer");
                    permissionDao.deleteMenusByMenuIds("TNT_ID", rent.getId(), "use");
                }
            }
            // 更新子节点 parentIds
            Rent o = new Rent();
            o.setId(rent.getId());
            List<Rent> list = rentDao.findByParentIdsLike(o);
            for (Rent e : list) {
                if (e.getParentIdList() != null && oldParentIds != null) {
                    e.setParentIdList(e.getParentIdList().replace(oldParentIds, rent.getParentIdList()));
                    rentDao.updateParentIds(e);
                    // 上级发生变化时，清空转授权和使用权
                    if (!oldParentIds.equals(rent.getParentIdList())) {
                        permissionDao.deleteMenusByMenuIds("TNT_ID", e.getId(), "transfer");
                        permissionDao.deleteMenusByMenuIds("TNT_ID", e.getId(), "use");
                    }
                }
            }
        } catch (Exception e) {
            logger.error(e.getMessage());
            try {
                session.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "交易失败！");
        } finally {
            UserUtils.removeCache(UserUtils.CACHE_RENT_LIST);
        }
    }
    
    public void syncStoreEnv(Rent rent){
    	rentDao.syncStoreEnv(rent);
    }
    
    /**
     * 删除文件信息
     * @param id
     */
    private void delFile(String id){
        IDBSession session = DBSessionFactory.getSession();
        try {
            String sql = "DELETE FROM t_sys_file where id=?";
            session.execute(sql, id);
        } catch (Exception e) {
            throw new BaseException(SysErr.E_MESSAGE, "删除文件异常");
        }
    }
    
    public void saveFile(Rent rent){
        String bgImage = rent.getBgImg();
        if(!DataUtil.isNullStr(bgImage)){
            // 获取文件名
            String fileName = FileUtil.getFileName(bgImage);
            // 获取文件类型
            String fileType = FileUtil.getFileType(bgImage);
            // 通过文件管理器上传的图片保存的真实路径
            String realPath = ParamUtil.getUploadFile() + ParamUtil.USERFILES_BASE_URL + bgImage.split(ParamUtil.USERFILES_BASE_URL)[1];
            // 获取对应的预览路径
            String previewPath = FileUtil.getPreviewFilePath(realPath, false);
            FileDO bgImageFile = new FileDO();
            bgImageFile.setFileName(fileName);
            bgImageFile.setSaveName(IdGen.uuid()+"."+fileType);
            bgImageFile.setFileTp(fileType);
            bgImageFile.setResTp(Constants.MSMALL_RENT);
            bgImageFile.setSavePath(realPath.replaceAll(ParamUtil.getUploadFile(), ""));
            bgImageFile.setProjName(Servlets.getRequest().getContextPath());
            bgImageFile.setPrevPath(previewPath);
            bgImageFile.setQuoteFlg(FileDO.IS_USED_Y);  // 设置为已引用
            if(!fileService.insert(bgImageFile)){
                throw new BaseException(SysErr.E_MESSAGE, "保存背景图失败！");
            }
            rent.setBgImg(bgImageFile.getId());
        }
        
        String description = rent.getTntDesc();
        if(!DataUtil.isNullStr(description)){
            // 获取文件名
            String descFileName = rent.getId()+".txt";
            // 获取文件类型
            String descFileType = "txt";
            // 真实路径
            String descRealPath = ParamUtil.getUploadFile() + ParamUtil.USERFILES_BASE_URL + "msmall/rent/" + DateUtil.getYear() + "/" +DateUtil.getMonth() + "/"
                    +DateUtil.getDay()+"/"+ descFileName;
            // 把内容写入到文件中
            FileUtil.createFile(descRealPath);
            FileUtil.writeToFile(descRealPath, description, false);
            String descPreviewPath = FileUtil.getPreviewFilePath(descRealPath, false);
            // 获取对应的预览路径
            FileDO descFile = new FileDO();
            descFile.setFileName(descFileName);
            descFile.setSaveName(descFileName);
            descFile.setResTp(Constants.MSMALL_RENT);
            descFile.setFileTp(descFileType);
            descFile.setSavePath(descRealPath.replaceAll(ParamUtil.getUploadFile(), ""));
            descFile.setPrevPath(descPreviewPath);
            descFile.setProjName(Servlets.getRequest().getContextPath());
            descFile.setQuoteFlg(FileDO.IS_USED_Y); // 设置为已引用
            if (!fileService.insert(descFile)) {
                throw new BaseException(SysErr.E_MESSAGE, "保存租户简介信息文件失败！");
            }
            rent.setTntDesc(descFile.getId());
        }

    }    

}
