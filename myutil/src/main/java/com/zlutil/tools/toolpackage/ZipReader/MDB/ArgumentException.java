/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2019/4/23
 * Author: chenyp
 * Email: chenyp@dist.com.cn
 * Desc：
 */
package com.zlutil.tools.toolpackage.ZipReader.MDB;


public class ArgumentException extends AbstractException  {

    public ArgumentException(String message) {
        super(IMSExceptionCodes.Common.IllegalArgument, "无效或非法参数【" + message+"】");
    }

}