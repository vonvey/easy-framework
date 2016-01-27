package org.easy4j.framework.bean;

/**
 * @author fengwei (ivonvey@gmail.com) //
 * @date 15/12/27 18:49
 */

/**
 * 返回数据对象
 * JSON格式
 */
public class Data {
    /**
     * 模型数据
     */
    private Object model;

    public Data() {
    }
    public Data(Object model) {
        this.model = model;
    }

    public void setModel(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
