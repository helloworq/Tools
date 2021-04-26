/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2019/4/22
 * Author: chenyp
 * Email: chenyp@dist.com.cn
 * Desc：
 */
package com.zlutil.tools.toolpackage.ZipReader.MDB;


public class MessageException extends AbstractException {
    public MessageException(String msg) {
        super(IMSExceptionCodes.Common.Message, msg);
    }
}
