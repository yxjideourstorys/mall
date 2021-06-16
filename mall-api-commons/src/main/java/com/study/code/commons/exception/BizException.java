package com.study.code.commons.exception;

/**
 * 如果希望写一个检查性异常类，则需要继承 Exception 类。
 * 如果你想写一个运行时异常类，那么需要继承 RuntimeException 类
 */
public class BizException extends RuntimeException {

    public BizException() {
        super();
    }

    public BizException(String message) {
        super(message);
    }

    public BizException(String message, Throwable cause) {
        super(message, cause);
    }

    public BizException(Throwable cause) {
        super(cause);
    }
}
