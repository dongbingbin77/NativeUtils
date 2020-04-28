package com.dongbingbin.nativeutils.utils;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class RxUtils {


    public static <T> ObservableTransformer<T, T> applySchedulersIO() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

    public static <T> ObservableTransformer<T, T> applySchedulersCompute() {
        return new ObservableTransformer<T, T>() {
            @Override
            public ObservableSource<T> apply(Observable<T> upstream) {
                return upstream.subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }


    public static<R> void callComputeF(final CallIO<R> callIO,final  CallMain<R> callMain,final CallThrow callThrow){
        Observable.just(1).map(new Function<Integer, R>() {
            @Override
            public R apply(Integer t) throws Exception {
                R result = null;
                //try{
                    result = callIO.callIO();
//                }catch (Exception ex1){
//                    ex1.printStackTrace();
//                }
                return result;
            }
        }).compose(RxUtils.<R>applySchedulersCompute())
                .subscribe(new Consumer<R>() {
                    @Override
                    public void accept(R result) throws Exception {
                        if (callMain != null) {
                            callMain.callMain(result);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if(callThrow!=null){
                            callThrow.callMainThrow(throwable);
                        }
                    }
                });
    }

    public interface CallIO<R> {
        R callIO();
    }

    public interface CallMain<R>{
        R callMain(R t);
    }

    public interface CallThrow{
        java.lang.Throwable callMainThrow(java.lang.Throwable t);
    }
}