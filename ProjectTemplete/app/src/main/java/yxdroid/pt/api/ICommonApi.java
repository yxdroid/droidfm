package yxdroid.pt.api;

import yxdroid.droidfm.bean.Result;
import yxdroid.pt.bean.Token;

import io.reactivex.Observable;
import retrofit2.http.POST;

public interface ICommonApi {

    @POST("token/get2")
    Observable<Result<Token>> getToken();
}
