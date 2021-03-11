/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2019/4/8
 * Author: chenyp
 * Email: chenyp@dist.com.cn
 * Desc：
 */
package com.zlutil.tools.toolpackage.ZipReader.MDB;

public abstract class AbstractException extends RuntimeException {

    /**
     * 业务代码
     */
    int code;

    public AbstractException(int code, String message){
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}