package com.mredrock.cyxbsmobile.network;

import android.net.Uri;

import com.google.gson.Gson;
import com.mredrock.cyxbsmobile.BuildConfig;
import com.mredrock.cyxbsmobile.config.Const;
import com.mredrock.cyxbsmobile.model.Course;
import com.mredrock.cyxbsmobile.model.MovieResult;
import com.mredrock.cyxbsmobile.model.RedrockApiWrapper;
import com.mredrock.cyxbsmobile.model.Student;
import com.mredrock.cyxbsmobile.model.Subject;
import com.mredrock.cyxbsmobile.network.exception.ApiException;
import com.mredrock.cyxbsmobile.network.exception.RedrockApiException;
import com.mredrock.cyxbsmobile.network.service.RedrockApiService;
import com.mredrock.cyxbsmobile.network.service.UpDownloadService;
import com.mredrock.cyxbsmobile.util.OkHttpUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * RequestManager
 * 请求服务在 {@link RedrockApiService} 与 {@link UpDownloadService} 中定义
 * Created by cc on 16/1/20.
 */
public enum RequestManager {

    INSTANCE;

    private UpDownloadService upDownloadService;
    private RedrockApiService redrockApiService;

    private static final int DEFAULT_TIMEOUT = 30;

    public static RequestManager getInstance() {
        return INSTANCE;
    }

    RequestManager() {
        OkHttpClient client = configureOkHttp(new OkHttpClient.Builder());

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Const.END_POINT_REDROCK)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                .build();

        upDownloadService = retrofit.create(UpDownloadService.class);
        redrockApiService = retrofit.create(RedrockApiService.class);
    }

    public OkHttpClient configureOkHttp(OkHttpClient.Builder builder) {
        builder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);

        if (BuildConfig.DEBUG) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BASIC);
            builder.addInterceptor(logging);
        }

        return builder.build();
    }

    public void download(String url, Subscriber<ResponseBody> subscriber) {
        Observable<ResponseBody> observable = upDownloadService.download(url);

        emitObservable(observable, subscriber);
    }

    /* 测试上传文件 */
    @Deprecated
    public void uploadTest(Subscriber<String> subscriber, Uri fileUri) {
        Observable<String> observable =
                upDownloadService.uploadTest(
                    /* 请求地址 */UpDownloadService.TEST_UPLOAD_URL,
                    /* 除了文件，其他POST参数 *///OkHttpUtils.createStringRequestBody("values"),
                    /* 文件，"file"是参数名 */OkHttpUtils.createFileRequestBody("file", fileUri))
                .map(wrapper -> wrapper.info);

        emitObservable(observable, subscriber);
    }

    public void getTopMovie(Subscriber<List<Subject>> subscriber, int start, int count) {
        Observable<List<Subject>> observable = redrockApiService.getTopMovie(RedrockApiService.MOVIE_URL, start, count)
                .map(new MovieResultFunc<>());

        emitObservable(observable, subscriber);
    }

    public void getNowWeek(Subscriber<Integer> subscriber, String stuNum, String idNum) {
        Observable<Integer> observable = redrockApiService.getCourse(stuNum, idNum, "0")
                .map(courseWrapper -> {
                    if (courseWrapper.status != Const.REDROCK_API_STATUS_SUCCESS) {
                        throw new RedrockApiException();
                    }
                    //Toast.makeText(APP.getContext(), courseWrapper.nowWeek, Toast.LENGTH_SHORT).show();
                    return Integer.parseInt(courseWrapper.nowWeek);
                });
        emitObservable(observable, subscriber);
    }

    public void getAllCourseJson(Subscriber<String> subscriber, String stuNum, String idNum) {
        getCourseJson(subscriber, stuNum, idNum, "0");
    }

    public void getCourseJson(Subscriber<String> subscriber, String stuNum, String idNum, String week) {
        Observable<String> observable = redrockApiService.getCourse(stuNum, idNum, week)
                .map(courseWrapper -> new Gson().toJson(courseWrapper));
        emitObservable(observable, subscriber);
    }

    public void getCourse(Subscriber<List<Course>> subscriber, String stuNum, String idNum, String week) {
        Observable<List<Course>> observable = redrockApiService.getCourse(stuNum, idNum, week).map(new RedrockApiWrapperFunc<>());
        emitObservable(observable, subscriber);
    }

    public void getStudent(Subscriber<List<Student>> subscriber,String stu){
        Observable<List<Student>> observable = redrockApiService.getStudent(stu)
                .map(studentWrapper -> studentWrapper.data);
        emitObservable(observable,subscriber);
    }

    public void getEmptyRoomList(Subscriber<List<String>> subscriber,String
            buildNum,String week,String weekdayNum,String sectionNum){
        Observable<List<String>> observable = redrockApiService
                .getEmptyRoomList(buildNum,week,weekdayNum,sectionNum)
                .map(new RedrockApiWrapperFunc<>());
        emitObservable(observable,subscriber);
    }

    private <T> void emitObservable(Observable<T> o, Subscriber<T> s) {
        o.subscribeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(s);
    }

    private class MovieResultFunc<T> implements Func1<MovieResult<T>, T> {

        @Override
        public T call(MovieResult<T> movieResult) {
            if (movieResult.count == 0) {
                throw new ApiException(100);
            }
            return movieResult.subjects;
        }
    }

    private class RedrockApiWrapperFunc<T> implements Func1<RedrockApiWrapper<T>, T> {

        @Override
        public T call(RedrockApiWrapper<T> wrapper) {
            if (wrapper.status != Const.REDROCK_API_STATUS_SUCCESS) {
                throw new RedrockApiException();
            }
            return wrapper.data;
        }
    }

}
