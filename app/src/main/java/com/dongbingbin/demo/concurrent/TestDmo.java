/**
 * FileName: TestDmo
 * Author: dongbingbin
 * Date: 2020/10/21 23:18
 * Description:
 * History:
 * <author> <time> <version> <desc>
 * 作者姓名 修改时间 版本号 描述
 */
package com.dongbingbin.demo.concurrent;

/**
 * @ClassName: TestDmo
 * @Description: java类作用描述
 * @Author: dongbingbin
 * @Date: 2020/10/21 23:18
 */
public class TestDmo {
    public static void main(String[] args){
        String.format("我是%s1谁呀","123");
        Integer integer = Integer.valueOf("123");

        Animal animal = new Dog();
        Dog dog = new Dog();

        try {
            animal.move();
        }catch (Exception ex1){
            ex1.printStackTrace();
        }

        dog.move();
    }
}