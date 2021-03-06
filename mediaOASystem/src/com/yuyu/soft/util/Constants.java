package com.yuyu.soft.util;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class Constants {

    public static final String              DEFAULT_PASSWORD                   = "888888";

    /**
     * 验证码
     */
    public static final String              IMAGE_CODE                         = "image.code";

    /**
     * 分页的条数
     */
    public static final int                 DEFAULT_PAGE_SIZE                  = 10;

    /**
     * 导出excel的默认分页条数
     */
    public static final int                 DEFAULT_PAGE_SIZE_FOR_EXPORT_EXCEL = 50000;

    /**
     * 后台获取session中用户的key
     */
    public static final String              USER_SESSION_KEY                   = "loginedUser";

    /**
     * 上传根路径
     */
    public static final String              BASE_UPLOAD_PATH                   = "upload";

    /**
     * 角色所拥有的资源url  Map<String roleId, Set<String res_url>>
     */
    public static Map<String, Set<String>>  ROLE_URL                           = new HashMap<String, Set<String>>();

    /**
     * 角色所拥有的资源id  Map<String roleId, Set<String resId>>
     */
    public static Map<String, Set<Integer>> ROLE_RES_ID_MAP                    = new HashMap<String, Set<Integer>>();

    public static final Map<String, String> USER_RELATIONSHIP_MAP              = new LinkedHashMap<String, String>() {
                                                                                   {
                                                                                       put("0",
                                                                                           "台聘");
                                                                                       put("1",
                                                                                           "企聘");
                                                                                       put("2",
                                                                                           "视通聘");
                                                                                       put("3",
                                                                                           "外籍");
                                                                                       put("4",
                                                                                           "索贝聘");
                                                                                       put("5",
                                                                                           "前卫聘");
                                                                                   }
                                                                               };

    public static final Map<String, String> COST_COMPANY_MAP                   = new LinkedHashMap<String, String>() {
                                                                                   {
                                                                                       put("1",
                                                                                           "中视前卫");
                                                                                       put("2",
                                                                                           "国际视通");
                                                                                       put("3",
                                                                                           "成都索贝");
                                                                                   }
                                                                               };

    public static final Map<String, String> DISABLED_MAP                       = new LinkedHashMap<String, String>() {
                                                                                   {
                                                                                       put("0",
                                                                                           "启用");
                                                                                       put("1",
                                                                                           "关闭");
                                                                                   }
                                                                               };
    public static final Map<String, String> REMIND_TYLE_MAP                    = new LinkedHashMap<String, String>() {
                                                                                   {
                                                                                       put("0",
                                                                                           "节日短信");
                                                                                       put("1",
                                                                                           "生日短信");
                                                                                   }
                                                                               };
    public static final Map<String, String> BLOOD_TYPE_MAP                     = new LinkedHashMap<String, String>() {//血型
                                                                                   {
                                                                                       put("1", "A");
                                                                                       put("2", "B");
                                                                                       put("3", "O");
                                                                                       put("4",
                                                                                           "AB");
                                                                                       put("5",
                                                                                           "其他");
                                                                                   }
                                                                               };
    public static final Map<String, String> SEX_MAP                            = new LinkedHashMap<String, String>() {//性别
                                                                                   {
                                                                                       put("1", "男");
                                                                                       put("2", "女");
                                                                                   }
                                                                               };
    public static final Map<String, String> POLITICAL_STATUS_MAP               = new LinkedHashMap<String, String>() {//政治面貌
                                                                                   {
                                                                                       put("1",
                                                                                           "中共党员");
                                                                                       put("2",
                                                                                           "中共预备党员");
                                                                                       put("3",
                                                                                           "共青团员");
                                                                                       put("4",
                                                                                           "群众");
                                                                                       put("5",
                                                                                           "其他");
                                                                                   }
                                                                               };
    public static final Map<String, String> MARRIAGE_STATUS_MAP                = new LinkedHashMap<String, String>() {//婚姻状况
                                                                                   {
                                                                                       put("1",
                                                                                           "未婚");
                                                                                       put("2",
                                                                                           "已婚");
                                                                                       put("3",
                                                                                           "离异");
                                                                                       put("4",
                                                                                           "丧偶");
                                                                                   }
                                                                               };
    public static final Map<String, String> CONSTELLATION_MAP                  = new LinkedHashMap<String, String>() {//星座
                                                                                   {
                                                                                       put("1",
                                                                                           "白羊座(3.21-4.19)");
                                                                                       put("2",
                                                                                           "金牛座(4.20-5.20)");
                                                                                       put("3",
                                                                                           "双子座(5.21-6.21)");
                                                                                       put("4",
                                                                                           "巨蟹座(6.22-7.22)");
                                                                                       put("5",
                                                                                           "狮子座(7.23-8.22)");
                                                                                       put("6",
                                                                                           "处女座(8.23-9.22)");
                                                                                       put("7",
                                                                                           "天秤座(9.23-10.23)");
                                                                                       put("8",
                                                                                           "天蝎座(10.24-11.22)");
                                                                                       put("9",
                                                                                           "射手座(11.23-12.21)");
                                                                                       put("10",
                                                                                           "摩羯座(12.22-1.19)");
                                                                                       put("11",
                                                                                           "水瓶座(1.20-2.18)");
                                                                                       put("12",
                                                                                           "双鱼座(2.19-3.20)");
                                                                                   }
                                                                               };
    public static final Map<String, String> CONSTELLATION_NO_DATE_MAP          = new LinkedHashMap<String, String>() {//星座
                                                                                   {
                                                                                       put("1",
                                                                                           "白羊座");
                                                                                       put("2",
                                                                                           "金牛座");
                                                                                       put("3",
                                                                                           "双子座");
                                                                                       put("4",
                                                                                           "巨蟹座");
                                                                                       put("5",
                                                                                           "狮子座");
                                                                                       put("6",
                                                                                           "处女座");
                                                                                       put("7",
                                                                                           "天秤座");
                                                                                       put("8",
                                                                                           "天蝎座");
                                                                                       put("9",
                                                                                           "射手座");
                                                                                       put("10",
                                                                                           "摩羯座");
                                                                                       put("11",
                                                                                           "水瓶座");
                                                                                       put("12",
                                                                                           "双鱼座");
                                                                                   }
                                                                               };
    public static final Map<String, String> HAVE_OR_NOT_MAP                    = new LinkedHashMap<String, String>() {//有无
                                                                                   {
                                                                                       put("1", "有");
                                                                                       put("0", "无");
                                                                                   }
                                                                               };
    public static final Map<String, String> IS_OR_NOT_MAP                      = new LinkedHashMap<String, String>() {//是否
                                                                                   {
                                                                                       put("1", "是");
                                                                                       put("0", "否");
                                                                                   }
                                                                               };
    public static final Map<String, String> DOMICILE_TYPE_MAP                  = new LinkedHashMap<String, String>() {//户口性质
                                                                                   {
                                                                                       put("1",
                                                                                           "本市城镇");
                                                                                       put("2",
                                                                                           "本市农村");
                                                                                       put("3",
                                                                                           "外埠城镇");
                                                                                       put("4",
                                                                                           "外埠农村");
                                                                                   }
                                                                               };
    public static final Map<String, String> ID_TYPE_MAP                        = new LinkedHashMap<String, String>() {//证件类型
                                                                                   {
                                                                                       put("1",
                                                                                           "身份证");
                                                                                       put("2",
                                                                                           "护照");
                                                                                       put("3",
                                                                                           "军官证");
                                                                                   }
                                                                               };
    public static final Map<String, String> WORK_TYPE_MAP                      = new LinkedHashMap<String, String>() {//工作性质
                                                                                   {
                                                                                       put("1",
                                                                                           "全职");
                                                                                       put("2",
                                                                                           "兼职");
                                                                                       put("3",
                                                                                           "实习");
                                                                                   }
                                                                               };
    public static final Map<String, String> EDU_DEGREE_MAP                     = new LinkedHashMap<String, String>() {//学历
                                                                                   {
                                                                                       put("1",
                                                                                           "小学");
                                                                                       put("2",
                                                                                           "初中");
                                                                                       put("3",
                                                                                           "高中");
                                                                                       put("4",
                                                                                           "中专");
                                                                                       put("5",
                                                                                           "大专");
                                                                                       put("6",
                                                                                           "本科");
                                                                                       put("7",
                                                                                           "研究生");
                                                                                       put("8",
                                                                                           "博士生");
                                                                                   }
                                                                               };
    public static final Map<String, String> FAMILY_RELATIONSHIP_MAP            = new LinkedHashMap<String, String>() {//家庭成员与本人关系
                                                                                   {
                                                                                       put("1",
                                                                                           "父亲");
                                                                                       put("2",
                                                                                           "母亲");
                                                                                       put("5",
                                                                                           "配偶");
                                                                                       put("6",
                                                                                           "子女");
                                                                                       put("7",
                                                                                           "兄弟姐妹");
                                                                                   }
                                                                               };
    public static final Map<String, String> CONTRACT_TYPE_MAP                  = new LinkedHashMap<String, String>() {//合同类型
                                                                                   {
                                                                                       put("1",
                                                                                           "固定期限劳动合同");
                                                                                       put("2",
                                                                                           "无固定期限劳动合同");
                                                                                       put("3",
                                                                                           "实习合同");
                                                                                       put("4",
                                                                                           "劳务合同");
                                                                                   }
                                                                               };
    public static final Map<String, String> RESUME_STATUS_MAP                  = new LinkedHashMap<String, String>() {//简历状态
                                                                                   {
                                                                                       put("1",
                                                                                           "面试");
                                                                                       put("2",
                                                                                           "未录用");
                                                                                       put("3",
                                                                                           "已录用");
                                                                                   }
                                                                               };
    public static final Map<String, String> USER_STATUS_MAP                    = new LinkedHashMap<String, String>() {//简历状态
                                                                                   {
                                                                                       put("1",
                                                                                           "实习");
                                                                                       put("2",
                                                                                           "试用");
                                                                                       put("3",
                                                                                           "在职");
                                                                                       put("4",
                                                                                           "离职");
                                                                                   }
                                                                               };
    public static final Map<String, String> EDU_TYPE_MAP                       = new LinkedHashMap<String, String>() {//学习性质
                                                                                   {
                                                                                       put("1",
                                                                                           "统招");
                                                                                       put("2",
                                                                                           "自考");
                                                                                   }
                                                                               };
    public static final Map<String, String> HIRED_STATUS_MAP                   = new LinkedHashMap<String, String>() {//录用状态
                                                                                   {
                                                                                       put("1",
                                                                                           "实习");
                                                                                       put("2",
                                                                                           "试用");
                                                                                   }
                                                                               };
    public static final Map<String, String> APPROVAL_STATUS_MAP                = new LinkedHashMap<String, String>() {//审批状态
                                                                                   {
                                                                                       put("1",
                                                                                           "未提交");
                                                                                       put("2",
                                                                                           "已提交");
                                                                                   }
                                                                               };
    public static final Map<String, String> DIMISSION_REASON_MAP               = new LinkedHashMap<String, String>() {//离职原因
                                                                                   {
                                                                                       put("1",
                                                                                           "不适应企业文化和工作氛围");
                                                                                       put("2",
                                                                                           "与同事相处关系不融洽");
                                                                                       put("3",
                                                                                           "对直属领导不满意");
                                                                                       put("4",
                                                                                           "工作压力大");
                                                                                       put("5",
                                                                                           "工作能力欠缺不能胜任本岗位工作，自愿离职");
                                                                                       put("6",
                                                                                           "试用期考核不通过");
                                                                                       put("7",
                                                                                           "合同期满考核不通过");
                                                                                       put("8",
                                                                                           "不能接受倒班或加班");
                                                                                       put("9",
                                                                                           "违反制度规定被辞退");
                                                                                       put("10",
                                                                                           "薪酬福利不满意");
                                                                                       put("11",
                                                                                           "假期过少，没有休息时间");
                                                                                       put("12",
                                                                                           "无发展前景、无晋升机会");
                                                                                       put("13",
                                                                                           "与个人职业规划不符");
                                                                                       put("14",
                                                                                           "个人身体欠佳");
                                                                                       put("15",
                                                                                           "个人家庭原因");
                                                                                       put("16",
                                                                                               "个人原因");
                                                                                   }
                                                                               };
    public static final Map<String, String> LEAVE_TYPE_MAP                     = new LinkedHashMap<String, String>() {//请假种类
                                                                                   {
                                                                                       put("1",
                                                                                           "年假");
                                                                                       put("2",
                                                                                           "病假");
                                                                                       put("3",
                                                                                           "事假");
                                                                                       put("4",
                                                                                           "产检");
                                                                                       put("5",
                                                                                           "产假");
                                                                                       put("6",
                                                                                           "婚假");
                                                                                       put("7",
                                                                                           "丧假");
                                                                                   }
                                                                               };
    public static final Map<String, String> OVERTIME_STATUS_MAP                = new LinkedHashMap<String, String>() {//请假种类
                                                                                   {
                                                                                       put("1",
                                                                                           "工作日");
                                                                                       put("2",
                                                                                           "休息日");
                                                                                   }
                                                                               };

}
