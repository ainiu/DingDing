
标题类
========================================

# 标题一
## 标题二
### 标题三
...
###### 标题六

标题一
======================================

副标题
--------------------------------------

代码类
======================================

`var c = 123;`

```java
public class {
    System.out.print('Hello World!');
    System.out.print('Hello World!');
    System.out.print('Hello World!');
    System.out.print('Hello World!');
    System.out.print('Hello World!');
}
```

```php
public function edit()
{
    $items = M('role') -> where(array('status' => 1)) -> field('id,name') -> select();
    $id = I('get.id');
    $roles = M('role_user') -> where(array('user_id' => $id)) -> getField('role_id', true);
    $item = M('admin') -> where(array('id' => $id)) -> find();
    $this -> assign(array(
        'id' => $id,
        'item' => $item,
        'items' => $items,
        'roles' => $roles
        ));
    $this -> display();
}
```

<table>
    <tr>
        <td>这是测试</td>
        <td>这是测试</td>
        <td>这是测试</td>
    </tr>
    <tr>
        <td>这是测试</td>
        <td>这是测试</td>
        <td>这是测试</td>
    </tr>
    <tr>
        <td>这是测试</td>
        <td>这是测试</td>
        <td>这是测试</td>
    </tr>
    <tr>
        <td>这是测试</td>
        <td>这是测试</td>
        <td>这是测试</td>
    </tr>
</table>

批注类

Md说明文档
===================

> 这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。这是一个MD说明文档。

*这是斜体文字*

<b>这是粗体</b>

列表类
========================

2. 列表一
3. 列表二
4. 防辐射
5. 发大水

+ 列表一
+ 列表二
- 列表三
- 列表四

引用类
============================
- 超链接

[百度](http://www.baidu.com)


图片
[img]()

高级用法
============================
表格

|标题一 |标题二             |
|------:|-------------------|
|内容一 |内容               |
|内容   |内容               |

[TOC]

公式输入
2015/12/8 工作日志
#标题一
--叮叮

