/*
 * 门店管理面板
 */

Ext.define('App.view.StorePanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.storepanel',
    requires: ['App.ux.RemoteComboBox'],
    baseUrl: '/stores',
    multiSelect: true,
    window: {
        title: '管理窗口',
        items: [
            {
                fieldLabel: '门店名称',
                name: 'name'
            },
            {
                fieldLabel: '门店类型',
                name: 'storeType.id',
                code: 'storeType',
                xtype: 'ux_remotecombobox',
                allowBlank: false
            },
            {
                fieldLabel: '所属区域',
                name: 'address.area.id',
                code: 'area',
                xtype: 'ux_remotecombobox',
                allowBlank: false
            }
        ]
    },
    queryWindow: {
        title: '查询窗口',
        items: [
            {
                fieldLabel: '门店名称',
                name: 'name'
            },
            {
                fieldLabel: '门店类型',
                name: 'storeType.id'
            }
        ]
    },

    fields: ['id', 'name', 'storeType.id', 'storeType.name', 'address.area.name',
        'address.id', 'address.name', 'createdDate'],

    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            width: 200,
            text: "门店名称",
            dataIndex: 'name',
            sortable: true
        },
        {
            width: 100,
            text: "门店类型",
            dataIndex: 'storeType.name'
        },
        {
            width: 100,
            text: "所属区域",
            dataIndex: 'address.area.name'
        },
        {
            width: 200,
            text: "门店地址",
            dataIndex: 'address.name'
        },
        {
            width: 160,
            text: "创建时间",
            dataIndex: 'createdDate',
            renderer: App.Util.dateRender
        }
    ]
});
