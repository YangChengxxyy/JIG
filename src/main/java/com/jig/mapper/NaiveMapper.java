package com.jig.mapper;

import com.jig.entity.common.User;
import com.jig.entity.jig.JigDefinition;
import com.jig.entity.jig.JigEntity;
import com.jig.entity.jig.JigPosition;
import com.jig.entity.jig.JigStock;
import com.jig.entity.out.OutgoSubmit;
import com.jig.entity.out.OutgoingJig;
import com.jig.entity.purchase.PendingPuchaseIncomeSubmit;
import com.jig.entity.repair.MaintenanceSubmit;
import com.jig.entity.repair.PendingRepairSubmit;
import com.jig.entity.repair.RepairJig;
import com.jig.entity.repair.RepairJigHistory;
import com.jig.entity.scrap.PendingScrapSubmit;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Mapper
@Repository
public interface NaiveMapper {

    int naive_input_purchase_jig(@Param("bill_no") String bill_no,
                                 @Param("code") String code,
                                 @Param("seq_id") int seq_id,
                                 @Param("jig_cabinet") String jig_cabinet,
                                 @Param("location") String location,
                                 @Param("description") String description,
                                 @Param("user_id") String user_id);

    /**
     * 将采购入库的工夹具入库
     *
     * @param bill_no     相关订单单据号
     * @param code
     * @param seq_id
     * @param jig_cabinet 夹具柜id
     * @param location    存放区域
     * @param bin         bin位
     * @param description 写入出入库历史的描述信息
     * @param user_id     入库人
     * @return
     */
    int naiveInputJigEntity(@Param("bill_no") String bill_no,
                            @Param("code") String code,
                            @Param("seq_id") int seq_id,
                            @Param("jig_cabinet") String jig_cabinet,
                            @Param("location") String location,
                            @Param("bin") String bin,
                            @Param("description") String description,
                            @Param("user_id") String user_id);

    /**
     * 初级用户获取 工夹具出库页面的工夹具存放位置list
     *
     * @param workcell_id 工作部门
     * @return
     */
    List<JigPosition> navieGetLocationList(@Param("workcell_id") String workcell_id);

    //
    /*List<JigEntity> navie_get_jig_list_by_location(@Param("jig_cabinet_id") String jig_cabinet,
                                                   @Param("jig_location_id") String jig_location_id,
                                                   @Param("worcell_id") String workcell);*/

    /**
     * 初级用户 根据选择的 夹具柜号和区号确定 工夹具的种类,名字,数目
     *
     * @param jig_cabinet_id
     * @param jig_location_id
     * @param workcell_id
     * @return
     */
    List<JigStock> navieGetJigListByLocation(@Param("jig_cabinet_id") String jig_cabinet_id,
                                             @Param("jig_location_id") String jig_location_id,
                                             @Param("workcell_id") String workcell_id,
                                             @Param("page_number") int page_number,
                                             @Param("page_size") int page_size);

    List<List<?>> navieGetJigListByLocationPages(@Param("jig_cabinet_id") String jig_cabinet_id,
                                                 @Param("jig_location_id") String jig_location_id,
                                                 @Param("workcell_id") String workcell_id);

    /**
     * 初级用户 根据选择的 夹具柜号和区号 确定该code的工夹具list
     *
     * @param jig_cabinet_id
     * @param jig_location_id
     * @param code
     * @return
     */
    List<JigEntity> navieGetJigEntityListByLocation(@Param("jig_cabinet_id") String jig_cabinet_id,
                                                    @Param("jig_location_id") String jig_location_id,
                                                    @Param("code") String code);

    /**
     * 初级用户 根据选择的工夹具存放区域 和 搜索条件来 确定工夹具list
     *
     * @param jig_cabinet_id
     * @param jig_location_id
     * @param code
     * @param name
     * @param user_for
     * @param workcell_id
     * @return
     */
    List<JigStock> navieGetJigListBySelect(@Param("jig_cabinet_id") String jig_cabinet_id,
                                           @Param("jig_location_id") String jig_location_id,
                                           @Param("code") String code,
                                           @Param("name") String name,
                                           @Param("user_for") String user_for,
                                           @Param("workcell_id") String workcell_id,
                                           @Param("page_number") int page_number,
                                           @Param("page_size") int page_size);

    //获取记录总条数
    List<List<?>> navieGetJigListBySelectPages(@Param("jig_cabinet_id") String jig_cabinet_id,
                                               @Param("jig_location_id") String jig_location_id,
                                               @Param("code") String code,
                                               @Param("name") String name,
                                               @Param("user_for") String user_for,
                                               @Param("workcell_id") String workcell_id);

