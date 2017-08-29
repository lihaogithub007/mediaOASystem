package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.ForeignExpert;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 外籍专家
 *                       
 * @Filename: IForeignExpertService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IForeignExpertService {

    List<ForeignExpert> queryForeignExpert(String hql, Map<String, Object> paramsMap,
                                           PagerInfo pager);

    ForeignExpert getForeignExpert(Long id);

    void addForeignExpert(ForeignExpert foreignExpert);

    void updateForeignExpert(ForeignExpert foreignExpert);

    void delForeignExpert(ForeignExpert foreignExpert);

    ResultMsg add_save(ForeignExpert foreignExpert, Long duty_id);

    ResultMsg edit_save(ForeignExpert foreignExpert, Long duty_id);

    boolean verify_foreign_expert_mobile(Long foreign_expert_id, String mobile);

    
	int getCountForExportExcel(String chinese_name, String english_name, String nationality, String card_number,
			String mobile);

	List<Object[]> getForeignExpertsForExportExcel(String chinese_name, String english_name, String nationality,
			String card_number, String mobile, int beginIndex, int pageSize);

}
