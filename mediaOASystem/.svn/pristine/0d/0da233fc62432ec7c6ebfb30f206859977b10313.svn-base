package com.yuyu.soft.util.enums;

import java.util.ArrayList;
import java.util.List;

 

/**
 *                       
 * @Filename: OnOrOffEnum.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
public enum OnOrOffEnum {
	ON("1","启用"),	
	OFF("2","停用");
	
	private final String id;   
    private final String name; 
    OnOrOffEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public static List<OnOrOffEnum> getAll(){
		OnOrOffEnum[] enums = OnOrOffEnum.values();
		List<OnOrOffEnum> list = new ArrayList<OnOrOffEnum>();
		for(OnOrOffEnum one:enums){
			list.add(one);
		}
		return list;
	}
	
	public static OnOrOffEnum get(String id){
		OnOrOffEnum result = null;
		OnOrOffEnum[] enums = OnOrOffEnum.values();
		for(OnOrOffEnum one:enums){
			if(one.getId().equals(id)){
				result = one;
				break;
			}
		}
		return result;
	}
	
	public static OnOrOffEnum get(Integer id){
		OnOrOffEnum result = null;
		OnOrOffEnum[] enums = OnOrOffEnum.values();
		for(OnOrOffEnum one:enums){
			if((id + "").equals(one.getId())){
				result = one;
				break;
			}
		}
		return result;
	}
	
	public static void main(String args []){
		List<OnOrOffEnum> list = OnOrOffEnum.getAll();
		for(OnOrOffEnum one:list){
			System.out.println(one.getId());
		}
		
		System.out.println(OnOrOffEnum.get(1).getName());
	}

    
}
