package com.adtec.prod.oper.dao;

import com.adtec.prod.oper.definition.adapter.entity.TPipSaleProdAdapterDOForMsmall;
import com.adtec.prod.oper.definition.saleprod.dao.TPipSaleProdDao;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.prod.oper.entity.*;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;
import com.adtec.framework.common.util.DataUtil;
import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@Component
public class ProdSaleProdDao implements IBaseDao<ProdSaleProdDO>{
	private final static Logger logger = LoggerFactory.getLogger(ProdSaleProdDao.class);
	@Autowired
	private TPipSaleProdDao tPipSaleProdDao;
	private static final String T_PROD_SALE_IMG = "T_PROD_SALE_IMG";
	


	
	public void deleteFirst(String saleProdCode) {
		// TODO Auto-generated method stub
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ")
		   .append(T_PROD_SALE_IMG)
		   .append(" where sale_prod_code=?");
		parameters.add(saleProdCode);
		IDBSession session = DBSessionFactory.getSession();
		try {
			session.executeByList(sql.toString(), parameters);
		} catch (SQLException e) {
            logger.error("删除图片信息失败："+ e.getMessage());
            throw new BaseException(SysErr.E_MESSAGE, "删除图片信息失败");
		}
	}

	
	public ProdSaleProdDO get(String saleProdCode) {
		IDBSession session = DBSessionFactory.getSession();
		List<Object> parameters = Lists.newArrayList();
		String sql = "select * from "+T_PROD_SALE_IMG+ " where sale_prod_code=?";
		parameters.add(saleProdCode);
		ProdSaleProdDO SaleProdDO = new ProdSaleProdDO();
		try {
			SaleProdDO = session.getObjectByList(sql, ProdSaleProdDO.class, parameters);
		} catch (SQLException e) {
			e.printStackTrace();
            logger.error("获取文件数据："+ e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "获取数据失败");
		}
		return SaleProdDO;	
	}



	@Override
	public int insert(ProdSaleProdDO objDO) {
		objDO.preInsert();
		int rs = 0;
        IDBSession session = DBSessionFactory.getSession();
        try {
            rs = session.saveObject(T_PROD_SALE_IMG, objDO, objDO.getIgnoreFields());
            if(rs==0){
            	 try {
                     session.rollback();
                 } catch (SQLException e1) {
                     e1.printStackTrace();
                 }
                 throw new BaseException(SysErr.E_MESSAGE, "插入数据条数为0！");
            }
        } catch (SQLException e) {
        	e.printStackTrace();
            logger.error("新增文档类型表异常："+ e.getMessage());
            try {
                session.rollback();
            } catch (SQLException e1) {
                e1.printStackTrace();
            }
            throw new BaseException(SysErr.E_MESSAGE, "插入数据失败！");
        }
        return rs;
	}



