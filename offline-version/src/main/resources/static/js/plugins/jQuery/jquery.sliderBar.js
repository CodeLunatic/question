/***************************************************************************************
 侧边栏插件
 @autor     iProg
 @date      2016-01-25
 @version   1.0

 使用方法：
 在页面建立html标签如下：
 <div class="sliderbar-container">
 <div class="title"><i></i> 通知消息</div>
 <div class="body">
 无消息
 </div>
 </div>

 说明：上面的class属性值，除了sliderbar-container1可以随意更改，其它的如title，body都
 不能更改哦！

 然后加入js代码如下，就可以了:
 <script type="text/javascript">
 $(function(){
    $('.sliderbar-container').sliderBar({
        open : true,
        top : 200,
        width : 360,
        height : 240,
        theme : '#463eee',
        position : 'right'
    });
 });
 </script>
 ****************************************************************************************/
;(function ($) {
    $.fn.extend({
        "sliderBar": function (options) {
            // 使用jQuery.extend 覆盖插件默认参数
            var opts = $.extend(
                {},
                $.fn.sliderBar.defalutPublic,
                options
            );

            // 这里的this 就是 jQuery对象，遍历页面元素对象
            // 加个return可以链式调用
            return this.each(function () {
                //获取当前元素 的this对象 
                var $this = $(this);

                $this.data('open', opts.open);

                privateMethods.initSliderBarCss($this, opts);

                switch (opts.position) {
                    case 'right' :
                        privateMethods.showAtRight($this, opts);
                        break;
                    case 'left'  :
                        privateMethods.showAtLeft($this, opts);
                        break;
                }

            });
        }
    });

    // 默认公有参数
    $.fn.sliderBar.defalutPublic = {
        open: false,           // 默认是否打开，true打开，false关闭
        top: 200,             // 距离顶部多高
        width: 280,           // body内容宽度
        height: 180,          // body内容高度
        theme: 'green',       // 主题颜色
        position: 'right'      // 显示位置，有left和right两种
    }

    var privateMethods = {
        initSliderBarCss: function (obj, opts) {
            obj.css({
                'width': opts.width + 20 + 'px',
                'height': opts.height + 20 + 'px',
                'top': opts.top + 'px',
                'border': '1px solid ' + opts.theme,
                'position': 'fixed',
                'font-family': 'Microsoft Yahei',
                'z-index': '9999'
            }).find('.body').css({
                'width': opts.width + 'px',
                'height': opts.height + 'px',
                'position': 'relative',
                'padding': '10px',
                'overflow-x': 'hidden',
                'overflow-y': 'auto',
                'font-family': 'Microsoft Yahei',
                'font-size': '12px'
            });

            var titleCss = {
                'width': '15px',
                'height': '105px',
                'position': 'absolute',
                'top': '-1px',
                'display': 'block',
                'background-color': opts.theme,
                'font-size': '13px',
                'padding': '8px 4px 0px 5px',
                'color': '#fff',
                'cursor': 'pointer',
                'font-family': 'Microsoft Yahei'
            }

            obj.find('.title').css(titleCss).find('i').css({
                'font-size': '15px'
            });
        },
        showAtLeft: function (obj, opts) {
            if (opts.open) {
                obj.css({left: '0px'});
                obj.find('.title').css('right', '-25px').find('i').attr('class', 'fa fa-chevron-circle-left');

            } else {
                obj.css({left: -opts.width - 22 + 'px'});
                obj.find('.title').css('right', '-25px').find('i').attr('class', 'fa fa-chevron-circle-right');

            }

            obj.find('.title').click(function () {
                if (obj.data('open')) {
                    obj.animate({left: -opts.width - 22 + 'px'}, 500);
                    $(this).find('i').attr('class', 'fa fa-chevron-circle-right');
                } else {
                    obj.animate({left: '0px'}, 500);
                    $(this).find('i').attr('class', 'fa fa-chevron-circle-left');
                }
                obj.data('open', obj.data('open') == true ? false : true);
            });
            obj.find('.title1').mouseout(function () {
                if (obj.data('open')) {
                    obj.animate({left: -opts.width - 22 + 'px'}, 500);
                    $(this).find('i').attr('class', 'fa fa-chevron-circle-right');
                } else {
                    obj.animate({left: '0px'}, 500);
                    $(this).find('i').attr('class', 'fa fa-chevron-circle-left');
                }
                obj.data('open', obj.data('open') == true ? false : true);
            });

        },
        showAtRight: function (obj, opts) {
            if (opts.open) {
                obj.css({right: '0px'});
                obj.find('.title').css('right', opts.width + 20 + 'px').find('i').attr('class', 'fa fa-chevron-circle-right');
            } else {
                obj.css({right: '25px'});
                obj.find('.title').css('right', opts.width + 20 + 'px').find('i').attr('class', 'fa fa-chevron-circle-left');
            }

            obj.find('.title').click(function () {
                if (obj.data('open')) {
                    obj.animate({right: -opts.width - 22 + 'px'}, 500);
                    $(this).find('i').attr('class', 'fa fa-chevron-circle-left');
                } else {
                    obj.animate({right: '0px'}, 500);
                    $(this).find('i').attr('class', 'fa fa-chevron-circle-right');
                }
                obj.data('open', obj.data('open') == true ? false : true);
            });
        }
    };
})(jQuery)
