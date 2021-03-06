package com.dongbingbin.demo.concurrent;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.FutureTask;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

import androidx.annotation.NonNull;

import com.dongbingbin.nativeutils.utils.DateUtils;

import javax.crypto.spec.PSource;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.ObservableSource;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.BiFunction;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservableJust;
import io.reactivex.internal.operators.observable.ObservablePublish;
import io.reactivex.observables.ConnectableObservable;
import io.reactivex.observables.GroupedObservable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

import io.reactivex.Observable;
import io.reactivex.ObservableEmitter;
import io.reactivex.ObservableOnSubscribe;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.internal.operators.observable.ObservablePublish;

public class SemaphoreExample1 {
    private final static int threadCount = 20;

    static String source = " select '$startdate到$sub_end_date的回购' as des, sum(d.buy_count) from (\n" +
            " select count(*) as buy_count from (\n" +
            " select distinct b.distinct_id from events as b\n" +
            "inner join  (\n" +
            " select distinct distinct_id as adistinct_id from events where event = 'PayTicketOrderDetail' and date = cast('$startdate' as timestamp) \n" +
            " and commodity_name like '%$keyword%'\n" +
            "   ) as a on a.adistinct_id=b.distinct_id\n" +
            "where event = 'PayTicketOrderDetail' \n" +
            "   \n" +
            "   \n" +
            "   and  date between cast('$p1_startdate_p1' as timestamp) and cast('$enddate' as timestamp)\n" +
            " and commodity_name like '%$keyword%' \n" +
            "   ) as c \n" +
            " \n" +
            "union\n" +
            "\n" +
            "select count(*) as buy_count from (\n" +
            "select distinct_id,count(*) count_dis from events where event = 'PayTicketOrderDetail' and date = cast('$startdate' as timestamp) \n" +
            " and commodity_name like '%$keyword%'\n" +
            " group by distinct_id ) as a where count_dis>1\n" +
            "   ) as d";


    public static void main(String[] args) throws Exception {
        String startdate = "2020-05-29";
        String enddate = "2020-06-14";
        String keywords = "银联立减";

        String startdate_plus_1 =DateUtils.dateToString(DateUtils.addDay(DateUtils.stringToDate(startdate),1));

        StringBuilder sb = new StringBuilder();

        Date from =  DateUtils.stringToDate(startdate);
        Date to = DateUtils.stringToDate(enddate);
        long days = DateUtils.getDayByMinusDate(from,to);

        for(int i=0;i<=days;i++){
            String temp = source;
            String subEndDate = DateUtils.dateToString(DateUtils.addDay(DateUtils.stringToDate(startdate),i));
            temp = temp.replace("$startdate",startdate);
            temp = temp.replace("$sub_end_date",subEndDate);
            temp = temp.replace("$keyword",keywords);
            temp = temp.replace("$p1_startdate_p1",startdate_plus_1);
            temp = temp.replace("$enddate",subEndDate);
            temp = temp.replace("$days",i+"");
//            sb.append(source.replace("$startdate",startdate));
//            sb.append(source.replace("$keyword",startdate));
//            sb.append(source.replace("$startdate+1",startdate_plus_1));
//            sb.append(source.replace("$enddate",subEndDate));
//            sb.append(source.replace("$days",days+""));
            sb.append("\n");
            sb.append(temp);
            if(i<days){
                sb.append("\n");
                sb.append("union");
            }
        }


        System.out.println(sb.toString());

       // testReturnError();
        //testSubscrib();
        //testGroup();
        //testScan();

        //testmapFlat();

        //testAmbArray();
//        Observable.just(1).ambWith()

        //testmapFlat();
        //subjectTest();

        //takeUntil();
//

        Callable callable = new Callable() {
            private int tickt=10;
            @Override
            public Object call() throws Exception {
                // TODO Auto-generated method stub
                String result;
                while(tickt>0) {
                    Thread.sleep(1000L);
                    System.out.println("票还剩余："+tickt);
                    tickt--;
                }
                result=(String) "票已卖光";
                return result;
            }
        };

        FutureTask<String> ft=new FutureTask<String>(callable);
        new Thread(ft).start();
        try{
            String result=ft.get();
            System.out.println(result);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }



        Observable.just(1,2,34,5,6).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer>6;
            }
        }).count().subscribe(new Consumer<Long>() {
            @Override
            public void accept(Long aLong) throws Exception {
                System.out.println(aLong);
            }
        });


    }

    static void test1() throws Exception {
        ExecutorService exec = Executors.newCachedThreadPool();
        // 每次最多三个线程获取许可
        final Semaphore semaphore = new Semaphore(3);
        final CountDownLatch cdl = new CountDownLatch(threadCount);
        for (int i = 0; i < threadCount; i++) {
            final int threadNum = i;
            exec.execute(new Runnable() {
                @Override
                public void run() {

                }
            });
        }
    }

    static void testReturnError(){
        Observable.just(1).map(new Function<Integer, Object>() {
            @Override
            public Object apply(Integer integer) throws Exception {
                throw new Exception("123");
            }
        }).onErrorResumeNext(new Function<Throwable, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Throwable throwable) throws Exception {
                return Observable.just(2);
            }
        }).onErrorReturnItem(33).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });
    }

    static void testSubscrib(){
        Observable.just(1).subscribeOn(Schedulers.computation()).subscribeOn(Schedulers.io())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer integer) throws Exception {
                        System.out.println(integer);
                    }
                });
    }

    static void testGroup(){
        String[] strs = {"mon","stud","dddf","sdf","df","ds","df"};
        Observable.fromArray(strs).groupBy(new Function<String, Object>() {
            @Override
            public Object apply(String s) throws Exception {
                return s.length()>3?"Three":"Default";
            }
        }).subscribe(new Consumer<GroupedObservable<Object, String>>() {
            @Override
            public void accept(final GroupedObservable<Object, String> objectStringGroupedObservable) throws Exception {
                System.out.println("*****************************************");
                objectStringGroupedObservable.subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws Exception {
                        System.out.println(s+":"+objectStringGroupedObservable.getKey());
                    }
                });
            }
        });
    }


    static void testScan(){
        Integer[] price = {1,2,3,4,5,6,7};
        Observable.fromArray(price).scan(new BiFunction<Integer, Integer, Integer>() {
            @Override
            public Integer apply(Integer integer, Integer integer2) throws Exception {
                return integer+integer2;
            }
        }).subscribe(new Consumer<Integer>() {
            @Override
            public void accept(Integer integer) throws Exception {
                System.out.println(integer);
            }
        });
    }

