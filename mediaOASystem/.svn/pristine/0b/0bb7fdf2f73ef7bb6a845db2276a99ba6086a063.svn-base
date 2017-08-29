package com.yuyu.soft.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import com.yuyu.soft.entity.OvertimeRecord;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 加班记录                      
 * @Filename: IOvertimeRecordService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IOvertimeRecordService {

    List<OvertimeRecord> queryOvertimeRecord(String hql, Map<String, Object> paramsMap,
                                             PagerInfo pager);

    List<Map<String, Object>> queryOvertimeRecordMap(String sql, Map<String, Object> paramsMap,
                                                     PagerInfo pager);

    OvertimeRecord getOvertimeRecord(Long id);

    void addOvertimeRecord(OvertimeRecord overtimeRecord);

    void updateOvertimeRecord(OvertimeRecord overtimeRecord);

    void delOvertimeRecord(OvertimeRecord overtimeRecord);

    /**
     * 根据用户ID和年月获取加班记录
     */
    OvertimeRecord getOvertimeRecordByUserIdAndDate(Long user_id, Date date);

    /**
     * 保存加班记录
     */
    ResultMsg save(HttpServletRequest request, Long id, Long user_id, String begin_time,
                   String end_time);

}
