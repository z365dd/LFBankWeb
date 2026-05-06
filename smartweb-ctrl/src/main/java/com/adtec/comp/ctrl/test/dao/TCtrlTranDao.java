package com.adtec.comp.ctrl.test.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.adtec.comp.ctrl.test.entity.TCtrlFileMsgDo;
import com.adtec.framework.impl.db.session.DBSessionFactory;
import com.adtec.framework.interfaces.db.session.IDBSession;
import com.adtec.sys.common.dao.IBaseDao;
import com.adtec.framework.exception.BaseException;
import com.adtec.framework.exception.SysErr;

@Component
public class TCtrlTranDao implements IBaseDao<TCtrlFileMsgDo>{
	
	private static final String T_CTRL_TRAN = "T_CTRL_TRAN_FILE";

	@Override
	public int insert(TCtrlFileMsgDo objDO) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("insert into ").append(T_CTRL_TRAN).append("(case_no,case_name,file_name,flg,file_path,user_name,create_by,create_date) values");
		sql.append("('");
		objDO.preInsert();
		sql.append(objDO.getCaseNo()).append("','");
		sql.append(objDO.getCaseName()).append("','");
		sql.append(objDO.getFileName()).append("','");
		sql.append(objDO.getFlg()).append("','");
		sql.append(objDO.getFilePath()).append("','");
		sql.append(objDO.getUserName()).append("','");
		sql.append(objDO.getCrtr()).append("','");
		sql.append(objDO.getCrtTime()).append("')");
		System.out.println(sql.toString());
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.execute(sql.toString());
			if(rs==0){
				try{
					session.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
				throw new BaseException(SysErr.E_MESSAGE, "本地保存记录失败");
			}
		} catch(SQLException e){
			try{
				session.rollback();
			}catch(SQLException e1){
				e1.printStackTrace();
			}
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, "本地保存记录失败");
		}
		return rs;
	}

	@Override
	public int update(TCtrlFileMsgDo objDO) {
		StringBuilder sql = new StringBuilder();
		sql.append("update ").append(T_CTRL_TRAN).append("set ");
		sql.append("case_name= '").append(objDO.getCaseName()).append("'");
		sql.append("where case_no = '").append(objDO.getCaseNo()).append("'");
		sql.append(" and flg = '").append(objDO.getFlg()).append("'");
		sql.append(" and user_name = '").append(objDO.getUserName()).append("'");
		
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.execute(sql.toString());
			if(rs==0){
				try{
					session.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
				throw new BaseException(SysErr.E_MESSAGE, "本地修改记录失败");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public int delete(TCtrlFileMsgDo objDO) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("delete from ").append(T_CTRL_TRAN).append(" where case_no ='");
		sql.append(objDO.getCaseNo()).append("'");
		sql.append(" and case_name = '").append(objDO.getCaseName()).append("'");
		sql.append(" and user_name = '").append(objDO.getUserName()).append("'");
		sql.append(" and flg = '").append(objDO.getFlg()).append("'");
		
		int rs = 0;
		IDBSession session = DBSessionFactory.getSession();
		try {
			rs = session.execute(sql.toString());
			if(rs==0){
				try{
					session.rollback();
				}catch(SQLException e1){
					e1.printStackTrace();
				}
				throw new BaseException(SysErr.E_MESSAGE, "本地删除记录失败");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return rs;
	}

	@Override
	public TCtrlFileMsgDo get(TCtrlFileMsgDo objDO) {
		// TODO Auto-generated method stub
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(T_CTRL_TRAN).append(" where 1=1 ");
		if(!"".equals(objDO.getFlg())){
			sql.append("and flg = '").append(objDO.getFlg()).append("'");
		}
		if(!"".equals(objDO.getUserName())){
			sql.append("and user_name = '").append(objDO.getUserName()).append("'");
		}
		if(!"".equals(objDO.getCaseName())){
			sql.append(" and case_name = '").append(objDO.getCaseName()).append("' ");
		}
		if(!"".equals(objDO.getCaseNo()) && "null".equals(objDO.getCaseNo()) ){
			sql.append(" and case_no = '").append(objDO.getCaseNo()).append("' ");
		}
		IDBSession session = DBSessionFactory.getSession();
		List<TCtrlFileMsgDo> list = new ArrayList<TCtrlFileMsgDo>();
		TCtrlFileMsgDo rs= null;
		try {
			list = session.getObjectList(sql.toString(), TCtrlFileMsgDo.class);
			if(list!=null && list.size()>0){
				rs = list.get(0);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, "单条数据查询失败！");
		}
		return rs;
	}

	@Override
	public List<TCtrlFileMsgDo> list(TCtrlFileMsgDo objDO, int start, int limit){
		StringBuilder sql = new StringBuilder();
		sql.append("select * from ").append(T_CTRL_TRAN).append(" where 1=1 ");
		if(!"".equals(objDO.getFlg())){
			sql.append("and flg = '").append(objDO.getFlg()).append("'");
		}
		if(!"".equals(objDO.getUserName())){
			sql.append("and user_name = '").append(objDO.getUserName()).append("'");
		}
		if(!"".equals(objDO.getCaseName())){
			sql.append(" and case_name like '%").append(objDO.getCaseName()).append("%' ");
		}
		sql.append(" order by case_no ");
		IDBSession session = DBSessionFactory.getSession();
		List<TCtrlFileMsgDo> list = new ArrayList<TCtrlFileMsgDo>(); 
		try {
			if(limit==0){
				list = session.getObjectList(sql.toString(), TCtrlFileMsgDo.class);
			}else{
				list = session.getObjectListForPage(sql.toString(), TCtrlFileMsgDo.class, start, limit);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, "查询列表失败");
		}
		return list;
	}
	
	//添加时获取下一个可用的id
	public int getNextId(String flg){
		StringBuilder sql = new StringBuilder();
		sql.append("select max(case_no) from ").append(T_CTRL_TRAN).append(" where 1=1 ");
		if(!"".equals(flg)){
			sql.append("and flg = '").append(flg).append("'");
		}
		IDBSession session = DBSessionFactory.getSession();
		int nextId=0;
		ResultSet rs = null;
		try {
			rs = session.getResultSet(sql.toString());
			if(null != rs && rs.next()){
				String num = rs.getString(1);
				if(null != num && !"null".equals(num) && !"".equals(num) ){
					nextId=Integer.parseInt(num.substring(1));
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new BaseException(SysErr.E_MESSAGE, "返回id失败");
		} finally {
			try {
				session.closeResultSetAndStatement(rs);
				DBSessionFactory.closeSession(session);
			} catch (SQLException throwables) {
				System.out.println("sql异常");
			}
		}
		return nextId;
	}

	@Override
	public List<TCtrlFileMsgDo> list(int start, int limit, Object... param) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TCtrlFileMsgDo> list(TCtrlFileMsgDo objDO) {
		// TODO Auto-generated method stub
		return null;
	}

}
