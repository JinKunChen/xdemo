/*
 * 操作日志管理面板
 */

Ext.define('App.view.LogPanel', {
    alias: 'widget.logpanel',
    extend: 'App.ux.CrudPanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: "/products",
    fields: ['id', 'name', 'price', 'brand.id', 'brand.name', 'model', 'imgUrl', 'description'],
    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            text: "产品名称",
            width: 200,
            dataIndex: 'name'
        },
        {
            text: "型号",
            width: 80,
            dataIndex: 'model'
        },
        {
            text: "价格",
            width: 80,
            formatter: 'usMoney',
            dataIndex: 'price'
        },
        {
            text: "所属品牌",
            width: 80,
            dataIndex: 'brand.name'
        },
        {
            text: "商品描述",
            flex: 1,
            dataIndex: 'description'
        }
    ]
});
