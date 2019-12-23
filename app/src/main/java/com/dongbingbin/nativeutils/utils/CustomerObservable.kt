package com.dongbingbin.nativeutils.utils

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

class CustomerObservable<T>(upstream1:Observable<T> ) : Observable<T>(){
    private var upstream: Observable<T> = upstream1

    override fun subscribeActual(observer: Observer<in T>) {
        upstream.subscribe(CustomerObserver<T>(observer as Observer<T>))
    }

    class CustomerObserver<R>(observer1:Observer<R>):Observer<R>{
        private val observer: Observer<R> = observer1
        override fun onComplete() {
            observer.onComplete()
        }

        override fun onSubscribe(d: Disposable) {
            observer.onSubscribe(d)
        }

        override fun onNext(t: R) {
            observer.onNext(t)
        }

        override fun onError(e: Throwable) {
        }
    }
}