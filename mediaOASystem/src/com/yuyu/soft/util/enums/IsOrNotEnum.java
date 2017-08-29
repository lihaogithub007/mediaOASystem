package com.yuyu.soft.util.enums;

import java.util.ArrayList;
import java.util.List;

 

/**是否
 *                       
 * @Filename: StatusEnum.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
public enum IsOrNotEnum {
	NO("1","否"),	
	IS("2","是");
	
	private final String id;   
    private final String name; 
    IsOrNotEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public static List<IsOrNotEnum> getAll(){
		IsOrNotEnum[] enums = IsOrNotEnum.values();
		List<IsOrNotEnum> list = new ArrayList<IsOrNotEnum>();
		for(IsOrNotEnum one:enums){
			list.add(one);
		}
		return list;
	}
	
	public static IsOrNotEnum get(String id){
		IsOrNotEnum result = null;
		IsOrNotEnum[] enums = IsOrNotEnum.values();
		for(IsOrNotEnum one:enums){
			if(one.getId().equals(id)){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static IsOrNotEnum get(Integer id){
		IsOrNotEnum result=null;
		IsOrNotEnum[] enums=IsOrNotEnum.values();
		for(IsOrNotEnum one:enums){
			if((id+"").equals(one.getId())){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static void main(String args []){
		List<IsOrNotEnum> list=IsOrNotEnum.getAll();
		for(IsOrNotEnum one:list){
			System.out.println(one.getId());
		}
		
		System.out.println(IsOrNotEnum.get(1).getName());
	}

    
}
