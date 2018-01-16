package yxdroid.droidfm.http;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.orhanobut.logger.Logger;

import yxdroid.droidfm.bean.Result;

import java.io.IOException;
import java.io.StringReader;
import java.nio.charset.Charset;

import okhttp3.ResponseBody;
import retrofit2.Converter;

public class MyGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    private static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Gson mGson;
    private final TypeAdapter<T> adapter;

    public MyGsonResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        mGson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        String response = value.string();

        Logger.i("httpResponse = " + response);

        Result<T> re;
        try {
            re = mGson.fromJson(response, Result.class);
        } catch (JsonSyntaxException e) {
            Logger.e(e.getCause().getMessage());
            value.close();
            throw new IOException(e.getMessage(), e);
        }

        if (!re.isSuccessful()) {
            value.close();
            throw new ApiException(re.getCode(), re.getMsg());
        }
        try {
            return adapter.read(mGson.newJsonReader(new StringReader(response)));
        } finally {
            value.close();
        }
    }
}
