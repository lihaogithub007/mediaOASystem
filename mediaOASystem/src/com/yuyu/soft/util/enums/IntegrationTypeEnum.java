package com.yuyu.soft.util.enums;

import java.util.ArrayList;
import java.util.List;

 

/**积分类型
 *                       
 * @Filename: IntegrationTypeEnum.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
public enum IntegrationTypeEnum {
	//类型，名称，分数
	POSTS("1","发帖",2),	
	REPLAY("2","回帖",1),
	LOGIN("3","登录",1),
	GOOD("4","精华帖",5),
	REGISTER("5","注册",9),
	POSTSDEL("6","删帖",-3);	
	
	private final String id;   
    private final String name; 
    private final Integer value; 
    IntegrationTypeEnum(String id, String name,Integer value) {
        this.id = id;
        this.name = name;
        this.value = value;
    }
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public Integer getValue() {
		return value;
	}
	public static List<IntegrationTypeEnum> getAll(){
		IntegrationTypeEnum[] enums=IntegrationTypeEnum.values();
		List<IntegrationTypeEnum> list =new ArrayList<IntegrationTypeEnum>();
		for(IntegrationTypeEnum one:enums){
			list.add(one);
		}
		return list;
	}
	
	public static IntegrationTypeEnum get(String id){
		IntegrationTypeEnum result=null;
		IntegrationTypeEnum[] enums=IntegrationTypeEnum.values();
		for(IntegrationTypeEnum one:enums){
			if(one.getId().equals(id)){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static IntegrationTypeEnum get(Integer id){
		IntegrationTypeEnum result=null;
		IntegrationTypeEnum[] enums=IntegrationTypeEnum.values();
		for(IntegrationTypeEnum one:enums){
			if((id+"").equals(one.getId())){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static void main(String args []){
		List<IntegrationTypeEnum> list=IntegrationTypeEnum.getAll();
		for(IntegrationTypeEnum one:list){
			System.out.println(one.getId()+" = "+one.getName()+" = "+one.getValue());
		}
		
		System.out.println(IntegrationTypeEnum.get(1).getValue());
		System.out.println(IntegrationTypeEnum.get(1).getName());
	}

    
}
