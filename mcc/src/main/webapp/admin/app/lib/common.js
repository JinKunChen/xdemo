Ext.ns("App");
/**
 * 配置类
 */
App.Config = {
    Action: {
        save: '/save',
        remove: '/delete',
        query: '/query',
        export: '/exportExcel',
        import: '/importExcel'
    },
    Search: {
        searchPrefix: 'q',
        operates: ['eq', 'ne', 'gt', 'gte', 'lt', 'gt', 'prefixLike', 'suffixLike',
            'prefixNotLike', 'suffixNotLike', 'like', 'notLike', 'isNull', 'isNotNull', 'in', 'notIn'],

        getValues: function (form) {
            //兼容JPA:searchable查询
            var values = form.getValues(),
                fields = form.getFields().items,
                fLen = fields.length,
                newValues = {},
                field, f, fieldName, fieldValue;

            for (f = 0; f < fLen; f++) {
                field = fields[f];
                fieldName = field.name;
                fieldValue = values[fieldName];
                //过滤空值
                if (!Ext.isEmpty(fieldValue)) {
                    if (field.hasOwnProperty('operate') && Ext.Array.contains(this.operates, field['operate'])) {
                        newValues[this.toSearchName(fieldName, field['operate'])] = fieldValue;
                    } else {
                        newValues[this.toSearchName(fieldName, 'eq')] = values[fieldName];
                    }
                }
            }
            return newValues;
        },
        toSearchName: function (name, operate) {
            operate = name.indexOf('__') ? '' : operate; //‘__’代表:多条件查询（包含__AND__或__OR__）
            return this.searchPrefix + '.' + name + '_' + operate;
        }
    },
    searchOperates: ['eq', 'ne', 'gt', 'gte', 'lt', 'gt', 'prefixLike', 'suffixLike',
        'prefixNotLike', 'suffixNotLike', 'like', 'notLike', 'isNull', 'isNotNull', 'in', 'notIn'],
    comboUrl: 'system/queryComboData'
};

Ext.Ajax.on('requestcomplete', function (conn, response, options, eOpts) {
    if (response.responseText == "{\"redirect\":\"login\"}") {
        alert("1");
        window.location.href = "login.?backurl=" + window.location.href;
    }
}, this);

App.common = function () {
    var msgCt;

    function createBox(t, s) {
        // return ['<div class="msg">',
        //         '<div class="x-box-tl"><div class="x-box-tr"><div class="x-box-tc"></div></div></div>',
        //         '<div class="x-box-ml"><div class="x-box-mr"><div class="x-box-mc"><h3>', t, '</h3>', s, '</div></div></div>',
        //         '<div class="x-box-bl"><div class="x-box-br"><div class="x-box-bc"></div></div></div>',
        //         '</div>'].join('');
        return '<div class="msg"><h3>' + t + '</h3><p>' + s + '</p></div>';
    }

    return {
        msg: function (title, format) {
            if (!msgCt) {
                msgCt = Ext.DomHelper.insertFirst(document.body, {id: 'msg-div'}, true);
            }
            var s = Ext.String.format.apply(String, Array.prototype.slice.call(arguments, 1));
            var m = Ext.DomHelper.append(msgCt, createBox(title, s), true);
            m.hide();
            m.slideIn('t').ghost("t", {delay: 1000, remove: true});
        },

        init: function () {
            if (!msgCt) {
                // It's better to create the msg-div here in order to avoid re-layouts
                // later that could interfere with the HtmlEditor and reset its iFrame.
                msgCt = Ext.DomHelper.insertFirst(document.body, {id: 'msg-div'}, true);
            }
//            var t = Ext.get('exttheme');
//            if(!t){ // run locally?
//                return;
//            }
//            var theme = Cookies.get('exttheme') || 'aero';
//            if(theme){
//                t.dom.value = theme;
//                Ext.getBody().addClass('x-'+theme);
//            }
//            t.on('change', function(){
//                Cookies.set('exttheme', t.getValue());
//                setTimeout(function(){
//                    window.location.reload();
//                }, 250);
//            });
//
//            var lb = Ext.get('lib-bar');
//            if(lb){
//                lb.show();
//            }
        }
    };
}();
/**
 * 工具类
 */
