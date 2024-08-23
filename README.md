# library
图书管理系统
整个系统采取了前后端分离的设计：后端采用了Springboot作为系统的开发基础，使用springsecurity框架实现认证、权限控制，采用了mybatis-plus框架完成DAO层的处理，使用redis对数据进行了缓存，同时使用Springboot的默认日志logback，作为日志框架来存储日志。使用的是MySQL作为存储数据库。
前端采用了elmentadmin模版进行改造开发，主要是使用了vue+elementui进行开发。
管理员的登录账号/密码：admin/123456 普通用户：user01/123456登录界面
![1724410417730](https://github.com/user-attachments/assets/05f10fb5-d212-43d0-991c-5f018872b05e)

登录之后根据权限来判断功能菜单，管理员登录可以操作图书信息以及添加系统的用户

![图片](https://github.com/user-attachments/assets/204f85ec-00ad-4603-a80b-8e1aeff6562e)
如果是普通用户则只能查阅图书信息，无法进行操作
![图片](https://github.com/user-attachments/assets/366986c7-f9ef-49ec-aee1-e9a4c2d936f7)
管理员登录可以操作图书信息--添加
![图片](https://github.com/user-attachments/assets/608d5a48-e8c3-48eb-b77e-9be0e6df0293)
管理员登录可以操作图书信息--修改
![图片](https://github.com/user-attachments/assets/608d5a48-e8c3-48eb-b77e-9be0e6df0293)
管理员登录可以操作图书信息--查询
![图片](https://github.com/user-attachments/assets/2cfcf702-c519-435b-bab4-7b1ca7a68d5b)
管理员登录可以操作图书信息--删除
![图片](https://github.com/user-attachments/assets/308828d4-e636-4d9c-bda7-adba4d0172ef)
