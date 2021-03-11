package com.fh.plugin;

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.PropertyException;

import org.apache.ibatis.executor.ErrorContext;
import org.apache.ibatis.executor.ExecutorException;
import org.apache.ibatis.executor.parameter.ParameterHandler;
import org.apache.ibatis.executor.statement.BaseStatementHandler;
import org.apache.ibatis.executor.statement.RoutingStatementHandler;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.mapping.ParameterMode;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.scripting.xmltags.ForEachSqlNode;
import org.apache.ibatis.session.Configuration;
import org.apache.ibatis.type.TypeHandler;
import org.apache.ibatis.type.TypeHandlerRegistry;

import com.fh.entity.Page;
import com.fh.util.ReflectHelper;
import com.fh.util.Tools;
/**
 * 
* 类名称：分页插件
* 类描述： 
* @author rhz
* 作者单位： 
* 联系方式：
* 修改时间：2016年2月1日
* @version 1.0
 */
@Intercepts({@Signature(type=StatementHandler.class,method="prepare",args={Connection.class})})
public class PageInterceptor implements Interceptor {

	private static String dialect = "";	//数据库方言
	private static String pageSqlId = ""; //mapper.xml中需要拦截的ID(正则匹配)
	
	@Override
	public Object intercept(Invocation invocation) throws Throwable {
		// TODO Auto-generated method stub
		if(invocation.getTarget() instanceof RoutingStatementHandler){
			RoutingStatementHandler statementHandler = (RoutingStatementHandler)invocation.getTarget(); 
			StatementHandler delegate = (StatementHandler) ReflectHelper.getValueByFieldName(statementHandler, "delegate");  
			BoundSql boundSql = delegate.getBoundSql();
			Object obj = boundSql.getParameterObject();
			if(obj==null){
				throw new NullPointerException("parameterObject尚未实例化！");
			}else if(obj instanceof Page) {
				//通过反射获取delegate父类BaseStatementHandler的mappedStatement属性 
				MappedStatement mappedStatement = (MappedStatement)ReflectHelper.getValueByFieldName(delegate, "mappedStatement");
				//拦截到的prepare方法参数是一个Connection对象                  
				Connection connection = (Connection)invocation.getArgs()[0];
				//获取当前要执行的Sql语句，也就是我们直接在Mapper映射语句中写的Sql语句                 
				String sql = boundSql.getSql(); 
				String countSql = "select count(0) from (" + sql+ ")  tmp_count"; //记录统计 == oracle 加 as 报错(SQL command not properly ended)
				PreparedStatement countStmt = connection.prepareStatement(countSql);
				BoundSql countBS = new BoundSql(mappedStatement.getConfiguration(),countSql,boundSql.getParameterMappings(),obj);
				setParameters(countStmt,mappedStatement,countBS,obj);
				ResultSet rs = countStmt.executeQuery();
				int count = 0;
				if (rs.next()) {
					count = rs.getInt(1);
				}
				rs.close();
				countStmt.close();
				Page page = null;
				if(obj instanceof Page){	//参数就是Page实体
					 page = (Page) obj;
					 //System.out.println("=============pageoageoage=========");
					 //System.out.println(page);
					 page.setEntityOrField(true);	 
					 page.setTotalResult(count);
					 //System.out.println(page);
				}else{	//参数为某个实体，该实体拥有Page属性
					Field pageField = ReflectHelper.getFieldByFieldName(obj,"page");
					if(pageField!=null){
						page = (Page) ReflectHelper.getValueByFieldName(obj,"page");
						if(page==null)
							page = new Page();
						page.setEntityOrField(false); 
						page.setTotalResult(count);
						ReflectHelper.setValueByFieldName(obj,"page", page); //通过反射，对实体对象设置分页对象
					}else{
						throw new NoSuchFieldException(obj.getClass().getName()+"不存在 page 属性！");
					}
				}
				
				//给当前的page参数对象设置总记录数                 
				this.setTotalRecord(page,mappedStatement, connection);
				//获取分页Sql语句                  
				String pageSql = this.getPageSql(page, sql);  
				//System.out.println("=================sql+page====================");
				//System.out.println(sql);
				//System.out.println(page);
				//System.out.println(pageSql);
				//利用反射设置当前BoundSql对应的sql属性为我们建立好的分页Sql语句 
				ReflectHelper.setValueByFieldName(boundSql, "sql", pageSql);
				}         
			}          
		return invocation.proceed();  
	}
	
	
	/**
	 * 对SQL参数(?)设值,参考org.apache.ibatis.executor.parameter.DefaultParameterHandler
	 * @param ps
	 * @param mappedStatement
	 * @param boundSql
	 * @param parameterObject
	 * @throws SQLException
	 */
	private void setParameters(PreparedStatement ps,MappedStatement mappedStatement,BoundSql boundSql,Object parameterObject) throws SQLException {
		ErrorContext.instance().activity("setting parameters").object(mappedStatement.getParameterMap().getId());
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		if (parameterMappings != null) {
			Configuration configuration = mappedStatement.getConfiguration();
			TypeHandlerRegistry typeHandlerRegistry = configuration.getTypeHandlerRegistry();
			MetaObject metaObject = parameterObject == null ? null: configuration.newMetaObject(parameterObject);
			for (int i = 0; i < parameterMappings.size(); i++) {
				ParameterMapping parameterMapping = parameterMappings.get(i);
				if (parameterMapping.getMode() != ParameterMode.OUT) {
					Object value;
					String propertyName = parameterMapping.getProperty();
					PropertyTokenizer prop = new PropertyTokenizer(propertyName);
					if (parameterObject == null) {
						value = null;
					} else if (typeHandlerRegistry.hasTypeHandler(parameterObject.getClass())) {
						value = parameterObject;
					} else if (boundSql.hasAdditionalParameter(propertyName)) {
						value = boundSql.getAdditionalParameter(propertyName);
					} else if (propertyName.startsWith(ForEachSqlNode.ITEM_PREFIX)&& boundSql.hasAdditionalParameter(prop.getName())) {
						value = boundSql.getAdditionalParameter(prop.getName());
						if (value != null) {
							value = configuration.newMetaObject(value).getValue(propertyName.substring(prop.getName().length()));
						}
					} else {
						value = metaObject == null ? null : metaObject.getValue(propertyName);
					}
					TypeHandler typeHandler = parameterMapping.getTypeHandler();
					if (typeHandler == null) {
						throw new ExecutorException("There was no TypeHandler found for parameter "+ propertyName + " of statement "+ mappedStatement.getId());
					}
					typeHandler.setParameter(ps, i + 1, value, parameterMapping.getJdbcType());
				}
			}
		}
	}
	
	
	private void setTotalRecord(Page page,MappedStatement mappedStatement, Connection connection) {
		//获取对应的BoundSql，这个BoundSql其实跟我们利用StatementHandler获取到的BoundSql是同一个对象。
		//delegate里面的boundSql也是通过mappedStatement.getBoundSql(paramObj)方法获取到的。
		BoundSql boundSql = mappedStatement.getBoundSql(page);         
		//获取到我们自己写在Mapper映射语句中对应的Sql语句 
		String sql = boundSql.getSql();
		//通过查询Sql语句获取到对应的计算总记录数的sql语句
		String countSql = this.getCountSql(sql);
		//通过BoundSql获取对应的参数映射 
		List<ParameterMapping> parameterMappings = boundSql.getParameterMappings();
		//利用Configuration、查询记录数的Sql语句countSql、参数映射关系parameterMappings和参数对象page建立查询记录数对应的BoundSql对象。
		BoundSql countBoundSql = new BoundSql(mappedStatement.getConfiguration(), countSql, parameterMappings, page);
		//通过mappedStatement、参数对象page和BoundSql对象countBoundSql建立一个用于设定参数的ParameterHandler对象
		ParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, page, countBoundSql);
		//通过connection建立一个countSql对应的PreparedStatement对象。
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = connection.prepareStatement(countSql);
			//通过parameterHandler给PreparedStatement对象设置参数
			parameterHandler.setParameters(pstmt);
			//之后就是执行获取总记录数的Sql语句和获取结果了。
			rs = pstmt.executeQuery();
			if (rs.next()) {
				int totalRecord = rs.getInt(1);
				//给当前的参数page对象设置总记录数 
				page.setTotalResult(totalRecord);
				} 
		}catch (SQLException e) {
				e.printStackTrace();
		}finally{
				try {
					if (rs != null)
						rs.close();
					if (pstmt != null)
						pstmt.close();
				} catch (SQLException e) {
						e.printStackTrace();
				}         
		}      
	}  

	
	private String getPageSql(Page page, String sql) {
		StringBuffer sqlBuffer = new StringBuffer(sql);
		if ("mysql".equalsIgnoreCase(dialect)) {
			return getMysqlPageSql(page, sqlBuffer);
		} else if ("oracle".equalsIgnoreCase(dialect)) {
			return getOraclePageSql(page, sqlBuffer);
		} 
		return sqlBuffer.toString();
	} 

	
	private String getMysqlPageSql(Page page, StringBuffer sqlBuffer) {
		//计算第一条记录的位置，Mysql中记录的位置是从0开始的。  
		//System.out.println("page:"+page.getPage()+"-------"+page.getRows());
		int offset = (page.getCurrentPage()-1) * page.getShowCount();
		sqlBuffer.append(" limit ").append(offset).append(",").append(page.getShowCount());
		return sqlBuffer.toString();     
	}  


	
    private String getOraclePageSql(Page page, StringBuffer sqlBuffer) {
    	//计算第一条记录的位置，Oracle分页是通过rownum进行的，而rownum是从1开始的
    	int offset = page.getCurrentPage() * page.getShowCount()+1;
    	sqlBuffer.insert(0, "select u.*, rownum r from (").append(") u where rownum < ").append(offset + page.getShowCount());
    	sqlBuffer.insert(0, "select * from (").append(") where r >= ").append(offset);
    	//上面的Sql语句拼接之后大概是这个样子：        
    	//select * from (select u.*, rownum r from (select * from t_user) u where rownum < 31) where r >= 16
    	return sqlBuffer.toString();
    } 


	/**
      * 根据原Sql语句获取对应的查询总记录数的Sql语句
      * @param sql
      * @return      
    */      
	private String getCountSql(String sql) {
		int index = sql.indexOf("from");
		return "select count(*) " + sql.substring(index);
	}

	
	
	@Override
	public Object plugin(Object target) {
		// TODO Auto-generated method stub
    	if (target instanceof StatementHandler) {
    		return Plugin.wrap(target, this);
    	} else {
    		return target;
    	} 
	}
	@Override
	public void setProperties(Properties p) {
		// TODO Auto-generated method stub
		dialect = p.getProperty("dialect");
		if (Tools.isEmpty(dialect)) {
			try {
				throw new PropertyException("dialect property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		pageSqlId = p.getProperty("pageSqlId");
		if (Tools.isEmpty(pageSqlId)) {
			try {
				throw new PropertyException("pageSqlId property is not found!");
			} catch (PropertyException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	public String getDialect() {
		return dialect;	
	}
	@SuppressWarnings("static-access")
	public void setDialect(String dialect) {
		this.dialect = dialect;	
	} 
	public String getPageSqlId() {
		return pageSqlId;	
	} 	
	@SuppressWarnings("static-access")
	public void setPageSqlId(String pageSqlId) {
		this.pageSqlId = pageSqlId;	
	}

	
}
