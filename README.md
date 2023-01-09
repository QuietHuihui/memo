
# 备忘录软件

Springboot+JpaRepository+JavaFX
![Image text](https://raw.githubusercontent.com/QuietHuihui/memo/main/img-folder/memo.png?token=GHSAT0AAAAAAB4IYN5FRFTVYRCHJFPUPEG6Y53V4FA)


## 源码使用方法

首先将代码解压，然后打开Spring Tool Suite4, 点击import existing maven project ,便可以将此项目导入。
之后再右键项目，Run As中选择Springboot App便可以实现。
## 开发工具
使用SceneBuilder创建所有前端页面，使用Spring Tool Suite4 来创建Springboot项目，使用的jdk版本为1.8，引入javafx依赖，根据各个控件的id和onAction编写事件。使用Sqlite3数据库存放用户和备忘信息。
## 实现功能
1、用户登录和注册，登录和注册界面可以相互跳转。</br>
2、备忘查看界面，可以点击“添加备忘”按钮，弹出添加备忘对话框，
点击“提交”之后，表格刷新，可以看到新的备忘标题，状态，详情按钮和删除按钮。</br>
3、单击表格中每条备忘的详情图标按钮，可以弹出详细信息对话框，在其中能够编辑并提交对备忘的更改。</br>
4、单击表格中每条备忘的删除图标按钮，可以删除备忘。</br>
5、在搜索框中输入关键词，单击“搜索”按钮，便可以在表格中展示在标题或者内容中含有指定关键词的备忘记录。</br>
6、在搜索框中输入需要替换的内容，在替换框中输入需要替换的内容，单击“替换”按钮，便可以把所有备忘中，含有需替换关键词的标题和内容替换。</br>
7、单击“所有备忘”查看所有备忘，单击“未完成备忘”查看未完成备忘，单击“已完成备忘”查看已完成备忘。
