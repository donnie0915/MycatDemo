package com.donnie.config;

/*
 * 多租户节点持有者
 */
public class MultiTenantHolder {

    //租户节点存储在ThreadLocal
    private static ThreadLocal<String> currentNodeThreadLocal = new ThreadLocal<String>();

    public static void setCurrentNode(String currentNode) {
        if (currentNode != null) {
            currentNodeThreadLocal.set(currentNode);
        }
    }


    public static String getCurrentNode() {
        return currentNodeThreadLocal.get();
    }

    /**
     * 清除本地线程变量
     */
    public static void remove() {
        currentNodeThreadLocal.remove();
    }
}