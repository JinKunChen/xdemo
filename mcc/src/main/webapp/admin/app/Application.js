/**
 * The main application class. An instance of this class is created by app.js when it calls
 * Ext.application(). This is the ideal place to handle application launch and initialization
 * details.
 */
Ext.define('App.Application', {
    extend: 'Ext.app.Application',

    name: 'App',

    uses:['App.SimData', 'Ext.ux.ajax.*'],


    stores: [
        // TODO: add global / shared stores here
    ],

    controllers: [
        'Root'
        // TODO: add controllers here
    ],

    launch: function () {
        // TODO - Launch the application


        //模拟测试数据
        Ext.ux.ajax.SimManager.init().register({
            '/authenticate': {
                type: 'json',
                data: function (ctx) {
                    return Ext.apply({}, true);
                }
            }
        });
    }
});
