/**
 * 基于Ext.form.Panel的FormPanel表单封装
 */
Ext.define('App.ux.FormPanel', {
    extend: 'Ext.form.Panel',
    alias: 'widget.ux_formpanel',
    bodyBorder: false,
    frame: true, // 是否渲染表单
    defaultType: 'textfield',
    padding: '5px 20px 5px 0px',
    defaults: { // 统一设置表单字段默认属性
        autoFitErrors: false,// 展示错误信息时是否自动调整字段组件宽度
        labelSeparator: '：',// 分隔符
        labelWidth: 80,// 标签宽度
        width: 350,// 字段宽度
        allowBlank: true,// 是否允许为空
        labelAlign: 'right',// 标签对齐方式
        msgTarget: 'qtip' // 显示一个浮动的提示信息
    },
    initComponent: function () {
        this.callParent(arguments);
    }
});
