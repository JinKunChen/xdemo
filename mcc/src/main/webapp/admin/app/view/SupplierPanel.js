/*
 * 供应商管理面板
 */

Ext.define('App.view.SupplierPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.supplierpanel',
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
                fieldLabel: '公司名称',
                name: 'company'
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
                fieldLabel: '所属区域',
                name: 'area.id',
                code: 'area',
                xtype: 'ux_remotecombobox',
                allowBlank: false
            },
            {
                fieldLabel: '门店地址',
                name: 'address'
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

    fields: ['id', 'name', 'storeType.id', 'storeType.name', 'company', 'linkman', 'phone',
        'areaId.id', 'areaId.name', 'address', 'createdDate'],

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
            width: 160,
            text: "公司名称",
            dataIndex: 'company'
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
            width: 100,
            text: "所属区域",
            dataIndex: 'area.name'
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
