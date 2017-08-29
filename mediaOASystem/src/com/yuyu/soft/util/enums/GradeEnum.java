package com.yuyu.soft.util.enums;

import java.util.ArrayList;
import java.util.List;

 

/**会员等级
 *                       
 * @Filename: IntegrationTypeEnum.java
 * @Version: 1.0
 * @Author: 范光洲
 * @Email: goodfgz@163.com
 *
 */
public enum GradeEnum {
	//类型，名称，分数   会员积分小于哪个分数就属于某个等级
	xqb("1","学前班","10"),	
	xxs("2","小学生","50"),
	czs("3","初中生","100"),
	gzs("4","高中生","120"),
	dzs("5","大专生","150"),
	bks("6","本科生","300"),
	sss("7","硕士生","1000"),
	bss("8","博士生","2000"),
	fjs("9","副教授","5000"),
	js("10","教授","500000");
	
	private final String id;   
    private final String name; 
    private final String value; 
    GradeEnum(String id, String name,String value) {
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
	public String getValue() {
		return value;
	}
	public static List<GradeEnum> getAll(){
		GradeEnum[] enums=GradeEnum.values();
		List<GradeEnum> list =new ArrayList<GradeEnum>();
		for(GradeEnum one:enums){
			list.add(one);
		}
		return list;
	}
	
	public static GradeEnum get(String id){
		GradeEnum result=null;
		GradeEnum[] enums=GradeEnum.values();
		for(GradeEnum one:enums){
			if(one.getId().equals(id)){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static GradeEnum get(Integer id){
		GradeEnum result=null;
		GradeEnum[] enums=GradeEnum.values();
		for(GradeEnum one:enums){
			if((id+"").equals(one.getId())){
				result=one;
				break;
			}
		}
		return result;
	}
	
	public static void main(String args []){
		List<GradeEnum> list=GradeEnum.getAll();
		for(GradeEnum one:list){
			System.out.println(one.getId()+" = "+one.getName()+" = "+one.getValue());
		}
		
		System.out.println(GradeEnum.get(1).getValue());
		System.out.println(GradeEnum.get(1).getName());
	}

    
}
