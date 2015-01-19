/*
 * 用户管理面板
 */

Ext.define('App.view.UserPanel', {
    extend: 'App.ux.CrudPanel',
    alias: 'widget.userpanel',
    requires: ['App.ux.RemoteComboBox'],
    entityId: "id",
    baseUrl: '/system/users',
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
                    fieldLabel: '登录名称',
                    name: 'login',
                    allowBlank: false
                },
                {
                    fieldLabel: '用户名',
                    name: 'name',
                    allowBlank: false
                }, {
                    fieldLabel: '登录名称',
                    name: 'password',
                    allowBlank: false
                },
                {
                    fieldLabel: '邮箱',
                    name: 'email',
                    vtype: 'email',
                    allowBlank: false
                },
                {
                    name: 'id',
                    hidden: true
                }
            ]
        };
    },
    getQueryWindow: function () {
        return {
            title: '菜单窗口',
            items: [
                {
                    fieldLabel: '登录名称',
                    name: 'login'
                },
                {
                    fieldLabel: '邮箱',
                    name: 'email'
                }
            ]
        };
    },
    getFields: function () {
        return ['id', 'login', 'firstName', 'email', 'activated', 'createdBy', 'createdDate',
            'lastModifiedBy', 'lastModifiedDate', "roleNames"];
    },

    getColumns: function () {
        return [
            {
                xtype: 'rownumberer'
            },
            {
                text: '授权',
                align: 'center',
                width: 60,
                menuDisabled: true,
                sortable: false,
                xtype: 'actioncolumn',
                width: 50,
                align: 'center',
                items: [
                    {
                        iconCls: 'editIcon',
                        tooltip: '授权',
                        handler: function (grid, rowIndex, colIndex) {
                            var records = grid.getSelectionModel().getSelection();
                            if (Ext.isEmpty(records)) {
                                Ext.Msg.alert("提示", "请先选择要设置菜单权限的用户!");
                                return;
                            }
                            Ext.create('Ext.window.Window', {
                                modal: true,
                                shadow: true,
                                title: '菜单权限设置',
                                items: [Ext.create('App.view.PermissionMenuTreePanel', {
                                    userId: records[0].get('id')
                                })]
                            }).show();

                        }
                    }
                ]
            },
            {
                width: 100,
                text: "登录名称",
                dataIndex: 'login',
                sortable: true
            },
            {
                width: 100,
                text: "用户名",
                dataIndex: 'firstName'
            },
            {
                width: 120,
                text: "邮箱",
                dataIndex: 'email'
            },
            {
                width: 160,
                text: "创建时间",
                dataIndex: 'createdDate',
                renderer: App.Util.dateRender
            },
            {
                width: 160,
                text: "上次登录时间",
                dataIndex: 'lastModifiedDate',
                renderer: App.Util.dateRender

            },
            {
                flex: 100,
                text: "所属角色",
                dataIndex: 'roleNames'
            },
            {
                width: 80,
                text: "是否激活",
                dataIndex: 'activated',
                renderer: App.Util.booleanRender
            }
        ];
    }
});
