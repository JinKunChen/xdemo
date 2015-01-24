/*
 * 地区管理面板
 */

Ext.define('App.view.AreaPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.areapanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: '/system/areas',
    window: {
        title: '地区管理窗口',
        items: [
            {
                fieldLabel: '地区名称',
                name: 'name'
            },
            {
                fieldLabel: '地区编码',
                name: 'code'
            }
        ]
    },
    queryWindow: {
        title: '地区管理窗口',
        items: [
            {
                fieldLabel: '地区名称',
                name: 'name'
            },
            {
                fieldLabel: '地区编码',
                name: 'code'
            }
        ]
    },
    fields: ['id', 'name', 'code', 'parent.id', 'parent.name', 'SN'],
    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            width: 100,
            text: "地区名称",
            dataIndex: 'name',
            sortable: true
        },
        {
            width: 100,
            text: "地区编码",
            dataIndex: 'code'
        },
        {
            width: 80,
            text: "排序号",
            dataIndex: 'SN'
        }
    ]
});
