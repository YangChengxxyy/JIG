package com.jig.service;

import com.jig.entity.common.*;
import com.jig.entity.jig.JigDefinition;
import com.jig.entity.jig.JigModel;
import com.jig.entity.jig.JigPart;
import com.jig.entity.purchase.PurchaseIncomeSubmit;
import com.jig.entity.repair.MaintenanceType;
import com.jig.entity.scrap.ScrapSubmit;
import com.jig.entity.warehouse.JigCabinet;
import com.jig.mapper.CommonMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CommonService {
    @Autowired
    private CommonMapper commonMapper;

    public JigDefinition getSimpleJigDefinition(String id) {
        return commonMapper.getSimpleJigDefinition(id);
    }

    /**
     * 获取单个JigDefinition对象(直接从jig_definition表里取数据)
     *
     * @param id
     * @return
     */
    public JigDefinition get_jig_info(String id) {
        return commonMapper.get_jig_info(id);
    }

    public Position getPosition(String code, String seq_id) {
        return commonMapper.getPosition(code, seq_id);
    }

    public List<ProductionLine> getProductionLineList() {
        return commonMapper.getProductionLineList();
    }

    public List<Workcell> getWorkcellList() {
        return commonMapper.getWorkcellList();
    }

    public List<Family> getFamilyList() {
        return commonMapper.getFamilyList();
    }

    public List<JigModel> getModelList() {
        return commonMapper.getModelList();
    }

    public List<JigPart> getPartNoList() {
        return commonMapper.getPartNoList();
    }

    public List<String> getCodeList() {
        return commonMapper.getCodeList();
    }

    public List<String> codeGetSeqId(String code) {
        return commonMapper.codeGetSeqId(code);
    }

    public List<String> codeGetInSeqId(String code) {
        return commonMapper.codeGetInSeqId(code);
    }

    public List<MaintenanceType> get_maintenance_type_list() {
        return commonMapper.get_maintenance_type_list();
    }


    //方便其他controller调用的

    /**
     * 获取单个报废申请
     *
     * @param id 申请id
     * @return
     */
    public ScrapSubmit getScrapSubmit(String id) {
        return commonMapper.getScrapSubmit(id);
    }

    /**
     * 获取单个采购入库申请
     *
     * @param id
     * @return
     */
    public PurchaseIncomeSubmit getPurchaseSubmit(String id) {
        return commonMapper.getPurchaseSubmit(id);
    }

    /**
     * 获取单个报废申请历史记录
     *
     * @param id 申请id
     * @return
     */
    public ScrapSubmit getScrapSubmitHistory(String id) {
        return commonMapper.getScrapSubmitHistory(id);
    }

    /**
     * 获取单个采购入库申请历史记录
     *
     * @param id
     * @return
     */
    public PurchaseIncomeSubmit getPurchaseSubmitHistory(String id) {
        return commonMapper.getPurchaseSubmitHistory(id);
    }

    public List<JigCabinet> get_jig_cabinet_list() {
        return commonMapper.get_jig_cabinet_list();
    }

    public List<JigDefinition> getJigDefinition(int page_number, int page_size) {
        page_number = (page_number - 1) * page_size;
        return commonMapper.getJigDefinition(page_number, page_size);
    }

    //根据id获取maintenance_type类型
    public MaintenanceType get_maintenance_type(String id) {
        return commonMapper.get_maintenance_type(id);
    }

    public int getJigDefinitionPage() {
        return commonMapper.getJigDefinitionPage();
    }

    // 根据采购审批id获取申请人id
    public User getUserByPurchaseSubmitId(String id) {
        return commonMapper.getUserByPurchaseSubmitId(id);
    }

    public User getUserByScrapSubmitId(String id) {
        return commonMapper.getUserByScrapSubmitId(id);
    }

}
