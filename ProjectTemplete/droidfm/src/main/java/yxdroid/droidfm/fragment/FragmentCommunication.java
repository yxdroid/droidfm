package yxdroid.droidfm.fragment;

import android.text.TextUtils;

import com.orhanobut.logger.Logger;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class FragmentCommunication {

    private static final FragmentCommunication ourInstance = new FragmentCommunication();

    private Map<String, FunctionParamAndResult> functionParamAndResultMap;
    private Map<String, FunctionOnlyParam> functionOnlyParamMap;
    private Map<String, FunctionOnlyResult> functionOnlyResultMap;
    private Map<String, FunctionNoParamNoResult> functionNoParamNoResultMap;

    public static FragmentCommunication getInstance() {
        return ourInstance;
    }

    private FragmentCommunication() {
        functionParamAndResultMap = new HashMap<>();
        functionOnlyParamMap = new HashMap<>();
        functionOnlyResultMap = new HashMap<>();
        functionNoParamNoResultMap = new HashMap<>();
    }

    public FragmentCommunication add(Function function) {

        if (function instanceof FunctionParamAndResult) {
            functionParamAndResultMap.put(function.getFunctionName(), (FunctionParamAndResult) function);
        } else if (function instanceof FunctionOnlyParam) {
            functionOnlyParamMap.put(function.getFunctionName(), (FunctionOnlyParam) function);
        } else if (function instanceof FunctionOnlyResult) {
            functionOnlyResultMap.put(function.getFunctionName(), (FunctionOnlyResult) function);
        } else if (function instanceof FunctionNoParamNoResult) {
            functionNoParamNoResultMap.put(function.getFunctionName(), (FunctionNoParamNoResult) function);
        }

        return this;
    }

    public void remove(String activityName) {
        realRemove(activityName, functionParamAndResultMap);
        realRemove(activityName, functionOnlyParamMap);
        realRemove(activityName, functionOnlyResultMap);
        realRemove(activityName, functionNoParamNoResultMap);
    }

    private void realRemove(String activityName, Map<String, ? extends Function> functionMap) {
        Iterator<? extends Map.Entry<String, ? extends Function>> it = functionMap.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, ? extends Function> entry = it.next();
            Function function = entry.getValue();
            if (function.getActivityName().equals(activityName)) {
                Logger.i("remove fragment communication interface " + activityName + " : " + function.getFunctionName());
                it.remove();
            }
        }
    }

    /**
     * 执行有参数 有返回值的接口
     *
     * @param functionName
     */
    public <Param, Result> Result invoke(String functionName, Param param, Class<Result> c) {

        if (TextUtils.isEmpty(functionName)) {
            return null;
        }

        FunctionParamAndResult functionParamAndResult = functionParamAndResultMap.get(functionName);
        if (functionParamAndResult == null) {
            Logger.i("not found function");
            return null;
        }

        Object result = functionParamAndResult.run(param);
        if (result != null) {
            return c.cast(result);
        } else {
            return null;
        }
    }

    /**
     * 执行有参数 无返回值的接口
     */
    public <Param> void invoke(String functionName, Param param) {
        if (TextUtils.isEmpty(functionName)) {
            return;
        }

        FunctionOnlyParam functionOnlyParam = functionOnlyParamMap.get(functionName);
        if (functionOnlyParam == null) {
            Logger.i("not found function");
            return;
        }

        functionOnlyParam.run(param);
    }

    /**
     * 执行 无参数 有返回值的接口
     */
    public <Result> Result invoke(String functionName, Class<Result> c) {
        if (TextUtils.isEmpty(functionName)) {
            return null;
        }

        FunctionOnlyResult functionOnlyResult = functionOnlyResultMap.get(functionName);
        if (functionOnlyResult == null) {
            Logger.i("not found function");
            return null;
        }

        Object result = functionOnlyResult.run();
        if (result != null) {
            return c.cast(result);
        } else {
            return null;
        }
    }

    /**
     * 执行无参数 无返回值的接口
     */
    public void invoke(String functionName) {
        if (TextUtils.isEmpty(functionName)) {
            return;
        }

        FunctionNoParamNoResult functionNoParamNoResult = functionNoParamNoResultMap.get(functionName);
        if (functionNoParamNoResult == null) {
            Logger.i("not found function");
            return;
        }

        functionNoParamNoResult.run();
    }
}
