import Mixin from '/static/js/mixin/multipage-route-mixin.js'

Mixin.methods.routeUpdate0 = Mixin.methods.routeUpdate;
Mixin.methods.routeUpdate = function (to, form) {
    let userId = to.params.userId;
    if (userId) {
        this.requestParam.authorId = userId;
    }

    this.routeUpdate0(to, form);
    this.$emit('switch-author', userId);
}

export default Mixin;