App.Util = {
    booleanRender: function (value, p, record) {
        return value
            ? "<span style=\"color:green;\">是</span>"
            : "<span style=\"color:red;\">否</span>";
    },
    //dateRender: function (format) {
    //    format = format || "Y-m-d G:i";
    //    return Ext.util.Format.dateRenderer(format);
    //},
    dateRender: function (value) {
        return Ext.util.Format.dateRenderer(new Date(value), "Y-m-d G:i");
    },
    secondDateRender: function (value) {
        var date = new Date(value * 1000);
        return Ext.util.Format.date(date, "Y-m-d G:i");
    },
    objectRender: function (p, backgroundColor) {
        return function (v, meta) {
            if (backgroundColor)
                meta.attr = 'style="background-color:' + backgroundColor + ';"';
            var s = "";
            try {
                s = v ? eval("v." + p) : "";
            } catch (e) {
            }
            return s;
        };
    },
    linkRender: function (v) {
        if (!v)
            return "";
        else
            return Ext.String
                .format("<a href='{0}' target='_blank'>{0}</a>", v);
    },
    comboxRender: function (v) {
        if (v) {
            return v.text || v.title || v;
        }
    },
    typesRender: function (types) {
        return function (v) {
            for (var i = 0; i < types.length; i++) {
                try {

                    if (types[i][1] === v)
                        return types[i][0];
                } catch (e) {
                    alert(types);
                }
            }
            return "";
        };
    },
    moneyRender: function (v) {
        if (v) {
            if (v.toFixed) {
                if (v < 0)
                    return "<font color=red>" + v.toFixed(2) + "<font>";
                else
                    return v.toFixed(2);
            } else
                return v;
        }
    },
    updateCheckStatus: function (parentNode, checked) {
        Ext.each(parentNode.childNodes, function (childNode, index, allItems) {
            //Ext.Msg.alert('节点信息',node.get('text')+';index=' + index);
            childNode.set('checked', checked);
            App.Util.updateCheckStatus(childNode, checked);
        });
    }
};

//自定义VType类型，验证日期选择范围
Ext.apply(Ext.form.field.VTypes, {
    //验证方法
    dateRange: function (val, field) {
        var beginDate = null,//开始日期
            beginDateCmp = null,//开始日期组件
            endDate = null,//结束日期
            endDateCmp = null,//结束日期组件
            validStatus = true;//验证状态
        if (field.dateRange) {
            //获取开始时间
            if (!Ext.isEmpty(field.dateRange.begin)) {
                beginDateCmp = Ext.getCmp(field.dateRange.begin);
                beginDate = beginDateCmp.getValue();
            }
            //获取结束时间
            if (!Ext.isEmpty(field.dateRange.end)) {
                endDateCmp = Ext.getCmp(field.dateRange.end);
                endDate = endDateCmp.getValue();
            }
        }
        //如果开始日期或结束日期有一个为空则校验通过
        if (!Ext.isEmpty(beginDate) && !Ext.isEmpty(endDate)) {
            validStatus = beginDate <= endDate;
        }

        return validStatus;
    },
    //验证提示信息
    dateRangeText: '开始日期不能大于结束日期，请重新选择。'
});

//自定义VType类型，数字
Ext.apply(Ext.form.field.VTypes, {
    'numeric': function () {
        var numericRe = /(^-?\d\d*\.\d*$)|(^-?\d\d*$)|(^-?\.\d\d*$)/;
        return function (v) {
            return numericRe.test(v);
        }
    }()
    , 'numericText': 'Not a valid numeric number. Must be numbers'
    , 'numericMask': /[.0-9]/
});
/**
 * @class App.util.Downloader
 * @singleton
 *
 * @example
 * <pre>
 * App.util.Downloader.get({
 *      url: 'http://example.com/download?filename=test.txt',
 *      params: {
 *          param1: 'value1'
 *      }
 * });
 * </pre>
 *
 */
Ext.define('App.util.Downloader', {

    /**
     * Singleton class
     * @type {Boolean}
     */
    singleton: true,

    downloadFrame: null,

    downloadForm: null,

    /**
     * Get/Download from url
     * @param config
     */
    get: function (config) {
        var me = this,
            body = Ext.getBody();
        config = config || {};

        /**
         * Support for String config as url
         */
        if (Ext.isString(config)) {
            config = {
                url: config
            };
        }


        me.downloadFrame = body.createChild({
            tag: 'iframe',
            cls: 'x-hidden',
            id: 'app-upload-frame',
            name: 'uploadframe'
        });

        me.downloadForm = body.createChild({
            tag: 'form',
            cls: 'x-hidden',
            id: 'app-upload-form',
            target: config.target || 'app-upload-frame'
        });


        Ext.Ajax.request({
            url: config.url || '.',
            params: config.params || {},
            form: me.downloadForm,
            isUpload: true,
            scope: me,
            success: me.handleException,
            failure: me.handleException
        });
    },

    handleException: function (response, options) {
        var me = this,
            result = Ext.decode(response.responseText, true);

        if (result) {
            Ext.Msg.alert('Message', result['message']);
        } else {
            Ext.Msg.alert('Message', ' An unknown Error occurred while downloading.');
        }
    }

});

String.prototype.endsWith = function (suffix) {
    return this.indexOf(suffix, this.length - suffix.length) !== -1;
};
