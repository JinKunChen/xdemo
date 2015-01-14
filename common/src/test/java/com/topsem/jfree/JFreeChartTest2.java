package com.topsem.jfree;

/**
 * @author Chen on 14-12-3.
 */

import lombok.SneakyThrows;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.ChartUtilities;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.axis.CategoryAxis;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.title.TextTitle;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.ui.ApplicationFrame;

import javax.swing.*;
import java.awt.*;
import java.io.File;

public class JFreeChartTest2 extends ApplicationFrame {
    public JFreeChartTest2(String title) {
        super(title);
        this.setContentPane(createPanel()); //构造函数中自动创建Java的panel面板
    }

    public static CategoryDataset createDataset() //创建柱状图数据集
    {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        dataset.setValue(10, "a", "管理人员");
        dataset.setValue(20, "b", "市场人员");
        dataset.setValue(40, "c", "开发人员");
        dataset.setValue(15, "d", "其他人员");
        return dataset;
    }

    public static JFreeChart createChart(CategoryDataset dataset) //用数据集创建一个图表
    {
        JFreeChart chart = ChartFactory.createBarChart("hi", "人员分布",
            "人员数量", dataset, PlotOrientation.VERTICAL, true, true, false); //创建一个JFreeChart
        chart.setTitle(new TextTitle("某公司组织结构图", new Font("宋体", Font.BOLD + Font.ITALIC, 20)));//可以重新设置标题，替换“hi”标题
        CategoryPlot plot = (CategoryPlot) chart.getPlot();//获得图标中间部分，即plot
        CategoryAxis categoryAxis = plot.getDomainAxis();//获得横坐标
        categoryAxis.setLabelFont(new Font("微软雅黑", Font.BOLD, 12));//设置横坐标字体
        return chart;
    }

    @SneakyThrows
    public static JPanel createPanel() {
        JFreeChart chart = createChart(createDataset());
        ChartUtilities.saveChartAsPNG(new File( "某公司组织结构图.png"), chart, 550, 250);
        return new ChartPanel(chart); //将chart对象放入Panel面板中去，ChartPanel类已继承Jpanel
    }

    public static void main(String[] args) {
        JFreeChartTest2 chart = new JFreeChartTest2("某公司组织结构图");
        chart.pack();//以合适的大小显示
        chart.setVisible(true);

    }
}
