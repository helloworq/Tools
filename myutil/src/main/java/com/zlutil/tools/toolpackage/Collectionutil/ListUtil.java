package com.zlutil.tools.toolpackage.Collectionutil;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Company: 上海数慧系统技术有限公司
 * Department: 数据中心
 * Date: 2020-08-20 15:49
 * Author: zhengja
 * Email: zhengja@dist.com.cn
 * Desc：ListUtil 工具类
 */
public abstract class ListUtil {

    /**
     * 计算目标对象中的元素不存在于参照对象范围内的值
     *
     * @param refer  参照对象
     * @param target 目标对象
     * @return
     */
    public static List<String> diff(final List<String> refer, final List<String> target) {
        if (ObjectUtil.isNull(target)) {
            return new ArrayList<>();
        }
        if (ObjectUtil.isNull(refer)) {
            return target;
        }
        //对象克隆工具在utils中
        List<String> cloneRefer = CloneUtil.deepClone(refer);
        List<String> cloneTarget = CloneUtil.deepClone(target);
        // 交集
        cloneRefer.retainAll(cloneTarget);
        //从目标对象中移除交集
        cloneTarget.removeAll(cloneRefer);
        return cloneTarget;
    }

    /**
     * list 转 string 逗号拼接
     */
    public static String listToString(List<String> list) {
        if (list == null) {
            return null;
        }
        return String.join(",", list);
    }

    /**
     * string 转 list
     */
    public static List<String> stringToList(String strs) {
        if (strs == null || strs.equals("")) {
            return null;
        }
        return Arrays.asList(strs.split(","));
    }

    /**
     * 判断list为 null
     *
     * @param list
     * @return
     */
    public static boolean isNull(List<String> list) {
        if (list == null || list.isEmpty()) {
            return true;
        }
        return false;
    }

    /**
     * 判断list 不为null
     *
     * @param list
     * @return
     */
    public static boolean isNonNull(List<String> list) {
        if (list != null && !list.isEmpty()) {
            return true;
        }
        return false;
    }


}
