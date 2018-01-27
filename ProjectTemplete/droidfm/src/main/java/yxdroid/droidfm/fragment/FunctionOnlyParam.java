package yxdroid.droidfm.fragment;


public abstract class FunctionOnlyParam<Param> extends Function {

    public FunctionOnlyParam(String activityName, String name) {
        super(activityName, name);
    }

    public abstract void run(Param param);
}
