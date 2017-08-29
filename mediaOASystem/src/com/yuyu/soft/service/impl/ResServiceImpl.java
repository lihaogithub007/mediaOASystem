package com.yuyu.soft.service.impl;

import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.yuyu.soft.dao.IBaseDao;
import com.yuyu.soft.entity.Res;
import com.yuyu.soft.entity.ResGroup;
import com.yuyu.soft.service.IResService;
import com.yuyu.soft.util.CommUtil;

@Service("resService")
public class ResServiceImpl implements IResService {

    @Resource
    private IBaseDao<Res> resDao;

    @Override
    public Res getRes(Long id) {
        return resDao.get(Res.class, id);
    }

    @Override
    public void addRes(Res res) {
        this.resDao.save(res);

    }

    @Override
    public List<Res> queryByUrl(String url) {
        StringBuilder s = new StringBuilder();
        s.append("from Res t where t.disabled = false");
        s.append(" and t.res_url = '").append(url).append("'");
        s.append(" order by t.createtime desc");
        return resDao.find(s.toString());
    }

    @Override
    public void add_save(String res_name, String res_url, ResGroup rg) {
        if (CommUtil.isBlank(res_url) || CommUtil.isBlank(res_name) || rg == null
            || rg.getId() == null) {
            return;
        }
        List<Res> tempList = queryByUrl(res_url);
        if (tempList == null || tempList.size() == 0) {
            Res res = new Res();
            res.setDisabled(false);
            res.setCreatetime(new Date());
            res.setRes_name(res_name);
            res.setRes_url(res_url);
            res.setResGroup(rg);
            addRes(res);
        }
    }

}
