package com.jig.controller;

import com.jig.annotation.Permission;
import com.jig.entity.common.Message;
import com.jig.entity.common.Role;
import com.jig.entity.purchase.PurchaseIncomeHistory;
import com.jig.entity.purchase.PurchaseIncomeSubmit;
import com.jig.entity.repair.RepairJigHistory;
import com.jig.entity.scrap.ScrapHistory;
import com.jig.entity.scrap.ScrapSubmit;
import com.jig.service.HighService;
import com.jig.service.MessageService;
import com.jig.utils.PoiUtil;
import com.jig.utils.RedisUtil;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Permission(Role.high)
@RestController
@RequestMapping("/api/high/")
@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
public class HighJson {

    @Autowired
    private HighService highService;
    @Autowired
    private RedisUtil redisUtil;
    @Value("${file.resource-url}")
    public String RESOURCE_URL;
    @Autowired
    private WebSocketServer webSocketServer;
    @Autowired
    private PoiUtil poiUtil;
    @Autowired
    MessageService messageService;
    public static final String SCRAP_IMAGE_NAME = "images/scrap_images/";
    public static final String REPAIR_IMAGE_NAME = "images/repair_images/";
    public static final String SCRAP = "SCRAP";
    public static final String REPAIR = "REPAIR";

    @NotNull
    private Map<String, Object> getStringObjectMap(List<?> data, int max) {
        Map<String, Object> map = new HashMap<>(2);
        map.put("data", data);
        map.put("max", max);
        return map;
    }

    private String getScrapPathName(String fileName) {
        UUID uuid = UUID.randomUUID();
        String uuidString = uuid.toString();
        String after = fileName.substring(fileName.lastIndexOf('.'));
        return SCRAP_IMAGE_NAME + SCRAP + "-" + uuidString + after;
    }

