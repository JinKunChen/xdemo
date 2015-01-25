Ext.define('App.model.Area', {
    extend: 'App.model.tree.Base',
    alias:'model.area',
    fields: [ {
        name: 'name'
    }, {
        name: 'pid',
        mapping: 'parent.id'
    }, {
        name: 'code'
    }]
});

