/*
 * 商品类型管理面板
 */

Ext.define('App.view.ProductTypePanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.producttypepanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: "/productTypes",
    window: {
        title: '管理窗口',
        items: [
            {
                fieldLabel: '商品类型',
                name: 'name'
            }
        ]
    },
    queryWindow: {
        title: '查询窗口',
        items: [
            {
                fieldLabel: '商品类型',
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
            text: "商品类型",
            dataIndex: 'name'
        }
    ]
});
