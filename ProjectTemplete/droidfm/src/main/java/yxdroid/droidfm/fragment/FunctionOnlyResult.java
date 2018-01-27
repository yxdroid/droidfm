package yxdroid.droidfm.fragment;

public abstract class FunctionOnlyResult<Result> extends Function {

    public FunctionOnlyResult(String activityName, String name) {
        super(activityName, name);
    }

    public abstract Result run();
}
