/*
 * 菜单管理面板
 */

Ext.define('App.view.ProductPanel', {
    extend: 'App.ux.CrudPanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: "/products",
    initComponent: function () {
        var me = this;
        me.fields = me.getFields();
        me.columns = me.getColumns();
        me.window = me.getWindow();
        me.callParent(arguments);
    },
    getWindow: function () {
        return {
            title: '编辑窗口',
            items: [
                {
                    name: 'id',
                    hidden: true
                },
                {
                    fieldLabel: '产品名称',
                    name: 'name',
                    allowBlank: false
                },
                {
                    fieldLabel: '售价',
                    name: 'price',
                    allowBlank: false
                },
                {
                    fieldLabel: '商品描述',
                    name: 'description',
                    allowBlank: false
                }
            ]
        };
    },
    getFields: function () {
        return ['id', 'name', 'price', 'description'];
    },
    getColumns: function () {
        return [
            {
                xtype: 'rownumberer'
            },
            {
                text: "产品名称",
                width: 120,
                dataIndex: 'name'
            },
            {
                text: "售价",
                width: 80,
                dataIndex: 'price'
            }
            ,
            {
                text: "商品描述",
                width: 200,
                dataIndex: 'description'
            }
        ];
    }
});
