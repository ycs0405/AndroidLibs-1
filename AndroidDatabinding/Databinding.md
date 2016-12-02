###Databinding可以做什么？
- 在 xml 布局文件中进行数据与事件绑定
- 在 Java 代码中声明 ViewModel 并注入
- 添加自定义绑定方法做更多奇妙的事

###使用Databinding可以带来那些好处？
- 不再需要书写 findViewById
- 不要需要使用 setOnXXXListener() 绑定事件
- Activity &　Fragment的代码更加简洁
- XML 是 UI 的唯一真实来源
- 基本不再需要给 View 设置 id
- 再也不需要写 Adapter
- …

###Databinding是如何开始工作的？
1. 开始编译。
2. 处理layout文件。
3. 解析表达式。
4. Java编译。
5. 解析依赖。
6. 找到setter。

layout文件编译后 -> 生成相应的代码：
1. layout的xml文件名 -> JavaBinding类名。如：activitty_main -> ActivityMainBinding。
2. view id 根据下划线转变成驼峰命名。如：tv_title -> tvTitle。
3. variable 生成相应的setVariable方法。

###性能问题
- 0反射
- findViewById需要遍历整个ViewGroup，使用DataBinding，一次性注入所有的view。
- Use bit flag to invalidate (mDirtyFlags), only refresh fields changed
- Operation triggered in next executeBindings()
- Cached expression result, like:
    - a ? (b ? c : d) : e
    - f ? (b ? c : d) : f










