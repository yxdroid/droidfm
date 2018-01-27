package yxdroid.droidfm.fragment;

public abstract class FunctionNoParamNoResult extends Function {


    public FunctionNoParamNoResult(String activityName, String name) {
        super(activityName, name);
    }

    public abstract void run();
}