    /**
     * high添加采购入库申请
     *
     * @param submit_id          申请人id
     * @param bill_no            订单号
     * @param production_line_id 产线id
     * @param codes              工夹具代码
     * @param counts             数量
     * @return 添加成功，否则服务器异常
     */
    @RequestMapping(value = "add_shoplist", method = RequestMethod.GET)
    public boolean highAddShoplist(@RequestParam("submit_id") String submit_id, @RequestParam("bill_no") String bill_no,
                                   @RequestParam("production_line_id") String production_line_id, @RequestParam("code") String codes,
                                   @RequestParam("count") String counts) {
        try {
            PurchaseIncomeSubmit purchaseIncomeSubmit = new PurchaseIncomeSubmit();
            purchaseIncomeSubmit.setSubmit_id(submit_id);
            purchaseIncomeSubmit.setProduction_line_id(Integer.parseInt(production_line_id));
            purchaseIncomeSubmit.setBill_no(bill_no);
            purchaseIncomeSubmit.setCode(codes);
            purchaseIncomeSubmit.setCount(counts);

            highService.highAddShoplist(purchaseIncomeSubmit);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * high获取采购入库申请列表
     *
     * @param page_number 页码
     * @return 采购入库申请列表
     */
    @RequestMapping("/get_purchase_income_submit_list")
    public Map<String, Object> highGetPurchaseIncomeSubmitList(@RequestParam("page_number") int page_number,
                                                               @RequestParam("page_size") int page_size) {
        Map<String, Object> map = new HashMap<>(3);
        map.put("data", highService.highGetPurchaseIncomeSubmitList(page_number, page_size));
        int all = highService.highGetPurchaseIncomeSubmitListPage();
        map.put("all", all);
        map.put("max", (int) Math.ceil(all / (double) page_size));
        return map;
    }

    /**
     * high修改采购入库申请单
     *
     * @param id                 purchase_income_submit表id
     * @param code               工夹具代码
     * @param count              数量
     * @param production_line_id 产线id
     * @return 是否修改成功
     */
    @RequestMapping("update_purchase_income_submit")
    public boolean highUpdatePurchaseIncomeSubmit(@RequestParam("id") String id, @RequestParam("code") String code,
                                                  @RequestParam("count") String count, @RequestParam("production_line_id") String production_line_id) {
        try {
            highService.highUpdatePurchaseIncomeSubmit(id, code, count, production_line_id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * high获取查询到的入库申请历史
     *
     * @param bill_no            单据号
     * @param submit_name        申请人
     * @param code               工夹具代码
     * @param production_line_id 产线id
     * @param status             状态
     * @param start_date         最早日期
     * @param end_date           最晚日期
     * @param page_number        页码
     * @return 查询到的入库申请历史
     */
    @RequestMapping("search_purchase_income_history")
    public Map<String, Object> highSearchPurchaseIncomeHistory(@RequestParam("bill_no") String bill_no,
                                                               @RequestParam("submit_name") String submit_name, @RequestParam("code") String code,
                                                               @RequestParam("production_line_id") String production_line_id, @RequestParam("status") String status,
                                                               @RequestParam("start_date") String start_date, @RequestParam("end_date") String end_date,
                                                               @RequestParam("page_number") int page_number, @RequestParam("page_size") int page_size) {
        int all = highService.highSearchPurchaseIncomeHistoryPage(bill_no, submit_name, code, production_line_id,
                status, start_date, end_date);
        Map<String, Object> map = new HashMap<>(3);
        map.put("data", highService.highSearchPurchaseIncomeHistory(bill_no, submit_name, code, production_line_id,
                status, start_date, end_date, page_number, page_size));
        map.put("max", (int) Math.ceil(all / (double) all));
        map.put("all", all);
        return map;
    }

    /**
     * high删除出库申请
     *
     * @param id purchase_income_submit表id
     * @return 是否成功
     */
    @RequestMapping("delete_purchase_submit")
    public boolean highDeletePurchaseSubmit(@RequestParam("id") String id) {
        try {
            highService.highDeletePurchaseSubmit(id);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * high获取报修申请记录
     *
     * @return 获取报修申请记录
     */
    @RequestMapping("get_repair_jig")
    public Map<String, Object> highGetRepairJig(@RequestParam("id") String id,
                                                @RequestParam("page_number") int page_number, @RequestParam("page_size") int page_size) {
        int all = highService.highGetRepairJigPage(id);
        Map<String, Object> map = getStringObjectMap(highService.highGetRepairJig(id, page_number, page_size),
                (int) Math.ceil(all / (double) all));
        map.put("all", all);
        return map;
    }

    /**
     * high搜索历史报修记录
     *
     * @param id          员工id
     * @param code        工夹具代码
     * @param seq_id      工夹具序列号
     * @param submit_name 申请时间
     * @param status      状态
     * @param start_date  最早时间
     * @param end_date    最晚时间
     * @param page_number 页码
     * @return 搜索到历史报修记录
     */
    @RequestMapping("search_repair_history")
    public Map<String, Object> highSearchRepairHistory(@RequestParam("id") String id, @RequestParam("code") String code,
                                                       @RequestParam("seq_id") String seq_id, @RequestParam("submit_name") String submit_name,
                                                       @RequestParam("status") String status, @RequestParam("start_date") String start_date,
                                                       @RequestParam("end_date") String end_date, @RequestParam("page_number") int page_number,
                                                       @RequestParam("page_size") int page_size) {
        int all = highService.highSearchRepairHistoryPage(id, code, seq_id, submit_name, status, start_date, end_date);
        Map<String, Object> map = getStringObjectMap(highService.highSearchRepairHistory(id, code, seq_id, submit_name,
                status, start_date, end_date, page_number, page_size), (int) Math.ceil(all / (double) all));
        map.put("all", all);
        return map;
    }

    /**
     * high获取报废申请
     *
     * @param submit_id   申请人id
     * @param page_number 页码
     * @return 报废申请
     */
    @RequestMapping("get_scrap")
    public Map<String, Object> highGetScrap(@RequestParam("submit_id") String submit_id,
                                            @RequestParam("page_number") int page_number, @RequestParam("page_size") int page_size) {
        int all = highService.highGetScrapPage(submit_id);
        Map<String, Object> map = getStringObjectMap(highService.highGetScrap(submit_id, page_number, page_size),
                (int) Math.ceil(all / (double) all));
        map.put("all", all);
        return map;
    }

    /**
     * high搜索报废历史
     *
     * @param code        工夹具代码
     * @param seq_id      工夹具序列号
     * @param submit_id   申请人id
     * @param status      状态
     * @param start_date  最早日期
     * @param end_date    最晚日期
     * @param page_number 页码
     * @return 搜索到的报废历史
     */
    @RequestMapping("search_scrap_history")
    public Map<String, Object> highSearchScrapHistory(@RequestParam("code") String code,
                                                      @RequestParam("seq_id") String seq_id, @RequestParam("submit_id") String submit_id,
                                                      @RequestParam("scrap_reason") String scrap_reason, @RequestParam("status") String status,
                                                      @RequestParam("start_date") String start_date, @RequestParam("end_date") String end_date,
                                                      @RequestParam("page_number") int page_number, @RequestParam("page_size") int page_size) {
        int all = highService.highSearchScrapHistoryPage(code, seq_id, submit_id, scrap_reason, status, start_date,
                end_date);
        Map<String, Object> map = getStringObjectMap(highService.highSearchScrapHistory(code, seq_id, submit_id,
                scrap_reason, status, start_date, end_date, page_number, page_size),
                (int) Math.ceil(all / (double) all));
        map.put("all", all);
        return map;
    }

    /**
     * high提交报废
     *
     * @param code         工夹具代码
     * @param seq_id       工夹具序列号
     * @param submit_id    申请人id
     * @param scrap_reason 报废原因
     * @param files        文件
     * @return 成功与否
     */
    @RequestMapping(value = "submit_scrap", method = RequestMethod.POST)
    public boolean highSubmitScrap(@RequestParam("code") String code, @RequestParam("seq_id") String seq_id,
                                   @RequestParam("submit_id") String submit_id, @RequestParam("scrap_reason") String scrap_reason,
                                   @RequestParam("scrap_type") String scrap_type, @RequestParam("file") MultipartFile[] files,
                                   @RequestParam(value = "workcell_id", defaultValue = "", required = false) String workcell_id) {
        try {
            StringBuilder pathName = new StringBuilder("");
            if (files.length == 1) {
                String fileName = getScrapPathName(files[0].getOriginalFilename());
                FileUtils.writeByteArrayToFile(new File(RESOURCE_URL + fileName), files[0].getBytes());
                pathName.append(fileName);
            } else {
                for (int i = 0; i < files.length - 1; i++) {
                    String fileName = getScrapPathName(files[i].getOriginalFilename());
                    FileUtils.writeByteArrayToFile(new File(RESOURCE_URL + fileName), files[i].getBytes());
                    pathName.append(fileName).append('|');
                }
                String fileName = getScrapPathName(files[files.length - 1].getOriginalFilename());
                FileUtils.writeByteArrayToFile(new File(RESOURCE_URL + fileName), files[files.length - 1].getBytes());
                pathName.append(fileName);
            }

            ScrapSubmit scrapSubmit = new ScrapSubmit();
            scrapSubmit.setCode(code);
            scrapSubmit.setSeq_id(seq_id);
            scrapSubmit.setSubmit_id(submit_id);
            scrapSubmit.setScrap_reason(scrap_reason);
            scrapSubmit.setScrap_type(scrap_type);

            highService.highSubmitScrap(scrapSubmit, pathName.toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    /**
     * 若取消提交报废，需删除原来的图片
     *
     * @return
     */
    @RequestMapping("mobile_remove_scrap_uuid")
    public boolean mobileRemoveScrapUuid(@RequestParam("file_name") String[] file_name) {
        for (String filename : file_name) {
            try {
                FileUtils.forceDelete(new File(RESOURCE_URL + filename));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }

    @RequestMapping("mobile_upload_scrap_photo")
    public String mobileUploadScrapPhoto(@RequestParam("file") MultipartFile file) {
        try {
            String pathName = getScrapPathName(file.getOriginalFilename());
            FileUtils.writeByteArrayToFile(new File(RESOURCE_URL + pathName), file.getBytes());
            return pathName;
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
    }

    @RequestMapping("mobile_submit_scrap")
    public boolean mobileSubmitScrap(@RequestParam("code") String code, @RequestParam("seq_id") String seq_id,
                                     @RequestParam("submit_id") String submit_id, @RequestParam("scrap_reason") String scrap_reason,
                                     @RequestParam("scrap_type") String scrap_type, @RequestParam("file_name") String[] file_name) {
        StringBuilder all_path = new StringBuilder();
        for (int i = 0; i < file_name.length - 1; i++) {
            all_path.append(file_name[i]).append("|");
        }
        all_path.append(file_name[file_name.length - 1]);
        ScrapSubmit scrapSubmit = new ScrapSubmit();
        scrapSubmit.setCode(code);
        scrapSubmit.setSeq_id(seq_id);
        scrapSubmit.setSubmit_id(submit_id);
        scrapSubmit.setScrap_reason(scrap_reason);
        scrapSubmit.setScrap_type(scrap_type);
        highService.highSubmitScrap(scrapSubmit, all_path.toString());
        return true;
    }

    /**
     * high删除报废
     *
     * @param id              表id
     * @param scrap_photo_url 报废图片路径
     * @return 成功与否
     */
    @RequestMapping("delete_scrap")
    public boolean highDeleteScrap(@RequestParam("id") String id,
                                   @RequestParam("scrap_photo_url") String scrap_photo_url) {
        String fileName = RESOURCE_URL + scrap_photo_url;
        File file = new File(fileName);
        return highService.highDeleteScrap(id) && file.delete();
    }

    /**
     * high获取报修总数
     *
     * @param submit_id high用户id
     * @return 报修总数
     */
    @RequestMapping("get_repair_count")
    public int highGetRepairCount(@RequestParam("submit_id") String submit_id) {
        return highService.highGetRepairCount(submit_id);
    }

    /**
     * high报修概况
     *
     * @param submit_id high
     * @return 保修概况
     */
    @RequestMapping("get_repair_basic")
    public List<Map<String, Object>> highGetRepairBasic(@RequestParam("submit_id") String submit_id) {
        return highService.highGetRepairBasic(submit_id);
    }

    @RequestMapping("handle_repair_submit")
    public boolean handleRepairSubmit(@RequestParam("id") int id, @RequestParam("submit_id") String submit_id, @RequestParam("informed_id") String informed_id,
                                      @RequestParam("state") boolean state, @RequestParam(value = "reason", required = false) String reason,@RequestParam(value = "workcell_id",required = false,defaultValue = "")String workcell_id) {
        try {
            if (state) {
                highService.highAgreeRepairSubmit(id, submit_id);
                if(!"".equals(workcell_id)){
                    long now = System.currentTimeMillis();
                    Message message = new Message("/warehouse/repair", "id", String.valueOf(id), submit_id + "通过了你的报修申请", now);
                    webSocketServer.sendMessageToId(informed_id, message);
                    messageService.idAdd(informed_id,"naive",workcell_id,"/warehouse/repair", "id", String.valueOf(id), submit_id + "通过了你的报修申请", now);
                }
            } else {
                highService.highDisagreeRepairSubmit(id, submit_id, reason);
                if(!"".equals(workcell_id)){
                    long now = System.currentTimeMillis();
                    Message message = new Message("/repair/search", "id", String.valueOf(id), submit_id + "拒绝了你的报修申请", now);
                    webSocketServer.sendMessageToId(informed_id, message);
                    messageService.idAdd(informed_id,"naive",workcell_id,"/repair/search", "id", String.valueOf(id), submit_id + "拒绝了你的报修申请", now);
                }
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @RequestMapping("download_one_purchase_history")
    public void highDownloadOnePurchaseHistory(HttpServletResponse response,
                                               @RequestParam(value = "bill_no") String bill_no, @RequestParam(value = "submit_name") String submit_name,
                                               @RequestParam(value = "code") String code,
                                               @RequestParam(value = "production_line_id") String production_line_id,
                                               @RequestParam(value = "status") String status, @RequestParam(value = "start_date") String start_date,
                                               @RequestParam(value = "end_date") String end_date, @RequestParam(value = "page_number") int page_number,
                                               @RequestParam(value = "file_name") String file_name, @RequestParam("page_size") int page_size) {
        List<PurchaseIncomeHistory> list = highService.highSearchPurchaseIncomeHistory(bill_no, submit_name, code,
                production_line_id, status, start_date, end_date, page_number, page_size);
        poiUtil.outputFile(response, file_name, list);
    }

    @RequestMapping("download_all_purchase_history")
    public void highDownloadAllPurchaseHistory(HttpServletResponse response,
                                               @RequestParam(value = "bill_no") String bill_no, @RequestParam(value = "submit_name") String submit_name,
                                               @RequestParam(value = "code") String code,
                                               @RequestParam(value = "production_line_id") String production_line_id,
                                               @RequestParam(value = "status") String status, @RequestParam(value = "start_date") String start_date,
                                               @RequestParam(value = "end_date") String end_date, @RequestParam(value = "file_name") String file_name) {
        List<PurchaseIncomeHistory> list = highService.highSearchAllPurchaseIncomeHistory(bill_no, submit_name, code,
                production_line_id, status, start_date, end_date);
        poiUtil.outputFile(response, file_name, list);
    }

    @RequestMapping("download_one_repair_history")
    public void highDownloadOneRepairHistory(HttpServletResponse response, @RequestParam("id") String id,
                                             @RequestParam(value = "code") String code, @RequestParam(value = "seq_id") String seq_id,
                                             @RequestParam(value = "submit_name") String submit_name, @RequestParam(value = "status") String status,
                                             @RequestParam(value = "start_date") String start_date, @RequestParam(value = "end_date") String end_date,
                                             @RequestParam(value = "page_number") int page_number, @RequestParam(value = "file_name") String file_name,
                                             @RequestParam("page_size") int page_size) throws Exception {
        List<RepairJigHistory> list = highService.highSearchRepairHistory(id, code, seq_id, submit_name, status,
                start_date, end_date, page_number, page_size);
        poiUtil.outputFile(response, file_name, list);
    }

    @RequestMapping("download_all_repair_history")
    public void highDownloadAllRepairHistory(HttpServletResponse response, @RequestParam("id") String id,
                                             @RequestParam(value = "code") String code, @RequestParam(value = "seq_id") String seq_id,
                                             @RequestParam(value = "submit_name") String submit_name, @RequestParam(value = "status") String status,
                                             @RequestParam(value = "start_date") String start_date, @RequestParam(value = "end_date") String end_date,
                                             @RequestParam(value = "file_name") String file_name) throws Exception {
        List<RepairJigHistory> list = highService.highSearchAllRepairHistory(id, code, seq_id, submit_name, status,
                start_date, end_date);
        poiUtil.outputFile(response, file_name, list);
    }

    @RequestMapping("download_one_scrap_history")
    public void highDownloadOneScrapHistory(HttpServletResponse response, @RequestParam(value = "code") String code,
                                            @RequestParam(value = "seq_id") String seq_id, @RequestParam(value = "submit_id") String submit_id,
                                            @RequestParam(value = "status") String status, @RequestParam(value = "start_date") String start_date,
                                            @RequestParam(value = "end_date") String end_date, @RequestParam(value = "page_number") int page_number,
                                            @RequestParam("page_size") int page_size, @RequestParam(value = "scrap_reason") String scrap_reason,
                                            @RequestParam(value = "file_name") String file_name) throws Exception {
        List<ScrapHistory> list = highService.highSearchScrapHistory(code, seq_id, submit_id, scrap_reason, status,
                start_date, end_date, page_number, page_size);
        poiUtil.outputFile(response, file_name, list);
    }

    @RequestMapping("download_all_scrap_history")
    public void highDownloadAllScrapHistory(HttpServletResponse response, @RequestParam(value = "code") String code,
                                            @RequestParam(value = "seq_id") String seq_id, @RequestParam(value = "submit_id") String submit_id,
                                            @RequestParam(value = "status") String status, @RequestParam(value = "start_date") String start_date,
                                            @RequestParam(value = "scrap_reason") String scrap_reason,
                                            @RequestParam(value = "end_date") String end_date, @RequestParam(value = "file_name") String file_name)
            throws Exception {
        List<ScrapHistory> list = highService.highSearchAllScrapHistory(code, seq_id, submit_id, scrap_reason, status,
                start_date, end_date);
        poiUtil.outputFile(response, file_name, list);
    }
}
