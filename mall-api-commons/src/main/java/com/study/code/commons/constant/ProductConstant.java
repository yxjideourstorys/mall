package com.study.code.commons.constant;

public class ProductConstant {

    public enum AttrEnum {
        ATTR_TYPE_SALE(0,"销售属性"),
        ATTR_TYPE_BASE(1, "基本属性");

        private final int code;
        private final String msg;

        AttrEnum(int code, String msg){
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

    public enum EnableEnum {
        ATTR_ENABLE_0(0, "禁用"),
        ATTR_ENABLE_1(1, "可用");

        private final int code;
        private final String msg;

        EnableEnum(int code, String msg){
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
