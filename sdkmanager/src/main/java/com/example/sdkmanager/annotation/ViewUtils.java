//package com.example.sdkmanager.annotation;
//
//import android.app.Activity;
//import android.view.View;
//
//import java.lang.reflect.Field;
//
///**
// * Created by moment on 2018/7/3.
// */
//
//public class ViewUtils {
//    public static void inject(Activity activity) {
//        inject(new ViewFinder(activity), activity);
//    }
//
//    // 兼容View
//    public static void inject(View view) {
//        inject(new ViewFinder(view), view);
//    }
//
//    // 兼容Fragment
//    public static void inject(View view, Object object) {
//        inject(new ViewFinder(view), object);
//    }
//
//    private static void inject(ViewFinder viewFinder, Object object) {
//        injectFiled(viewFinder, object);
//        injectEvent(viewFinder, object);
//    }
//
//    // 注入事件
//    private static void injectEvent(ViewFinder viewFinder, Object object) {
//
//    }
//
//    /**
//     * 注入属性
//     */
//    private static void injectFiled(ViewFinder viewFinder, Object object) {
//        // object --> activity or fragment or view 是反射的类
//        // viewFinder --> 只是一个view的findViewById的辅助类
//
//        // 1. 获取所有的属性
//        Class<?> clazz = object.getClass();
//        // 获取所有属性包括私有和公有
//        Field[] fields = clazz.getDeclaredFields();
//
//        for (Field field : fields) {
//            // 2. 获取属性上面ViewById的值
//            ViewById viewById = field.getAnnotation(ViewById.class);
//
//            if (viewById != null) {
//                // 获取ViewById属性上的viewId值
//                int viewId = viewById.value();
//                // 3. 通过findViewById获取View
//                View view = viewFinder.findViewById(viewId);
//
//                if (view != null) {
//                    // 4. 反射注入View属性
//                    // 设置所有属性都能注入包括私有和公有
//                    field.setAccessible(true);
//                    try {
//                        field.set(object, view);
//                    } catch (IllegalAccessException e) {
//                        e.printStackTrace();
//                    }
//                } else {
//                    throw new RuntimeException("Invalid @ViewInject for "
//                            + clazz.getSimpleName() + "." + field.getName());
//                }
//            }
//        }
//    }
//}
