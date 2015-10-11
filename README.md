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

##MPie
![](https://github.com/mychoices/Mchart/blob/master/image/pie.gif?raw=true)
- 相关属性
~~~
<!--间隔线的颜色-->
<attr name="pieInterColor" format="color"/>
<!--间隔线的宽度-->
<attr name="pieInterWidth" format="dimension"/>
<!--背景颜色-->
<attr name="piebackground" format="color"/>
<!--特殊的角度- 默认0 -->
<attr name="specialAngle" format="integer"/>
<!--移动的时候显示突出的扇形还是手指提起的时候才显示-->
<attr name="outMoving" format="boolean"/>
<!--是否支持扇形的选中突出效果 默认true-->
<attr name="PieSelector" format="boolean"/>
<!--选中的扇形 突出部分长度-->
<attr name="pointPieOut" format="dimension"/>
<!--是否显示提示线-->
<attr name="showLine" format="boolean"/>
<!--显示所有提示线-->
<attr name="showCenterAll" format="boolean"/>
<!--提示线居中显示 默认true-->
<attr name="showInCenAngle" format="boolean"/>
<!--旋转的时候显示提示线条 将会使提示线的动画失效-->
<attr name="movingShowTX" format="boolean"/>
<!--提示线条的宽度-->
<attr name="TsWidth" format="dimension"/>
<!--提示线颜色-->
<attr name="TsColor" format="color"/>
<!--提示线字体的大小-->
<attr name="TtextSize" format="dimension"/>
<!--提示线横线部分的长度-->
<attr name="TshLong" format="dimension"/>
<!--外辅助圆局四周的边距 也就是 提示线转折点距离边框的最小距离-->
<attr name="lpading" format="dimension"/>
~~~
###相关方法

	~~~
	setShowCenterAll(boolean showall);//是否显示所有信息提示线条
	setShowInCenAngle(boolean showInCenAngle2)//提示线条 扇形中心显示
	setShowLine(boolean showLine)//是否显示提示线条
	setPieShowAnimation(PieShowAnimation ani)//设置饼图的展示动画
	setDegrees(float degrees)//设置饼图的旋转角度
	setMovingShowTX(boolean movingShowTX)//设置旋转的时候是否显示提示线
	setPieRotateable(boolean pieRotateable)//设置饼图是否可以旋转
	~~~

-----
##NEXT
- 统一相关方法名字
- 补充自定义属性
