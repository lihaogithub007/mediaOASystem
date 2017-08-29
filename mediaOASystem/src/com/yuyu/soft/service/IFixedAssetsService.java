package com.yuyu.soft.service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

import com.yuyu.soft.entity.FixedAssets;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

public interface IFixedAssetsService {

    /**
     * 查询固定资产列表(分页)
     */
    List<FixedAssets> queryFixedAssets(String hql, Map<String, Object> paramsMap, PagerInfo pager);

    /**
     * 根据ID获取固定资产对象
     */
    FixedAssets getFixedAssets(Long id);

    /**
     * 验证设备终身码是否存在
     */
    boolean verify_equipment_lifetime_number(Long fixed_assets_id, String equipment_lifetime_number);

    /**
     * 添加固定资产
     */
    public void addFixAssets(FixedAssets fixedAssets);

    /**
     * 添加固定资产保存
     */
    public ResultMsg add_save(FixedAssets fixedAssets, Long user_id, Long equipment_id, Long userId);

    /**
     * 编辑固定资产保存
     */
    public ResultMsg edit_save(FixedAssets fixedAssets, Long user_id, Long equipment_id, Long userId);

    /**
     * 删除固定资产
     */
    void delFixedAssets(FixedAssets fixedAssets);

    void batchDelFixedAssets(String delIds);

    /**
    * 查询固定资产数量(sql)
    * 用于导出Excel
    */
    int getCountForExportExcel(String equipment_lifetime_number, Long equipment_id,
                               String serial_number, String true_name, String user_card_number);

    /**
     * 查询固定资产(sql)
     * 用于导出Excel
     */
    List<Object[]> getFixedAssetsForExportExcel(String equipment_lifetime_number,
                                                Long equipment_id, String serial_number,
                                                String true_name, String user_card_number,
                                                int beginIndex, int pageSize);

    BigDecimal getTotalPriceForExportExcel(String equipment_lifetime_number, Long equipment_id,
                                           String serial_number, String true_name,
                                           String user_card_number);

}
