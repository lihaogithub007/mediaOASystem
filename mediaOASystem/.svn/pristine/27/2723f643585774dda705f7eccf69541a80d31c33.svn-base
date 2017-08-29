package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.ResGroup;
import com.yuyu.soft.service.IResGroupService;

@Service("resGroupService")
public class ResGroupServiceImpl implements IResGroupService {
    @Resource
    private IBaseDao<ResGroup> resGroupDao;

    @Override
    public List<ResGroup> queryAllResGroup() {
        StringBuilder s = new StringBuilder();
        s.append("from ResGroup t where t.disabled = false");
        s.append(" order by t.id asc");
        return resGroupDao.find(s.toString());
    }

    @Override
    public ResGroup getResGroup(Long id) {
        return resGroupDao.get(ResGroup.class, id);
    }

    @Override
    public void addResGroup(ResGroup rg) {
        this.resGroupDao.save(rg);

    }

    @Override
    public ResGroup getByName(String rg_name) {
        List<ResGroup> rgList = queryByName(rg_name);
        if (rgList == null || rgList.size() == 0) {
            return null;
        }
        return rgList.get(0);
    }

    @Override
    public List<ResGroup> queryByName(String rg_name) {
        StringBuilder s = new StringBuilder();
        s.append("from ResGroup t where t.disabled = false");
        s.append(" and t.res_group_name = '").append(rg_name).append("'");
        s.append(" order by t.createtime desc");
        return resGroupDao.find(s.toString());
    }

    @Override
    public ResGroup add_save(String rg_name) {
        ResGroup rg = new ResGroup();
        rg.setDisabled(false);
        rg.setCreatetime(new Date());
        rg.setRes_group_name(rg_name);
        addResGroup(rg);
        return getResGroup(rg.getId());

    }
}
