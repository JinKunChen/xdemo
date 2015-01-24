/*
 * 订单管理面板
 */

Ext.define('App.view.OrderPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.orderpanel',
    requires: ['Ext.grid.column.Action','App.ux.RemoteComboBox'],
    baseUrl: '/orders',
    selType: 'checkboxmodel',
    window: {
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
    },
    queryWindow: {
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
    },

    fields: ['id', 'price', 'createdDate'],

    columns: [
        {
            xtype: 'rownumberer'
        }, {
            width: 100,
            text: "订单编号",
            dataIndex: 'id',
            sortable: true
        },
        {
            width: 100,
            text: "订单总金额",
            dataIndex: 'totalAmount',
            sortable: true
        },
        {
            width: 160,
            text: "创建时间",
            dataIndex: 'createdDate',
            renderer: App.Util.dateRender
        },
        {
            menuDisabled: true,
            sortable: false,
            xtype: 'actioncolumn',
            width: 50,
            items: [
                {
                    text: "查看",
                    iconCls: 'sell-col',
                    tooltip: 'Sell stock',
                    handler: function (grid, rowIndex, colIndex) {
                        alert("ss");
                    }
                }
                //,{
                //    text: "查看",
                //    tooltip: '查看订单明细',
                //    handler: function (grid, rowIndex, colIndex) {
                //        var records = grid.getSelectionModel().getSelection();
                //        if (Ext.isEmpty(records)) {
                //            Ext.Msg.alert("提示", "请先选择要查看的订单!");
                //            return;
                //        }
                //        Ext.create('App.view.OrderDetailWindow', {
                //            orderId: records[0].get('id')
                //        }).show();
                //
                //    }
                //}
            ]
        }
    ]
});


/**
 * 订单明细面板
 */
Ext.define('App.view.OrderDetailPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.orderdetailpanel',
    baseUrl: '/orderItems',
    fields: ['id', 'product.name', 'product.price', 'amount', 'subtotal'],
    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            width: 100,
            text: "商品名称",
            dataIndex: 'product.name',
            sortable: true
        },
        {
            width: 100,
            text: "价格",
            dataIndex: 'product.price',
            sortable: true
        },
        {
            width: 100,
            text: "数量",
            dataIndex: 'amount',
            sortable: true
        },
        {
            width: 160,
            text: "小计",
            dataIndex: 'subtotal'
        }
    ]
});

/**
 * 订单明细窗体
 */
Ext.define('App.view.OrderDetailWindow', {
    extend: 'Ext.window.Window',
    alias: 'widget.orderdetailwindow',
    initComponent: function (orderId) {
        // 处理按钮
        this.items = [{
            xtype: 'orderdetailpanel',
            orderId: orderId
        }];
        this.callParent(arguments);
    }
});
