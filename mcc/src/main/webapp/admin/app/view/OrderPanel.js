/*
 * 用户管理面板
 */

Ext.define('App.view.OrderPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.userpanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: '/orders',
    multiSelect: true,
    initComponent: function () {
        var me = this;
        me.window = me.getWindow();
        me.queryWindow = me.getQueryWindow();
        me.fields = me.getFields();
        me.columns = me.getColumns();
        me.callParent(arguments);
    },
    getWindow: function () {
        return {
            title: '菜单窗口',
            items: [
                {
                    fieldLabel: '订单编号',
                    name: 'id'
                },
                {
                    fieldLabel: '创建时间',
                    name: 'createdDate'
                }
            ]
        };
    },
    getQueryWindow: function () {
        return {
            title: '菜单窗口',
            items: [
                {
                    fieldLabel: '订单编号',
                    name: 'id'
                },
                {
                    fieldLabel: '创建时间',
                    name: 'createdDate'
                }
            ]
        };
    },

    getFields: function () {
        return ['id', 'createdDate'];
    },

    getColumns: function () {
        return [
            {
                xtype: 'rownumberer'
            },

            {
                width: 100,
                text: "订单编号",
                dataIndex: 'id',
                sortable: true
            },
            {
                width: 160,
                text: "创建时间",
                dataIndex: 'createdDate',
                renderer: App.Util.dateRender
            }
        ];
    }
});
