package com.dongbingbin.widget;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import io.reactivex.exceptions.CompositeException;
import io.reactivex.exceptions.Exceptions;
import io.reactivex.plugins.RxJavaPlugins;

public class TestObservable <T> extends Observable<T> {
    private final Observable<T> upstream;
    public TestObservable(Observable<T> upstream) {
        this.upstream = upstream;
    }
    @Override
    protected void subscribeActual(Observer<? super T> observer) {
        upstream.subscribe(new TestObserver<T>(observer));

    }

    public static class TestObserver<R> implements Observer<R>{

        private final Observer<? super R> observer;

        TestObserver(Observer<? super R> observer) {
            this.observer = observer;
        }

        @Override public void onSubscribe(Disposable disposable) {
            observer.onSubscribe(disposable);
        }

        @Override public void onNext(R response) {
            observer.onNext(response);
        }

        @Override public void onError(Throwable throwable) {
//            try {
//                observer.onNext(Result.<R>error(throwable));
//            } catch (Throwable t) {
//                try {
//                    observer.onError(t);
//                } catch (Throwable inner) {
//                    Exceptions.throwIfFatal(inner);
//                    RxJavaPlugins.onError(new CompositeException(t, inner));
//                }
//                return;
//            }
            observer.onComplete();
        }

        @Override public void onComplete() {
            observer.onComplete();
        }
    }
}
