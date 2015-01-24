/*
 * 门店管理面板
 */

Ext.define('App.view.StoreTypePanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.storetypepanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: '/storeTypes',
    window: {
        title: '管理窗口',
        items: [
            {
                fieldLabel: '门店类型',
                name: 'name'
            }
        ]
    },
    queryWindow: {
        title: '查询窗口',
        items: [
            {
                fieldLabel: '门店类型',
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
            text: "门店类型",
            dataIndex: 'name'
        }
    ]
});
