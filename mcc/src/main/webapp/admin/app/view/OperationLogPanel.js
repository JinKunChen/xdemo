/*
 * 操作日志管理面板
 */

Ext.define('App.view.OperationLogPanel', {
    alias: 'widget.logpanel',
    extend: 'App.ux.CrudPanel',
    baseUrl: "/operationLogs",
    fields: ['id', 'name', 'operator', 'detail', 'operation', 'createdDate'],
    columns: [
        {
            xtype: 'rownumberer'
        },
        {
            text: "日志名称",
            width: 200,
            dataIndex: 'name'
        },
        {
            text: "操作人",
            width: 80,
            dataIndex: 'operator'
        },
        {
            text: "日志明细",
            flex: 1,
            dataIndex: 'detail'
        },
        {
            text: "操作类型",
            width: 80,
            dataIndex: 'operation'
        },
        {
            width: 160,
            text: "创建时间",
            dataIndex: 'createdDate',
            renderer: App.Util.dateRender
        }
    ]
});
