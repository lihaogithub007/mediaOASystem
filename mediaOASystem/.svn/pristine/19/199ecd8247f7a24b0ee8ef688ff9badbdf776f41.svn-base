package com.yuyu.soft.util.enums;

import java.util.ArrayList;
import java.util.List;

 

/**删除标志
 *                       
 * @Filename: StatusEnum.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
public enum StatusEnum {
	NORMAL("1","正常"),	
	DEL("2","删除");
	
	private final String id;   
    private final String name; 
    StatusEnum(String id, String name) {
        this.id = id;
        this.name = name;
    }
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	
	public static List<StatusEnum> getAll(){
		StatusEnum[] enums=StatusEnum.values();
		List<StatusEnum> list =new ArrayList<StatusEnum>();
		for(StatusEnum one:enums){
			list.add(one);
		}
		return list;
	}
	
	public static StatusEnum get(String id){
		StatusEnum result=null;
		StatusEnum[] enums=StatusEnum.values();
		for(StatusEnum one:enums){
			if(one.getId().equals(id)){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static StatusEnum get(Integer id){
		StatusEnum result=null;
		StatusEnum[] enums=StatusEnum.values();
		for(StatusEnum one:enums){
			if((id+"").equals(one.getId())){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static void main(String args []){
		List<StatusEnum> list=StatusEnum.getAll();
		for(StatusEnum one:list){
			System.out.println(one.getId());
		}
		
		System.out.println(StatusEnum.get(1).getName());
	}

    
}