    //根据code和seq_id来获取该工夹具实体的出入库历史list
    List<OutgoSubmit> naive_get_out_and_in_history_list(@Param("code") String code,
                                                        @Param("seq_id") String seq_id);

    //根据code和seq_id来获取该工夹具实体的检点历史记录list
    List<MaintenanceSubmit> naive_get_maintenance_history_list(@Param("code") String code,
                                                               @Param("seq_id") String seq_id);


    int naive_change_jig_position(@Param("jig_id") String jig_id,
                                  @Param("code") String code,
                                  @Param("seq_id") String seq_id,
                                  @Param("old_position") String old_position,
                                  @Param("jig_cabinet_id") String jig_cabinet_id,
                                  @Param("location_id") String location_id,
                                  @Param("bin") String bin,
                                  @Param("user") User user);

    //初级用户 根据夹具柜号和区号确定 检点的工夹具list
    List<JigEntity> navieGetMaintenanceJigDetailList(@Param("jig_cabinet_id") String jig_cabinet_id,
                                                     @Param("jig_location_id") String jig_location_id,
                                                     @Param("code") String code);


    /**
     * 工夹具出库
     *
     * @param code      工夹具代码
     * @param seq_id    工夹具序列号
     * @param submit_id 申请人id
     * @param rec_id    记录人id
     */
    void naiveOutgoJig(@Param("jig_id") String jig_id,
                       @Param("code") String code,
                       @Param("seq_id") String seq_id,
                       @Param("submit_id") String submit_id,
                       @Param("production_line_id") String production_line_id,
                       @Param("rec_id") String rec_id);

    /**
     * 工夹具检点
     *
     * @param code
     * @param seq_id
     * @param reason  出现的问题,多个用|分开
     * @param user_id
     * @return
     */
    int naive_maintenance_jig(@Param("code") String code,
                              @Param("seq_id") String seq_id,
                              @Param("reason") String reason,
                              @Param("is_repair") int is_repair,
                              @Param("user_id") String user_id);

    /**
     * 获得已出库工夹具OutgoingJig对象
     *
     * @return 已出库工夹具OutgoingJig对象
     */
    List<OutgoingJig> naiveGetOutgoingJigList(@Param("code") String code,
                                              @Param("name") String name,
                                              @Param("start_date") String start_date,
                                              @Param("end_date") String end_date,
                                              @Param("user_for") String user_for,
                                              @Param("page_number") int page_number,
                                              @Param("page_size") int page_size,
                                              @Param("workcell_id") String workcell_id);


    /**
     * 获得已出库工夹具OutgoingJig对象所有页数
     *
     * @return 已出库工夹具OutgoingJig对象
     */
    List<OutgoingJig> naiveGetOutgoingJigListAllPages(@Param("code") String code,
                                                      @Param("name") String name,
                                                      @Param("start_date") String start_date,
                                                      @Param("end_date") String end_date,
                                                      @Param("user_for") String user_for,
                                                      @Param("workcell_id") String workcell_id);

    /**
     * 获得已出库工夹具记录数
     *
     * @return 已出库工夹具记录数
     */
    int getOutgoingJigListPage(@Param("code") String code,
                               @Param("name") String name,
                               @Param("start_date") String start_date,
                               @Param("end_date") String end_date,
                               @Param("user_for") String user_for,
                               @Param("workcell_id") String workcell_id);

    /**
     * 工夹具入库
     *
     * @param id     outgoing_jig表id
     * @param code   工夹具代码
     * @param seq_id 工夹具序列号
     * @param rec_id 记录人id
     */
    void naiveReturnJig(@Param("id") String id,
                        @Param("code") String code,
                        @Param("seq_id") String seq_id,
                        @Param("submit_id") String submit_id,
                        @Param("rec_id") String rec_id,
                        @Param("production_line_id") String production_line_id);

    /**
     * 删除对应的记录
     *
     * @param id outgoing_jig表id
     */
    void naiveDeleteOutgoingJig(@Param("id") String id);

    List<RepairJigHistory> naiveGetRepairHistory(@Param("submit_id") String submit_id, @Param("page_number") int page_number, @Param("page_size") int page_size);

    int naiveGetRepairHistoryPage(@Param("submit_id") String submit_id);

    /**
     * 初级用户获取报修记录
     *
     * @param submit_id
     * @param page_number
     * @return
     */
    List<RepairJig> naiveGetRepairList(@Param("submit_id") String submit_id, @Param("page_number") int page_number, @Param("page_size") int page_size);

