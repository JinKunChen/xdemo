/**
 * 基于Ext.form.field.ComboBox的ComboBox下拉封装
 */
Ext.define('App.ux.RemoteComboBox', {
    extend: 'Ext.form.field.ComboBox',
    alias: 'widget.ux_remotecombobox',
    //fieldLabel: '未定义',
    code: '未定义',
    displayField: 'text',// 定义要显示的字段
    valueField: 'value',// 定义值字段
    // allQuery:'all',//查询全部信息的查询字符串
    minChars: 1,// 下拉列表框自动选择前用户需要输入的最小字符数量
    queryDelay: 300,// 查询延迟时间
    queryParam: 'searchKey',// 查询的名字
    triggerAction: 'all',// 单击触发按钮显示全部数据
    queryMode: 'remote',// 远程模式
    emptyText: '请选择...',
    //forceSelection : true,//要求输入值必须在列表中存在
    autoSelect: true,
    typeAhead: true,//允许自动选择匹配的剩余部分文本
    pageSize: 50,
    listConfig: {
        loadingText: '正在加载信息...',// 加载数据时显示的提示信息
        emptyText: '未找到匹配值',// 当值不在列表是的提示信息
        maxHeight: 100
        // 设置下拉列表的最大高度为60像素
    },

    initComponent: function () {
        var me = this;
        me.store = Ext.create('Ext.data.Store', {
            fields: ['text', 'value'],
            proxy: {
                type: 'ajax',// Ext.data.AjaxProxy
                url: App.Config.comboUrl,
                actionMethods: 'POST',
                reader: {
                    rootProperty: 'content',
                    totalProperty: 'totalElements'
                },
                extraParams: {
                    code: me.code
                }
            }
        });
        me.callParent(arguments);
    }
});
