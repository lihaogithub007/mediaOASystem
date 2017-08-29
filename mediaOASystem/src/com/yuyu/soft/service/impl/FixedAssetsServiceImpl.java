package com.yuyu.soft.service.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.FixedAssets;
import com.yuyu.soft.service.IEquipmentService;
import com.yuyu.soft.service.IFixedAssetsService;
import com.yuyu.soft.service.IUserService;
import com.yuyu.soft.util.CommUtil;
import com.yuyu.soft.util.PagerInfo;
import com.yuyu.soft.util.ResultMsg;

@Service("fixedAssetsService")
public class FixedAssetsServiceImpl implements IFixedAssetsService {

    @Resource
    private IBaseDao<FixedAssets> fixedAssetsDao;
    @Resource
    private IEquipmentService     equipmentService;
    @Resource
    private IUserService          userService;

    /**
     * 查询固定资产列表(分页)
     * @param hql
     * @param paramsMap
     * @param pager
     * @return
     */
    public List<FixedAssets> queryFixedAssets(String hql, Map<String, Object> paramsMap,
                                              PagerInfo pager) {
        if (pager != null) {
            pager.setRowsCount(fixedAssetsDao.count("select count(t) " + hql, paramsMap));
            return fixedAssetsDao.find(hql, paramsMap, pager.getStart(), pager.getPageSize());
        } else {
            return fixedAssetsDao.find(hql, paramsMap);
        }
    }

    /**
     * 根据ID获取固定资产对象
     */
    public FixedAssets getFixedAssets(Long id) {
        return fixedAssetsDao.get(FixedAssets.class, id);
    }

    /**
     * 验证设备终身码是否存在
     */
    public boolean verify_equipment_lifetime_number(Long fixed_assets_id,
                                                    String equipment_lifetime_number) {
        boolean flag = true;

        Map<String, Object> paramsMap = new HashMap<String, Object>();
        paramsMap.put("equipment_lifetime_number", equipment_lifetime_number);
        StringBuilder s = new StringBuilder();
        s.append("from FixedAssets t where t.disabled = false");
        s.append(" and t.equipment_lifetime_number =:equipment_lifetime_number");
        if (CommUtil.isNotNull(fixed_assets_id)) {
            s.append(" and t.id !=:fixed_assets_id");
            paramsMap.put("fixed_assets_id", fixed_assets_id);
        }
        List<FixedAssets> fixedAssets = fixedAssetsDao.find(s.toString(), paramsMap);
        if ((fixedAssets != null) && (fixedAssets.size() > 0)) {
            flag = false;
        }
        return flag;
    }

    /**
     * 添加固定资产保存
     */
    public ResultMsg add_save(FixedAssets fixedAssets, Long user_id, Long equipment_id, Long userId) {
        ResultMsg rmg = save_check(fixedAssets, user_id, equipment_id, userId);
        if (!rmg.getResult()) {
            return rmg;
        }
        fixedAssets.setDisabled(false);
        fixedAssets.setCreatetime(new Date());
        fixedAssets.setUser(userService.getUser(user_id));
        fixedAssets.setEquipment(equipmentService.getEquipment(equipment_id));
        addFixAssets(fixedAssets);
        return CommUtil.setResultMsgData(rmg, true, "固定资产保存成功");

    }

    public void addFixAssets(FixedAssets fixedAssets) {
        this.fixedAssetsDao.save(fixedAssets);
    }

    private ResultMsg save_check(FixedAssets fixedAssets, Long user_id, Long equipment_id,
                                 Long userId) {

        if (fixedAssets == null) {
            return CommUtil.setResultMsgData(null, false, "固定资产对象为空");
        }
        if (userId == null) {
            return CommUtil.setResultMsgData(null, false, "登录超时，请重新登录后操作");
        }
        if (CommUtil.isBlank(fixedAssets.getEquipment_lifetime_number())) {
            return CommUtil.setResultMsgData(null, false, "设备终身码不能为空");
        } else if (fixedAssets.getEquipment_lifetime_number().trim().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "设备终身码最多50字符");
        }

