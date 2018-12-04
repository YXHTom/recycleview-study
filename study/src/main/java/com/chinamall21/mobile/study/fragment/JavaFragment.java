package com.chinamall21.mobile.study.fragment;

import android.view.View;

import com.chinamall21.mobile.study.R;
import com.chinamall21.mobile.study.base.BaseFragment;
import com.chinamall21.mobile.study.utils.LogUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * desc：
 * author：Created by xusong on 2018/11/7 12:20.
 */


public class JavaFragment extends BaseFragment {

    public static JavaFragment newInstance() {
        JavaFragment fragment = new JavaFragment();
        return fragment;
    }

    @Override
    protected void initView(View rootView) {
        // 通过Java反射机制得到类的包名和类名
        //test1();
        //验证所有的类都是class类的实例对象
        //test2();
        //通过Java反射机制，用Class 创建类对象[这也就是反射存在的意义所在]，无参构造
        //test3();

        //建造者模式
        test4();

    }

    private void test4() {
        Director director = new Director();
        CreateBuilder createBuilder = new CreateBuilder();
        director.Construct(createBuilder);
        Computer computer = createBuilder.GetComputer();
        computer.show();
    }

    public abstract class Builder {
        public abstract void buildCPU();

        public abstract void buildMainBoard();

        //装硬盘
        public abstract void buildBD();
        //返回产品的方法：获得组装好的电脑
        public abstract Computer GetComputer();
    }

    //装机人员
    public class Director {
        public void Construct(Builder builder) {
            builder.buildCPU();
            builder.buildMainBoard();
            builder.buildBD();
        }
    }

    //具体建造者
    public class CreateBuilder extends Builder {
        Computer mComputer = new Computer();

        @Override
        public void buildCPU() {
            mComputer.Add("cpu");
        }

        @Override
        public void buildMainBoard() {
            mComputer.Add("mainboard");
        }

        @Override
        public void buildBD() {
            mComputer.Add("bd");
        }

        @Override
        public Computer GetComputer() {
            return mComputer;
        }
    }

    //定义具体产品类（Product）
    public class Computer {
        private List<String> parts = new ArrayList<>();

        //用于将组件组装到电脑里
        public void Add(String part) {
            parts.add(part);
        }

        public void show() {
            for (int i = 0; i < parts.size(); i++) {
                LogUtils.LogE(parts.get(i).toString());
            }
            LogUtils.LogE("电脑组装完成，请验收");
        }
    }


    private void test3() {
        Class<?> c = null;
        try {
            c = Class.forName("com.chinamall21.mobile.study.fragment.JavaFragment$Person");

            Person person = (Person) c.newInstance();
            person.setAge(20);
            person.setName("xuqiang");
            LogUtils.LogE(person.toString());

        } catch (Exception e) {
            LogUtils.LogE("Exception");
            e.printStackTrace();
        }
    }

    private void test2() {
        Class<?> c1 = null;
        Class<?> c2 = null;
        try {
            c1 = Class.forName("com.chinamall21.mobile.study.fragment.JavaFragment$Person");
            LogUtils.LogE(c1.toString());
        } catch (ClassNotFoundException e) {
            LogUtils.LogE("ClassNotFoundException");
            e.printStackTrace();
        }
        c2 = Person.class;
        LogUtils.LogE(c2.toString());
        LogUtils.LogE("1111111111");

    }

    private void test1() {
        Person person = new Person();
        LogUtils.LogE("getName =" + person.getClass().getName() + " getSimpleName =" + person.getClass().getSimpleName());
        LogUtils.LogE(" getPackage =" + getClass().getPackage().getName());
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_drag;
    }

    class Person {

        private String name;
        private int age;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        @Override
        public String toString() {
            return "Person{" +
                    "name='" + name + '\'' +
                    ", age=" + age + '}';
        }
    }


}
