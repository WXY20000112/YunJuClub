package com.wxy.subject.common.listener;

import com.mybatisflex.annotation.InsertListener;
import com.mybatisflex.annotation.UpdateListener;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.Date;

/**
 * @program: YunJuClub-Flex
 * @description: 监听数据进行新增和更新时进行数据填充操作
 * @author: 32115
 * @create: 2024-05-13 14:54
 */
public class MyInsertAndUpdateListener implements InsertListener, UpdateListener {

    /**
     * @author: 32115
     * @description: 数据新增时执行该操作
     * @date: 2024/5/13
     * @param: object
     * @return: void
     */
    @SneakyThrows
    @Override
    public void onInsert(Object object) {
        // 由于传递过来的object对象无法确定是哪个实体类
        // 所以我们通过反射机制获取对应实体类，并获取实体类的set方法，对字段进行赋值
        // 修改操作类似，区别是修改时只需设置setUpdateTime和setUpdateUser字段

        // 1、反射获取具体类对象
        Class<?> objectClass = object.getClass();

        // 反射获取类构造器，用到时可取消注释
        // Constructor<?>[] constructors = objectClass.getConstructors();

        // 反射获取类的属性名称，用到时可取消注释
        // Field[] fields = objectClass.getDeclaredFields();

        // 反射获取类的所有方法，用到时可取消注释
        // Method[] methods = objectClass.getDeclaredMethods();

        // 2、获取setCreateTime、setUpdateTime、setCreateUser、SetUpdateUser方法
        // 其中第二个参数是要获取的方法的参数类型，有多个用逗号分隔即可
        Method setCreatedTime = objectClass.getDeclaredMethod("setCreatedTime", Date.class);
        Method setUpdateTime = objectClass.getDeclaredMethod("setUpdateTime", Date.class);
        Method setCreatedBy = objectClass.getDeclaredMethod("setCreatedBy", String.class);
        Method setUpdateBy = objectClass.getDeclaredMethod("setUpdateBy", String.class);

        // 3、调用invoke方法执行获取到的类方法，设置字段值
        setCreatedTime.invoke(object, new Date());
        setUpdateTime.invoke(object, new Date());
        setCreatedBy.invoke(object, "wxy");
        setUpdateBy.invoke(object, "wxy");
    }

    /**
     * @author: 32115
     * @description: 数据修改时执行该操作
     * @date: 2024/5/13
     * @param: object
     * @return: void
     */
    @SneakyThrows
    @Override
    public void onUpdate(Object object) {
        // 1、反射获取具体类对象
        Class<?> objectClass = object.getClass();

        // 2、获取setCreateTime、setUpdateTime、setCreateUser、SetUpdateUser方法
        // 其中第二个参数是要获取的方法的参数类型，有多个用逗号分隔即可
        Method setUpdateTime = objectClass.getDeclaredMethod("setUpdateTime", Date.class);
        Method setUpdateBy = objectClass.getDeclaredMethod("setUpdateBy", String.class);

        // 3、调用invoke方法执行获取到的类方法，设置字段值
        setUpdateTime.invoke(object, new Date());
        setUpdateBy.invoke(object, "wxy");
    }
}