	@Override
	public int update(ProdSaleProdDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public int delete(ProdSaleProdDO objDO) {
		// TODO Auto-generated method stub
		return 0;
	}



	@Override
	public ProdSaleProdDO get(ProdSaleProdDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<ProdSaleProdDO> list(ProdSaleProdDO objDO) {
		// TODO Auto-generated method stub
		return null;
	}



	@Override
	public List<ProdSaleProdDO> list(ProdSaleProdDO objDO, int start, int limit) {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public List<ProdSaleProdDO> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	public FProdSaleProdPageViewResDTO getPerPage(String saleProdCode) {
		FProdSaleProdPageViewResDTO resDTO = new FProdSaleProdPageViewResDTO();
		List<FProdSaleProdPageViewResListKeyListDTO> listKeyListDTOS = new ArrayList<>();
		IDBSession session = DBSessionFactory.getSession();
		List<Object> parameters = Lists.newArrayList();
		String sql = "select a.KEY_NO,a.KEY_NAME,a.SER,a.VSL_FLG,a.VAL_TP,a.VAL_LEN,a.ENTER_TP as KEY_TP,b.ELEM_KEY,b.ELEM_NAME,b.ELEM_KV ,a.DEFA_KV from T_PIP_SALE_PROD_ADAPTER a LEFT JOIN T_PIP_KEY_CTRL b ON a.KEY_NO = b.KEY_NO where a.SALE_PROD_CODE=? order by ser asc ";
		parameters.add(saleProdCode);
		ResultSet rs = null;
		try {
			rs = session.getResultSetByList(sql, parameters);
			while (rs.next()) {
				List<FProdSaleProdPageViewResListKeyListCtrlListDTO> listKeyListCtrlListDTOS = new ArrayList<>();
				String KEY_TP = rs.getString("KEY_TP");
				String ELEM_KEY = rs.getString("ELEM_KEY");
				String ELEM_KV = rs.getString("ELEM_KV");
				// 01输入框类型
				if ("01".equals(KEY_TP)) {
					ELEM_KEY = "text.note";
					ELEM_KV = "";
				}
				FProdSaleProdPageViewResListKeyListCtrlListDTO resListKeyListCtrlListDTO = new FProdSaleProdPageViewResListKeyListCtrlListDTO();
				resListKeyListCtrlListDTO.setKEY_NO(ELEM_KEY);
				resListKeyListCtrlListDTO.setKV(ELEM_KV);
				listKeyListCtrlListDTOS.add(resListKeyListCtrlListDTO);
				if ("text.note".equals(ELEM_KEY)) {
					resListKeyListCtrlListDTO = new FProdSaleProdPageViewResListKeyListCtrlListDTO();
					resListKeyListCtrlListDTO.setKEY_NO("text.check");
					resListKeyListCtrlListDTO.setKV("telephone;integer-pos-neg");
					listKeyListCtrlListDTOS.add(resListKeyListCtrlListDTO);
				} else if ("radio.kv".equals(ELEM_KEY)) {
					resListKeyListCtrlListDTO = new FProdSaleProdPageViewResListKeyListCtrlListDTO();
					resListKeyListCtrlListDTO.setKEY_NO("radio.arrange");
					resListKeyListCtrlListDTO.setKV("inline");
					listKeyListCtrlListDTOS.add(resListKeyListCtrlListDTO);
				} else if ("checkbox.kv".equals(ELEM_KEY)) {
					resListKeyListCtrlListDTO = new FProdSaleProdPageViewResListKeyListCtrlListDTO();
					resListKeyListCtrlListDTO.setKEY_NO("checkbox.arrange");
					resListKeyListCtrlListDTO.setKV("horizontal");
					listKeyListCtrlListDTOS.add(resListKeyListCtrlListDTO);
				}

				FProdSaleProdPageViewResListKeyListDTO resListKeyListDTO = new FProdSaleProdPageViewResListKeyListDTO();
				resListKeyListDTO.setCTRL_LIST(listKeyListCtrlListDTOS);
				resListKeyListDTO.setKEY_NO(rs.getString("KEY_NO"));
				resListKeyListDTO.setKEY_NAME(rs.getString("KEY_NAME"));
				resListKeyListDTO.setLINE_SER(rs.getString("SER"));
				resListKeyListDTO.setCOL_SER(rs.getString("SER"));
				resListKeyListDTO.setFLG(rs.getString("VSL_FLG"));
				resListKeyListDTO.setVAL_TP(rs.getString("VAL_TP"));
				resListKeyListDTO.setVAL_LEN(rs.getString("VAL_LEN"));
				resListKeyListDTO.setKEY_TP(KEY_TP);
				resListKeyListDTO.setINPUT_FLG("01");
				resListKeyListDTO.setDEFA_KV(rs.getString("DEFA_KV"));
				resListKeyListDTO.setKEY_FLG("00");
				listKeyListDTOS.add(resListKeyListDTO);

			}

			List<FProdSaleProdPageViewResListDTO> listDTOS = new ArrayList<>();
			FProdSaleProdPageViewResListDTO resListDTO = new FProdSaleProdPageViewResListDTO();
			resListDTO.setKEY_LIST(listKeyListDTOS);
			resListDTO.setATOM_PROD_CODE(saleProdCode);
			resListDTO.setATOM_PROD_DESC(tPipSaleProdDao.get(saleProdCode).getSaleProdDesc());
			listDTOS.add(resListDTO);

			resDTO.setLIST(listDTOS);

		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取文件数据", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取数据失败");
		} finally {
			try {
				session.closeResultSetAndStatement(rs);
				DBSessionFactory.closeSession(session);
			} catch (SQLException e) {
				logger.error("关闭删除数据异常：" + e.getMessage());
			}
		}
		return resDTO;
	}

	public List<TPipSaleProdAdapterDOForMsmall> getPerPageForMsmall(TPipSaleProdAdapterDOForMsmall obj, int start, int limit) {
		List<TPipSaleProdAdapterDOForMsmall> list = Lists.newArrayList();
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();

		if (DataUtil.isNullStr(obj.getSvcCode())) {
			sql.append("select DISTINCT a.*,b.ELEM_KEY,b.ELEM_NAME,b.ELEM_KV,c.comp_no,c.comp_name ");
			sql.append(" from T_PIP_SALE_PROD_ADAPTER a LEFT JOIN T_PIP_KEY_CTRL b ON a.KEY_NO = b.KEY_NO")
					.append(" LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
					.append(" LEFT JOIN T_PIP_ATOM_PROD_SVC d on c.atom_prod_code=d.atom_prod_code ")
					.append(getWhereSql(obj, parameters));
		} else {
			String[] ss = obj.getSvcCode().split("\\,");
			for (int i = 0; i < ss.length; i++) {
				sql.append("select DISTINCT a.*,b.ELEM_KEY,b.ELEM_NAME,b.ELEM_KV,c.comp_no,c.comp_name ");
				sql.append(" ,d.svc_code ");
				sql.append(" from T_PIP_SALE_PROD_ADAPTER a LEFT JOIN T_PIP_KEY_CTRL b ON a.KEY_NO = b.KEY_NO")
						.append(" LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
						.append(" LEFT JOIN T_PIP_ATOM_PROD_SVC d on c.atom_prod_code=d.atom_prod_code ")
						.append(getWhereSql(obj, parameters));
				sql.append(" and d.svc_code=? and a.KEY_NO in (select KEY_NO from T_PIP_COMP_SVC_PARA WHERE svc_code=?) ");
				parameters.add(ss[i]);
				parameters.add(ss[i]);
				if (i+1 != ss.length) {
					sql.append(" union all ");
				}
			}
		}
		try {
			IDBSession session = DBSessionFactory.getSession();
			if (limit == 0) {
				list = session.getObjectListByList(sql.toString(), TPipSaleProdAdapterDOForMsmall.class, parameters);
			} else {
				list = session.getObjectListByListForPage(sql.toString(), TPipSaleProdAdapterDOForMsmall.class, start, limit, parameters);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			logger.error("获取文件数据", e);
			throw new BaseException(SysErr.E_MESSAGE, "获取数据失败");
		}
		return list;
	}

	public int getTotal(TPipSaleProdAdapterDOForMsmall obj) {
		List<Object> parameters = Lists.newArrayList();
		StringBuilder sql = new StringBuilder();

		if (DataUtil.isNullStr(obj.getSvcCode())) {
			sql.append("select DISTINCT a.*,b.ELEM_KEY,b.ELEM_NAME,b.ELEM_KV,c.comp_no,c.comp_name ");
			sql.append(" from T_PIP_SALE_PROD_ADAPTER a LEFT JOIN T_PIP_KEY_CTRL b ON a.KEY_NO = b.KEY_NO")
					.append(" LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
					.append(" LEFT JOIN T_PIP_ATOM_PROD_SVC d on c.atom_prod_code=d.atom_prod_code ")
					.append(getWhereSql(obj, parameters));
		} else {
			String[] ss = obj.getSvcCode().split("\\,");
			for (int i = 0; i < ss.length; i++) {
				sql.append("select DISTINCT a.*,b.ELEM_KEY,b.ELEM_NAME,b.ELEM_KV,c.comp_no,c.comp_name ");
				sql.append(" ,d.svc_code ");
				sql.append(" from T_PIP_SALE_PROD_ADAPTER a LEFT JOIN T_PIP_KEY_CTRL b ON a.KEY_NO = b.KEY_NO")
						.append(" LEFT JOIN T_PIP_SALE_PROD_ATOM_PROD c on a.sale_prod_code=c.sale_prod_code ")
						.append(" LEFT JOIN T_PIP_ATOM_PROD_SVC d on c.atom_prod_code=d.atom_prod_code ")
						.append(getWhereSql(obj, parameters));
				sql.append(" and d.svc_code=? and a.KEY_NO in (select KEY_NO from T_PIP_COMP_SVC_PARA WHERE svc_code=?) ");
				parameters.add(ss[i]);
				parameters.add(ss[i]);
				if (i+1 != ss.length) {
					sql.append(" union all ");
				}
			}
		}
		String countSql = "select count(1) from (" + sql.toString() + ") ct";
		IDBSession session = DBSessionFactory.getSession();
		int total = 0;
		try {
			total = session.accountByList(countSql, parameters);
		} catch (Exception e) {
			logger.error("总记录数查询异常：" + e.getMessage());
			throw new BaseException(SysErr.E_MESSAGE, "总记录数查询失败！");
		}
		return total;
	}

	public String getWhereSql(TPipSaleProdAdapterDOForMsmall obj, List<Object> parameters) {
		StringBuffer sql = new StringBuffer(" where 1=1 ");
		if ( !DataUtil.isNullStr(obj.getSaleProdCode())) {
			sql.append(" AND a.SALE_PROD_CODE = ? ");
			parameters.add(obj.getSaleProdCode());
		}
		if ( !DataUtil.isNullStr(obj.getCompNo())) {
			sql.append(" AND ( ");
			String[] ss = obj.getCompNo().split("\\,");
			for (int i = 0 ; i < ss.length; i++) {
				if (i+1 == ss.length) {
					sql.append(" c.comp_no = ? ");
					parameters.add(ss[i]);
				} else {
					sql.append(" c.comp_no = ? or ");
					parameters.add(ss[i]);
				}
			}
			sql.append(" ) ");
		}
//		if ( !DataUtil.isNullStr(obj.getSvcCode())) {
//			sql.append(" AND ( ");
//			String[] ss = obj.getSvcCode().split("\\,");
//			for (int i = 0 ; i < ss.length; i++) {
//				if (i+1 == ss.length) {
//					sql.append(" d.svc_code = ? ");
//					parameters.add(ss[i]);
//				} else {
//					sql.append(" d.svc_code = ? or ");
//					parameters.add(ss[i]);
//				}
//			}
//			sql.append(" ) ");
//		}
		return sql.toString();
	}

}
