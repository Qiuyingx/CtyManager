package com.yard.manager.modules.util;

import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import com.yard.core.service.common.DBService;

public class JdbcUtils {
    private static ThreadLocal<Connection> tl=new ThreadLocal<Connection>();  
    private static DataSource ds = null;  
    static{  
        try{  
            ds = DBService.getDataSource();
        }catch (Exception e) {  
            throw new ExceptionInInitializerError(e);  
        }  
    }  
    public static DataSource getDataSource() throws SQLException{  
        return ds;  
    }  
    public static Connection getConnection() throws SQLException{  
        try{  
            //得到当前线程上绑定的连接  
            Connection conn = tl.get();  
            if(conn==null){  //代表线程上没有绑定连接  
                conn = ds.getConnection();  
                tl.set(conn);  
            }  
            return conn;  
        }catch (Exception e) {
        	e.printStackTrace();
            throw new RuntimeException(e);  
        }  
    }  
      
      
    public static void start(){  
        try{  
            //得到当前线程上绑定连接开启事务  
            Connection conn = tl.get();  
            if(conn==null){  //代表线程上没有绑定连接  
                conn = ds.getConnection();  
                tl.set(conn);  
            }  
            conn.setAutoCommit(false);  
        }catch (Exception e) {  
        	e.printStackTrace();
            throw new RuntimeException(e);  
        }  
    }  
      
      
    public static void commit(){  
        try{  
            Connection conn = tl.get();  
            if(conn!=null){  
                conn.commit();  
            }  
        }catch (Exception e) {  
        	e.printStackTrace();
            throw new RuntimeException(e);  
        }  
    }  
      
    public static void close(){  
        try{  
            Connection conn = tl.get();  
            if(conn!=null){  
                conn.close();  
            }  
        }catch (Exception e) {  
        	e.printStackTrace();
            throw new RuntimeException(e);  
        }finally{  
            tl.remove();   //千万注意，解除当前线程上绑定的链接（从threadlocal容器中移除对应当前线程的链接）  
        }  
    }  
    
    public static void roleback(){  
        try{  
            Connection conn = tl.get();  
            if(conn!=null){  
                conn.rollback(); 
            }  
        }catch (Exception e) {  
        	e.printStackTrace();
            throw new RuntimeException(e);  
        } 
    } 
    
    public static void reset(Connection conn){  
        try{  
            if(conn!=null){   
                tl.set(conn); 
            }  
        }catch (Exception e) {  
        	e.printStackTrace();
            throw new RuntimeException(e);  
        } 
    }  
}
