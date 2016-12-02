/*
 * AcScope     2016/9/20-09-20
 * Copyright (c) 2016 KOTERWONG All right reserved
 */
package me.koterwong.di.scope;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Koterwong on 2016/9/20 14:51
 *
 * 理解Scope：
 * 1. 管理Component和Module之间的关系，保证Component和Module是匹配的。
 * 编译器会检查 Component管理的Modules，若发现标注Component的自定义Scope注解
 * 与Modules中的标注创建类实例方法的注解不一样，就会报错。（使用@Inject注解标注的实例，则用Scope标注实例类）
 * Singleton也是Scope的一种实现。
 *
 * 2. 更好的管理Component之间的组织方式，不管是依赖方式还是包含方式，
 * 都有必要用自定义的Scope注解标注这些Component，这些注解最好不要一样了，
 * 不一样是为了能更好的体现出Component之间的组织方式。
 * 还有编译器检查有依赖关系或包含关系的Component，若发现有Component没有用自定义Scope注解标注，则会报错。
 *
 * 自定Scope，为每个Activity的Component和Module提供注解作用域。
 */
@Scope
@Retention(RetentionPolicy.RUNTIME)
public @interface ActScope {

}
