package com.fh.util;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanInitializationException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import com.fh.util.express.constant.PropertyConstant;

public class PropertyPlaceholderConfigurerExt extends PropertyPlaceholderConfigurer{
	 protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
	            throws BeansException {
	        try {
	            String url = props.getProperty(PropertyConstant.JDBC_URL);
	            if (url != null)
	                props.setProperty(PropertyConstant.JDBC_URL, DESUtil.getDecryptString(url));
	            String username = props.getProperty(PropertyConstant.JDBC_USERNAME);
	            if (username != null)
	                props.setProperty(PropertyConstant.JDBC_USERNAME, DESUtil.getDecryptString(username));
	            String password = props.getProperty(PropertyConstant.JDBC_PASSWORD);
	            if (password != null)
	            props.setProperty(PropertyConstant.JDBC_PASSWORD, DESUtil.getDecryptString(password));
	            String url3 = props.getProperty(PropertyConstant.JDBC_URL3);
	            if (url3 != null)
	                props.setProperty(PropertyConstant.JDBC_URL3, DESUtil.getDecryptString(url3));
	            String username3 = props.getProperty(PropertyConstant.JDBC_USERNAME3);
	            if (username3 != null)
	                props.setProperty(PropertyConstant.JDBC_USERNAME3, DESUtil.getDecryptString(username3));
	            String password3 = props.getProperty(PropertyConstant.JDBC_PASSWORD3);
	            if (password3 != null)
	                props.setProperty(PropertyConstant.JDBC_PASSWORD3, DESUtil.getDecryptString(password3));
	            super.processProperties(beanFactory, props);
	        } catch (Exception e) {
	            e.printStackTrace();
	            throw new BeanInitializationException(e.getMessage());
	        }
	    }

}
