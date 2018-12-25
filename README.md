Goal-Capability-Commitment Ontology Model Based the Medical Waste Automated Guided Vehicle Transportation–基于GCC本体模型的MWT-AGV系统
=========================================================================================================================================================================

中文文档 | [English Document](https:)

说明
---------------------------

本系统是在GCC模型的基础上进行的自适应协作实验。

*  Goal-Capability-Commitment本体模型是实验的基础。
*  实验1：
*  实验2：
*  实验3：

**实验的理论细节可参考\[系统理论介绍.pdf\]**

本系统使用java语言编写，**本体模型文件使用OWL文件建模**，场景建模OWl本体文件使用即可 [GCCmodel文件](https://github.com/bluemapleman/NewsRecommendSystem/blob/master/test_data.sql)直接下载放在指定位置即可。

使用
---------------------------

### 预备工作

#### 一、软件安装

*   1、protege3.4.8:本体建模和解析;
*   2、Graphviz 2.28：规划图使用；（默认安装路径为D:\\Program Files (x86)\\Graphviz 2.28，若安装在其他位置，项目中要更改org.wit.rpt.drawtool包中GraphViz.java中的路径）

#### 二、文件位置

**本系统涉及本体OWL文件和图规划文件的路径问题。（若文件路径存在问题，无法正常运行）**

本系统需要GCCmodel模型文件作为场景基础进行实验，图规划实验生成的图片存放在指定的文件夹中，可自定义修改。

*   **1、GCCmodel.rar**中的模型文件放在：D:\\GCCmodel文件夹中；
*   **2、图规划**生成图片存放文件夹：D:\\GH-AGVsModel文件夹中；

#### 三、实验说明

**本系统涉及三个主要实验**

实验 | 实验内容 | 包(所在位置) | 实验说明 
---- | -------- |-----|-----
1 | 匹配     | experiments | 能力目标匹配(加入承诺、在线生成承诺、预先生成承诺)
2 | 目标树   | experiments | 目标树方法
3 | 图规划   | org.wit.rpt.graphplan | 1、GraphPlanNotSimCa.java：没有添加相似度，只有能力的图规划；<br>2、GraphPlanNotSimCaCo.java：没有添加相似度，有承诺的图规划；<br>3、GraphPlanSimCaCo.java：有相似度和承诺的图规划；


#### 四、主要算法介绍

算法1

代码示例：

**注意：。。。。。**

额外说明
-----------------------------

1.

2.

3.

更新日志
=============================

**欢迎大家踊跃提出自己对该推荐系统的任何想法和建议！**

版本 | 日期 | 特性 
------ | ----- | ------ 
V1.0.0 | 2018/12/24 | 测试代码上传的效果。 
