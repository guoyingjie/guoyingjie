/**
 * Created by Administrator on 2016/10/20.
 */

(function () {

    $('#alterBtn').click(function () {

        noRepeatClick(this);

        var mypwd = $('.myinput');
        // 获取密码
        var ClassPwd = mypwd.eq(0).val();  // 主账号密码
        var subPwd1 = mypwd.eq(1).val(); // 新子账号密码1
        var subPwd2 = mypwd.eq(2).val(); // 新子账号密码2
        console.log(subPwd1,subPwd2,ClassPwd);

        if (subPwd1.length<6) {
            ModelShow('新密码太短');
            return
        }
        if (!(subPwd1 === subPwd2)) {
            ModelShow('新密码错误');
            return
        }
        // if (!(ClassPwd === '123456')) {
        //     ModelShow('密码错误');
        //     return
        // }
        console.log('修改完成');
        window.location.href = 'account.html'
    });


    // 清空按钮
    $('.input-row i').click(function () {
        var index = $('.input-row i').index(this);
        var input = $('.input-row input').eq(index);
        input.val('');
        input.focus();
    });
})();

// model
function ModelShow(msg) {
    $('#errMsg').text(msg);
    var err = $('#errModel');
    err.fadeIn(1000,function () {
        err.fadeOut(1000);
    });

}

// 防止重复点击
function noRepeatClick(self) {
    self.setAttribute("disabled", true); // 禁止点击
    self.timer = setTimeout(function () {
        self.removeAttribute("disabled");
        clearTimeout(self.timer);
    },1000)
}
