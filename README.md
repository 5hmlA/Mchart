#Screenshots
![](http://i.imgur.com/JaoqkeW.gif)
![](https://github.com/mychoices/Mchart/blob/master/2323.gif?raw=true)

#Usage
##Step 1
###Gradle
```
dependencies {
    compile 'yun.jonas.jang:mchart:1.1.1'
}

```

##MProgressWidthMsg 
![](https://github.com/mychoices/Mchart/blob/master/image/msg.gif?raw=true)

- 相关自定义属性
```
<!--进度条颜色-->
<attr name="recColor" format="color"></attr>
<!--文字颜色-->
<attr name="msgColor" format="color"/>
<!--矩形进度条的背景颜色-->
<attr name="backColor" format="color"/>
<!--单位-->
<attr name="unit" format="string"/>
<!--圆角半径-->
<attr name="recRound" format="dimension"/>
<attr name="TextToProgress" format="dimension"/>
<attr name="currprogress" format="float"/>
<!--是否显示 百分比-->
<attr name="percent" format="boolean"/>
<attr name="maxprogress" format="float"/>
<attr name="msgMode" format="enum">
    <!--层叠-->
    <enum name="msg_stack" value="0" />
    <!--排列-->
    <enum name="msg_arrange" value="1" />
</attr>
```
###setCurrent
```
setCurrentPercentAni(float current)//设置百分比进度 带有动画
setCurrentAni(float current)//设置当前进度 带有动画
setMax(float max)//设置总进度 默认100
```

##MProgressBall
![](https://github.com/mychoices/Mchart/blob/master/image/ball.gif?raw=true)
- 相关属性
```
<attr name="progressColor" format="color"/>
<attr name="backgroundColor" format="color"/>
<attr name="balltextColor" format="color"/>
<attr name="progressMax" format="float"/>
<attr name="progressCurrent" format="float"/>
进度模式  0 波浪模式   1 直线普通模式
<attr name="progressMode" format="enum">
    <enum name="wave" value="0"/>
    <enum name="normal" value="1"/>
</attr>
<attr name="balltextSize" format="dimension"/>
```
###setCurrent
~~~
setProgressMode(int progressMode)//设置进度球模式 
setProgressCurrentAni(float progressCurrent)//设置当前进度 含有动画
setProgressCurrent(float progressCurrent)//设置当前进度 没有动画
animateShow()//重新执行一下动画
setProgressMax(float progressMax)//设置总进度  默认100
~~~

##ChargingView
![](https://github.com/mychoices/Mchart/blob/master/image/charge.gif?raw=true)
相关属性(后续添加)
###setCurrent
~~~
setCurrent(float current)//设置当前进度 含有动画
~~~

-----
##NEXT
- 统一相关方法名字
- 补充自定义属性
