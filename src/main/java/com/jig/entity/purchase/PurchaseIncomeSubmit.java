package com.jig.entity.purchase;

import java.util.UUID;

public class PurchaseIncomeSubmit {
    private String id;
    private String submit_id;
    private String submit_name;
    private String submit_time;
    private String code;//多个用 | 分开
    private String count;//多个用 | 分开
    private String first_time;//初审时间
    private String first_acceptor;//初审人id
    private String first_acceptor_name;
    private String first_reason;//初审不通过的原因
    private String final_time;//终审时间
    private String final_acceptor;//终审人id
    private String final_acceptor_name;//终审人
    private String final_reason;//终审不通过的原因
    private String status;//审核状态 :0待审核 1初审未通过 2初审通过 3终审未通过 4终审通过
    private int production_line_id;//产线id
    private String production_line_name;
    private String bill_no;//单据号
    private String tool_photo_url;//工夹具照片路径
    private String code_count;

    public PurchaseIncomeSubmit() {
        id = UUID.randomUUID().toString().replaceAll("-", "");
    }

    public PurchaseIncomeSubmit(String a) {
        id = UUID.randomUUID().toString().replaceAll("-", "");
        if (a.equals("create")) {
            this.submit_id = "";
            this.submit_name = "";
            this.submit_time = "";
            this.code = "";
            this.count = "";
            this.first_time = "";
            this.first_acceptor = "";
            this.first_acceptor_name = "";
            this.first_reason = "";
            this.final_time = "";
            this.final_acceptor = "";
            this.final_acceptor_name = "";
            this.final_reason = "";
            this.status = "";
            this.production_line_id = 0;
            this.production_line_name = "";
            this.bill_no = "";
            this.tool_photo_url = "";
        }

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSubmit_id() {
        return submit_id;
    }

    public void setSubmit_id(String submit_id) {
        this.submit_id = submit_id;
    }

    public String getSubmit_name() {
        return submit_name;
    }

    public void setSubmit_name(String submit_name) {
        this.submit_name = submit_name;
    }

    public String getSubmit_time() {
        return submit_time;
    }

    public void setSubmit_time(String submit_time) {
        this.submit_time = submit_time;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public String getFirst_time() {
        return first_time;
    }

    public void setFirst_time(String first_time) {
        this.first_time = first_time;
    }

    public String getFirst_acceptor() {
        return first_acceptor;
    }

    public void setFirst_acceptor(String first_acceptor) {
        this.first_acceptor = first_acceptor;
    }

    public String getFirst_acceptor_name() {
        return first_acceptor_name;
    }

    public void setFirst_acceptor_name(String first_acceptor_name) {
        this.first_acceptor_name = first_acceptor_name;
    }

    public String getFinal_time() {
        return final_time;
    }

    public void setFinal_time(String final_time) {
        this.final_time = final_time;
    }

    public String getFinal_acceptor() {
        return final_acceptor;
    }

    public void setFinal_acceptor(String final_acceptor) {
        this.final_acceptor = final_acceptor;
    }

    public String getFinal_acceptor_name() {
        return final_acceptor_name;
    }

    public void setFinal_acceptor_name(String final_acceptor_name) {
        this.final_acceptor_name = final_acceptor_name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getProduction_line_id() {
        return production_line_id;
    }

    public void setProduction_line_id(int production_line_id) {
        this.production_line_id = production_line_id;
    }

    public String getProduction_line_name() {
        return production_line_name;
    }

    public void setProduction_line_name(String production_line_name) {
        this.production_line_name = production_line_name;
    }

    public String getBill_no() {
        return bill_no;
    }

    public void setBill_no(String bill_no) {
        this.bill_no = bill_no;
    }

    public String getTool_photo_url() {
        return tool_photo_url;
    }

    public void setTool_photo_url(String tool_photo_url) {
        this.tool_photo_url = tool_photo_url;
    }

    public String getFirst_reason() {
        return first_reason;
    }

    public void setFirst_reason(String first_reason) {
        this.first_reason = first_reason;
    }

    public String getFinal_reason() {
        return final_reason;
    }

    public void setFinal_reason(String final_reason) {
        this.final_reason = final_reason;
    }

    public String getCode_count() {
        return code_count;
    }

    public void setCode_count(String code_count) {
        this.code_count = code_count;
    }

    @Override
    public String toString() {
        return "PurchaseIncomeSubmit{" +
                "id='" + id + '\'' +
                ", submit_id='" + submit_id + '\'' +
                ", submit_name='" + submit_name + '\'' +
                ", submit_time='" + submit_time + '\'' +
                ", code='" + code + '\'' +
                ", count='" + count + '\'' +
                ", first_time='" + first_time + '\'' +
                ", first_acceptor='" + first_acceptor + '\'' +
                ", first_acceptor_name='" + first_acceptor_name + '\'' +
                ", first_reason='" + first_reason + '\'' +
                ", final_time='" + final_time + '\'' +
                ", final_acceptor='" + final_acceptor + '\'' +
                ", final_acceptor_name='" + final_acceptor_name + '\'' +
                ", final_reason='" + final_reason + '\'' +
                ", status='" + status + '\'' +
                ", production_line_id=" + production_line_id +
                ", production_line_name='" + production_line_name + '\'' +
                ", bill_no='" + bill_no + '\'' +
                ", tool_photo_url='" + tool_photo_url + '\'' +
                '}';
    }

    public String[] getOperateUpdateInfo(String code, String count, String production_line_id) {
        //通过修改前的submit与修改的值进行比较，获取field,old_value,new_value插入数据库

        //这样设置是有点问题的，如果只修改了code,没有修改count，只会有code的信息
        String[] a = new String[3];
        String field = "";
        String old_value = "";
        String new_value = "";
        if (!this.getCode().equals(code)) {
            old_value += this.getCode();
            new_value += count;
            field += "code";
        }
        if (!this.getCount().equals(count)) {
            if (old_value.equals("")) {
                old_value += this.getCount();
                new_value += count;
                field += "count";
            } else {
                old_value += "~" + this.getCount();
                new_value += "~" + count;
                field += "~count";
            }
        }
        if (this.getProduction_line_id() != Integer.valueOf(production_line_id)) {
            if (old_value.equals("")) {
                old_value += this.getProduction_line_id();
                new_value += production_line_id;
                field += "production_line_id";
            } else {
                old_value += "~" + this.getProduction_line_id();
                new_value += "~" + production_line_id;
                field += "~production_line_id";
            }
        }
        a[0] = field;
        a[1] = old_value;
        a[2] = new_value;
        return a;
    }

    public String[] PassSubmitInfo(String new_status) {
        String[] a = new String[3];
        String field = "";
        String old_value = "";
        String new_value = "";
        if (!this.status.equals(new_status)) {
            field += "status";
            old_value += this.status;
            new_value += new_status;
        }
        if (new_status.equals("1") || new_status.equals("2")) {
            if (first_reason != null) {
                if (field.equals("")) {
                    field += "first_reason";
                    old_value += first_reason;
                    new_value += "";
                } else {
                    field += "~first_reason";
                    old_value += "~" + first_reason;
                    new_value += "~";
                }
            }
        } else if (new_status.equals("3") || new_status.equals("4")) {
            if (final_reason != null) {
                if (field.equals("")) {
                    field += "final_reason";
                    old_value += final_reason;
                    new_value += "";
                } else {
                    field += "~final_reason";
                    old_value += "~" + final_reason;
                    new_value += "~";
                }
            }
        }
        a[0] = field;
        a[1] = old_value;
        a[2] = new_value;
        //System.out.println("pass:" + Arrays.toString(a));
        return a;
    }

    public String[] NoPassSubmitInfo(String new_status, String no_pass_reason) {
        String[] a = new String[3];
        String field = "";
        String old_value = "";
        String new_value = "";
        if (!this.status.equals(new_status)) {
            field += "status";
            old_value += this.status;
            new_value += new_status;
        }
        if (new_status.equals("1") || new_status.equals("2")) {
            if (field.equals("")) {
                field += "first_reason";
                old_value += first_reason == null ? "" : first_reason;
                new_value += no_pass_reason;
            } else {
                field += "~first_reason";
                old_value += "~" + (first_reason == null ? "" : first_reason);
                new_value += "~" + no_pass_reason;
            }
        } else if (new_status.equals("3") || new_status.equals("4")) {
            if (field.equals("")) {
                field += "final_reason";
                old_value += final_reason == null ? "" : final_reason;
                new_value += no_pass_reason;
            } else {
                field += "~final_reason";
                old_value += "~" + (final_reason == null ? "" : final_reason);
                new_value += "~" + no_pass_reason;
            }
        }


        a[0] = field;
        a[1] = old_value;
        a[2] = new_value;
        //System.out.println("no_pass:"+ Arrays.toString(a));
        return a;
    }
}
