package yxdroid.droidfm.fragment;

public abstract class FunctionParamAndResult<Param, Result> extends Function {


    public FunctionParamAndResult(String activityName, String name) {
        super(activityName, name);
    }

    public abstract Result run(Param param);
}
