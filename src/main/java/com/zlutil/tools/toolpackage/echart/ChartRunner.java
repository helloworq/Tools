package com.zlutil.tools.toolpackage.echart;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChartRunner {
    public static void main(String[] args) {
        List<String> list=new ArrayList<>();
        // 绘图数据集
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        for (Object obj : list) {
            Map<String, Object> map = (Map) obj;
            dataSet.setValue((Long) map.get("num"), (String) map.get("mn"), map.get("ym").toString());
        }
        JFreeChart  chart = ChartFactory.createLineChart(
                "图表标题",
                "X轴标题",
                "Y轴标题",
                dataSet,
                PlotOrientation.VERTICAL, // 绘制方向
                true, // 显示图例
                true, // 采用标准生成器
                false // 是否生成超链接
        );
    }
}
