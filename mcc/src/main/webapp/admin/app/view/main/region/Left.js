Ext.define('App.view.main.region.Left', {
        extend: 'App.ux.TreePanel',
        alias: 'widget.main-left',
        title: '系统菜单',
        iconCls: 'book_previousIcon',
        collapsible: true,
        enableDD: false,
        url: "/system/menus/getTree", // 获取菜单树的地址
        tools: [{
            type: 'refresh',
            tooltip: '刷新',
            handler: function (event, toolEl, panel) {
                console.dir(panel.up('main-left').store);
                panel.up('main-left').store.reload();
            }
        }],
        initComponent: function () {
            this.callParent(arguments);
        }
    }
);
