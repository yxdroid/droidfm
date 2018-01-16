package yxdroid.pt.api;

import yxdroid.droidfm.http.Params;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface IUserApi {

    @POST("account/login")
    @FormUrlEncoded
    Observable<String> userLogin(@FieldMap Params params);
}
