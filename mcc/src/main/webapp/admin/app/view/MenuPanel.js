/*
 * 菜单管理面板
 */

Ext.define('App.view.MenuPanel', {
    extend: 'App.ux.CrudPanel',
    requires: ['App.ux.RemoteComboBox'],
    alias: 'widget.menupanel',
    entityId: "id",
    multiSelect: true,
    baseUrl: "/system/menu",
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
                    fieldLabel: '菜单名称',
                    name: 'name',
                    allowBlank: false
                },
                {
                    fieldLabel: '上级菜单-t',
                    name: 'parent',
                    code: 'menu',
                    xtype: 'ux_remotecombobox',
                    allowBlank: false
                },
                {
                    fieldLabel: '上级菜单-tt',
                    name: 'parentddd',
                    code: 'menu11',
                    xtype: 'ux_remotecombobox',
                    allowBlank: false
                },
                {
                    fieldLabel: '上级菜单',
                    name: 'parent.id',
                    code: 'menu',
                    xtype: 'ux_remotecombobox',
                    allowBlank: false
                    /*,
                     refer : {
                     $text : 'ddddd'
                     },
                     whereClause : {
                     $text : '分析'
                     },*/
                },
                {
                    fieldLabel: '基路径',
                    name: 'basePath'
                },
                {
                    fieldLabel: '面板类',
                    name: 'clazz'
                },
                {
                    fieldLabel: '请求地址',
                    name: 'hrefTarget'
                },
                {
                    fieldLabel: '图标CSS类',
                    name: 'iconCls'
                },
                {
                    fieldLabel: '排序号',
                    name: 'sortNo',
                    xtype: 'numberfield',
                    value: 0,
                    maxValue: 99,
                    minValue: 0
                },
                {
                    fieldLabel: '展开状态',
                    name: 'expanded',
                    value: 0
                },
                {
                    fieldLabel: '备注',
                    name: 'remark'
                }
            ]
        };
    },
    getQueryWindow: function () {
        return {
            title: '菜单窗口',
            items: [
                {
                    fieldLabel: '菜单名称',
                    name: 'name',
                    operate: 'like'
                },
                {
                    fieldLabel: '上级菜单',
                    name: 'pid',
                    operate: 'eq'
                },
                {
                    fieldLabel: '请求地址',
                    name: 'hrefTarget',
                    operate: 'like'
                }
            ]
        };
    },
    getFields: function () {
        return ['id', 'parent.id','parent.name', 'name', 'hrefTarget', 'clazz', 'sortNo', 'iconCls', 'expanded', 'leaf',
            'remark'];
    },
    getColumns: function () {
        return [
            {
                xtype: 'rownumberer'
            },
            {
                text: "菜单名称",
                align: 'center',
                width: 120,
                dataIndex: 'name'
            },
            {
                text: "面板类",
                width: 200,
                dataIndex: 'clazz',
                sortable: true
            },
            {
                text: "请求路径",
                flex: 100,
                dataIndex: 'hrefTarget',
                renderer: App.Util.linkRender
            },
            {
                text: "排序号",
                width: 80,
                dataIndex: 'sortNo'
            },
            {
                text: "图标CSS类名",
                width: 120,
                dataIndex: 'iconCls'
            },
            {
                text: "展开状态",
                width: 80,
                dataIndex: 'expanded',
                renderer: App.Util.booleanRender
            },
            {
                text: "是否叶子节点",
                width: 80,
                dataIndex: 'leaf',
                renderer: App.Util.booleanRender
            }
        ];
    }
});