    /**
     * @param submit_id
     * @return
     */
    int naiveGetRepairListPage(@Param("submit_id") String submit_id);

    /**
     * 初级用户提交报修
     *
     * @param repair
     * @param pathName
     */
    void naiveSubmitRepair(@Param("repair") RepairJig repair, @Param("pathName") String pathName);

    /**
     * 获取查询到的对应页数的List对象
     *
     * @param code       工夹具代码
     * @param name       工夹具名字
     * @param workcell   工作部门
     * @param family     类别
     * @param userFor    用途
     * @param pageNumber 页码
     * @return 查询到的对应页数的List对象
     */
    List<JigDefinition> naiveSearchJigDefinition(@Param("code") String code, @Param("name") String name, @Param("workcell") String workcell, @Param("family") String family, @Param("userFor") String userFor, @Param("pageNumber") int pageNumber, @Param("page_size") int page_size);

    /**
     * 获取查询到的结果总页数
     *
     * @param code     工夹具代码
     * @param name     工夹具名字
     * @param workcell 工作部门
     * @param family   类别
     * @param userFor  用途
     * @return 查询到的结果总页数
     */
    int naiveSearchJigDefinitionPage(@Param("code") String code, @Param("name") String name, @Param("workcell") String workcell, @Param("family") String family, @Param("userFor") String userFor);

    /**
     * 通过Mybatis的方法来实现查询到所有的工夹具List
     *
     * @param code     工夹具代码
     * @param name     工夹具名字
     * @param workcell 工作部门
     * @param family   类别
     * @param userFor  用途
     * @return 查询到的所有结果的List对象
     */
    List<JigDefinition> searchAllJigDefinition(@Param("code") String code, @Param("name") String name, @Param("workcell") String workcell, @Param("family") String family, @Param("userFor") String userFor);

    /**
     * 获取出库申请集合
     *
     * @return 出库申请集合
     */
    List<OutgoSubmit> naiveGetOutgoingSubmit(@Param("page_number") int page_number);

    /**
     * 获取出库记录条数
     *
     * @return 出库记录条数
     */
    int naiveGetOutgoSubmitPage();

    int getMaxSeqId(@Param("code") String code);

    List<OutgoingJig> naiveGetAllOutgoingJigList(@Param("code") String code, @Param("name") String name, @Param("start_date") String start_date, @Param("end_date") String end_date, @Param("user_for") String user_for, @Param("workcell_id") String workcell_id);

    double naive_get_jig_trouble_percent_all();

    List<MaintenanceSubmit> naive_get_trouble_maintenance_list(@Param("is_repair") int is_repair);

    int naive_get_trouble_reason_count_by_reason(@Param("code") String code,
                                                 @Param("seq_id") String seq_id,
                                                 @Param("reason") String reason);

    int naive_get_reason_count_in_all(@Param("reason") String reason);

    List<PendingPuchaseIncomeSubmit> naive_get_pending_purchase_submit_list(@Param("workcell_id") String workcell_id,
                                                                            @Param("status") int status,
                                                                            @Param("page_number") int page_number,
                                                                            @Param("page_size") int page_size);

    int naive_get_pending_purchase_submit_list_pages(@Param("workcell_id") String workcell_id,
                                                     @Param("status") int status);

    List<PendingScrapSubmit> naive_get_pending_scrap_submit_list(@Param("workcell_id") String workcell_id,
                                                                 @Param("page_number") int page_number,
                                                                 @Param("page_size") int page_size);

    int naive_get_pending_scrap_submit_list_pages(@Param("workcell_id") String workcell_id);

    int naive_scrap_jig(@Param("code") String code,
                        @Param("seq_id") String seq_id,
                        @Param("jig_id") String jig_id,
                        @Param("submit_id") String submit_id,
                        @Param("user_id") String user_id);

    List<PendingRepairSubmit> naive_get_pending_repair_submit_list(@Param("workcell_id") String workcell_id,
                                                                   @Param("page_number") int page_number,
                                                                   @Param("page_size") int page_size);

    int naive_get_pending_repair_submit_list_pages(@Param("workcell_id") String workcell_id);

    List<User> naive_get_repair_man_list(@Param("workcell_id") String workcell_id);

    int naive_choose_repair_man(@Param("repair_man_id") String repair_man_id,
                                @Param("repair_submit_id") String repair_submit_id);

    void naive_repair_finish(@Param("submit") PendingRepairSubmit submit);

    RepairJigHistory getARepairHistory(@Param("id") String id);

    PendingRepairSubmit naive_get_a_pending_repair_submit(@Param("id") String id);

    void delRepairSubmit(String id);
}
