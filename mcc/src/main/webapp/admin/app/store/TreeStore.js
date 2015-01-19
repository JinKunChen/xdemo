/**
 * Created by Chen on 15/1/18.
 */
Ext.define('App.store.TreeStore', {
    extend: 'Ext.data.TreeStore',
    alias: 'widget.ux_treestore',
    defaultRootId: '1',
    autoLoad: false,
    root: {
        expanded: true
    },
    nodeParam: "id",
    fields: [
        {
            name: 'text',
            mapping: 'name'
        }, {
            name: 'pid',
            mapping: 'parent.id'
        },
        {
            name: 'clazz',
            type: 'string'
        }
    ]
});
