/*
 * 基于Ext.tree.Panel的treePanel扩展
 *
 * Created by Chen on 15/1/18.
 */
Ext.define('App.ux.TreePanel', {
    extend: 'Ext.tree.Panel',
    alias: 'widget.ux_treepanel',
    requires: ['App.store.TreeStore'],
    margins: '0 0 -1 1',
    region: 'west',
    width: 212,
    minSize: 130,
    maxSize: 300,
    split: true,
    rootVisible: false,
    containerScroll: true,
    autoScroll: false,
    listeners: {
        checkchange: function (node, checked) {
            if (!node.isExpanded()) {
                node.expand(true);
                setTimeout(function () {
                    node.set('checked', checked);
                    App.Util.updateCheckStatus(node, checked);
                }, 200);  //等待节点更新
            }
            App.Util.updateCheckStatus(node, checked);
        }
    },

    initComponent: function () {
        this.store = new App.store.TreeStore({
            proxy: {
                type: "ajax", // 获取方式
                url: this.url// 获取树的地址
            }
        });
        this.callParent(arguments);
    },

    getCheckedNodeIds: function () {
        var records = this.getView().getChecked(),
            ids = [];
        Ext.Array.each(records, function (rec) {
            ids.push(rec.get('id'));
        });
        return ids;
    }
});