        if (CommUtil.isBlank(fixedAssets.getEquipment_model())) {
            return CommUtil.setResultMsgData(null, false, "设备型号不能为空");
        } else if (fixedAssets.getEquipment_model().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "设备型号最多50字符");
        }
        if (CommUtil.isBlank(fixedAssets.getSerial_number())) {
            return CommUtil.setResultMsgData(null, false, "序列号不能为空");
        } else if (fixedAssets.getSerial_number().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "序列号最多50字符");
        }
        if (fixedAssets.getUnit_price() == null || "".equals(fixedAssets.getUnit_price())) {
            return CommUtil.setResultMsgData(null, false, "单价不能为空");
        } else if (fixedAssets.getUnit_price().toString().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "单间最多50字符");
        }
        if (CommUtil.isBlank(fixedAssets.getManufacturer_name())) {
            return CommUtil.setResultMsgData(null, false, "生产厂家不能为空");
        } else if (fixedAssets.getManufacturer_name().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "生产厂家最多50字符");
        }
        if (CommUtil.isBlank(user_id.toString())) {
            return CommUtil.setResultMsgData(null, false, "使用人不能为空");
        } else if (user_id.toString().length() > 50) {
            return CommUtil.setResultMsgData(null, false, "使用人最多50字符");
        }
        //        if (outsideIncomeDispatches.getIncome_dispatches_time() == null) {
        //            return CommUtil.setResultMsgData(null, false, "收文日期不能为空");
        //        }
        return CommUtil.setResultMsgData(null, true, "台外收文对象校验成功");
    }

    @Override
    public ResultMsg edit_save(FixedAssets fixedAssets, Long user_id, Long equipment_id, Long userId) {
        ResultMsg rmg = save_check(fixedAssets, user_id, equipment_id, userId);
        if (!rmg.getResult()) {
            return rmg;
        }

        updateFixedAssets(fixedAssets);
        return CommUtil.setResultMsgData(rmg, true, "固定资产编辑保存成功");
    }

    private void updateFixedAssets(FixedAssets fixedAssets) {
        this.fixedAssetsDao.update(fixedAssets);
    }

    /**
     * 删除固定资产
     */
    public void delFixedAssets(FixedAssets fixedAssets) {
        fixedAssets.setDisabled(true);
        updateFixedAssets(fixedAssets);
    }

    @Override
    public void batchDelFixedAssets(String delIds) {
        String[] ids = delIds.split(",");
        for (int i = 0; i < ids.length; i++) {
            FixedAssets fixedAssets = getFixedAssets(CommUtil.null2Long(ids[i]));
            if (fixedAssets != null) {
                delFixedAssets(fixedAssets);
            }
        }
    }

    /**
     * 查询固定资产数量(sql)
     * 用于导出Excel
     */
    public int getCountForExportExcel(String equipment_lifetime_number, Long equipment_id,
                                      String serial_number, String true_name,
                                      String user_card_number) {
        String sql = getCountSQL()
                     + getCommonSQL(equipment_lifetime_number, equipment_id, serial_number,
                         true_name, user_card_number);
        return fixedAssetsDao.countBySql(sql, null);
    }

    @Override
    public BigDecimal getTotalPriceForExportExcel(String equipment_lifetime_number,
                                                  Long equipment_id, String serial_number,
                                                  String true_name, String user_card_number) {
        String sql = "select sum(coalesce(unit_price,0))"
                     + getCommonSQL(equipment_lifetime_number, equipment_id, serial_number,
                         true_name, user_card_number);
        List<BigDecimal> tempList = fixedAssetsDao.executeNativeQuery(sql, null, 0, -1);
        if (tempList == null || tempList.size() != 1) {
            return null;
        }
        return tempList.get(0);
    }

    private String getCommonSQL(String equipment_lifetime_number, Long equipment_id,
                                String serial_number, String true_name, String user_card_number) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_fixed_assets fa");
        s.append(" left join tb_equipment eq");
        s.append(" on fa.equipment_id = eq.id");
        s.append(" where fa.disabled = 0");
        if (CommUtil.isNotNull(equipment_lifetime_number)) {
            s.append(" and fa.equipment_lifetime_number like '%").append(equipment_lifetime_number)
                .append("%'");
        }
        if (CommUtil.isNotNull(equipment_id)) {
            s.append(" and eq.id = ").append(equipment_id);
        }
        if (CommUtil.isNotNull(serial_number)) {
            s.append(" and fa.serial_number like '%").append(serial_number).append("%'");
        }
        if (CommUtil.isNotNull(true_name)) {
            s.append(" and fa.true_name like '%").append(true_name).append("%'");
        }
        if (CommUtil.isNotNull(user_card_number)) {
            s.append(" and fa.user_card_number like '%").append(user_card_number).append("%'");
        }
        return s.toString();
    }

    private String getCountSQL() {
        return "select count(fa.id)";
    }

    /**
     * 查询固定资产(sql)
     * 用于导出Excel
     */
    public List<Object[]> getFixedAssetsForExportExcel(String equipment_lifetime_number,
                                                       Long equipment_id, String serial_number,
                                                       String true_name, String user_card_number,
                                                       int beginIndex, int pageSize) {
        String sql = getQueryListSQL()
                     + getCommonListSQL(equipment_lifetime_number, equipment_id, serial_number,
                         true_name, user_card_number);

        List<Object[]> list = (ArrayList<Object[]>) fixedAssetsDao.findBySql(sql, null, beginIndex,
            pageSize);

        return list;
    }

    private String getCommonListSQL(String equipment_lifetime_number, Long equipment_id,
                                    String serial_number, String true_name, String user_card_number) {
        StringBuffer s = new StringBuffer();
        s.append(" from tb_fixed_assets fa");
        s.append(" left join tb_equipment eq on fa.equipment_id = eq.id");
        s.append(" left join tb_user u on fa.user_id = u.id");
        s.append(" where fa.disabled = 0");
        if (CommUtil.isNotNull(equipment_lifetime_number)) {
            s.append(" and fa.equipment_lifetime_number like '%").append(equipment_lifetime_number)
                .append("%'");
        }
        if (CommUtil.isNotNull(equipment_id)) {
            s.append(" and eq.id = ").append(equipment_id);
        }
        if (CommUtil.isNotNull(serial_number)) {
            s.append(" and fa.serial_number like '%").append(serial_number).append("%'");
        }
        if (CommUtil.isNotNull(true_name)) {
            s.append(" and fa.true_name like '%").append(true_name).append("%'");
        }
        if (CommUtil.isNotNull(user_card_number)) {
            s.append(" and fa.user_card_number like '%").append(user_card_number).append("%'");
        }
        return s.toString();
    }

    private String getQueryListSQL() {
        StringBuffer s = new StringBuffer();
        //        select 
        //        fa.equipment_lifetime_number ,
        //        eq.equipment_name,
        //        fa.equipment_model,
        //        fa.serial_number,
        //        fa.unit_price ,
        //        fa.manufacturer_name,
        //        u.true_name,
        //        fa.user_card_number,
        //        fa.user_company ,
        //        fa.equipment_place ,
        //        fa.remark
        s.append("select ");
        s.append(" fa.equipment_lifetime_number,");//设备终身码
        s.append(" eq.equipment_name,");//设备名称
        s.append(" fa.equipment_model,");//型号
        s.append(" fa.serial_number,");//序列号
        s.append(" fa.unit_price,");//单价
        s.append(" fa.manufacturer_name,");//生产厂家
        s.append(" u.true_name,");//使用人
        s.append(" fa.user_card_number,");//工作证号
        s.append(" fa.user_company,");//使用部门
        s.append(" fa.equipment_place,");//设备位置
        s.append(" fa.remark");//备注
        return s.toString();
    }

}
