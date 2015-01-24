/*
 * 供应商管理面板
 */

Ext.define('App.view.SupplierPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.supplierpanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: '/suppliers',
    multiSelect: true,
    window: {
        title: '管理窗口',
        items: [
            {
                fieldLabel: '供应商名称',
                name: 'name'
            },
            {
                fieldLabel: '联系人',
                name: 'linkman'
            },
            {
                fieldLabel: '联系电话',
                name: 'phone'
            },
            {
                fieldLabel: '地址',
                name: 'address'
            }
        ]
    },

    fields: ['id', 'name','linkman', 'phone', 'address', 'createdDate'],

    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            width: 200,
            text: "供应商名称",
            dataIndex: 'name',
            sortable: true
        },
        {
            width: 120,
            text: "联系人",
            dataIndex: 'linkman'
        },
        {
            width: 160,
            text: "联系电话",
            dataIndex: 'phone'
        },
        {
            width: 200,
            text: "门店地址",
            dataIndex: 'address'
        },
        {
            width: 160,
            text: "创建时间",
            dataIndex: 'createdDate',
            renderer: App.Util.dateRender
        }
    ]
});
