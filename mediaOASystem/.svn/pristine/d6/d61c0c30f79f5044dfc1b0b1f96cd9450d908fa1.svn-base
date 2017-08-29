package com.yuyu.soft.entity;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.yuyu.soft.entity.common.CommonEntity;

@Entity
@Table(name = "tb_resume_others")
public class ResumeOthers extends CommonEntity {

    /**
     *Comment for <code>serialVersionUID</code>
     */
    private static final long serialVersionUID = 1L;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "resume_id")
    private Resume            resume;                       //简历
    private String            reward_name;                  //何时何地受到何种奖励
    private String            punishment_name;              //何时何地受到何种处分
    private String            dimission_reason;             //离职原因
    private String            contract_status_with_old_unit; //与原单位劳动合同情况
    private Integer           has_non_competition;          //有无竞业条例：0.无 1.有
    private String            apply_reason;                 //应聘原因
    private Integer           has_relatives_in_unit;        //有无亲属在本单位任职：0.无 1.有
    private Integer           accept_other_positions;       //如未能被申请岗位录用，有无其他意向岗位：0.无 1.有
    private Integer           accept_unit_adjustment;       //是否接受单位调剂：0.否 1.是
    private String            self_evaluation;              //个人评价
    private String            supplement_instruction;       //其他补充说明或其它个人特别的期望

    public Resume getResume() {
        return resume;
    }

    public void setResume(Resume resume) {
        this.resume = resume;
    }

    public String getReward_name() {
        return reward_name;
    }

    public void setReward_name(String reward_name) {
        this.reward_name = reward_name;
    }

    public String getPunishment_name() {
        return punishment_name;
    }

    public void setPunishment_name(String punishment_name) {
        this.punishment_name = punishment_name;
    }

    public String getDimission_reason() {
        return dimission_reason;
    }

    public void setDimission_reason(String dimission_reason) {
        this.dimission_reason = dimission_reason;
    }

    public String getContract_status_with_old_unit() {
        return contract_status_with_old_unit;
    }

    public void setContract_status_with_old_unit(String contract_status_with_old_unit) {
        this.contract_status_with_old_unit = contract_status_with_old_unit;
    }

    public Integer getHas_non_competition() {
        return has_non_competition;
    }

    public void setHas_non_competition(Integer has_non_competition) {
        this.has_non_competition = has_non_competition;
    }

    public String getApply_reason() {
        return apply_reason;
    }

    public void setApply_reason(String apply_reason) {
        this.apply_reason = apply_reason;
    }

    public Integer getHas_relatives_in_unit() {
        return has_relatives_in_unit;
    }

    public void setHas_relatives_in_unit(Integer has_relatives_in_unit) {
        this.has_relatives_in_unit = has_relatives_in_unit;
    }

    public Integer getAccept_other_positions() {
        return accept_other_positions;
    }

    public void setAccept_other_positions(Integer accept_other_positions) {
        this.accept_other_positions = accept_other_positions;
    }

    public Integer getAccept_unit_adjustment() {
        return accept_unit_adjustment;
    }

    public void setAccept_unit_adjustment(Integer accept_unit_adjustment) {
        this.accept_unit_adjustment = accept_unit_adjustment;
    }

    public String getSelf_evaluation() {
        return self_evaluation;
    }

    public void setSelf_evaluation(String self_evaluation) {
        this.self_evaluation = self_evaluation;
    }

    public String getSupplement_instruction() {
        return supplement_instruction;
    }

    public void setSupplement_instruction(String supplement_instruction) {
        this.supplement_instruction = supplement_instruction;
    }
}