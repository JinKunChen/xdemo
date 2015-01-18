/**
 * This class is the view model for the Main view of the application.
 */
Ext.define('App.view.main.MainModel', {
    extend: 'Ext.app.ViewModel',

    alias: 'viewmodel.main',

    data: {
        name: 'MCC运营后台',
        // 左边菜单的加载
        NavigationMenu: [{
            text: '权限管理',
            expanded: true,
            items: [{
                text: '功能菜单',
                module: 'MoudelMenu',
                glyph: 0xf00b
            }, {
                text: '角色权限',
                module: 'RoleAuthority',
                glyph: 0xf1a2
            }, {
                text: '人员角色',
                module: 'UserROle',
                glyph: 0xf183
            }, {
                text: '人员信息',
                module: 'UserInfo',
                glyph: 0xf007
            }]
        }, {
            text: '收案立案', // 菜单项的名称
            icon: '', // 菜单顶的图标地址
            glyph: 0,// 菜单项的图标字体的数值
            expanded: true, // 在树形菜单中是否展开
            description: '', // 菜单项的描述
            items: [{
                text: '收案登记', // 菜单条的名称
                module: 'Global', // 对应模块的名称
                icon: '', // 菜单条的图标地址
                glyph: 0xf0f7// 菜单条的图标字体
            }, {
                text: '系列案管理',
                module: 'Project',
                icon: '',
                glyph: 0xf02e
            }]
        },
            {
                text: '车辆管理',
                expanded: true,
                items: [{
                    text: '用车申请',
                    module: 'CarApply',
                    glyph: 0xf0d1
                }, {
                    text: '用车审批',
                    module: 'CarApproval',
                    glyph: 0xf090
                }, {
                    text: '车辆常识',
                    module: 'CarCommon',
                    glyph: 0xf1ba
                }, {
                    text: '规章制度',
                    module: 'CarRegulations',
                    glyph: 0xf0ca
                }, {
                    text: '司机信息',
                    module: 'DriverInfo',
                    glyph: 0xf0f0
                }, {
                    text: '车辆实时信息',
                    module: 'CarRealTimeInfo',
                    glyph: 0xf1b9
                }, {
                    text: '车辆维护信息',
                    module: 'CarMaintainInfo',
                    glyph: 0xf0f5
                }, {
                    text: '量化积分信息',
                    module: 'QuantitativeIntegral',
                    glyph: 0xf163
                }, {
                    text: 'GPS轨迹信息',
                    module: 'GPSTrajectory',
                    glyph: 0xf041
                }, {
                    text: '其它费用信息',
                    module: 'OtherFees',
                    glyph: 0xf157
                }]
            }

        ]
    }

    //TODO - add data, formulas and/or methods to support your view
});

