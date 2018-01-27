package yxdroid.pt.api;

import io.reactivex.Observable;
import retrofit2.http.FieldMap;
import retrofit2.http.POST;
import yxdroid.droidfm.http.Params;

public interface IUserApi {

    @POST("account/login")
    Observable<String> userLogin(@FieldMap Params params);
}
