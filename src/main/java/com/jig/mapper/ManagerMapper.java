package com.jig.mapper;

import com.jig.entity.common.User;
import com.jig.entity.jig.JigDefinition;
import com.jig.entity.jig.JigEntity;
import com.jig.entity.operate.Operate;
import com.jig.entity.purchase.PurchaseIncomeSubmit;
import com.jig.entity.purchase.PurchaseTotalSubmitManDetail;
import com.jig.entity.scrap.ScrapSubmit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface ManagerMapper {
    /**
     * 获取经理模块下的采购管理菜单下的采购审批
     *
     * @param user_id
     * @return
     */
    List<PurchaseIncomeSubmit> get_manager_purchase_submit_list(@Param("user_id") String user_id,
                                                                @Param("page_number") int page_number,
                                                                @Param("page_size") int page_size,
                                                                @Param("workcell_id") String workcell_id);

    int get_manager_purchase_submit_list_pages(@Param("user_id") String user_id,
                                               @Param("workcell_id") String workcell_id);

    PurchaseIncomeSubmit get_manager_purchase_detail(@Param("id") String id);

    /**
     * 经理模式下审批通过采购单
     *
     * @param id
     * @param pass 采购审批通过后的状态
     * @return
     */
    int manager_pass_purchase_submit(@Param("id") String id,
                                     @Param("pass") String pass,
                                     @Param("user") User user,
                                     @Param("field") String field,
                                     @Param("old_value") String old_value,
                                     @Param("new_value") String new_value);

    int manager_no_pass_purchase_submit(@Param("id") String id,
                                        @Param("status") String status,
                                        @Param("final_reason") String final_reason,
                                        @Param("user") User user,
                                        @Param("field") String field,
                                        @Param("old_value") String old_value,
                                        @Param("new_value") String new_value);

    List<JigDefinition> get_manager_jig_info_list(@Param("page_number") int page_number, @Param("page_size") int page_size);

    int get_manager_jig_info_list_pages();

    /**
     * 经理模式下获取和查询历史采购记录及页数
     *
     * @param bill_no     单据号
     * @param submit_name 申请人名字
     * @param start_date  开始时间
     * @param end_date    结束时间
     * @param status      状态
     * @param page_number 当前页数
     * @param workcell_id 工作部门id
     * @return
     */
    List<PurchaseIncomeSubmit> get_manager_purchase_submit_list_history(@Param("bill_no") String bill_no,
                                                                        @Param("submit_name") String submit_name,
                                                                        @Param("start_date") String start_date,
                                                                        @Param("end_date") String end_date,
                                                                        @Param("status") String status,
                                                                        @Param("page_number") int page_number,
                                                                        @Param("page_size") int page_size,
                                                                        @Param("workcell_id") String workcell_id);

    int get_manager_purchase_submit_list_history_pages(@Param("bill_no") String bill_no,
                                                       @Param("submit_name") String submit_name,
                                                       @Param("start_date") String start_date,
                                                       @Param("end_date") String end_date,
                                                       @Param("status") String status,
                                                       @Param("workcell_id") String workcell_id);

    /**
     * 获取经理模块下/历史采购记录/查看历史操作记录
     *
     * @param submit_id   要查询的记录id
     * @param workcell_id
     * @return
     */
    List<Operate> manager_get_purchase_submit_history_operate(@Param("submit_id") String submit_id,
                                                              @Param("workcell_id") String workcell_id);

    /**
     * 获取左侧菜单栏的通知消息数量
     *
     * @return
     */
    int get_manager_purchase_submit_count();

    int get_manager_scrap_submit_count();

    /**
     *
     */
    List<ScrapSubmit> manager_get_scrap_submit_list(@Param("user_id") String user_id,
                                                    @Param("page_number") int page_number,
                                                    @Param("page_size") int page_size,
                                                    @Param("workcell_id") String workcell_id);

    int manager_get_scrap_submit_list_pages(@Param("user_id") String user_id,
                                            @Param("workcell_id") String workcell_id);

    /**
     * 经理模式审批报废申请
     *
     * @param submit_id 报废申请的id
     * @param status    经过审批之后报废申请的最终状态
     *                  分为两步 1.更新
     */
    int manager_pass_scrap_submit(@Param("submit_id") String submit_id,
                                  @Param("status") String status,
                                  @Param("user") User user,
                                  @Param("field") String field,
                                  @Param("old_value") String old_value,
                                  @Param("new_value") String new_value);

    int manager_no_pass_scrap_submit(@Param("submit_id") String submit_id,
                                     @Param("no_pass_reason") String no_pass_reason,
                                     @Param("user") User user,
                                     @Param("field") String field,
                                     @Param("old_value") String old_value,
                                     @Param("new_value") String new_value);

    /**
     * 经理模式下历史报废审批记录的显示及其页数查询
     */
    List<ScrapSubmit> get_manager_scrap_submit_list_history(@Param("code") String code,
                                                            @Param("seq_id") String seq_id,
                                                            @Param("start_date") String start_date,
                                                            @Param("end_date") String end_date,
                                                            @Param("status") String status,
                                                            @Param("scrap_reason") String scrap_reason,
                                                            @Param("page_number") int page_number,
                                                            @Param("page_size") int page_size,
                                                            @Param("workcell_id") String workcell_id);

    int get_manager_scrap_submit_list_history_pages(@Param("code") String code,
                                                    @Param("seq_id") String seq_id,
                                                    @Param("start_date") String start_date,
                                                    @Param("end_date") String end_date,
                                                    @Param("status") String status,
                                                    @Param("scrap_reason") String scrap_reason,
                                                    @Param("workcell_id") String workcell_id);

    /**
     * 经理模式,获取仓库工夹具余量
     *
     * @return 工夹具实体数组
     */
    List<JigEntity> get_manager_store_jig_list();

    /**
     * 经理模式，获取采购统计模块下的采购员细节数据
     *
     * @param bill_no
     * @param submit_name
     * @param start_date
     * @param end_date
     * @param status      审批状态 默认是4
     * @return
     */
    List<PurchaseTotalSubmitManDetail> get_manager_purchase_total_submit_man(@Param("bill_no") String bill_no,
                                                                             @Param("submit_name") String submit_name,
                                                                             @Param("start_date") String start_date,
                                                                             @Param("end_date") String end_date,
                                                                             @Param("status") String status);

    List<PurchaseIncomeSubmit> get_all_manager_purchase_submit_list_history(@Param("bill_no") String bill_no, @Param("submit_name") String submit_name, @Param("start_date") String start_date, @Param("end_date") String end_date, @Param("status") String status, @Param("workcell_id") String workcell_id);

    List<ScrapSubmit> get_all_manager_scrap_submit_list_history(@Param("code") String code, @Param("seq_id") String seq_id, @Param("start_date") String start_date, @Param("end_date") String end_date, @Param("status") String status, @Param("scrap_reason") String scrap_reason, @Param("workcell_id") String workcell_id);
}
