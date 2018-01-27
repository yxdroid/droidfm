package yxdroid.droidfm.fragment;

/**
 * 函数类型包括:
 * 1. 有参数 有返回值
 * 2. 没参数 没返回值
 * 3. 有参数 没返回值
 * 4. 没参数 有返回值
 */
public abstract class Function {

    private String activityName;
    private String functionName;

    public Function(String activityName, String name) {
        this.activityName = activityName;
        this.functionName = name;
    }

    public void setFunctionName(String functionName) {
        this.functionName = functionName;
    }

    public String getFunctionName() {
        return functionName;
    }

    public String getActivityName() {
        return activityName;
    }

    public void setActivityName(String activityName) {
        this.activityName = activityName;
    }
}
