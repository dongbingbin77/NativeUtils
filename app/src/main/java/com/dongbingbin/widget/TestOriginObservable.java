package com.dongbingbin.widget;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public class TestOriginObservable <T> extends Observable<T> {

    T temp;
    public TestOriginObservable(T t) {
        temp = t;
    }

    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        observer.onSubscribe(new Disposable() {
            @Override
            public void dispose() {

            }

            @Override
            public boolean isDisposed() {
                return false;
            }
        });

        try {
            Thread.sleep(2000);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        observer.onNext(temp);

        try {
            Thread.sleep(2000);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
//        Object o = null;
//        System.out.println(o.hashCode());
        observer.onComplete();

    }
}
