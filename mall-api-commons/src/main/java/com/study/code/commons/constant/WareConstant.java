package com.study.code.commons.constant;

public class WareConstant {

    public enum PurchaseEnum {
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        RECEIVED(2,"已领取"),
        DONE(3,"已完成"),
        HAS_ERROR(4, "有异常");

        private final int code;
        private final String msg;

        PurchaseEnum(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }

    public enum PurchaseDetailEnum {
        CREATED(0,"新建"),
        ASSIGNED(1,"已分配"),
        PURCHASE_ING(2,"正在采购"),
        DONE(3,"已完成"),
        PURCHASE_ERROR(4, "采购失败");

        private final int code;
        private final String msg;

        PurchaseDetailEnum(int code, String msg){
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public String getMsg() {
            return msg;
        }
    }
}
