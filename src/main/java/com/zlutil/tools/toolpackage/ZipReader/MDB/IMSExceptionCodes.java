/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2019/4/8
 * Author: chenyp
 * Email: chenyp@dist.com.cn
 * Desc：业务错误码
 */
package com.zlutil.tools.toolpackage.ZipReader.MDB;

public final class IMSExceptionCodes {

    public static final class Common{
        public static final int Message = 10000;
        public static final int IllegalArgument = 10001;
        public static final int InvalidateRequest = 10002;
        public static final int DuplicateId = 10003;
        public static final int DuplicateName = 10004;
        public static final int DuplicateCode = 10005;
        public static final int ExistSubData = 10006;
        public static final int SwapData = 10007;
        public static final int NotExist = 10008;
        public static final int Exist = 10009;
        public static final int Used = 10010;
        public static final int RepeatName = 10011;
        public static final int ExcelTitleError = 10012;
        public static final int LicenseInvalid = 10110;
        public static final int LicenseExpire = 10111;
    }

    public static final class Dic{
        public static final int NotExist = 20001;
        public static final int ValueNotExist = 20002;
        public static final int ValueExist = 20003;
        public static final int DeleteSystemDic = 20004;
        public static final int HasUsed = 20005;
        public static final int TypeNotExist = 20006;
        public static final int DeleteValue = 20007;
    }

    public static final class LandType{
        public static final int NotExist = 80001;
        public static final int HasUsed = 80002;
        public static final int Exist = 80003;
    }

    public static final class Attr{
        public static final int NotExist = 30001;
        public static final int HasUsed = 30002;
        public static final int InvalidateAttrValue = 30003;
    }

    public static final class Region{
        public static final int Import = 40000;
        public static final int NotExist = 40001;
        public static final int NoAttr = 40002;
        public static final int NoRef = 40003;
        public static final int HasUsed = 41004;

    }

    public static final class Unit{
        public static final int NotExist = 50001;
        public static final int HasUsed = 50002;
    }

    public static final class Indicator{
        public static final int NotExist = 51001;
        public static final int Delete = 51002;
        public static final int ValueNotExist = 51003;
        public static final int RepeatValue = 51004;
    }

    public static final class System{
        public static final int NotExist = 60001;
        public static final int CollectionHasExist = 60002;
        public static final int CollectionNotExist = 60003;
        public static final int HasUsed = 60004;
        public static final int DeleteSystem = 60005;
        public static final int HasData = 60006;
        public static final int Update = 60007;
        public static final int Attr = 60008;
        public static final int SystemForbiddenChange = 60009;
    }

    public static final class Time{
        public static final int NotExist = 70001;
        public static final int NotMatch = 70002;
        public static final int LengthHasUsed = 70003;
        public static final int HasUsed = 70004;
        public static final int LengthForbiddenChange = 70005;

    }

    public static final class StandardValue{
        public static final int NotExist = 80001;
        public static final int Illegal = 80002;
        public static final int DuplicateLevel = 80003;

    }

    public static final class ImsOrg{
        public static final int NotExist = 90001;
    }

    public static final class ImsUser{
        public static final int NotLogin = 100000;
        public static final int NotExist = 100001;
        public static final int NotAdmin = 100010;
        public static final int TokenInvalid = 100011;
        public static final int NoPower = 100012;
        public static final int AppIdException = 100013;
    }

}
