//存放交互逻辑
//javascript模块化
var seckill = {
    //封装秒杀相关ajax的url
    URL: {
        now: function () {
            return '/seckill/time/now';
        },
        exposer:function (secId) {
            return '/seckill/' + secId +'/exposer';
        },
        execution:function (secId, md5) {
          return  '/seckill/'+ secId +'/'+ md5 + '/execute';
        }
    },
    handleSeckill: function(secId, node){
        //获取秒杀地址，控制显示逻辑，执行秒杀
        console.log("进入了秒杀函数...");
        node.hide().html('<button class="btn btn-primary btn-lg" id="killBtn">开始秒杀</button>');
        var exposerUrl = seckill.URL.exposer(secId);
        console.log("exposerUrl...:"+exposerUrl);
        $.post(exposerUrl,{},function (result) {
            if(result && result['success']){
                // console.log("?????所以我进来了吗？");
                var exposer = result['data'];
                console.log("exposer:"+exposer['exposed']);
                if(exposer['exposed']){
                    //开启秒杀
                    //获取地址
                    var md5 = exposer['md5'];
                    var killUrl = seckill.URL.execution(secId, md5);
                    console.log('killUrl:'+ killUrl);
                    //绑定一次点击事件
                    $('#killBtn').one('click',function () {
                       //执行秒杀请求
                        //1. 禁用按钮
                        $(this).addClass('disabled');
                       //2.发送秒杀请求,执行秒杀
                        $.post(killUrl,{},function (result) {
                            if(result && result['success']){
                               //3.显示秒杀结果
                                var seckillExecution = result['data'];
                                var state = seckillExecution['state'];
                                var stateInfo = seckillExecution['stateInfo'];
                                if(state == 1)
                                    node.html('<span class="label label-success">'+stateInfo+'</span>');
                                else
                                    node.html('<span class="label label-danger">'+stateInfo+'</span>');
                           }else{
                                console.error("秒杀请求不响应");
                           }
                        });
                    });
                    node.show(300);
                }else{
                    //未开启秒杀
                   var now = exposer['now'];
                   var start = exposer['start'];
                   var end = exposer['end'];
                   //重新计算计时逻辑
                   seckill._countdown(now,start,end);
                }
            }else{
                console.log('result:'+result);
            }
        });
    },
    //验证手机号
    validatePhone: function(phone){
        if (phone && phone.length == 11 && !isNaN(phone)) {
            return true;
        } else {
            return false;
        }
    },
    _countdown: function(secId, nowTime, startTime, endTime){
        var seckillBox = $("#seckill-box");
        //时间判断
        if(nowTime > endTime){
            //秒杀结束
            seckillBox.html("秒杀结束");
        }else if(nowTime < startTime){
            //秒杀未开始
            //计时时间绑定
            var killTime = new Date(startTime + 1000);//防止用户时间偏移
            seckillBox.countdown(killTime,function (event) {
               var format = event.strftime('秒杀倒计时： %D天 %H时 %M分 %S秒');
               seckillBox.html(format);
               /*时间完成后的回调*/
            }).on('finish.countdown',function(){
                seckill.handleSeckill(secId, seckillBox);
            });
        }else{
            //秒杀开始
            seckill.handleSeckill(secId, seckillBox);
        }
    },
    //详情页秒杀逻辑
    detail: {
        //详情页初始化
        init: function (params) {
            //手机验证和登录，计时交互
            //规划交互流程
            //在cookie中查找手机号
            var userPhone = $.cookie('userPhone');
            console.log("userPhone:" + userPhone);
           //验证手机号
            if(!seckill.validatePhone(userPhone)){
                //绑定phone
                var userPhoneModal = $('#userPhoneModal');
                //显示弹出层
               userPhoneModal.modal({
                   show : true,//显示弹出层
                   backdrop: 'static',//简直位置关闭
                   keyboard: false
               });
               $('#userPhoneBtn').click(function () {
                   var inputPhone = $('#userPhoneKey').val();
                   console.log('inputPhone='+inputPhone);//TODO
                   if(seckill.validatePhone(inputPhone)){
                       //写入cookie
                       $.cookie('userPhone',inputPhone,{expires:7, path:'/seckill'});
                       //刷新界面
                       window.location.reload();
                   }else{
                       $('#userPhoneMessage').hide().html('<label class="label label-danger">手机号错误!</label>').show(300);
                   }
               });
            }
            //已经登录
            //计时交互
            var startTime = params['startTime'];
            var endTime = params['endTime'];
            var secId = params['secId'];
            $.get(seckill.URL.now(),{},function (result) {
                if(result && result['success']){
                    var nowTime = result['data'];
                    //时间判断,计时交互
                    // console.log("secId:" + secId);
                    seckill._countdown(secId, nowTime, startTime, endTime);
                }else{
                    console.log('result:'+result);
                }
            });
        }
    }
}