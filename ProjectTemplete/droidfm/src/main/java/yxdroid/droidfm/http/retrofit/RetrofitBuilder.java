package yxdroid.droidfm.http.retrofit;

import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.orhanobut.logger.Logger;

import yxdroid.droidfm.http.MyGsonConverterFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.Headers;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;

public class RetrofitBuilder {

    private String baseApi;
    private Map<String, String> headers;

    private HttpLoggingInterceptor httpLoggingInterceptor;

    private static class SingletonHolder {
        private static final RetrofitBuilder INSTANCE = new RetrofitBuilder();
    }

    private RetrofitBuilder() {
    }

    public static final RetrofitBuilder getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public void initHttp(String baseApi, HttpLoggingInterceptor.Level httpLoggerLevel) {
        this.baseApi = baseApi;

        if (httpLoggerLevel != HttpLoggingInterceptor.Level.NONE) {
            httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    Logger.i(message);
                }
            });
            httpLoggingInterceptor.setLevel(httpLoggerLevel);
        }

    }

    public void initHeaders(Map<String, String> headers) {
        this.headers = headers;
    }

    public void addHeader(String key, String value) {
        if (headers == null) {
            headers = new HashMap<>();
        }
        headers.put(key, value);
    }

    public Retrofit build() {
        return build(baseApi);
    }

    public Retrofit build(String baseApi) {

        OkHttpClient.Builder okHttpBuilder = new OkHttpClient.Builder();

        okHttpBuilder.addInterceptor(interceptor).readTimeout(3, TimeUnit.SECONDS)
                .connectTimeout(3, TimeUnit.SECONDS)
                .writeTimeout(3, TimeUnit.MINUTES)
                .retryOnConnectionFailure(true);

        if (httpLoggingInterceptor != null) {
            okHttpBuilder.addInterceptor(httpLoggingInterceptor);
        }

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(baseApi)
                .addConverterFactory(MyGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpBuilder.build())
                .build();


        return retrofit;
    }

    private Interceptor interceptor = new Interceptor() {
        @Override
        public okhttp3.Response intercept(Chain chain) throws IOException {

            Request.Builder builder = chain.request().newBuilder();

            if (headers != null) {
                builder.headers(Headers.of(headers));
            }

            Request request = builder.build();

            return chain.proceed(request);
        }
    };
}
