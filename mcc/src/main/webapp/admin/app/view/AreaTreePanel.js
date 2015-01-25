/*
 * 地区管理面板
 */

Ext.define('App.view.AreaTreePanel', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.areatreepanel',
    requires: [
        'Ext.data.*',
        'Ext.grid.*',
        'Ext.tree.*',
        'App.model.Area'
    ],

    xtype: 'tree-grid',
    closable: true,
    useArrows: true,
    rootVisible: false,
    multiSelect: true,
    singleExpand: true,

    store: new App.store.TreeStore({
        defaultRootId: '0',
        model: Ext.create('App.model.Area'),
        proxy: {
            type: "ajax", // 获取方式
            url: '/system/areas/getTree' // 获取树的地址
        }
    }),

    columns: [
        //{
        //    xtype: 'rownumberer'
        //},
        {
            xtype: 'treecolumn',
            text: '地区名称',
            flex: 2,
            sortable: true,
            dataIndex: 'name'
        },
        {
            text: '地区编码',
            flex: 1,
            sortable: true,
            dataIndex: 'code'
        },
        {
            text: '排序号',
            flex: 1,
            sortable: true,
            dataIndex: 'SN'
        }
    ]
});
