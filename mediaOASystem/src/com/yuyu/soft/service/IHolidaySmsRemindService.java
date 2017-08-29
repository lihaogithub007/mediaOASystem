package com.yuyu.soft.service;

import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.HolidaySmsRemind;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

/**
 * 节日短信问候接口
 *                       
 * @Filename: IHolidaySmsRemindService.java
 * @Version: 1.0
 * @Author: 李明
 * @Email: limn_xmj@163.com
 *
 */
public interface IHolidaySmsRemindService {

    /**
     * 查询今天的所有节日短信问候（启用状态）
     */
    List<HolidaySmsRemind> queryTodayHolidaySmsReminds();

    /**
     * 查询所有的生日短信问候（启用状态）
     */
    List<HolidaySmsRemind> queryTodayBirthDaySmsReminds();

    /**
     * 查询节日短信问候列表
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
    List<HolidaySmsRemind> queryHolidaySmsRemind(String hql, Map<String, Object> paramsMap,
                                                 PagerInfo pager);

    /**
     * 根据ID获取节日短信问候对象
     * @param id
     * @return
     */
    HolidaySmsRemind getHolidaySmsRemind(Long id);

    /**
     * 添加节日短信问候
     * @param holidaySmsRemind
     */
    void addHolidaySmsRemind(HolidaySmsRemind holidaySmsRemind);

    /**
     * 更新节日短信问候
     * @param holidaySmsRemind
     */
    public void updateHolidaySmsRemind(HolidaySmsRemind holidaySmsRemind);

    /**
     * 删除节日短信问候
     */
    public void delHolidaySmsRemind(HolidaySmsRemind holidaySmsRemind);

    /**
     * 添加节日短信问候保存
     */
    public ResultMsg add_save(HolidaySmsRemind holidaySmsRemind);

    /**
     * 节日短信问候编辑保存
     */
    public ResultMsg edit_save(HolidaySmsRemind holidaySmsRemind);

}
