/*
 * 品牌管理面板
 */

Ext.define('App.view.BrandPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.brandpanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: "/brands",
    window: {
        title: '管理窗口',
        items: [
            {
                fieldLabel: '品牌名称',
                name: 'name'
            }
        ]
    },
    queryWindow: {
        title: '查询窗口',
        items: [
            {
                fieldLabel: '品牌名称',
                name: 'name'
            }
        ]
    },
    fields: ['id', 'name'],
    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            width: 100,
            text: "品牌名称",
            dataIndex: 'name'
        }
    ]
});