//当你传递多个Observable给amb操作符时，该操作符只发射其中一个Observable的数据和通知：
// 首先发送通知给amb操作符的的那个Observable，
// 不管发射的是一项数据还是一个onError或onCompleted通知，amb将忽略和丢弃其它所有Observables的发射物

//给定多个Observable，只让第一个发射数据的Observable发射全部数据。
//谁先出发，就接收谁，其他都不接收
//ambWith和ambArray差不多，Observable.amb(o1,o2)和o1.ambWith(o2)是一样的效果。
    static void testAmbArray() {
        Observable o1 = Observable.just("a", "b", "c").delay(2000, TimeUnit.MILLISECONDS);
        Observable o2 = Observable.just("=", "-", "*").delay(1000, TimeUnit.MILLISECONDS);
        Observable o3 = Observable.just("1", "2", "3");

        Observable observable = Observable.ambArray(o1, o2, o3);
        Disposable disposable = observable.subscribe(new Consumer<String>() {
            @Override
            public void accept(@NonNull String s) throws Exception {
                System.out.println("accept: " + s);
            }
        });
    }

    static void testmapFlat(){
        List<List<String>> lists = new ArrayList();
        for(int i=0;i<5;i++){
            List<String> temp = new ArrayList<>();
            for(int j=0;j<5;j++){
                temp.add(j+"");
            }
            lists.add(temp);
        }
        Observable.fromArray(lists.toArray()).flatMap(new Function<Object, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Object o) throws Exception {
                return Observable.fromArray(o);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println(o);
            }
        });

        Observable.just(1).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {
                return Observable.just(2);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {

            }
        });
    }

    static void subjectTest(){



        //ConnectableObservable connectableObservable = Observable.just(1,2,3,4,5).replay();
        final PublishSubject ps = PublishSubject.create();
        ConnectableObservable connectableObservable = ps.replay();
        //ForkJoinPool forkJoinPool = ForkJoinPool.commonPool();
        Disposable disposable1 = connectableObservable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("1狼神："+o);
            }
        });

        Disposable disposable2 = connectableObservable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("2狼神："+o);
            }
        });

        Disposable disposable3 = connectableObservable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("3狼神："+o);
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                Integer i = 0;
                while (true) {
                    try {
                        Thread.sleep(1000L);
                    }catch (Exception ex1){
                        ex1.printStackTrace();
                    }
                    ps.onNext(i);
                    i=i+1;
                }
            }
        }).start();
        try {
            Thread.sleep(2000L);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }

        connectableObservable.connect();
        try {
            Thread.sleep(2000L);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        disposable1.dispose();
        try {
            Thread.sleep(2000L);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }
        //ps.onComplete();
        connectableObservable.connect(new Consumer<Disposable>() {
            @Override
            public void accept(Disposable disposable) throws Exception {
                disposable.dispose();
            }
        });
    }

    static void takeUntil(){
        Observable.just(1,2,3,4,5,6,7,8,9).takeUntil(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer>5;
            }
        });
    }

    static void mapAndMapFlat(){
        Observable.just(1,2,3,4,5,6,7,8,9).map(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {
                return Observable.just(integer*2);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                //o.getClass() class io.reactivex.internal.operators.observable.ObservableJust
            }
        });

        Observable.just(1,2,3,4,5,6,7,8,9).flatMap(new Function<Integer, ObservableSource<?>>() {
            @Override
            public ObservableSource<?> apply(Integer integer) throws Exception {
                return Observable.just(integer*2);
            }
        }).subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                //o.getClass() class java.lang.Integer
            }
        });
    }

    static void contact(){
        Observable observable1 = Observable.just(1,2,3,4,5).map(new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer o) throws Exception {
                Thread.sleep(2000L);
                System.out.println("observable1:"+o);
                return o;
            }
        }).subscribeOn(Schedulers.io());
        Observable observable2 = Observable.just(5,6,7,8,9).map(new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer o) throws Exception {
                Thread.sleep(2000L);
                System.out.println("observable2:"+o);
                return o;
            }
        }).subscribeOn(Schedulers.io());

        Observable.concat(observable1,observable2).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Thread.sleep(1000L);
                System.out.println("merget consumer:"+o);
            }
        });
    }

    public static void merge(){
        Observable observable1 = Observable.just(1,2,3,4,5).map(new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer o) throws Exception {
                Thread.sleep(2000L);
                System.out.println("observable1:"+o);
                return o;
            }
        }).subscribeOn(Schedulers.io());
        Observable observable2 = Observable.just(5,6,7,8,9).map(new Function<Integer,Integer>() {
            @Override
            public Integer apply(Integer o) throws Exception {
                Thread.sleep(2000L);
                System.out.println("observable2:"+o);
                return o;
            }
        }).subscribeOn(Schedulers.io());

        Observable.merge(observable1,observable2).subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                Thread.sleep(1000L);
                System.out.println("merget consumer:"+o);
            }
        });
    }
    public static void publish(){
        ObservablePublish<Object> observable = (ObservablePublish<Object>) Observable.create(new ObservableOnSubscribe<Object>() {
            @Override
            public void subscribe(ObservableEmitter<Object> emitter) throws Exception {
                for(int i=0;i<10;i++){
                    Thread.sleep(1000);
                    emitter.onNext(i);
                }
            }
        }).subscribeOn(Schedulers.io()).observeOn(Schedulers.io()).publish();
        observable.subscribe(new Consumer() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("111:"+o);
            }
        });

        observable.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("222:"+o);
            }
        });

        observable.connect();
        try {
            Thread.sleep(5000L);
        }catch (Exception ex1){
            ex1.printStackTrace();
        }


        observable.subscribe(new Consumer<Object>() {
            @Override
            public void accept(Object o) throws Exception {
                System.out.println("333:"+o);
            }
        });
    }

    /**
     * refcount
     */
    public static void refCount(){
        Consumer<Long> subscriber1 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("subscriber1: "+aLong);
            }
        };

        Consumer<Long> subscriber2 = new Consumer<Long>() {
            @Override
            public void accept(@NonNull Long aLong) throws Exception {
                System.out.println("   subscriber2: "+aLong);
            }
        };

        ConnectableObservable<Long> connectableObservable = Observable.create(new ObservableOnSubscribe<Long>() {
            @Override
            public void subscribe(@NonNull final ObservableEmitter<Long> e) throws Exception {
                Observable.interval(10, TimeUnit.MILLISECONDS,Schedulers.computation())
                        .take(Integer.MAX_VALUE)
                        .subscribe(new Consumer<Long>() {
                            @Override
                            public void accept(Long aLong) throws Exception {
                                e.onNext(aLong);
                            }
                        });
            }
        }).observeOn(Schedulers.newThread()).publish();
        connectableObservable.connect();

        Observable<Long> observable = connectableObservable.refCount();

        Disposable disposable1 = observable.subscribe(subscriber1);
        Disposable disposable2 = observable.subscribe(subscriber2);

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        disposable1.dispose();
        disposable2.dispose();

        System.out.println("重新开始数据流");

        disposable1 = observable.subscribe(subscriber1);
        disposable2 = observable.subscribe(subscriber2);

        try {
            Thread.sleep(20L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private static void test(int threadNum) throws Exception {
        System.out.println("{"+threadNum+"}");
        Thread.sleep(1000);
    }
}
